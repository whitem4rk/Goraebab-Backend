package api.goraebab.domain.blueprint.service;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;

import api.goraebab.domain.blueprint.dto.SyncResultDto;
import java.util.List;

/**
 * Service interface for managing blueprints.
 * Provides methods for creating, modifying, retrieving, applying blueprints to Docker.
 *
 * @author whitem4rk
 * @version 1.0
 */
public interface BlueprintService {

    List<BlueprintsResDto> getBlueprints(Long storageId);

    BlueprintResDto getBlueprintById(Long storageId, Long blueprintId);

    SyncResultDto saveBlueprint(Long storageId, BlueprintReqDto blueprintReqDto);

    SyncResultDto modifyBlueprint(Long storageId, Long blueprintId, BlueprintReqDto blueprintReqDto);

    void deleteBlueprint(Long storageId, Long blueprintId);

}