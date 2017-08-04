package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Comentarios;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Comentarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios,Long> {

    @Query("select comentarios from Comentarios comentarios where comentarios.user.login = ?#{principal.username}")
    List<Comentarios> findByUserIsCurrentUser();
    
}
