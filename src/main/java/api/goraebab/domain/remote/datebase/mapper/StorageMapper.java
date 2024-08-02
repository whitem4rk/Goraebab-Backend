package api.goraebab.domain.remote.datebase.mapper;

import api.goraebab.domain.remote.datebase.dto.StorageReqDto;
import api.goraebab.domain.remote.datebase.entity.Storage;

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
