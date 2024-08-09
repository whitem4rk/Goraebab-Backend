package api.goraebab.domain.remote.harbor.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HarborResDto {

    private Long harborId;

    private String host;

    private String name;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;


    @Builder
    public HarborResDto(Long harborId, String host, String name,
        LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.harborId = harborId;
        this.host = host;
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

}
