package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Visibilidade;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Visibilidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisibilidadeRepository extends JpaRepository<Visibilidade,Long> {
    
}
