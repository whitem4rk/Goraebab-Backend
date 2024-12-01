package api.goraebab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configuration class for application-level beans.
 *
 * <p>This class defines beans that are required across the application, ensuring centralized management and
 * reusability of these components.</p>
 *
 * @author whitem4rk
 * @version 1.0
 */
@Configuration
public class AppConfig {

  /**
   * Creates and returns a {@link RestClient} bean for making HTTP requests.
   *
   * @return a configured instance of {@link RestClient}.
   * @see api.goraebab.global.util.ConnectionUtil
   */
  @Bean
  public RestClient restClient() {
    return RestClient.create();
  }

}