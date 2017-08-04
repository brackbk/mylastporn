package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Favoritos;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Favoritos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos,Long> {

    @Query("select favoritos from Favoritos favoritos where favoritos.user.login = ?#{principal.username}")
    List<Favoritos> findByUserIsCurrentUser();
    
}
