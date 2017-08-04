package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.TagsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tags.
 */
public interface TagsService {

    /**
     * Save a tags.
     *
     * @param tagsDTO the entity to save
     * @return the persisted entity
     */
    TagsDTO save(TagsDTO tagsDTO);

    /**
     *  Get all the tags.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TagsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tags.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TagsDTO findOne(Long id);

    /**
     *  Delete the "id" tags.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tags corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TagsDTO> search(String query, Pageable pageable);
}
