package api.goraebab.domain.remote.docker.service;

import api.goraebab.domain.remote.docker.dto.DaemonReqDto;
import api.goraebab.domain.remote.docker.dto.DaemonResDto;
import api.goraebab.domain.remote.docker.entity.Daemon;
import api.goraebab.domain.remote.docker.mapper.DaemonMapper;
import api.goraebab.domain.remote.docker.repository.DaemonRepository;
import api.goraebab.global.exception.CustomException;
import api.goraebab.global.exception.ErrorCode;
import api.goraebab.global.util.ConnectionUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link DaemonService} interface, providing business logic
 * for managing {@link Daemon} entities.
 *
 * <p>This service handles operations such as retrieving all daemons, connecting a new daemon,
 * and deleting existing daemons. It interacts with the {@link DaemonRepository} for data persistence
 * and uses {@link DaemonMapper} for entity-DTO conversions.</p>
 *
 * @author whitem4rk
 * @version 1.0
 * @see ConnectionUtil
 */
@Service
@RequiredArgsConstructor
public class DaemonServiceImpl implements DaemonService {

  private final DaemonRepository daemonRepository;

  @Override
  public List<DaemonResDto> getDaemons() {
    try {
      List<Daemon> daemonList = daemonRepository.findAll();
      return DaemonMapper.INSTANCE.entityListToResDtoList(daemonList);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.RETRIEVAL_FAILED);
    }
  }

  /**
   * Connects to a daemon using the provided configuration and saves it in the repository.
   *
   * <p>The method validates the connection to the daemon using {@link ConnectionUtil#testDockerPing},
   * and if successful, maps the provided {@link DaemonReqDto} to a {@link Daemon} entity and saves it
   * to the database.</p>
   *
   * @param daemonReqDto the {@link DaemonReqDto} containing the connection details for the daemon.
   * @throws CustomException if the connection to the daemon fails ({@link ErrorCode#CONNECTION_FAILED}).
   */
  @Override
  public void connectDaemon(DaemonReqDto daemonReqDto) {
    boolean connected = ConnectionUtil.testDockerPing(daemonReqDto.getHost(), daemonReqDto.getPort());
    if (connected) {
      Daemon daemon = DaemonMapper.INSTANCE.reqDtoToEntity(daemonReqDto);
      daemonRepository.save(daemon);
    } else {
      throw new CustomException(ErrorCode.CONNECTION_FAILED);
    }
  }

  @Override
  public void deleteDaemon(Long daemonId) {
    try {
      daemonRepository.deleteById(daemonId);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.DELETE_FAILED);
    }
  }

}
