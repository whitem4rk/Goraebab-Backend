package api.goraebab.domain.blueprint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlueprintsResDto {

    private Long blueprintId;

    private String name;

    private String data;

    private Boolean isRemote;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

}
