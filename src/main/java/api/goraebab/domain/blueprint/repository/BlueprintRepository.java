package api.goraebab.domain.blueprint.repository;

import api.goraebab.domain.blueprint.entity.Blueprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlueprintRepository extends JpaRepository<Blueprint, Long> {

}
