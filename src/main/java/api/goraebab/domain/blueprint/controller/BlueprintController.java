package api.goraebab.domain.blueprint.controller;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;
import api.goraebab.domain.blueprint.service.BlueprintServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Blueprint API")
@RequestMapping("/database/{databaseId}")
@RequiredArgsConstructor
public class BlueprintController {

    private final BlueprintServiceImpl blueprintService;

    @Operation(summary = "Retrieve the list of blueprints")
    @GetMapping("/blueprints")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<List<BlueprintsResDto>> getBlueprints(@PathVariable Long databaseId) {
        List<BlueprintsResDto> blueprints = blueprintService.getBlueprints(databaseId);

        return ResponseEntity.ok(blueprints);
    }

    @Operation(summary = "Retrieve a single blueprint")
    @GetMapping("/blueprint/{blueprintId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<BlueprintResDto> getBlueprint(@PathVariable Long databaseId,
                                                        @PathVariable Long blueprintId) {
        BlueprintResDto blueprint = blueprintService.getBlueprint(databaseId, blueprintId);

        return ResponseEntity.ok(blueprint);
    }

    @Operation(summary = "Save the blueprint to the local database")
    @PostMapping("/blueprint/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<Void> saveBlueprint(@PathVariable Long databaseId,
                                              @RequestBody @Valid BlueprintReqDto blueprintReqDto) {
        blueprintService.saveBlueprint(databaseId, blueprintReqDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Modify the blueprint")
    @PatchMapping("/blueprint/{blueprintId}/modify")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<Void> modifyBlueprint(@PathVariable Long databaseId,
                                                @PathVariable Long blueprintId,
                                                @RequestBody @Valid BlueprintReqDto blueprintReqDto) {
        blueprintService.modifyBlueprint(databaseId, blueprintId, blueprintReqDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete the blueprint")
    @PatchMapping("/blueprint/{blueprintId}/delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<Void> deleteBlueprint(@PathVariable Long databaseId,
                                                @PathVariable Long blueprintId) {
        blueprintService.deleteBlueprint(databaseId, blueprintId);

        return ResponseEntity.ok().build();
    }

}
