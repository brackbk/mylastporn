package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Tipo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRepository extends JpaRepository<Tipo,Long> {
    
}
