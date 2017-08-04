package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.DenunciasDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Denuncias.
 */
public interface DenunciasService {

    /**
     * Save a denuncias.
     *
     * @param denunciasDTO the entity to save
     * @return the persisted entity
     */
    DenunciasDTO save(DenunciasDTO denunciasDTO);

    /**
     *  Get all the denuncias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DenunciasDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" denuncias.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DenunciasDTO findOne(Long id);

    /**
     *  Delete the "id" denuncias.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the denuncias corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DenunciasDTO> search(String query, Pageable pageable);
}
