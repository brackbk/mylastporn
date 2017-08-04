package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.SeguidoresPaginaService;
import com.mylastporn.app.domain.SeguidoresPagina;
import com.mylastporn.app.repository.SeguidoresPaginaRepository;
import com.mylastporn.app.repository.search.SeguidoresPaginaSearchRepository;
import com.mylastporn.app.service.dto.SeguidoresPaginaDTO;
import com.mylastporn.app.service.mapper.SeguidoresPaginaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SeguidoresPagina.
 */
@Service
@Transactional
public class SeguidoresPaginaServiceImpl implements SeguidoresPaginaService{

    private final Logger log = LoggerFactory.getLogger(SeguidoresPaginaServiceImpl.class);

    private final SeguidoresPaginaRepository seguidoresPaginaRepository;

    private final SeguidoresPaginaMapper seguidoresPaginaMapper;

    private final SeguidoresPaginaSearchRepository seguidoresPaginaSearchRepository;

    public SeguidoresPaginaServiceImpl(SeguidoresPaginaRepository seguidoresPaginaRepository, SeguidoresPaginaMapper seguidoresPaginaMapper, SeguidoresPaginaSearchRepository seguidoresPaginaSearchRepository) {
        this.seguidoresPaginaRepository = seguidoresPaginaRepository;
        this.seguidoresPaginaMapper = seguidoresPaginaMapper;
        this.seguidoresPaginaSearchRepository = seguidoresPaginaSearchRepository;
    }

    /**
     * Save a seguidoresPagina.
     *
     * @param seguidoresPaginaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeguidoresPaginaDTO save(SeguidoresPaginaDTO seguidoresPaginaDTO) {
        log.debug("Request to save SeguidoresPagina : {}", seguidoresPaginaDTO);
        SeguidoresPagina seguidoresPagina = seguidoresPaginaMapper.toEntity(seguidoresPaginaDTO);
        seguidoresPagina = seguidoresPaginaRepository.save(seguidoresPagina);
        SeguidoresPaginaDTO result = seguidoresPaginaMapper.toDto(seguidoresPagina);
        seguidoresPaginaSearchRepository.save(seguidoresPagina);
        return result;
    }

    /**
     *  Get all the seguidoresPaginas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeguidoresPaginaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SeguidoresPaginas");
        return seguidoresPaginaRepository.findAll(pageable)
            .map(seguidoresPaginaMapper::toDto);
    }

    /**
     *  Get one seguidoresPagina by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeguidoresPaginaDTO findOne(Long id) {
        log.debug("Request to get SeguidoresPagina : {}", id);
        SeguidoresPagina seguidoresPagina = seguidoresPaginaRepository.findOne(id);
        return seguidoresPaginaMapper.toDto(seguidoresPagina);
    }

    /**
     *  Delete the  seguidoresPagina by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeguidoresPagina : {}", id);
        seguidoresPaginaRepository.delete(id);
        seguidoresPaginaSearchRepository.delete(id);
    }

    /**
     * Search for the seguidoresPagina corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeguidoresPaginaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SeguidoresPaginas for query {}", query);
        Page<SeguidoresPagina> result = seguidoresPaginaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(seguidoresPaginaMapper::toDto);
    }
}
