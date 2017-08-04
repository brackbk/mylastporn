package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.FavoritosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Favoritos.
 */
public interface FavoritosService {

    /**
     * Save a favoritos.
     *
     * @param favoritosDTO the entity to save
     * @return the persisted entity
     */
    FavoritosDTO save(FavoritosDTO favoritosDTO);

    /**
     *  Get all the favoritos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FavoritosDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" favoritos.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FavoritosDTO findOne(Long id);

    /**
     *  Delete the "id" favoritos.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the favoritos corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FavoritosDTO> search(String query, Pageable pageable);
}
