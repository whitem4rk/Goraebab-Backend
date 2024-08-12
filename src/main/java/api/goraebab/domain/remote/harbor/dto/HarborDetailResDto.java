package api.goraebab.domain.remote.harbor.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HarborDetailResDto {

    private Long harborId;

    private String name;

    private String host;

    private Integer port;

    private String username;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

    @Builder
    public HarborDetailResDto(Long harborId, String name, String host, Integer port, String username,
        LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.harborId = harborId;
        this.name = name;
        this.host = host;
        this.port = port;
        this.username = username;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }
}
