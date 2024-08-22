package api.goraebab.service;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;
import api.goraebab.domain.blueprint.entity.Blueprint;
import api.goraebab.domain.blueprint.mapper.BlueprintMapper;
import api.goraebab.domain.blueprint.repository.BlueprintRepository;
import api.goraebab.domain.blueprint.service.BlueprintServiceImpl;
import api.goraebab.domain.remote.database.dto.StorageResDto;
import api.goraebab.domain.remote.database.entity.DBMS;
import api.goraebab.domain.remote.database.entity.Storage;
import api.goraebab.domain.remote.database.repository.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class BlueprintServiceTest {

    @Mock
    private BlueprintRepository blueprintRepository;

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private BlueprintServiceImpl blueprintService;

    private Long storageId;
    private Long blueprintId;
    private Storage storage;
    private Blueprint blueprint;
    private BlueprintReqDto blueprintReqDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        storageId = 1L;
        blueprintId = 1L;
        storage = new Storage(storageId, "123.123.123.123", 8080, DBMS.MYSQL, "Storage1", "root", "password");
        blueprint = new Blueprint(blueprintId, "Blueprint1", "Data1", false, storage);
        blueprintReqDto = new BlueprintReqDto("Blueprint2", "Data2");
    }

    @Test
    @DisplayName("설계도 전체 목록 조회")
    void getBlueprints() {
        List<Blueprint> blueprints = List.of(blueprint);
        given(blueprintRepository.findByStorageId(storageId)).willReturn(blueprints);
        given(BlueprintMapper.INSTANCE.toBlueprintsResDtoList(blueprints)).willReturn(List.of(
                new BlueprintsResDto(blueprintId, "Blueprint1", "Data1", false, LocalDateTime.now(), LocalDateTime.now())
        ));

        List<BlueprintsResDto> result = blueprintService.getBlueprints(storageId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Blueprint1", result.get(0).getName());
    }

    @Test
    @DisplayName("설계도 단건 조회")
    void getBlueprint() {
        Blueprint blueprint = new Blueprint(blueprintId, "Blueprint1", "Data1", false, storage);
        StorageResDto storageResDto = StorageResDto.builder()
                .storageId(storage.getId())
                .host(storage.getHost())
                .port(storage.getPort())
                .dbms(storage.getDbms())
                .name(storage.getName())
                .username(storage.getUsername())
                .build();

        given(blueprintRepository.findByStorageIdAndId(storageId, blueprintId)).willReturn(Optional.of(blueprint));
        given(BlueprintMapper.INSTANCE.toBlueprintResDto(blueprint)).willReturn(new BlueprintResDto(
                blueprintId, "Blueprint1", "Data1", false, LocalDateTime.now(), LocalDateTime.now(), storageResDto
        ));

        BlueprintResDto result = blueprintService.getBlueprint(storageId, blueprintId);

        assertNotNull(result);
        assertEquals(blueprintId, result.getBlueprintId());
        assertEquals("Blueprint1", result.getName());
        assertEquals(storageId, result.getStorageInfo().getStorageId());
        assertEquals(storage.getHost(), result.getStorageInfo().getHost());
    }

    @Test
    @DisplayName("설계도 저장")
    void saveBlueprint() {
        given(storageRepository.findById(storageId)).willReturn(Optional.of(storage));

        blueprintService.saveBlueprint(storageId, blueprintReqDto);

        verify(blueprintRepository.save(argThat(bp ->
                "Blueprint2".equals(bp.getName())
                        && "Data2".equals(bp.getData())
                        && storage.equals(bp.getStorage())
                        && !bp.getIsRemote()
        )));
    }

    @Test
    @DisplayName("설계도 삭제")
    void deleteBlueprint() {
        given(blueprintRepository.findByStorageIdAndId(storageId, blueprintId));

        blueprintService.deleteBlueprint(storageId, blueprintId);

        verify(blueprintRepository).delete(blueprint);
    }

}
