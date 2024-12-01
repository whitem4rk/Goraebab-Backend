package api.goraebab.domain.remote.database.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

/**
 * The list of compatible DBMS.
 * You can check connection logic in config package.
 *
 * @author whitem4rk
 * @version 1.0
 * @see api.goraebab.config.JpaConfig
 */
public enum DBMS {
  MYSQL, MARIADB, POSTGRESQL, ORACLE, SQLSERVER;


  @JsonCreator
  public static DBMS fromDBMS(String val) {
    return Arrays.stream(values())
        .filter(type -> type.toString().equals(val))
        .findAny()
        .orElse(null);
  }

}
