package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.ComentariosService;
import com.mylastporn.app.domain.Comentarios;
import com.mylastporn.app.repository.ComentariosRepository;
import com.mylastporn.app.repository.search.ComentariosSearchRepository;
import com.mylastporn.app.service.dto.ComentariosDTO;
import com.mylastporn.app.service.mapper.ComentariosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Comentarios.
 */
@Service
@Transactional
public class ComentariosServiceImpl implements ComentariosService{

    private final Logger log = LoggerFactory.getLogger(ComentariosServiceImpl.class);

    private final ComentariosRepository comentariosRepository;

    private final ComentariosMapper comentariosMapper;

    private final ComentariosSearchRepository comentariosSearchRepository;

    public ComentariosServiceImpl(ComentariosRepository comentariosRepository, ComentariosMapper comentariosMapper, ComentariosSearchRepository comentariosSearchRepository) {
        this.comentariosRepository = comentariosRepository;
        this.comentariosMapper = comentariosMapper;
        this.comentariosSearchRepository = comentariosSearchRepository;
    }

    /**
     * Save a comentarios.
     *
     * @param comentariosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ComentariosDTO save(ComentariosDTO comentariosDTO) {
        log.debug("Request to save Comentarios : {}", comentariosDTO);
        Comentarios comentarios = comentariosMapper.toEntity(comentariosDTO);
        comentarios = comentariosRepository.save(comentarios);
        ComentariosDTO result = comentariosMapper.toDto(comentarios);
        comentariosSearchRepository.save(comentarios);
        return result;
    }

    /**
     *  Get all the comentarios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ComentariosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comentarios");
        return comentariosRepository.findAll(pageable)
            .map(comentariosMapper::toDto);
    }

    /**
     *  Get one comentarios by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ComentariosDTO findOne(Long id) {
        log.debug("Request to get Comentarios : {}", id);
        Comentarios comentarios = comentariosRepository.findOne(id);
        return comentariosMapper.toDto(comentarios);
    }

    /**
     *  Delete the  comentarios by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comentarios : {}", id);
        comentariosRepository.delete(id);
        comentariosSearchRepository.delete(id);
    }

    /**
     * Search for the comentarios corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ComentariosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Comentarios for query {}", query);
        Page<Comentarios> result = comentariosSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(comentariosMapper::toDto);
    }
}
