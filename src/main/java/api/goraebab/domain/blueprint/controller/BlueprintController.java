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
@RequiredArgsConstructor
public class BlueprintController {

    private final BlueprintServiceImpl blueprintService;

    @Operation(summary = "Retrieve the list of blueprints")
    @GetMapping("/database/{databaseId}/blueprints")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<List<BlueprintsResDto>> getBlueprints(@PathVariable Long databaseId) {
        List<BlueprintsResDto> blueprints = blueprintService.getBlueprints(databaseId);

        return ResponseEntity.ok(blueprints);
    }

    @Operation(summary = "Retrieve a single blueprint")
    @GetMapping("/database/{databaseId}/blueprint/{blueprintId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<BlueprintResDto> getBlueprint(@PathVariable Long databaseId, @PathVariable Long blueprintId) {
        BlueprintResDto blueprint = blueprintService.getBlueprint(databaseId, blueprintId);

        return ResponseEntity.ok(blueprint);
    }

    @Operation(summary = "Save the blueprint to the local database")
    @PostMapping("/blueprint/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<Void> saveBlueprint(@RequestBody @Valid BlueprintReqDto blueprintReqDto) {
        blueprintService.saveBlueprint(blueprintReqDto);

        return ResponseEntity.ok().build();
    }

}
