package api.goraebab.domain.blueprint.dockerObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Custom port.
 *
 * @author whitem4rk
 * @version 1.0
 * @see ProcessedData
 */
@Getter
@NoArgsConstructor
public class CustomPort {

  private Integer privatePort;

  private Integer publicPort;


  @Builder
  public CustomPort(int privatePort, int publicPort) {
    this.privatePort = privatePort;
    this.publicPort = publicPort;
  }

}