package api.goraebab.domain.remote.database.controller;

import api.goraebab.domain.remote.database.dto.StorageReqDto;
import api.goraebab.domain.remote.database.dto.StorageResDto;
import api.goraebab.domain.remote.database.service.StorageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Remote Database API")
@RequiredArgsConstructor
public class StorageController {

  private final StorageServiceImpl storageServiceImpl;

  @Operation(summary = "Load remote daemons info from database")
  @GetMapping("/remote/storages")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = StorageResDto.class))}),
  })
  public ResponseEntity<List<StorageResDto>> loadStorages() {
    List<StorageResDto> storageResDtoListList = storageServiceImpl.getStorages();

    return ResponseEntity.ok(storageResDtoListList);
  }

    @Operation(summary = "Connect and save remote storage in database")
  @PostMapping("/remote/storage/connect")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  public ResponseEntity<Void> connectStorage(
      @RequestBody @Valid StorageReqDto storageReqDto) {
    storageServiceImpl.connectStorage(storageReqDto);

    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Copy remote storage blueprints into database")
  @PostMapping("/remote/storage/{storageId}/copy")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  public ResponseEntity<Void> copyStorage(
      @PathVariable Long storageId) {
    storageServiceImpl.copyStorage(storageId);

    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Delete remote storage in database")
  @DeleteMapping("/remote/storage/{storageId}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  public ResponseEntity<Void> disconnectStorage(
      @PathVariable Long storageId) {
    storageServiceImpl.deleteStorage(storageId);

    return ResponseEntity.ok().build();
  }

}
