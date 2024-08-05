package api.goraebab.domain.blueprint.service;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;
import api.goraebab.domain.blueprint.entity.Blueprint;
import api.goraebab.domain.blueprint.mapper.BlueprintMapper;
import api.goraebab.domain.blueprint.repository.BlueprintRepository;
import api.goraebab.domain.remote.database.entity.Storage;
import api.goraebab.domain.remote.database.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlueprintServiceImpl implements BlueprintService {

    private final BlueprintRepository blueprintRepository;
    private final StorageRepository storageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BlueprintsResDto> getBlueprints(Long storageId) {
        List<Blueprint> blueprints = blueprintRepository.findByStorageId(storageId);

        return BlueprintMapper.INSTANCE.toBlueprintsResDtoList(blueprints);

    }

    @Override
    @Transactional(readOnly = true)
    public BlueprintResDto getBlueprint(Long storageId, Long blueprintId) {
        Blueprint blueprint = blueprintRepository.findByStorageIdAndId(storageId, blueprintId)
                .orElseThrow(() -> new NotFoundException("Blueprint not found"));

        return BlueprintMapper.INSTANCE.toBlueprintResDto(blueprint);
    }

    @Override
    @Transactional
    public void saveBlueprint(BlueprintReqDto blueprintReqDto) {
        Storage storage = storageRepository.findById(blueprintReqDto.getStorageId())
                .orElseThrow(() -> new NotFoundException("Storage not found"));

        Blueprint blueprint = Blueprint.builder()
                .name(blueprintReqDto.getName())
                .data(blueprintReqDto.getData())
                .storage(storage)
                .build();

        blueprint.setAsLocal();
        blueprintRepository.save(blueprint);
    }

}
