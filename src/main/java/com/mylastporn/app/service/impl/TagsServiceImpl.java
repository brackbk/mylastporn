package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.TagsService;
import com.mylastporn.app.domain.Tags;
import com.mylastporn.app.repository.TagsRepository;
import com.mylastporn.app.repository.search.TagsSearchRepository;
import com.mylastporn.app.service.dto.TagsDTO;
import com.mylastporn.app.service.mapper.TagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tags.
 */
@Service
@Transactional
public class TagsServiceImpl implements TagsService{

    private final Logger log = LoggerFactory.getLogger(TagsServiceImpl.class);

    private final TagsRepository tagsRepository;

    private final TagsMapper tagsMapper;

    private final TagsSearchRepository tagsSearchRepository;

    public TagsServiceImpl(TagsRepository tagsRepository, TagsMapper tagsMapper, TagsSearchRepository tagsSearchRepository) {
        this.tagsRepository = tagsRepository;
        this.tagsMapper = tagsMapper;
        this.tagsSearchRepository = tagsSearchRepository;
    }

    /**
     * Save a tags.
     *
     * @param tagsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TagsDTO save(TagsDTO tagsDTO) {
        log.debug("Request to save Tags : {}", tagsDTO);
        Tags tags = tagsMapper.toEntity(tagsDTO);
        tags = tagsRepository.save(tags);
        TagsDTO result = tagsMapper.toDto(tags);
        tagsSearchRepository.save(tags);
        return result;
    }

    /**
     *  Get all the tags.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TagsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");
        return tagsRepository.findAll(pageable)
            .map(tagsMapper::toDto);
    }

    /**
     *  Get one tags by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TagsDTO findOne(Long id) {
        log.debug("Request to get Tags : {}", id);
        Tags tags = tagsRepository.findOne(id);
        return tagsMapper.toDto(tags);
    }

    /**
     *  Delete the  tags by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tags : {}", id);
        tagsRepository.delete(id);
        tagsSearchRepository.delete(id);
    }

    /**
     * Search for the tags corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TagsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tags for query {}", query);
        Page<Tags> result = tagsSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(tagsMapper::toDto);
    }
}
