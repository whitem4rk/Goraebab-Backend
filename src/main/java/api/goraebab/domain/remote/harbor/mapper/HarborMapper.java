package api.goraebab.domain.remote.harbor.mapper;

import api.goraebab.domain.remote.harbor.dto.HarborDetailResDto;
import api.goraebab.domain.remote.harbor.dto.HarborReqDto;
import api.goraebab.domain.remote.harbor.dto.HarborResDto;
import api.goraebab.domain.remote.harbor.entity.Harbor;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HarborMapper {

    HarborMapper INSTANCE = Mappers.getMapper(HarborMapper.class);

    @Mapping(source = "id", target = "harborId")
    HarborResDto entityToResDto(Harbor harbor);

    @Mapping(source = "id", target = "harborId")
    HarborDetailResDto entityToDetailResDto(Harbor harbor);

    Harbor reqDtoToEntity(HarborReqDto harborReqDto);

    @IterableMapping(elementTargetType = HarborResDto.class)
    List<HarborResDto> entityListToResDtoList(List<Harbor> harbors);

}
