package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.ModulosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Modulos.
 */
public interface ModulosService {

    /**
     * Save a modulos.
     *
     * @param modulosDTO the entity to save
     * @return the persisted entity
     */
    ModulosDTO save(ModulosDTO modulosDTO);

    /**
     *  Get all the modulos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ModulosDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" modulos.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ModulosDTO findOne(Long id);

    /**
     *  Delete the "id" modulos.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the modulos corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ModulosDTO> search(String query, Pageable pageable);
}
