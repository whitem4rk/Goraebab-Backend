package api.goraebab.domain.blueprint.dto;

import api.goraebab.domain.remote.database.dto.StorageResDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlueprintResDto extends BlueprintsResDto {

    private StorageResDto storageInfo;

}
