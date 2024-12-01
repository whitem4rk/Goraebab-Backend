package api.goraebab.domain.remote.docker.entity;

import api.goraebab.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Daemon, which typically refers to a background process running on a
 * specific host and port with a given name. This class is mapped to the "daemon" table in the
 * database.
 *
 * <p>This class extends {@code BaseEntity}, which may include common fields like
 * creation and update timestamps.
 *
 * @author whitem4rk
 * @version 1.0
 * @see BaseEntity
 */
@Getter
@Entity
@Table(name = "daemon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Daemon extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String host;

  @Column(nullable = false)
  private Integer port;

  @Column(nullable = false)
  private String name;

  @Builder
  public Daemon(String host, Integer port, String name) {
    this.host = host;
    this.port = port;
    this.name = name;
  }

}
