package api.goraebab.domain.remote.database.entity;

import api.goraebab.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a storage entity that contains information about a database connection.
 *
 * <p>Instances of this class are typically used to persist and retrieve storage configurations from
 * the database.
 *
 * @author whitem4rk
 * @version 1.0
 * @see BaseEntity
 */
@Getter
@Entity
@Table(name = "storage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Storage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String host;

  @Column(nullable = false)
  private Integer port;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DBMS dbms;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  /**
   * Constructs a new {@link Storage} instance using the specified parameters.
   *
   * @param host the host address of the database server.
   * @param port the port number of the database server.
   * @param dbms the type of database management system.
   * @param name the custom name of the connection.
   * @param username the username for database authentication.
   * @param password the password for database authentication.
   */
  @Builder
  public Storage(
      String host, Integer port, DBMS dbms, String name, String username, String password) {
    this.host = host;
    this.port = port;
    this.dbms = dbms;
    this.name = name;
    this.username = username;
    this.password = password;
  }
}
