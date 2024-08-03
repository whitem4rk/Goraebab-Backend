package api.goraebab.domain.config;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  private final JpaProperties jpaProperties;

  private static final String HIBERNATE_DIALECT = "hibernate.dialect";
  private static final String MYSQL_DIALECT = "org.hibernate.dialect.MySQL8Dialect";
  private static final String POSTGRESQL_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
  private static final String ORACLE_DIALECT = "org.hibernate.dialect.Oracle12cDialect";
  private static final String SQLSERVER_DIALECT = "org.hibernate.dialect.SQLServerDialect";

  public JpaConfig(JpaProperties jpaProperties) {
    this.jpaProperties = jpaProperties;
  }

  @PostConstruct
  public void init() {
    configureJpaDialect();
  }

  private void configureJpaDialect() {
    if (datasourceUrl.contains("mysql")) {
      jpaProperties.getProperties().put(HIBERNATE_DIALECT, MYSQL_DIALECT);
    } else if (datasourceUrl.contains("postgresql")) {
      jpaProperties.getProperties().put(HIBERNATE_DIALECT, POSTGRESQL_DIALECT);
    } else if (datasourceUrl.contains("oracle")) {
      jpaProperties.getProperties().put(HIBERNATE_DIALECT, ORACLE_DIALECT);
    } else if (datasourceUrl.contains("sqlserver")) {
      jpaProperties.getProperties().put(HIBERNATE_DIALECT, SQLSERVER_DIALECT);
    } else {
      throw new IllegalArgumentException("Unsupported Database Type");
    }
  }

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .url(datasourceUrl)
        .username(username)
        .password(password)
        .driverClassName(driverClassName)
        .build();
  }

}
