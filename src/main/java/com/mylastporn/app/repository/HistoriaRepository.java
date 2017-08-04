package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Historia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Historia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriaRepository extends JpaRepository<Historia,Long> {

    @Query("select historia from Historia historia where historia.user.login = ?#{principal.username}")
    List<Historia> findByUserIsCurrentUser();
    
    @Query("select distinct historia from Historia historia left join fetch historia.tags left join fetch historia.paginas")
    List<Historia> findAllWithEagerRelationships();

    @Query("select historia from Historia historia left join fetch historia.tags left join fetch historia.paginas where historia.id =:id")
    Historia findOneWithEagerRelationships(@Param("id") Long id);
    
}
