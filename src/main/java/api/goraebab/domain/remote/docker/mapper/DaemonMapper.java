package api.goraebab.domain.remote.docker.mapper;

import api.goraebab.domain.remote.docker.dto.DaemonReqDto;
import api.goraebab.domain.remote.docker.dto.DaemonResDto;
import api.goraebab.domain.remote.docker.entity.Daemon;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link Daemon} entities and their corresponding DTOs.
 *
 * <p>This interface uses MapStruct to generate the implementation at compile time, ensuring efficient and type-safe mapping.</p>
 *
 * @author whitem4rk
 * @version 1.0
 */
@Mapper
public interface DaemonMapper {

  DaemonMapper INSTANCE = Mappers.getMapper(DaemonMapper.class);

  Daemon reqDtoToEntity(DaemonReqDto daemonReqDto);

  @Mapping(source = "id", target = "daemonId")
  DaemonResDto entityToResDto(Daemon daemon);

  List<DaemonResDto> entityListToResDtoList(List<Daemon> daemonList);

}
