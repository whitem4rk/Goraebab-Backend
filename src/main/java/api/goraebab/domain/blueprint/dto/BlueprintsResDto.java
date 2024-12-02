package api.goraebab.domain.blueprint.dto;

import api.goraebab.domain.blueprint.dockerObject.ProcessedData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response DTO which client request list of blueprints. This DTO only contains necessary
 * information.
 *
 * @author whitem4rk
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlueprintsResDto {

  @Schema(description = "The unique identifier of the blueprint.", example = "1")
  private Long blueprintId;

  @Schema(description = "The name of the blueprint.", example = "Project1 blueprint")
  private String name;

  @Schema(description = "The raw data associated with the blueprint.")
  private ProcessedData data;

  @Schema(
      description = "The flag indicating whether the blueprint is stored remotely.",
      example = "true")
  private Boolean isRemote;

  @Schema(
      description = "The date and time when the blueprint was created.",
      example = "2023-09-28T12:34:56")
  private LocalDateTime dateCreated;

  @Schema(
      description = "The date and time when the blueprint was last updated.",
      example = "2023-09-28T12:34:56")
  private LocalDateTime dateUpdated;
}
