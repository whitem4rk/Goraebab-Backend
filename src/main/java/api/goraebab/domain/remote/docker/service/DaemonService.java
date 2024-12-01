package api.goraebab.domain.remote.docker.service;

import api.goraebab.domain.remote.docker.dto.DaemonReqDto;
import api.goraebab.domain.remote.docker.dto.DaemonResDto;
import java.util.List;

/**
 * Service interface for managing daemon entities and their operations.
 *
 * <p>This interface provides methods for retrieving, connecting and deleting daemon configurations.</p>
 *
 * @author whitem4rk
 * @version 1.0
 */
public interface DaemonService {

  List<DaemonResDto> getDaemons();

  void connectDaemon(DaemonReqDto daemonReqDto);

  void deleteDaemon(Long daemonId);
}
