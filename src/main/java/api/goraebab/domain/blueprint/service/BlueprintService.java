package api.goraebab.domain.blueprint.service;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;

import java.util.List;

public interface BlueprintService {

    List<BlueprintsResDto> getBlueprints(Long storageId, boolean isRemote);

    BlueprintResDto getBlueprintById(Long storageId, Long blueprintId, boolean isRemote);

    void saveBlueprint(Long storageId, BlueprintReqDto blueprintReqDto);

    void modifyBlueprint(Long storageId, Long blueprintId, BlueprintReqDto blueprintReqDto);

    void deleteBlueprint(Long storageId, Long blueprintId);

}