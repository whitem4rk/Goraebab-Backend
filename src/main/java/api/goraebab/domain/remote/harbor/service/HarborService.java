package api.goraebab.domain.remote.harbor.service;

import api.goraebab.domain.remote.harbor.dto.HarborReqDto;
import api.goraebab.domain.remote.harbor.dto.HarborDetailResDto;
import api.goraebab.domain.remote.harbor.dto.HarborResDto;
import java.util.List;

public interface HarborService {

    List<HarborResDto> getHarbors();

    HarborDetailResDto getHarbor(Long harborId);

    void connectHarbor(HarborReqDto harborReqDto);

    void deleteHarbor(Long harborId);

}
