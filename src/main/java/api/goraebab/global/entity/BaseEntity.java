package api.goraebab.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

  @CreatedDate
  @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(0)")
  private LocalDateTime dateCreated;

  @CreatedDate
  @LastModifiedDate
  @Column(columnDefinition = "DATETIME(0)")
  private LocalDateTime dateUpdated;

}