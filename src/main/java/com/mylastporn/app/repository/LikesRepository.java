package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Likes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Likes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {

    @Query("select likes from Likes likes where likes.user.login = ?#{principal.username}")
    List<Likes> findByUserIsCurrentUser();
    
}
