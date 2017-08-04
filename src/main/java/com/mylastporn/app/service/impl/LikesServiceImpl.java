package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.LikesService;
import com.mylastporn.app.domain.Likes;
import com.mylastporn.app.repository.LikesRepository;
import com.mylastporn.app.repository.search.LikesSearchRepository;
import com.mylastporn.app.service.dto.LikesDTO;
import com.mylastporn.app.service.mapper.LikesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Likes.
 */
@Service
@Transactional
public class LikesServiceImpl implements LikesService{

    private final Logger log = LoggerFactory.getLogger(LikesServiceImpl.class);

    private final LikesRepository likesRepository;

    private final LikesMapper likesMapper;

    private final LikesSearchRepository likesSearchRepository;

    public LikesServiceImpl(LikesRepository likesRepository, LikesMapper likesMapper, LikesSearchRepository likesSearchRepository) {
        this.likesRepository = likesRepository;
        this.likesMapper = likesMapper;
        this.likesSearchRepository = likesSearchRepository;
    }

    /**
     * Save a likes.
     *
     * @param likesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LikesDTO save(LikesDTO likesDTO) {
        log.debug("Request to save Likes : {}", likesDTO);
        Likes likes = likesMapper.toEntity(likesDTO);
        likes = likesRepository.save(likes);
        LikesDTO result = likesMapper.toDto(likes);
        likesSearchRepository.save(likes);
        return result;
    }

    /**
     *  Get all the likes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LikesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Likes");
        return likesRepository.findAll(pageable)
            .map(likesMapper::toDto);
    }

    /**
     *  Get one likes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LikesDTO findOne(Long id) {
        log.debug("Request to get Likes : {}", id);
        Likes likes = likesRepository.findOne(id);
        return likesMapper.toDto(likes);
    }

    /**
     *  Delete the  likes by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Likes : {}", id);
        likesRepository.delete(id);
        likesSearchRepository.delete(id);
    }

    /**
     * Search for the likes corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LikesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Likes for query {}", query);
        Page<Likes> result = likesSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(likesMapper::toDto);
    }
}
