package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.HistoriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Historia.
 */
public interface HistoriaService {

    /**
     * Save a historia.
     *
     * @param historiaDTO the entity to save
     * @return the persisted entity
     */
    HistoriaDTO save(HistoriaDTO historiaDTO);

    /**
     *  Get all the historias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HistoriaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" historia.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HistoriaDTO findOne(Long id);

    /**
     *  Delete the "id" historia.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the historia corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HistoriaDTO> search(String query, Pageable pageable);
}
