package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Foto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Foto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoRepository extends JpaRepository<Foto,Long> {

    @Query("select foto from Foto foto where foto.user.login = ?#{principal.username}")
    List<Foto> findByUserIsCurrentUser();
    
    @Query("select distinct foto from Foto foto left join fetch foto.tags left join fetch foto.paginas")
    List<Foto> findAllWithEagerRelationships();

    @Query("select foto from Foto foto left join fetch foto.tags left join fetch foto.paginas where foto.id =:id")
    Foto findOneWithEagerRelationships(@Param("id") Long id);
    
}
