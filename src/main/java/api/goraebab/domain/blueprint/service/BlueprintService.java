package api.goraebab.domain.blueprint.service;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;

import java.util.List;

public interface BlueprintService {

    List<BlueprintsResDto> getBlueprints(Long storageId);

    BlueprintResDto getBlueprint(Long storageId, Long blueprintId);

    void saveBlueprint(BlueprintReqDto blueprintReqDto);

}
