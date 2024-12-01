package api.goraebab.domain.remote.database.repository;

import api.goraebab.domain.remote.database.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Storage} entities.
 *
 * <p>Extends the {@link JpaRepository} interface to provide CRUD operations and
 * additional query methods for {@link Storage} objects.</p>
 *
 * <p>This interface is a Spring Data JPA repository and will be implemented automatically
 * by Spring at runtime.</p>
 *
 * <p>Note: This repository currently does not include QueryDSL integration.
 * If you need to perform complex dynamic queries with type safety, you may consider
 * adding a custom repository interface and implementing QueryDSL functionality.</p>
 *
 * @author whitem4rk
 * @version 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface StorageRepository extends JpaRepository<Storage, Long> {

}
