package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.VisibilidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Visibilidade.
 */
public interface VisibilidadeService {

    /**
     * Save a visibilidade.
     *
     * @param visibilidadeDTO the entity to save
     * @return the persisted entity
     */
    VisibilidadeDTO save(VisibilidadeDTO visibilidadeDTO);

    /**
     *  Get all the visibilidades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VisibilidadeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" visibilidade.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VisibilidadeDTO findOne(Long id);

    /**
     *  Delete the "id" visibilidade.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the visibilidade corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VisibilidadeDTO> search(String query, Pageable pageable);
}
