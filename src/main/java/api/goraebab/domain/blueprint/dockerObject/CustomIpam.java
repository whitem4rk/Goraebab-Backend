package api.goraebab.domain.blueprint.dockerObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Custom ipam.
 *
 * @author whitem4rk
 * @version 1.0
 * @see ProcessedData
 */
@Getter
@NoArgsConstructor
public class CustomIpam {

  @JsonProperty("config")
  private List<CustomConfig> customConfig;


  @Builder
  public CustomIpam(List<CustomConfig> customConfig) {
    this.customConfig = customConfig;
  }

}
