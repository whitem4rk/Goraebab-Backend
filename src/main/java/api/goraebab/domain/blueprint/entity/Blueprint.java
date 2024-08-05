package api.goraebab.domain.blueprint.entity;

import api.goraebab.domain.remote.database.entity.Storage;
import api.goraebab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "blueprint")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blueprint extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Lob
  @Column(nullable = false)
  private String data;

  @Column(nullable = false)
  private Boolean isRemote;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "storage_id")
  private Storage storage;

  public void setAsRemote() {
    this.isRemote = true;
  }

  public void setAsLocal() {
    this.isRemote = false;
  }

  public void modify(String name, String data) {
    if (name != null) {
      this.name = name;
    }
    if (data != null) {
      this.data = data;
    }
  }

  @Builder
  public Blueprint(String name, String data, Storage storage) {
    this.name = name;
    this.data = data;
    this.storage = storage;
  }

}
