package api.goraebab.domain.blueprint.dto;

import api.goraebab.domain.blueprint.dockerObject.ProcessedData;
import api.goraebab.domain.remote.database.dto.StorageResDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlueprintResDto extends BlueprintsResDto {

  private StorageResDto storageInfo;

  public BlueprintResDto(
      Long blueprintId,
      String name,
      ProcessedData data,
      Boolean isRemote,
      LocalDateTime dateCreated,
      LocalDateTime dateUpdated,
      StorageResDto storageInfo) {
    super(blueprintId, name, data, isRemote, dateCreated, dateUpdated);
    this.storageInfo = storageInfo;
  }
}
