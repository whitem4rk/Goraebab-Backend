package api.goraebab.domain.remote.database.mapper;

import api.goraebab.domain.remote.database.dto.StorageReqDto;
import api.goraebab.domain.remote.database.entity.Storage;

public class StorageMapper {

  public static Storage toEntity(StorageReqDto storageReqDto) {
    return Storage.builder()
        .host(storageReqDto.getHost())
        .port(storageReqDto.getPort())
        .dbms(storageReqDto.getDbms())
        .name(storageReqDto.getName())
        .username(storageReqDto.getUsername())
        .password(storageReqDto.getPassword())
        .build();
  }

}
