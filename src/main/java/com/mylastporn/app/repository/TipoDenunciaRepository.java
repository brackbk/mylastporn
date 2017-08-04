package com.mylastporn.app.repository;

import com.mylastporn.app.domain.TipoDenuncia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoDenuncia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDenunciaRepository extends JpaRepository<TipoDenuncia,Long> {
    
}
