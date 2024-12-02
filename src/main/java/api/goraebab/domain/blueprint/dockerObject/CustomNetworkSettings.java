package api.goraebab.domain.blueprint.dockerObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Custom network settings.
 *
 * @author whitem4rk
 * @version 1.0
 * @see ProcessedData
 */
@Getter
@NoArgsConstructor
public class CustomNetworkSettings {

  private String gateway;

  private String ipAddress;

  @Builder
  public CustomNetworkSettings(String gateway, String ipAddress) {
    this.gateway = gateway;
    this.ipAddress = ipAddress;
  }
}
