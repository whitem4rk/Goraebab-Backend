package api.goraebab.domain.blueprint.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO that includes the success status of the container synchronization request contained in the original request.
 * Note that Whether the synchronization request succeed or not, this DTO is returned.
 *
 * @author whitem4rk
 * @version 1.0
 */
@Getter
@NoArgsConstructor
public class SyncResultDto {

  private List<Map<String, Object>> failedContainers;

  private List<Map<String, Object>> succeededContainers;


  @Builder
  public SyncResultDto(List<Map<String, Object>> failedContainers,
      List<Map<String, Object>> succeededContainers) {
    this.failedContainers = failedContainers;
    this.succeededContainers = succeededContainers;
  }
}
