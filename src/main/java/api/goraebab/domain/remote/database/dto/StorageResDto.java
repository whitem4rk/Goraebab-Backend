package api.goraebab.domain.remote.database.dto;

import api.goraebab.domain.remote.database.entity.DBMS;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StorageResDto {

  private Long storageId;

  private String host;

  private Integer port;

  private DBMS dbms;

  private String name;

  private String username;


  @Builder
  public StorageResDto(Long storageId, String host, Integer port, DBMS dbms, String name, String username) {
    this.storageId = storageId;
    this.host = host;
    this.port = port;
    this.dbms = dbms;
    this.name = name;
    this.username = username;
  }

}
