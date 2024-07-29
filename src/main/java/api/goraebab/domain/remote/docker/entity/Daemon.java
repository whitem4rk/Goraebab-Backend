package api.goraebab.domain.remote.docker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
@Table(name = "daemon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Daemon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String host;

  @Column(nullable = false)
  private Integer port;

  @Column(nullable = false)
  private String name;

  @CreatedDate
  @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(0)")
  private LocalDateTime createdDate;

  @CreatedDate
  @LastModifiedDate
  @Column(nullable = false, columnDefinition = "DATETIME(0)")
  private LocalDateTime updatedDate;

  @Builder
  public Daemon(String host, Integer port, String name) {
    this.host = host;
    this.port = port;
    this.name = name;
  }

}
