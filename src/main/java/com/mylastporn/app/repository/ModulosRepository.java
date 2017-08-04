package com.mylastporn.app.repository;

import com.mylastporn.app.domain.Modulos;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Modulos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModulosRepository extends JpaRepository<Modulos,Long> {
    
}
