package api.goraebab.domain.blueprint.dockerObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Custom image.
 *
 * @author whitem4rk
 * @version 1.0
 * @see ProcessedData
 */
@Getter
@NoArgsConstructor
public class CustomImage {

  private String imageId;

  private String name;

  private String tag;


  @Builder
  public CustomImage(String imageId, String name, String tag) {
    this.imageId = imageId;
    this.name = name;
    this.tag = tag;
  }

}