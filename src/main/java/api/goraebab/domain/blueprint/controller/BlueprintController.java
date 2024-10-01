package api.goraebab.domain.blueprint.controller;

import api.goraebab.domain.blueprint.dto.BlueprintReqDto;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;
import api.goraebab.domain.blueprint.service.BlueprintServiceImpl;
import api.goraebab.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/storage/{storageId}")
@RequiredArgsConstructor
public class BlueprintController {

    private final BlueprintServiceImpl blueprintService;

    @Operation(summary = "Retrieve the list of blueprints",
        description = "Fetches a list of blueprints associated with a specific storage ID.")
    @GetMapping("/blueprints")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Failed to retrieve data from the database.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"RETRIEVAL_FAILED\", \"code\": 500, \"message\": \"Failed to retrieve data from the database.\", \"errors\": []}"))
        )
    })
    public ResponseEntity<List<BlueprintsResDto>> getBlueprints(@PathVariable Long storageId) {
        List<BlueprintsResDto> blueprints = blueprintService.getBlueprints(storageId);

        return ResponseEntity.ok(blueprints);
    }

    @Operation(summary = "Retrieve a single blueprint",
        description = "Fetches the details of a single blueprint based on the provided blueprint ID.")
    @GetMapping("/blueprint/{blueprintId}")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "The type of the provided input value does not match the expected type.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"INVALID_TYPE_VALUE\", \"code\": 400, \"message\": \"The type of the provided input value does not match the expected type.\", \"errors\": []}"))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "The requested resource could not be found.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"NOT_FOUND_VALUE\", \"code\": 404, \"message\": \"The requested resource could not be found.\", \"errors\": []}"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Failed to retrieve data from the database.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"RETRIEVAL_FAILED\", \"code\": 500, \"message\": \"Failed to retrieve data from the database.\", \"errors\": []}"))
        )
    })
    public ResponseEntity<BlueprintResDto> getBlueprint(
        @PathVariable @Schema(description = "The unique identifier of the storage.") Long storageId,
        @PathVariable @Schema(description = "The unique identifier of the blueprint.") Long blueprintId) {
        BlueprintResDto blueprint = blueprintService.getBlueprint(storageId, blueprintId);

        return ResponseEntity.ok(blueprint);
    }

    @Operation(summary = "Save the blueprint to the local database",
        description = "Saves a new blueprint associated with the specified storage ID to the local database.")
    @PostMapping("/blueprint")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "The type of the provided input value does not match the expected type.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"INVALID_TYPE_VALUE\", \"code\": 400, \"message\": \"The type of the provided input value does not match the expected type.\", \"errors\": []}"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Failed to save blueprint.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"SAVE_FAILED\", \"code\": 500, \"message\": \"Failed to save blueprint.\", \"errors\": []}"))
        )
    })
    public ResponseEntity<Void> saveBlueprint(@PathVariable Long storageId,
                                              @RequestBody @Valid BlueprintReqDto blueprintReqDto) {
        blueprintService.saveBlueprint(storageId, blueprintReqDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Modify the blueprint",
        description = "Updates the details of an existing blueprint in the local database.")
    @PatchMapping("/blueprint/{blueprintId}")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "The type of the provided input value does not match the expected type.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"INVALID_TYPE_VALUE\", \"code\": 400, \"message\": \"The type of the provided input value does not match the expected type.\", \"errors\": []}"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Failed to modify blueprint",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"MODIFY_FAILED\", \"code\": 500, \"message\": \"Failed to modify blueprint.\", \"errors\": []}"))
        )
    })
    public ResponseEntity<Void> modifyBlueprint(
        @PathVariable @Schema(description = "The unique identifier of the storage.") Long storageId,
        @PathVariable @Schema(description = "The unique identifier of the blueprint.") Long blueprintId,
        @RequestBody @Valid BlueprintReqDto blueprintReqDto) {
        blueprintService.modifyBlueprint(storageId, blueprintId, blueprintReqDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete the blueprint",
        description = "Deletes the specified blueprint from the local database based on the blueprint ID.")
    @DeleteMapping("/blueprint/{blueprintId}")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "The type of the provided input value does not match the expected type.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"INVALID_TYPE_VALUE\", \"code\": 400, \"message\": \"The type of the provided input value does not match the expected type.\", \"errors\": []}"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Failed to delete the specified resource.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                examples =
                @ExampleObject(
                    value =
                        "{\"status\": \"DELETE_FAILED\", \"code\": 500, \"message\": \"Failed to delete the specified resource.\", \"errors\": []}"))
        )
    })
    public ResponseEntity<Void> deleteBlueprint(
        @PathVariable @Schema(description = "The unique identifier of the storage.") Long storageId,
        @PathVariable @Schema(description = "The unique identifier of the blueprint.") Long blueprintId) {
        blueprintService.deleteBlueprint(storageId, blueprintId);

        return ResponseEntity.ok().build();
    }

}
