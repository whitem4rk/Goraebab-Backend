package api.goraebab.domain.remote.docker.mapper;

import api.goraebab.domain.remote.docker.dto.DaemonReqDto;
import api.goraebab.domain.remote.docker.entity.Daemon;

public class DaemonMapper {

  public static Daemon toEntity(DaemonReqDto daemonReqDto) {
    return Daemon.builder()
        .host(daemonReqDto.getHost())
        .port(daemonReqDto.getPort())
        .name(daemonReqDto.getName())
        .build();
  }

}
