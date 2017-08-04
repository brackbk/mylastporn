package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Pagina;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Pagina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaginaRepository extends JpaRepository<Pagina,Long> {

    @Query("select pagina from Pagina pagina where pagina.user.login = ?#{principal.username}")
    List<Pagina> findByUserIsCurrentUser();
    
    @Query("select distinct pagina from Pagina pagina left join fetch pagina.tags")
    List<Pagina> findAllWithEagerRelationships();

    @Query("select pagina from Pagina pagina left join fetch pagina.tags where pagina.id =:id")
    Pagina findOneWithEagerRelationships(@Param("id") Long id);
    
}
