package com.mylastporn.app.repository;

import com.mylastporn.app.domain.SeguidoresPagina;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the SeguidoresPagina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeguidoresPaginaRepository extends JpaRepository<SeguidoresPagina,Long> {

    @Query("select seguidores_pagina from SeguidoresPagina seguidores_pagina where seguidores_pagina.user.login = ?#{principal.username}")
    List<SeguidoresPagina> findByUserIsCurrentUser();
    
}
