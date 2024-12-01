 package api.goraebab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

 /**
  * Configuration class for setting up Cross-Origin Resource Sharing (CORS) rules.
  *
  * <p>This configuration allows the application to handle requests from different origins,
  * enabling cross-origin communication for frontend-backend interactions in a secure and configurable way.</p>
  *
  * <p>Note: While this configuration is useful for rapid development and testing, a more restrictive policy
  * is recommended for production environments to prevent potential security risks such as unauthorized
  * access from malicious origins.</p>
  *
  * <p>For more details about CORS in Spring, refer to the <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-cors">Spring Documentation</a>.</p>
  *
  * @author whitem4rk
  * @version 1.0
  */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowedMethods("*")
        .allowCredentials(true);
  }

}
