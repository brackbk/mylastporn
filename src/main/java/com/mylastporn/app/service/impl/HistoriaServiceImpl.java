package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.HistoriaService;
import com.mylastporn.app.domain.Historia;
import com.mylastporn.app.repository.HistoriaRepository;
import com.mylastporn.app.repository.search.HistoriaSearchRepository;
import com.mylastporn.app.service.dto.HistoriaDTO;
import com.mylastporn.app.service.mapper.HistoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Historia.
 */
@Service
@Transactional
public class HistoriaServiceImpl implements HistoriaService{

    private final Logger log = LoggerFactory.getLogger(HistoriaServiceImpl.class);

    private final HistoriaRepository historiaRepository;

    private final HistoriaMapper historiaMapper;

    private final HistoriaSearchRepository historiaSearchRepository;

    public HistoriaServiceImpl(HistoriaRepository historiaRepository, HistoriaMapper historiaMapper, HistoriaSearchRepository historiaSearchRepository) {
        this.historiaRepository = historiaRepository;
        this.historiaMapper = historiaMapper;
        this.historiaSearchRepository = historiaSearchRepository;
    }

    /**
     * Save a historia.
     *
     * @param historiaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HistoriaDTO save(HistoriaDTO historiaDTO) {
        log.debug("Request to save Historia : {}", historiaDTO);
        Historia historia = historiaMapper.toEntity(historiaDTO);
        historia = historiaRepository.save(historia);
        HistoriaDTO result = historiaMapper.toDto(historia);
        historiaSearchRepository.save(historia);
        return result;
    }

    /**
     *  Get all the historias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Historias");
        return historiaRepository.findAll(pageable)
            .map(historiaMapper::toDto);
    }

    /**
     *  Get one historia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HistoriaDTO findOne(Long id) {
        log.debug("Request to get Historia : {}", id);
        Historia historia = historiaRepository.findOneWithEagerRelationships(id);
        return historiaMapper.toDto(historia);
    }

    /**
     *  Delete the  historia by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Historia : {}", id);
        historiaRepository.delete(id);
        historiaSearchRepository.delete(id);
    }

    /**
     * Search for the historia corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoriaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Historias for query {}", query);
        Page<Historia> result = historiaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(historiaMapper::toDto);
    }
}
