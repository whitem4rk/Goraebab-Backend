package api.goraebab.domain.blueprint.dockerObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Custom config.
 *
 * @author whitem4rk
 * @version 1.0
 * @see ProcessedData
 */
@Getter
@NoArgsConstructor
public class CustomConfig {

  private String subnet;

  @Builder
  public CustomConfig(String subnet) {
    this.subnet = subnet;
  }
}
