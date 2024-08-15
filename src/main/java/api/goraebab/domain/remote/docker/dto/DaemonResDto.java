package api.goraebab.domain.remote.docker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DaemonResDto {

  private Long daemonId;

  private String host;

  private Integer port;

  private String name;


  @Builder
  public DaemonResDto(Long daemonId, String host, Integer port, String name) {
    this.daemonId = daemonId;
    this.host = host;
    this.port = port;
    this.name = name;
  }

}
