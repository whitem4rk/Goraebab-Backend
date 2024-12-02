package api.goraebab.domain.blueprint.repository;

import api.goraebab.domain.blueprint.entity.Blueprint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Blueprint} entities.
 *
 * <p>Extends the {@link JpaRepository} interface to provide CRUD operations and additional query
 * methods for {@link Blueprint} objects.
 *
 * <p>This interface is a Spring Data JPA repository and will be implemented automatically by Spring
 * at runtime.
 *
 * <p>Note: This repository currently does not include QueryDSL integration. If you need to perform
 * complex dynamic queries with type safety, you may consider adding a custom repository interface
 * and implementing QueryDSL functionality.
 *
 * @author whitem4rk
 * @version 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface BlueprintRepository extends JpaRepository<Blueprint, Long> {

  List<Blueprint> findByStorageId(Long storageId);

  Blueprint findByStorageIdAndId(Long storageId, Long blueprintId);
}
