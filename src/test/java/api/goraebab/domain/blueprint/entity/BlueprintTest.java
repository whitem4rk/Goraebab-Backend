package api.goraebab.domain.blueprint.entity;

import static org.assertj.core.api.Assertions.assertThat;

import api.goraebab.domain.remote.database.entity.DBMS;
import api.goraebab.domain.remote.database.entity.Storage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlueprintTest {

  private static final String OLD_NAME = "old blueprint";
  private static final String OLD_DATA = "{\"old key\":\"old value\"}";
  private static final Boolean OLD_IS_REMOTE = Boolean.FALSE;
  private static final String NEW_NAME = "new blueprint";
  private static final String NEW_DATA = "{\"new key\":\"new value\"}";

  @Test
  @DisplayName(
      "Given valid inputs, when modifying a Blueprint, then it should update its fields correctly")
  void testValidModifyBlueprint() {
    // given
    Blueprint blueprint =
        Blueprint.builder()
            .name(OLD_NAME)
            .data(OLD_DATA)
            .isRemote(OLD_IS_REMOTE)
            .storage(null)
            .build();

    // when
    blueprint.modify(NEW_NAME, NEW_DATA);

    // then
    assertThat(blueprint.getName()).isEqualTo(NEW_NAME);
    assertThat(blueprint.getData()).isEqualTo(NEW_DATA);
  }

  @Test
  @DisplayName(
      "Given any null inputs, when modifying a Blueprint, then it should update only not null fields")
  void testNullModifyBlueprint() {
    // given
    Blueprint blueprint =
        Blueprint.builder()
            .name(OLD_NAME)
            .data(OLD_DATA)
            .isRemote(OLD_IS_REMOTE)
            .storage(null)
            .build();

    // when
    blueprint.modify(null, null);

    // then
    assertThat(blueprint.getName()).isEqualTo(OLD_NAME);
    assertThat(blueprint.getData()).isEqualTo(OLD_DATA);
  }

  @Test
  @DisplayName("Given a Blueprint, when setAsRemote is called, then isRemote should be true")
  void testSetAsRemote() {
    // given
    Blueprint blueprint =
        Blueprint.builder()
            .name(OLD_NAME)
            .data(OLD_DATA)
            .isRemote(OLD_IS_REMOTE)
            .storage(null)
            .build();

    // when
    blueprint.setAsRemote();

    // then
    assertThat(blueprint.getIsRemote()).isTrue();
  }

  @Test
  @DisplayName(
      "Given valid inputs including , when creating a Blueprint using Builder, then it should be initialized correctly")
  void testBlueprintBuilder() {
    // given
    Storage storage =
        Storage.builder()
            .name("storage name")
            .host("localhost")
            .port(3306)
            .dbms(DBMS.MYSQL)
            .username("root")
            .password("password")
            .build();

    // when
    Blueprint blueprint =
        Blueprint.builder()
            .name(OLD_NAME)
            .data(OLD_DATA)
            .isRemote(OLD_IS_REMOTE)
            .storage(storage)
            .build();

    // then
    assertThat(blueprint.getName()).isEqualTo(OLD_NAME);
    assertThat(blueprint.getData()).isEqualTo(OLD_DATA);
    assertThat(blueprint.getIsRemote()).isEqualTo(OLD_IS_REMOTE);
    assertThat(blueprint.getStorage()).isSameAs(storage);
  }

  @Test
  @DisplayName(
      "Given a Blueprint, when setting Storage, then it should update the Storage field correctly")
  void testSetStorage() {
    // given
    Storage storage =
        Storage.builder()
            .name("storage name")
            .host("localhost")
            .port(3306)
            .dbms(DBMS.MYSQL)
            .username("root")
            .password("password")
            .build();

    Blueprint blueprint =
        Blueprint.builder()
            .name(OLD_NAME)
            .data(OLD_DATA)
            .isRemote(OLD_IS_REMOTE)
            .storage(storage)
            .build();

    // when
    blueprint.setStorage(storage);

    // then
    assertThat(blueprint.getStorage()).isEqualTo(storage);
  }
}
