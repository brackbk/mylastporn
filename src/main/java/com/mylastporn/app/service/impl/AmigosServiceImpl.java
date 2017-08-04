package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.AmigosService;
import com.mylastporn.app.domain.Amigos;
import com.mylastporn.app.repository.AmigosRepository;
import com.mylastporn.app.repository.search.AmigosSearchRepository;
import com.mylastporn.app.service.dto.AmigosDTO;
import com.mylastporn.app.service.mapper.AmigosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Amigos.
 */
@Service
@Transactional
public class AmigosServiceImpl implements AmigosService{

    private final Logger log = LoggerFactory.getLogger(AmigosServiceImpl.class);

    private final AmigosRepository amigosRepository;

    private final AmigosMapper amigosMapper;

    private final AmigosSearchRepository amigosSearchRepository;

    public AmigosServiceImpl(AmigosRepository amigosRepository, AmigosMapper amigosMapper, AmigosSearchRepository amigosSearchRepository) {
        this.amigosRepository = amigosRepository;
        this.amigosMapper = amigosMapper;
        this.amigosSearchRepository = amigosSearchRepository;
    }

    /**
     * Save a amigos.
     *
     * @param amigosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AmigosDTO save(AmigosDTO amigosDTO) {
        log.debug("Request to save Amigos : {}", amigosDTO);
        Amigos amigos = amigosMapper.toEntity(amigosDTO);
        amigos = amigosRepository.save(amigos);
        AmigosDTO result = amigosMapper.toDto(amigos);
        amigosSearchRepository.save(amigos);
        return result;
    }

    /**
     *  Get all the amigos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmigosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Amigos");
        return amigosRepository.findAll(pageable)
            .map(amigosMapper::toDto);
    }

    /**
     *  Get one amigos by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AmigosDTO findOne(Long id) {
        log.debug("Request to get Amigos : {}", id);
        Amigos amigos = amigosRepository.findOne(id);
        return amigosMapper.toDto(amigos);
    }

    /**
     *  Delete the  amigos by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Amigos : {}", id);
        amigosRepository.delete(id);
        amigosSearchRepository.delete(id);
    }

    /**
     * Search for the amigos corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmigosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Amigos for query {}", query);
        Page<Amigos> result = amigosSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(amigosMapper::toDto);
    }
}
