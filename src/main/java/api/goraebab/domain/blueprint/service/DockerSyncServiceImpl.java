package api.goraebab.domain.blueprint.service;

import static api.goraebab.global.util.ConnectionUtil.testDockerPing;

import api.goraebab.domain.blueprint.dockerObject.CustomConfig;
import api.goraebab.domain.blueprint.dockerObject.CustomContainer;
import api.goraebab.domain.blueprint.dockerObject.CustomHost;
import api.goraebab.domain.blueprint.dockerObject.CustomNetwork;
import api.goraebab.domain.blueprint.dockerObject.CustomVolume;
import api.goraebab.domain.blueprint.dockerObject.ProcessedData;
import api.goraebab.global.exception.CustomException;
import api.goraebab.global.exception.ErrorCode;
import api.goraebab.global.util.DockerClientUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.api.model.Network.Ipam;
import com.github.dockerjava.api.model.Network.Ipam.Config;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link DockerSyncService} interface.
 *
 * <p>This class uses DockerClient to manage Docker resources locally or remotely. It performs
 * operations such as:
 *
 * <ul>
 *   <li>Removing existing Docker resources (containers, networks, volumes).
 *   <li>Synchronizing networks, volumes, and containers with the provided blueprint data.
 *   <li>Managing Docker images and ensuring the correct state of containers.
 * </ul>
 *
 * @author whitem4rk
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DockerSyncServiceImpl implements DockerSyncService {

  private final DockerClientUtil dockerClientFactory;
  private static final String CONTAINER_NAME_KEY = "containerName";
  private static final String READ_ONLY_MODE = "ro";
  private static final String CONTAINER_RESULT_STATUS_KEY = "status";
  private static final String CONTAINER_RESULT_MESSAGE_KEY = "message";
  private static final String CONTAINER_STATUS_SUCCESS = "success";
  private static final String CONTAINER_STATUS_FAILED = "failed";
  private static final String CONTAINER_START_SUCCESS_MESSAGE = "Container started successfully.";

  private static final String LOCAL_HOST_IP = "host.docker.internal";
  private static final int DOCKER_DAEMON_PORT = 2375;
  public static final Set<String> EXCLUDED_CONTAINER_NAME =
      new HashSet<>(
          Arrays.asList(
              "goraebab_next",
              "/goraebab_spring",
              "/goraebab_mysql",
              "/goraebab_mariadb",
              "/goraebab_postgresql",
              "/goraebab_oracle"));
  private static final Set<String> EXCLUDED_NETWORK_SET =
      new HashSet<>(Arrays.asList("bridge", "host", "none", "goraebab_network"));
  private static final String MOUNT_BIND_TYPE = "bind";
  private static final String MOUNT_VOLUME_TYPE = "volume";
  private static final String CONTAINER_RUNNING_STATE = "running";
  private static final String DEFAULT_BRIDGE_NETWORK_SUBNET = "172.17.0.0/16";
  private static final String DEFAULT_BRIDGE_NETWORK_NAME = "bridge";

  /**
   * Synchronizes Docker resources (networks, volumes, and containers) with the provided blueprint
   * data.
   *
   * <p>This method performs the following operations for each host in the blueprint data:
   *
   * <ol>
   *   <li>Attempts to establish a connection with the Docker daemon on the local or remote host and
   *       verifies it via a ping request.
   *   <li>Removes all non-default Docker resources (networks, containers, volumes) from the host.
   *   <li>Synchronizes networks, ensuring that any required custom networks are created.
   *   <li>Synchronizes volumes by creating any new volumes specified in the blueprint data.
   *   <li>Synchronizes containers by pulling necessary images (if not already available),
   *       configuring, and starting the containers in the target network.
   * </ol>
   *
   * <p>Each operation produces results that are returned as a list of status maps, with each map
   * providing details about the status (e.g., success or failure) of container synchronization.
   *
   * @param processedData the blueprint data containing configuration for networks, volumes, and
   *     containers
   * @return a list of maps containing the results of the synchronization for each container. Each
   *     map includes the container name, status (success or failure), and any associated messages.
   * @throws CustomException if any Docker-related error occurs during synchronization
   */
  @Override
  public List<Map<String, Object>> syncDockerWithBlueprintData(ProcessedData processedData) {

    List<Map<String, Object>> containerResults = new ArrayList<>();

    try {
      DockerClient dockerClient;
      List<CustomHost> customHosts = processedData.getCustomHost();

      for (CustomHost customHost : customHosts) {
        if (!customHost.getIsRemote()) {
          testDockerPing(LOCAL_HOST_IP, DOCKER_DAEMON_PORT);
          dockerClient = dockerClientFactory.createLocalDockerClient();
        } else {
          testDockerPing(customHost.getIp(), DOCKER_DAEMON_PORT);
          dockerClient = dockerClientFactory.createRemoteDockerClient(customHost.getIp());
        }

        removeAllContainers(dockerClient);
        removeAllNetworks(dockerClient);
        removeAllVolumes(dockerClient);

        syncNetworks(dockerClient, customHost.getCustomNetwork());
        syncVolumes(dockerClient, customHost.getCustomVolume());
        List<Map<String, Object>> syncResult =
            syncContainers(dockerClient, customHost.getCustomNetwork());
        containerResults.addAll(syncResult);

        log.debug(dockerClient.toString());
      }
    } catch (DockerException e) {
      throw new CustomException(ErrorCode.DOCKER_SYNC_FAILED, e);
    } catch (InterruptedException e) {
      throw new CustomException(ErrorCode.CONTAINER_SYNC_FAILED, e);
    }

    return containerResults;
  }

  /**
   * Check whether input networks settings are valid considering NAME, SUBNET, IP
   *
   * @param customNetworkList network list to be executed
   * @throws CustomException if network settings are invalid
   */
  private void customNetworkValidationCheck(List<CustomNetwork> customNetworkList) {
    for (CustomNetwork customNetwork : customNetworkList) {
      for (CustomConfig config : customNetwork.getCustomIpam().getCustomConfig()) {
        if (customNetwork.getName().equals(DEFAULT_BRIDGE_NETWORK_NAME)
            && !config.getSubnet().equals(DEFAULT_BRIDGE_NETWORK_SUBNET)) {
          throw new CustomException(ErrorCode.NETWORK_CREATION_FAILED);
        }
      }
    }
  }

  /**
   * Existing networks are left unchanged, and only newly added networks are created.
   *
   * @param dockerClient connection with Docker daemon
   * @param customNetworkList network list to be executed
   * @throws DockerException if any Docker-related operation fails
   */
  private void syncNetworks(DockerClient dockerClient, List<CustomNetwork> customNetworkList)
      throws DockerException {

    customNetworkValidationCheck(customNetworkList);
    List<Network> existingNetworks = dockerClient.listNetworksCmd().exec();

    for (CustomNetwork customNetwork : customNetworkList) {
      String customNetworkName = customNetwork.getName();
      boolean networkExists =
          existingNetworks.stream()
              .anyMatch(existingNetwork -> existingNetwork.getName().equals(customNetworkName));

      if (!networkExists) {
        List<Config> ipamConfigList = new ArrayList<>();

        for (CustomConfig config : customNetwork.getCustomIpam().getCustomConfig()) {
          Config ipamConfig = new Config();
          ipamConfig.withSubnet(config.getSubnet());
          ipamConfigList.add(ipamConfig);
        }

        dockerClient
            .createNetworkCmd()
            .withName(customNetworkName)
            .withDriver(customNetwork.getDriver())
            .withIpam(new Ipam().withConfig(ipamConfigList))
            .exec();
      }
    }
  }

  /**
   * Existing volumes are left unchanged, and only newly added volumes are created.
   *
   * @param dockerClient connection with Docker daemon
   * @param customVolumeList volume list to be executed
   * @throws DockerException if any Docker-related operation fails
   */
  private void syncVolumes(DockerClient dockerClient, List<CustomVolume> customVolumeList)
      throws DockerException {

    List<InspectVolumeResponse> existingVolumes = dockerClient.listVolumesCmd().exec().getVolumes();

    for (CustomVolume customVolume : customVolumeList) {
      String volumeName = customVolume.getName();

      boolean volumeExists =
          existingVolumes.stream()
              .anyMatch(existingVolume -> existingVolume.getName().equals(volumeName));

      if (!volumeExists) {
        dockerClient
            .createVolumeCmd()
            .withName(volumeName)
            .withDriver(customVolume.getDriver())
            .exec();
      }
    }
  }

  /**
   * Creates containers by performing the following steps:
   *
   * <ol>
   *   <li>Check if the required Docker image exists on the host.
   *   <li>If the image does not exist, pull the image from Docker Hub.
   *   <li>Set up ports, volumes, and mounts, and then create the containers.
   * </ol>
   *
   * <p>This method ensures that each container is properly configured before starting. If any step
   * fails, an appropriate exception is thrown.
   *
   * @param dockerClient the connection to the Docker daemon used to manage containers
   * @param customNetworkList the list of networks containing container configurations to be
   *     executed
   * @return a list of maps containing the results of the container creation process, including the
   *     container name, status, and any messages.
   * @throws DockerException if any Docker-related operation fails
   * @throws InterruptedException if pulling images is interrupted or delayed
   */
  private List<Map<String, Object>> syncContainers(
      DockerClient dockerClient, List<CustomNetwork> customNetworkList)
      throws DockerException, InterruptedException {

    List<Map<String, Object>> containerResults = new ArrayList<>();

    for (CustomNetwork customNetwork : customNetworkList) {
      for (CustomContainer customContainer : customNetwork.getCustomContainers()) {
        String containerName = customContainer.getContainerName();
        Map<String, Object> containerResult = new HashMap<>();
        containerResult.put(CONTAINER_NAME_KEY, containerName);

        try {
          String imageName = customContainer.getCustomImage().getName();
          String tag = customContainer.getCustomImage().getTag();

          try {
            dockerClient.inspectImageCmd(imageName).exec();
          } catch (NotFoundException e) {
            dockerClient.pullImageCmd(imageName).withTag(tag).start().awaitCompletion();
          }

          List<PortBinding> portBindings =
              customContainer.getCustomPorts().stream()
                  .map(
                      customPort ->
                          PortBinding.parse(
                              customPort.getPublicPort() + ":" + customPort.getPrivatePort()))
                  .collect(Collectors.toList());

          List<Bind> binds = new ArrayList<>();
          List<Mount> mounts = new ArrayList<>();

          customContainer
              .getCustomMounts()
              .forEach(
                  customMount -> {
                    if (MOUNT_BIND_TYPE.equals(customMount.getType())) {
                      binds.add(
                          new Bind(
                              customMount.getSource(), new Volume(customMount.getDestination())));
                    } else if (MOUNT_VOLUME_TYPE.equals(customMount.getType())) {
                      Mount mount =
                          new Mount()
                              .withType(MountType.VOLUME)
                              .withSource(customMount.getName())
                              .withTarget(customMount.getDestination())
                              .withReadOnly(READ_ONLY_MODE.equals(customMount.getMode()));

                      mounts.add(mount);
                    }
                  });

          HostConfig hostConfig =
              HostConfig.newHostConfig()
                  .withPortBindings(portBindings)
                  .withBinds(binds)
                  .withMounts(mounts);

          ContainerNetwork containerNetwork =
              new ContainerNetwork()
                  .withIpamConfig(new ContainerNetwork.Ipam())
                  .withIpv4Address(customContainer.getCustomNetworkSettings().getIpAddress());

          CreateContainerResponse containerResponse =
              dockerClient
                  .createContainerCmd(imageName)
                  .withName(containerName)
                  .withHostConfig(hostConfig)
                  .withEnv(customContainer.getCustomEnv())
                  .withCmd(customContainer.getCustomCmd())
                  .exec();

          dockerClient
              .connectToNetworkCmd()
              .withContainerId(containerResponse.getId())
              .withNetworkId(customNetwork.getName())
              .withContainerNetwork(containerNetwork)
              .exec();

          dockerClient.startContainerCmd(containerResponse.getId()).exec();

          containerResult.put(CONTAINER_RESULT_STATUS_KEY, CONTAINER_STATUS_SUCCESS);
          containerResult.put(CONTAINER_RESULT_MESSAGE_KEY, CONTAINER_START_SUCCESS_MESSAGE);

        } catch (Exception e) {
          containerResult.put(CONTAINER_RESULT_STATUS_KEY, CONTAINER_STATUS_FAILED);
          containerResult.put(CONTAINER_RESULT_MESSAGE_KEY, e.getMessage());
        }

        containerResults.add(containerResult);
      }
    }

    return containerResults;
  }

  /**
   * Remove all containers except Docker default containers and Goraebab custom containers
   *
   * @param dockerClient connection with Docker daemon
   * @throws DockerException if any Docker-related operation fails
   */
  private void removeAllContainers(DockerClient dockerClient) throws DockerException {
    List<Container> containerList = dockerClient.listContainersCmd().withShowAll(true).exec();

    for (Container container : containerList) {
      String[] containerNames = container.getNames();
      boolean isContain = false;

      for (String name : containerNames) {
        if (EXCLUDED_CONTAINER_NAME.contains(name)) {
          isContain = true;
          break;
        }
      }

      if (!isContain) {
        if (container.getState().equals(CONTAINER_RUNNING_STATE)) {
          dockerClient.stopContainerCmd(container.getId()).exec();
        }
        dockerClient.removeContainerCmd(container.getId()).exec();
      }
    }
  }

  /**
   * Remove all networks except Docker default networks and Goraebab custom network
   *
   * @param dockerClient connection with Docker daemon
   * @throws DockerException if any Docker-related operation fails
   */
  private void removeAllNetworks(DockerClient dockerClient) throws DockerException {
    List<Network> networkList = dockerClient.listNetworksCmd().exec();

    for (Network network : networkList) {
      String networkName = network.getName();
      if (!EXCLUDED_NETWORK_SET.contains(networkName)) {
        dockerClient.removeNetworkCmd(network.getId()).exec();
      }
    }
  }

  /**
   * Retrieve all containers in execution and check whether volume is used by containers and remove
   * all volumes except volumes not in use
   *
   * @param dockerClient connection with Docker daemon
   * @throws DockerException if any Docker-related operation fails
   */
  private void removeAllVolumes(DockerClient dockerClient) throws DockerException {
    List<InspectVolumeResponse> volumeList = dockerClient.listVolumesCmd().exec().getVolumes();

    List<Container> runningContainers = dockerClient.listContainersCmd().exec();
    Set<String> usedVolumes =
        runningContainers.stream()
            .map(Container::getId)
            .flatMap(
                containerId -> {
                  InspectContainerResponse containerInfo =
                      dockerClient.inspectContainerCmd(containerId).exec();
                  return Objects.requireNonNull(containerInfo.getMounts()).stream()
                      .map(InspectContainerResponse.Mount::getName);
                })
            .collect(Collectors.toSet());

    for (InspectVolumeResponse volume : volumeList) {
      String volumeName = volume.getName();
      if (!usedVolumes.contains(volumeName)) {
        dockerClient.removeVolumeCmd(volumeName).exec();
      }
    }
  }
}
