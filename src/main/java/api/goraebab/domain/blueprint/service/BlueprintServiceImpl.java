package api.goraebab.domain.blueprint.service;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;
import api.goraebab.domain.blueprint.entity.Blueprint;
import api.goraebab.domain.blueprint.mapper.BlueprintMapper;
import api.goraebab.domain.blueprint.repository.BlueprintRepository;
import api.goraebab.domain.remote.database.entity.Storage;
import api.goraebab.domain.remote.database.repository.StorageRepository;
import api.goraebab.global.exception.CustomException;
import api.goraebab.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(blueprints.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_VALUE);
        }

        return BlueprintMapper.INSTANCE.toBlueprintsResDtoList(blueprints);

    }

    @Override
    @Transactional(readOnly = true)
    public BlueprintResDto getBlueprint(Long storageId, Long blueprintId) {
        Blueprint blueprint = findBlueprintByStorageAndId(storageId, blueprintId);

        return BlueprintMapper.INSTANCE.toBlueprintResDto(blueprint);
    }


    @Override
    @Transactional
    public void saveBlueprint(Long storageId, BlueprintReqDto blueprintReqDto) {
        Storage storage = storageRepository.findById(storageId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VALUE));

        Blueprint blueprint = Blueprint.builder()
                .name(blueprintReqDto.getName())
                .data(blueprintReqDto.getData())
                .storage(storage)
                .build();

        blueprintRepository.save(blueprint);
    }

    @Override
    @Transactional
    public void modifyBlueprint(Long storageId, Long blueprintId, BlueprintReqDto blueprintReqDto) {
        Blueprint blueprint = findBlueprintByStorageAndId(storageId, blueprintId);
        blueprint.modify(blueprintReqDto.getName(), blueprintReqDto.getData());

        blueprintRepository.save(blueprint);
    }

    @Override
    @Transactional
    public void deleteBlueprint(Long storageId, Long blueprintId) {
        Blueprint blueprint = findBlueprintByStorageAndId(storageId, blueprintId);

        blueprintRepository.delete(blueprint);
    }

    private Blueprint findBlueprintByStorageAndId(Long storageId, Long blueprintId) {
        return blueprintRepository.findByStorageIdAndId(storageId, blueprintId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_VALUE));
    }

}
