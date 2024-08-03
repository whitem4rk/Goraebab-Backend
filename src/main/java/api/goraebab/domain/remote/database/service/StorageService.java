package api.goraebab.domain.remote.database.service;

import api.goraebab.domain.remote.database.dto.StorageReqDto;
import api.goraebab.domain.remote.database.entity.Storage;
import java.util.List;

public interface StorageService {

  List<Storage> getStorages();

  void connectStorage(StorageReqDto storageReqDto);

  void deleteStorage(Long daemonId);

  void copyStorage(Long storageId);
}
