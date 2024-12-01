package api.goraebab.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 *
 * <p>This configuration sets up the OpenAPI documentation for the application, providing metadata about
 * the API such as its title, description, and version. The documentation helps developers understand
 * and interact with the API effectively.</p>
 *
 * @author whitem4rk
 * @version 1.0
 * @see OpenAPI
 * @see Info
 */
@Configuration
public class SwaggerConfig {

  private static final String API_TITLE = "Goraebab API";
  private static final String API_CONTENT = "Goraebab helps Docker starters to easily configure Docker";
  private static final String API_VERSION = "1.0.0";


  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(apiInfo());
  }

  private Info apiInfo() {
    return new Info()
        .title(API_TITLE)
        .description(API_CONTENT)
        .version(API_VERSION);
  }

}