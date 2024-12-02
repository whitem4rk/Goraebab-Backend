package api.goraebab.domain.remote.database.service;

import api.goraebab.domain.blueprint.entity.Blueprint;
import api.goraebab.domain.blueprint.mapper.BlueprintRowMapper;
import api.goraebab.domain.blueprint.repository.BlueprintRepository;
import api.goraebab.domain.remote.database.dto.StorageReqDto;
import api.goraebab.domain.remote.database.dto.StorageResDto;
import api.goraebab.domain.remote.database.entity.Storage;
import api.goraebab.domain.remote.database.mapper.StorageMapper;
import api.goraebab.domain.remote.database.repository.StorageRepository;
import api.goraebab.global.exception.CustomException;
import api.goraebab.global.exception.ErrorCode;
import api.goraebab.global.util.ConnectionUtil;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing storage entities and their operations.
 *
 * @author whitem4rk
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

  private final StorageRepository storageRepository;
  private final BlueprintRepository blueprintRepository;

  private static final String SELECT_ALL_STORAGES = "SELECT * FROM storage";
  private static final String SELECT_ALL_BLUEPRINTS = "SELECT * FROM blueprint";

  @Override
  @Transactional(readOnly = true)
  public List<StorageResDto> getStorages() {
    try {
      List<Storage> storageList = storageRepository.findAll();
      return StorageMapper.INSTANCE.entityListToResDtoList(storageList);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.RETRIEVAL_FAILED);
    }
  }

  /**
   * Connects to a storage system using the provided configuration and saves the storage information
   * to the repository.
   *
   * <p>This method validates the storage configuration by attempting to execute a query on the
   * target storage. If the connection is successful, the storage details are mapped to a {@link
   * Storage} entity and persisted in the repository.
   *
   * @param storageReqDto the {@link StorageReqDto} containing the storage configuration details,
   *     such as host, port, username, and password.
   * @throws CustomException if the connection fails ({@link ErrorCode#CONNECTION_FAILED}).
   * @see ConnectionUtil
   */
  @Override
  @Transactional
  public void connectStorage(StorageReqDto storageReqDto) {
    try {
      DataSource dataSource = ConnectionUtil.createDataSource(storageReqDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      jdbcTemplate.queryForList(SELECT_ALL_STORAGES);

      Storage storage = StorageMapper.INSTANCE.reqDtoToEntity(storageReqDto);
      storageRepository.save(storage);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.CONNECTION_FAILED);
    }
  }

  @Override
  @Transactional
  public void deleteStorage(Long storageId) {
    try {
      storageRepository.deleteById(storageId);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.DELETE_FAILED);
    }
  }

  /**
   * Copies all blueprints which are stored in target storage to local storage. This functionality
   * allows users to import blueprints created on another server into the currently active server.
   *
   * @param storageId the ID of the storage whose blueprints are to be copied.
   * @throws CustomException if the storage is not found ({@link ErrorCode#NOT_FOUND_VALUE}) or if
   *     the copy operation fails ({@link ErrorCode#COPY_FAILED}).
   */
  @Override
  @Transactional
  public void copyStorage(Long storageId) {
    try {
      Storage storage =
          storageRepository
              .findById(storageId)
              .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VALUE));

      DataSource dataSource = ConnectionUtil.createDataSource(storage);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      List<Blueprint> copiedData =
          jdbcTemplate.query(SELECT_ALL_BLUEPRINTS, new BlueprintRowMapper());

      copiedData.forEach(
          blueprint -> {
            blueprint.setStorage(storage);
            blueprint.setAsRemote();
          });

      blueprintRepository.saveAll(copiedData);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.COPY_FAILED);
    }
  }
}
