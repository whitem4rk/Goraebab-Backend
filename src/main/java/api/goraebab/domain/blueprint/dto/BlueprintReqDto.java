package api.goraebab.domain.blueprint.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlueprintReqDto {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name must be less than 255 characters")
    @Schema(description = "The name of the blueprint.", example = "Project1 blueprint")
    private String name;

    @NotBlank(message = "Data must not be blank")
    @Schema(description = "The raw data associated with the blueprint.")
    private String data;

}
