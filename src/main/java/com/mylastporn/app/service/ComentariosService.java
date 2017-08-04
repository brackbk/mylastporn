package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.ComentariosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Comentarios.
 */
public interface ComentariosService {

    /**
     * Save a comentarios.
     *
     * @param comentariosDTO the entity to save
     * @return the persisted entity
     */
    ComentariosDTO save(ComentariosDTO comentariosDTO);

    /**
     *  Get all the comentarios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ComentariosDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" comentarios.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ComentariosDTO findOne(Long id);

    /**
     *  Delete the "id" comentarios.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the comentarios corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ComentariosDTO> search(String query, Pageable pageable);
}
