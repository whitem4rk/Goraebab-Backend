package api.goraebab.domain.remote.harbor.controller;

import api.goraebab.domain.remote.harbor.dto.HarborDetailResDto;
import api.goraebab.domain.remote.harbor.dto.HarborReqDto;
import api.goraebab.domain.remote.harbor.dto.HarborResDto;
import api.goraebab.domain.remote.harbor.service.HarborService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Harbor API")
@RequiredArgsConstructor
public class HarborController {

    private final HarborService harborService;

    @Operation(summary = "Load remote harbors info from database")
    @GetMapping("/remote/harbors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<List<HarborResDto>> loadHarbors() {
        List<HarborResDto> harborResDtoList = harborService.getHarbors();

        return ResponseEntity.ok(harborResDtoList);
    }

    @Operation(summary = "Retrieve a single harbor")
    @GetMapping("/remote/harbors/{harborId}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<HarborDetailResDto> getHarbor(@PathVariable Long harborId) {
        HarborDetailResDto harborDetailResDto = harborService.getHarbor(harborId);

        return ResponseEntity.ok(harborDetailResDto);
    }

    @Operation(summary = "Connect and save remote harbor in database")
    @PostMapping("/remote/harbor/connect")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<Void> connectHarbor(@RequestBody @Valid HarborReqDto harborReqDto) {
        harborService.connectHarbor(harborReqDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete the harbor")
    @DeleteMapping("/remote/harbors/{harborId}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<Void> disconnectHarbor(@PathVariable Long harborId) {
        harborService.deleteHarbor(harborId);

        return ResponseEntity.ok().build();
    }


}
