package api.goraebab.domain.blueprint.entity;

import api.goraebab.domain.remote.database.entity.Storage;
import api.goraebab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a blueprint entity with information.
 *
 * @author whitem4rk
 * @version 1.0
 */
@Getter
@Entity
@Table(name = "blueprint")
@NoArgsConstructor
public class Blueprint extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, columnDefinition = "json")
  private String data;

  @Column
  private Boolean isRemote = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "storage_id")
  private Storage storage;

  public void setStorage(Storage storage) {
    this.storage = storage;
  }

  public void setAsRemote() {
    this.isRemote = true;
  }

  /**
   * Modify.
   *
   * @param name the blueprint name
   * @param data the blueprint JSON format with escape handling applied.
   */
  public void modify(String name, String data) {
    if (name != null) {
      this.name = name;
    }
    if (data != null) {
      this.data = data;
    }
  }

  /**
   * Constructs a new instance
   *
   * @param name     the blueprint name
   * @param data     the blueprint JSON format with escape handling applied.
   * @param isRemote whether the blueprint is copied from remote database
   * @param storage  the storage to be stored
   */
  @Builder
  public Blueprint(String name, String data, Boolean isRemote, Storage storage) {
    this.name = name;
    this.data = data;
    this.isRemote = isRemote;
    this.storage = storage;
  }
}
