package api.goraebab.domain.blueprint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlueprintReqDto {

    @NotNull(message = "Storage ID must not be null")
    @Positive(message = "Storage ID must be a positive number")
    private Long storageId;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Data must not be blank")
    private String data;

}
