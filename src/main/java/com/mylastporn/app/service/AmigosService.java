package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.AmigosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Amigos.
 */
public interface AmigosService {

    /**
     * Save a amigos.
     *
     * @param amigosDTO the entity to save
     * @return the persisted entity
     */
    AmigosDTO save(AmigosDTO amigosDTO);

    /**
     *  Get all the amigos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AmigosDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" amigos.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AmigosDTO findOne(Long id);

    /**
     *  Delete the "id" amigos.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the amigos corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AmigosDTO> search(String query, Pageable pageable);
}
