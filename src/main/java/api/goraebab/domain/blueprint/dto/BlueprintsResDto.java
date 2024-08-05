package api.goraebab.domain.blueprint.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlueprintsResDto {

    private Long blueprintId;

    private String name;

    private String data;

    private Boolean isRemote;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
