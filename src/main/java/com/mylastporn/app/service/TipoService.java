package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.TipoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tipo.
 */
public interface TipoService {

    /**
     * Save a tipo.
     *
     * @param tipoDTO the entity to save
     * @return the persisted entity
     */
    TipoDTO save(TipoDTO tipoDTO);

    /**
     *  Get all the tipos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TipoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tipo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TipoDTO findOne(Long id);

    /**
     *  Delete the "id" tipo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TipoDTO> search(String query, Pageable pageable);
}
