package api.goraebab.domain.blueprint.dto;

import api.goraebab.domain.remote.database.dto.StorageResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlueprintResDto extends BlueprintsResDto {

    private StorageResDto storageInfo;

}
