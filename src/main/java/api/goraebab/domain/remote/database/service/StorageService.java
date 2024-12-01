package api.goraebab.domain.remote.database.service;

import api.goraebab.domain.remote.database.dto.StorageReqDto;
import api.goraebab.domain.remote.database.dto.StorageResDto;
import java.util.List;

/**
 * Service interface for managing storage entities and their operations.
 *
 * <p>This interface provides methods for retrieving, connecting, deleting, and copying storage configurations.</p>
 *
 * @author whitem4rk
 * @version 1.0
 */
public interface StorageService {

  List<StorageResDto> getStorages();

  void connectStorage(StorageReqDto storageReqDto);

  void deleteStorage(Long storageId);

  void copyStorage(Long storageId);

}
