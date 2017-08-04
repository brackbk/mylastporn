package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Denuncias;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Denuncias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DenunciasRepository extends JpaRepository<Denuncias,Long> {

    @Query("select denuncias from Denuncias denuncias where denuncias.user.login = ?#{principal.username}")
    List<Denuncias> findByUserIsCurrentUser();
    
}
