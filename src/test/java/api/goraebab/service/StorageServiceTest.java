package api.goraebab.service;

import api.goraebab.domain.blueprint.entity.Blueprint;
import api.goraebab.domain.blueprint.mapper.BlueprintRowMapper;
import api.goraebab.domain.blueprint.repository.BlueprintRepository;
import api.goraebab.domain.remote.database.dto.StorageReqDto;
import api.goraebab.domain.remote.database.dto.StorageResDto;
import api.goraebab.domain.remote.database.entity.DBMS;
import api.goraebab.domain.remote.database.entity.Storage;
import api.goraebab.domain.remote.database.mapper.StorageMapper;
import api.goraebab.domain.remote.database.repository.StorageRepository;
import api.goraebab.domain.remote.database.service.StorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {

    @Mock
    private StorageRepository storageRepository;

    @Mock
    private BlueprintRepository blueprintRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private StorageServiceImpl storageService;

    private Long storageId;
    private Storage storage;
    private StorageReqDto storageReqDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        storageId = 1L;
        storageReqDto = new StorageReqDto("123.123.123.123", 8080, DBMS.MYSQL, "Storage1", "root", "password");
        storage = new Storage(storageId, "123.123.123.123", 8080, DBMS.MYSQL, "Storage1", "root", "password");
        ReflectionTestUtils.setField(storageService, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    @DisplayName("원격 storage 목록 조회")
    void getStorages() {
        List<Storage> storages = List.of(storage);
        given(storageRepository.findAll()).willReturn(storages);
        given(StorageMapper.INSTANCE.entityListToResDtoList(storages)).willReturn(List.of(
                new StorageResDto(storageId, "123.123.123.123", 8080, DBMS.MYSQL, "Storage1", "root")
        ));

        List<StorageResDto> result = storageService.getStorages();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Storage1", result.get(0).getName());
    }

    @Test
    @DisplayName("원격 storage 연결")
    void connectStorage() {
        given(storageRepository.save(any(Storage.class))).willReturn(storage);
        given(this.jdbcTemplate.queryForList("SELECT * FROM goraebab.storage")).willReturn(List.of());

        assertDoesNotThrow(() -> storageService.connectStorage(storageReqDto));

        verify(jdbcTemplate, times(1)).queryForList("SELECT * FROM goraebab.storage");
        verify(storageRepository, times(1)).save(any(Storage.class));
    }

    @Test
    @DisplayName("원격 storage 복사")
    void copyStorage() {
        given(storageRepository.findById(storageId)).willReturn(Optional.of(storage));

        List<Blueprint> blueprints = List.of(new Blueprint());
        given(jdbcTemplate.query(anyString(), any(BlueprintRowMapper.class))).willReturn(blueprints);

        storageService.copyStorage(storageId);

        verify(blueprintRepository, times(1)).saveAll(blueprints);
    }

    @Test
    @DisplayName("원격 storage 삭제")
    void deleteStorage() {
        storageService.deleteStorage(storageId);

        verify(storageRepository, times(1)).deleteById(storageId);
    }

}
