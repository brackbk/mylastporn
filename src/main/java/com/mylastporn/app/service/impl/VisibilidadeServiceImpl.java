package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.VisibilidadeService;
import com.mylastporn.app.domain.Visibilidade;
import com.mylastporn.app.repository.VisibilidadeRepository;
import com.mylastporn.app.repository.search.VisibilidadeSearchRepository;
import com.mylastporn.app.service.dto.VisibilidadeDTO;
import com.mylastporn.app.service.mapper.VisibilidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Visibilidade.
 */
@Service
@Transactional
public class VisibilidadeServiceImpl implements VisibilidadeService{

    private final Logger log = LoggerFactory.getLogger(VisibilidadeServiceImpl.class);

    private final VisibilidadeRepository visibilidadeRepository;

    private final VisibilidadeMapper visibilidadeMapper;

    private final VisibilidadeSearchRepository visibilidadeSearchRepository;

    public VisibilidadeServiceImpl(VisibilidadeRepository visibilidadeRepository, VisibilidadeMapper visibilidadeMapper, VisibilidadeSearchRepository visibilidadeSearchRepository) {
        this.visibilidadeRepository = visibilidadeRepository;
        this.visibilidadeMapper = visibilidadeMapper;
        this.visibilidadeSearchRepository = visibilidadeSearchRepository;
    }

    /**
     * Save a visibilidade.
     *
     * @param visibilidadeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VisibilidadeDTO save(VisibilidadeDTO visibilidadeDTO) {
        log.debug("Request to save Visibilidade : {}", visibilidadeDTO);
        Visibilidade visibilidade = visibilidadeMapper.toEntity(visibilidadeDTO);
        visibilidade = visibilidadeRepository.save(visibilidade);
        VisibilidadeDTO result = visibilidadeMapper.toDto(visibilidade);
        visibilidadeSearchRepository.save(visibilidade);
        return result;
    }

    /**
     *  Get all the visibilidades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VisibilidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Visibilidades");
        return visibilidadeRepository.findAll(pageable)
            .map(visibilidadeMapper::toDto);
    }

    /**
     *  Get one visibilidade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VisibilidadeDTO findOne(Long id) {
        log.debug("Request to get Visibilidade : {}", id);
        Visibilidade visibilidade = visibilidadeRepository.findOne(id);
        return visibilidadeMapper.toDto(visibilidade);
    }

    /**
     *  Delete the  visibilidade by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Visibilidade : {}", id);
        visibilidadeRepository.delete(id);
        visibilidadeSearchRepository.delete(id);
    }

    /**
     * Search for the visibilidade corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VisibilidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Visibilidades for query {}", query);
        Page<Visibilidade> result = visibilidadeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(visibilidadeMapper::toDto);
    }
}
