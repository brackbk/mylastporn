package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Video;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {

    @Query("select video from Video video where video.user.login = ?#{principal.username}")
    List<Video> findByUserIsCurrentUser();
    
    @Query("select distinct video from Video video left join fetch video.tags left join fetch video.paginas")
    List<Video> findAllWithEagerRelationships();

    @Query("select video from Video video left join fetch video.tags left join fetch video.paginas where video.id =:id")
    Video findOneWithEagerRelationships(@Param("id") Long id);
    
}
