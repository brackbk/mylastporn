package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.PaginaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Pagina.
 */
public interface PaginaService {

    /**
     * Save a pagina.
     *
     * @param paginaDTO the entity to save
     * @return the persisted entity
     */
    PaginaDTO save(PaginaDTO paginaDTO);

    /**
     *  Get all the paginas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaginaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pagina.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaginaDTO findOne(Long id);

    /**
     *  Delete the "id" pagina.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pagina corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaginaDTO> search(String query, Pageable pageable);
}
