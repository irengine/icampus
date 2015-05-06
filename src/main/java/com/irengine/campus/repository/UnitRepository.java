package com.irengine.campus.repository;

import com.irengine.campus.domain.Unit;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Unit entity.
 */
public interface UnitRepository extends JpaRepository<Unit,Long> {

    @Query("select unit from Unit unit where unit.manager.login = ?#{principal.username}")
    List<Unit> findAllForCurrentUser();

}
