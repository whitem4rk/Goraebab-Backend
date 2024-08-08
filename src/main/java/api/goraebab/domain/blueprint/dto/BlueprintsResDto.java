package api.goraebab.domain.blueprint.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BlueprintsResDto {

    private Long blueprintId;

    private String name;

    private String data;

    private Boolean isRemote;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
