package api.goraebab.domain.blueprint.service;

import api.goraebab.domain.blueprint.dto.BridgeInfoDto;
import api.goraebab.domain.blueprint.dto.ContainerInfoDto;
import api.goraebab.domain.blueprint.dto.HostInfoDto;
import api.goraebab.domain.blueprint.dto.ParsedDataDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HtmlParserServiceImpl implements HtmlParserService {

    @Override
    public ParsedDataDto parseHtml(String htmlContent) {
        Document document = Jsoup.parse(htmlContent);

        // Host 정보 추출
        List<HostInfoDto> hosts = new ArrayList<>();
        Elements hostElements = document.select(".flex.flex-col.items-center.react-draggable");

        for(Element hostElement : hostElements) {
            String hostName = hostElement.selectFirst(".flex.items-center.justify-center.w-full.space-x-2 .text-sm.font-semibold").text();
            String hostIpAddress = hostElement.selectFirst(".text-lg.font-semibold").text();

            Element hostTypeElement = hostElement.selectFirst(".absolute.text-xs.font-semibold");
            String hostType = hostTypeElement != null ? hostTypeElement.text() : "";

            // Bridge 정보 추출
            List<BridgeInfoDto> bridges = new ArrayList<>();
            Elements bridgeElements = hostElement.select(".flex.flex-col.items-center.border.bg-white.rounded-lg");

            for(Element bridgeElement : bridgeElements) {
                Element bridgeInfoElement = bridgeElement.selectFirst(".w-full.text-center.text-blue_2");
                String bridgeInfo = bridgeInfoElement != null ? bridgeInfoElement.text() : "";
                BridgeInfoDto bridgeInfoDto = extractBridgeInfo(bridgeInfo);

                if (bridgeInfoDto.isValid()) {
                    // Container 정보 추출
                    List<ContainerInfoDto> containers = new ArrayList<>();
                    Elements containerElements = bridgeElement.select(".flex.justify-between.items-center.p-2.mb-2");

                    for (Element containerElement : containerElements) {
                        String containerName = containerElement.selectFirst("span").text();
                        String containerIpAddress = containerElement.select("span").get(1).text();

                        String containerStatus = "Unknown"; // 기본 상태는 Unknown
                        Elements statusElements = containerElement.select("span");

                        for (Element statusElement : statusElements) {
                            if (statusElement.text().equals("Running")) {
                                containerStatus = "Running"; // 상태가 Running일 경우
                                break;
                            } else if (statusElement.text().equals("Stopped")) {
                                containerStatus = "Stopped"; // 상태가 Stopped일 경우
                                break;
                            }
                        }

                        String image = containerElement.select("span.text-transparent").get(0).text();

                        List<String> volumes = new ArrayList<>();
                        Elements volumeElements = containerElement.select("p.text-transparent");
                        for (Element volumeElement : volumeElements) {
                            volumes.add(volumeElement.text());
                        }

                        containers.add(new ContainerInfoDto(containerName, containerIpAddress, containerStatus, image, volumes));
                    }

                    bridgeInfoDto.setContainers(containers);
                    bridges.add(bridgeInfoDto);
                }
            }

            hosts.add(new HostInfoDto(hostName, hostIpAddress, hostType, bridges));
        }

        return new ParsedDataDto(hosts);
    }

    private BridgeInfoDto extractBridgeInfo(String bridgeInfo) {
        String[] parts = bridgeInfo.split(":");

        if (parts.length == 2) {
            String bridgeName = parts[0].trim();
            String bridgeGateway = parts[1].trim();

            return new BridgeInfoDto(bridgeName, bridgeGateway);
        }
        return new BridgeInfoDto("", "");
    }

}