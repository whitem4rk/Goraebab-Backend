package api.goraebab.domain.blueprint.mapper;

import api.goraebab.domain.blueprint.dto.BlueprintResDto;
import api.goraebab.domain.blueprint.dto.BlueprintsResDto;
import api.goraebab.domain.blueprint.entity.Blueprint;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BlueprintMapper {

    BlueprintMapper INSTANCE = Mappers.getMapper(BlueprintMapper.class);

    @Mapping(source = "id", target = "blueprintId")
    @Mapping(source = "storage", target = "storageInfo")
    BlueprintResDto toBlueprintResDto(Blueprint blueprint);

    @Mapping(source = "id", target = "blueprintId")
    BlueprintsResDto toBlueprintsResDto(Blueprint blueprint);

    @IterableMapping(elementTargetType = BlueprintsResDto.class)
    List<BlueprintsResDto> toBlueprintsResDtoList(List<Blueprint> blueprints);

}
