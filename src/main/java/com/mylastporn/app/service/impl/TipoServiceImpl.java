package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.TipoService;
import com.mylastporn.app.domain.Tipo;
import com.mylastporn.app.repository.TipoRepository;
import com.mylastporn.app.repository.search.TipoSearchRepository;
import com.mylastporn.app.service.dto.TipoDTO;
import com.mylastporn.app.service.mapper.TipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tipo.
 */
@Service
@Transactional
public class TipoServiceImpl implements TipoService{

    private final Logger log = LoggerFactory.getLogger(TipoServiceImpl.class);

    private final TipoRepository tipoRepository;

    private final TipoMapper tipoMapper;

    private final TipoSearchRepository tipoSearchRepository;

    public TipoServiceImpl(TipoRepository tipoRepository, TipoMapper tipoMapper, TipoSearchRepository tipoSearchRepository) {
        this.tipoRepository = tipoRepository;
        this.tipoMapper = tipoMapper;
        this.tipoSearchRepository = tipoSearchRepository;
    }

    /**
     * Save a tipo.
     *
     * @param tipoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDTO save(TipoDTO tipoDTO) {
        log.debug("Request to save Tipo : {}", tipoDTO);
        Tipo tipo = tipoMapper.toEntity(tipoDTO);
        tipo = tipoRepository.save(tipo);
        TipoDTO result = tipoMapper.toDto(tipo);
        tipoSearchRepository.save(tipo);
        return result;
    }

    /**
     *  Get all the tipos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tipos");
        return tipoRepository.findAll(pageable)
            .map(tipoMapper::toDto);
    }

    /**
     *  Get one tipo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoDTO findOne(Long id) {
        log.debug("Request to get Tipo : {}", id);
        Tipo tipo = tipoRepository.findOne(id);
        return tipoMapper.toDto(tipo);
    }

    /**
     *  Delete the  tipo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tipo : {}", id);
        tipoRepository.delete(id);
        tipoSearchRepository.delete(id);
    }

    /**
     * Search for the tipo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tipos for query {}", query);
        Page<Tipo> result = tipoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(tipoMapper::toDto);
    }
}
