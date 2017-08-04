package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.LikesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Likes.
 */
public interface LikesService {

    /**
     * Save a likes.
     *
     * @param likesDTO the entity to save
     * @return the persisted entity
     */
    LikesDTO save(LikesDTO likesDTO);

    /**
     *  Get all the likes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LikesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" likes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LikesDTO findOne(Long id);

    /**
     *  Delete the "id" likes.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the likes corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LikesDTO> search(String query, Pageable pageable);
}
