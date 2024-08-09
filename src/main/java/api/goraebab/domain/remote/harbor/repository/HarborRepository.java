package api.goraebab.domain.remote.harbor.repository;

import api.goraebab.domain.remote.harbor.entity.Harbor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarborRepository extends JpaRepository<Harbor, Long> {
}
