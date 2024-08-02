package api.goraebab.domain.remote.datebase.service;

import api.goraebab.domain.blueprint.entity.Blueprint;
import api.goraebab.domain.blueprint.repository.BlueprintRepository;
import api.goraebab.domain.remote.datebase.dto.StorageReqDto;
import api.goraebab.domain.remote.datebase.entity.Storage;
import api.goraebab.domain.remote.datebase.mapper.StorageMapper;
import api.goraebab.domain.remote.datebase.repository.StorageRepository;
import api.goraebab.global.util.ConnectionUtil;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

  private final StorageRepository storageRepository;
  private final BlueprintRepository blueprintRepository;

  private static final String SELECT_ALL_STORAGES = "SELECT * FROM goraebab.storage";
  private static final String SELECT_ALL_BLUEPRINTS = "SELECT * FROM goraebab.blueprint";

  @Override
  @Transactional(readOnly = true)
  public List<Storage> getStorages() {
    return storageRepository.findAll();
  }

  @Override
  @Transactional
  public void connectStorage(StorageReqDto storageReqDto) {
    DataSource dataSource = ConnectionUtil.createDataSource(storageReqDto);
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.queryForList(SELECT_ALL_STORAGES);

    Storage storage = StorageMapper.toEntity(storageReqDto);
    storageRepository.save(storage);
  }

  @Override
  @Transactional
  public void deleteStorage(Long daemonId) {
    storageRepository.deleteById(daemonId);
  }

  @Override
  @Transactional
  public void copyStorage(Long storageId) {
    Storage storage = storageRepository.findById(storageId)
        .orElseThrow(() -> new NotFoundException("Storage not found"));

    DataSource dataSource = ConnectionUtil.createDataSource(storage);
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    List<Blueprint> copiedData = jdbcTemplate.query(SELECT_ALL_BLUEPRINTS,
        new BeanPropertyRowMapper<>(Blueprint.class));

    blueprintRepository.saveAll(copiedData);
  }
}
