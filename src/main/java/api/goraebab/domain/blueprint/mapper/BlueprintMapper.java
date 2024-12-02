package api.goraebab.domain.blueprint.mapper;

import api.goraebab.domain.blueprint.dockerObject.ProcessedData;
import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;
import api.goraebab.domain.blueprint.entity.Blueprint;
import api.goraebab.domain.remote.database.mapper.StorageMapper;
import api.goraebab.global.exception.CustomException;
import api.goraebab.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting {@link Blueprint} entities to various DTOs and vice versa.
 *
 * @author whitem4rk
 * @version 1.0
 */
@Mapper(uses = {StorageMapper.class})
public interface BlueprintMapper {

  BlueprintMapper INSTANCE = Mappers.getMapper(BlueprintMapper.class);

  @Mapping(source = "id", target = "blueprintId")
  @Mapping(source = "storage", target = "storageInfo")
  @Mapping(source = "data", target = "data", qualifiedByName = "stringToHost")
  BlueprintResDto toBlueprintResDto(Blueprint blueprint);

  @Mapping(source = "id", target = "blueprintId")
  @Mapping(source = "data", target = "data", qualifiedByName = "stringToHost")
  BlueprintsResDto toBlueprintsResDto(Blueprint blueprint);

  @IterableMapping(elementTargetType = BlueprintsResDto.class)
  List<BlueprintsResDto> toBlueprintsResDtoList(List<Blueprint> blueprints);

  /**
   * Custom mapping method to convert a JSON string into a {@link ProcessedData} object.
   *
   * @param data the JSON string to convert
   * @return the resulting {@link ProcessedData} object
   * @throws CustomException if the JSON string cannot be parsed
   */
  @Named("stringToHost")
  default ProcessedData stringToHost(String data) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(data, ProcessedData.class);
    } catch (IOException e) {
      throw new CustomException(ErrorCode.CONVERSION_FAILED);
    }
  }
}
