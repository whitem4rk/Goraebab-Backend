package api.goraebab.domain.remote.docker.controller;

import api.goraebab.domain.remote.docker.dto.DaemonReqDto;
import api.goraebab.domain.remote.docker.entity.Daemon;
import api.goraebab.domain.remote.docker.service.DaemonService;
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
@Tag(name = "Remote Docker API")
@RequiredArgsConstructor
public class DaemonController {

  private final DaemonService daemonService;

  @Operation(summary = "Load remote daemons info from database")
  @GetMapping("/remote/daemons")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = Daemon.class))}),
  })
  public ResponseEntity<List<Daemon>> loadDaemons() {
    List<Daemon> daemonList = daemonService.getDaemons();

    return ResponseEntity.ok(daemonList);
  }

  @Operation(summary = "Connect and save remote daemon in database")
  @PostMapping("/remote/daemon/connect")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  public ResponseEntity<Void> connectDaemon(@RequestBody @Valid DaemonReqDto daemonReqDto) {
    daemonService.connectDaemon(daemonReqDto);

    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Delete remote daemon in database")
  @DeleteMapping("/remote/daemon/{daemonId}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200")
  })
  public ResponseEntity<Void> disconnectDaemon(@PathVariable Long daemonId) {
    daemonService.deleteDaemon(daemonId);

    return ResponseEntity.ok().build();
  }

}
