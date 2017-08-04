package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.SeguidoresPaginaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SeguidoresPagina.
 */
public interface SeguidoresPaginaService {

    /**
     * Save a seguidoresPagina.
     *
     * @param seguidoresPaginaDTO the entity to save
     * @return the persisted entity
     */
    SeguidoresPaginaDTO save(SeguidoresPaginaDTO seguidoresPaginaDTO);

    /**
     *  Get all the seguidoresPaginas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeguidoresPaginaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" seguidoresPagina.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeguidoresPaginaDTO findOne(Long id);

    /**
     *  Delete the "id" seguidoresPagina.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the seguidoresPagina corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeguidoresPaginaDTO> search(String query, Pageable pageable);
}
