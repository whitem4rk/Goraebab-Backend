package api.goraebab.domain.remote.harbor.service;

import api.goraebab.domain.remote.harbor.dto.HarborReqDto;
import api.goraebab.domain.remote.harbor.dto.HarborDetailResDto;
import api.goraebab.domain.remote.harbor.dto.HarborResDto;
import api.goraebab.domain.remote.harbor.entity.Harbor;
import api.goraebab.domain.remote.harbor.mapper.HarborMapper;
import api.goraebab.domain.remote.harbor.repository.HarborRepository;
import api.goraebab.global.util.ConnectionUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class HarborServiceImpl implements HarborService {

  private final HarborRepository harborRepository;

  @Override
  @Transactional(readOnly = true)
  public List<HarborResDto> getHarbors() {
    List<Harbor> harbors = harborRepository.findAll();

    return HarborMapper.INSTANCE.entityListToResDtoList(harbors);

  }

  @Override
  @Transactional(readOnly = true)
  public HarborDetailResDto getHarbor(Long harborId) {
    Harbor harbor = harborRepository.findById(harborId)
        .orElseThrow(() -> new NotFoundException("Harbor not found"));

    return HarborMapper.INSTANCE.entityToDetailResDto(harbor);
  }

  @Override
  @Transactional
  public void connectHarbor(HarborReqDto harborReqDto) {
    try {
      boolean connected = ConnectionUtil.testHarborPing(harborReqDto.getHost(), harborReqDto.getPort());

      if (!connected) {
        throw new RuntimeException("Cannot access Harbor. Check if Harbor is active and accessible");
      }

      try {
        ConnectionUtil.harborLogin(harborReqDto.getHost(), harborReqDto.getPort(), harborReqDto.getUsername(), harborReqDto.getPassword());
      } catch (Exception e) {
        throw new RuntimeException("Harbor login failed. Please check your credentials and try again.", e);
      }

      harborRepository.save(HarborMapper.INSTANCE.reqDtoToEntity(harborReqDto));
    } catch (Exception e) {
      throw new RuntimeException("An unexpected error occurred while connecting to Harbor", e);
    }

  }

  @Override
  @Transactional
  public void deleteHarbor(Long harborId) {
    harborRepository.deleteById(harborId);
  }

}
