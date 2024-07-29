package api.goraebab.domain.remote.docker.service;

import api.goraebab.domain.remote.docker.dto.DaemonReqDto;
import api.goraebab.domain.remote.docker.entity.Daemon;
import java.util.List;

public interface DaemonService {

  List<Daemon> getDaemons();

  void connectDaemon(DaemonReqDto daemonReqDto);

  void deleteDaemon(Long daemonId);
}
