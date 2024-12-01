package api.goraebab.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract base class for entities, providing audit fields for creation and modification timestamps.
 *
 * <p>This class is annotated as a {@link MappedSuperclass}, meaning its fields are inherited by subclasses
 * and mapped to the corresponding database table columns.</p>
 *
 * <p>Note: For compatibility, columnDefinition about time fields was set to TIMESTAMP(0).
 * This method was the most universal. However, since the table definition is done
 * according to dbms through init.sql, it is not a big problem,
 * and if a dbms type is added, it must be set accordingly.</p>
 *
 * @author whitem4rk
 * @version 1.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

  @CreatedDate
  @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP(0)")
  private LocalDateTime dateCreated;

  @CreatedDate
  @LastModifiedDate
  @Column(columnDefinition = "TIMESTAMP(0)")
  private LocalDateTime dateUpdated;

}