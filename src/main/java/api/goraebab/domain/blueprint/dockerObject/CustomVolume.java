package api.goraebab.domain.blueprint.dockerObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomVolume {

  private String name;

  private String driver;

  private String mode;


  @Builder
  public CustomVolume(String name, String driver, String mode) {
    this.name = name;
    this.driver = driver;
    this.mode = mode;
  }

}
