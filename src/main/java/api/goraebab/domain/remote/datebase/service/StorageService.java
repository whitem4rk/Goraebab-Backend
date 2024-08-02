package api.goraebab.domain.remote.datebase.service;

import api.goraebab.domain.remote.datebase.dto.StorageReqDto;
import api.goraebab.domain.remote.datebase.entity.Storage;
import java.util.List;

public interface StorageService {

  List<Storage> getStorages();

  void connectStorage(StorageReqDto storageReqDto);

  void deleteStorage(Long daemonId);

  void copyStorage(Long storageId);
}
