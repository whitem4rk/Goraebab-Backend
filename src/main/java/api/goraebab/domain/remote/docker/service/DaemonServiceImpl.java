package api.goraebab.domain.remote.docker.service;

import api.goraebab.domain.remote.docker.dto.DaemonReqDto;
import api.goraebab.domain.remote.docker.entity.Daemon;
import api.goraebab.domain.remote.docker.mapper.DaemonMapper;
import api.goraebab.domain.remote.docker.repository.DaemonRepository;
import api.goraebab.global.util.ConnectionUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DaemonServiceImpl implements DaemonService {

  private final DaemonRepository daemonRepository;

  @Override
  public List<Daemon> getDaemons() {
    return daemonRepository.findAll();
  }

  @Override
  public void connectDaemon(DaemonReqDto daemonReqDto) {
    boolean connected = ConnectionUtil.testDockerPing(daemonReqDto.getHost(), daemonReqDto.getPort());
    if (connected) {
      Daemon daemon = DaemonMapper.toEntity(daemonReqDto);
      daemonRepository.save(daemon);
    } else {
      throw new RuntimeException("Unable to connect to Docker daemon");
    }
  }

  @Override
  public void deleteDaemon(Long daemonId) {
    daemonRepository.deleteById(daemonId);
  }

}
