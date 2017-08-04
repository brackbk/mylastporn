package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.FotoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Foto.
 */
public interface FotoService {

    /**
     * Save a foto.
     *
     * @param fotoDTO the entity to save
     * @return the persisted entity
     */
    FotoDTO save(FotoDTO fotoDTO);

    /**
     *  Get all the fotos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FotoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" foto.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FotoDTO findOne(Long id);

    /**
     *  Delete the "id" foto.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the foto corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FotoDTO> search(String query, Pageable pageable);
}
