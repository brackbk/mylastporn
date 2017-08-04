package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Amigos;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Amigos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmigosRepository extends JpaRepository<Amigos,Long> {

    @Query("select amigos from Amigos amigos where amigos.user.login = ?#{principal.username}")
    List<Amigos> findByUserIsCurrentUser();
    
}
