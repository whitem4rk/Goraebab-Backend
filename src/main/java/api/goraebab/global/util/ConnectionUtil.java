package api.goraebab.global.util;

import static api.goraebab.domain.remote.datebase.entity.DBMS.MYSQL;
import static api.goraebab.domain.remote.datebase.entity.DBMS.ORACLE;
import static api.goraebab.domain.remote.datebase.entity.DBMS.POSTGRESQL;
import static api.goraebab.domain.remote.datebase.entity.DBMS.SQLSERVER;

import api.goraebab.domain.remote.datebase.dto.StorageReqDto;
import api.goraebab.domain.remote.datebase.entity.DBMS;
import api.goraebab.domain.remote.datebase.entity.Storage;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ConnectionUtil {

  private static final String PING_URL = "http://%s:%d/_ping";
  private static final String OK_RESPONSE_BODY = "OK";

  private static final String DEFAULT_DATABASE_NAME = "goraebab";
  private static final Map<DBMS, String> DBMS_DRIVERS = Map.of(
      MYSQL, "com.mysql.cj.jdbc.Driver",
      POSTGRESQL, "org.postgresql.Driver",
      ORACLE, "oracle.jdbc.driver.OracleDriver",
      SQLSERVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver"
  );

  private static final Map<DBMS, String> DBMS_URL_PREFIX = Map.of(
      MYSQL, "jdbc:mysql://",
      POSTGRESQL, "jdbc:postgresql://",
      ORACLE, "jdbc:oracle:thin:@",
      SQLSERVER, "jdbc:sqlserver://"
  );

  public static DataSource createDataSource(StorageReqDto storageReqDto) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    String url = DBMS_URL_PREFIX.get(storageReqDto.getDbms()) +
        storageReqDto.getHost() + ":" + storageReqDto.getPort() + "/" + DEFAULT_DATABASE_NAME;

    dataSource.setDriverClassName(DBMS_DRIVERS.get(storageReqDto.getDbms()));
    dataSource.setUrl(url);
    dataSource.setUsername(storageReqDto.getUsername());
    dataSource.setPassword(storageReqDto.getPassword());

    return dataSource;
  }

  public static DataSource createDataSource(Storage storage) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    String url = DBMS_URL_PREFIX.get(storage.getDbms()) +
        storage.getHost() + ":" + storage.getPort() + "/" + DEFAULT_DATABASE_NAME;

    dataSource.setDriverClassName(DBMS_DRIVERS.get(storage.getDbms()));
    dataSource.setUrl(url);
    dataSource.setUsername(storage.getUsername());
    dataSource.setPassword(storage.getPassword());

    return dataSource;
  }

  public static boolean testPing(String host, int port) {
    String requestUrl = String.format(PING_URL, host, port);
    try {
      RestTemplate restTemplate = new RestTemplate();
      String response = restTemplate.getForObject(requestUrl, String.class);
      return Objects.equals(response, OK_RESPONSE_BODY);
    } catch (RestClientException e) {
      return false;
    }
  }

}
