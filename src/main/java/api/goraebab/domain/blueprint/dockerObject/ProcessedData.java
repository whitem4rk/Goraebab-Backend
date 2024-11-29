package api.goraebab.domain.blueprint.dockerObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The data which is used to synchronize Docker with.
 * From CustomHost class to leaf, all the fields are following docker daemon API request arguments.
 * If you add new features, check API documents and follow JSON key and value format.
 * Also, add "custom" prefix and change key format using @JsonProperty
 */
@Getter
@NoArgsConstructor
public class ProcessedData {

  @JsonProperty("host")
  private List<CustomHost> customHost;


  @Builder
  public ProcessedData(List<CustomHost> customHost) {
    this.customHost = customHost;
  }

}
