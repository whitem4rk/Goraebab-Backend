package api.goraebab.domain.blueprint.mapper;

import api.goraebab.domain.blueprint.entity.Blueprint;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * A RowMapper implementation that maps a row from the result set to a {@link Blueprint} object.
 *
 * <p>This implementation extracts the "data" column from the
 * result set and maps it to the {@code data} field in the {@link Blueprint} object.</p>
 *
 * @author whitem4rk
 * @version 1.0
 * @see org.springframework.jdbc.core.RowMapper
 */
public class BlueprintRowMapper implements RowMapper<Blueprint> {

  private static final String BLUEPRINT_DATA_COLUMN_NAME = "data";

  @Override
  public Blueprint mapRow(ResultSet rs, int rowNum) throws SQLException {
    Blueprint blueprint = Blueprint.builder()
        .data(rs.getString(BLUEPRINT_DATA_COLUMN_NAME))
        .build();

    return blueprint;
  }

}
