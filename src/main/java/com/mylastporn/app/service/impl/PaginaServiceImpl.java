package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.PaginaService;
import com.mylastporn.app.domain.Pagina;
import com.mylastporn.app.repository.PaginaRepository;
import com.mylastporn.app.repository.search.PaginaSearchRepository;
import com.mylastporn.app.service.dto.PaginaDTO;
import com.mylastporn.app.service.mapper.PaginaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Pagina.
 */
@Service
@Transactional
public class PaginaServiceImpl implements PaginaService{

    private final Logger log = LoggerFactory.getLogger(PaginaServiceImpl.class);

    private final PaginaRepository paginaRepository;

    private final PaginaMapper paginaMapper;

    private final PaginaSearchRepository paginaSearchRepository;

    public PaginaServiceImpl(PaginaRepository paginaRepository, PaginaMapper paginaMapper, PaginaSearchRepository paginaSearchRepository) {
        this.paginaRepository = paginaRepository;
        this.paginaMapper = paginaMapper;
        this.paginaSearchRepository = paginaSearchRepository;
    }

    /**
     * Save a pagina.
     *
     * @param paginaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaginaDTO save(PaginaDTO paginaDTO) {
        log.debug("Request to save Pagina : {}", paginaDTO);
        Pagina pagina = paginaMapper.toEntity(paginaDTO);
        pagina = paginaRepository.save(pagina);
        PaginaDTO result = paginaMapper.toDto(pagina);
        paginaSearchRepository.save(pagina);
        return result;
    }

    /**
     *  Get all the paginas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaginaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Paginas");
        return paginaRepository.findAll(pageable)
            .map(paginaMapper::toDto);
    }

    /**
     *  Get one pagina by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaginaDTO findOne(Long id) {
        log.debug("Request to get Pagina : {}", id);
        Pagina pagina = paginaRepository.findOneWithEagerRelationships(id);
        return paginaMapper.toDto(pagina);
    }

    /**
     *  Delete the  pagina by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pagina : {}", id);
        paginaRepository.delete(id);
        paginaSearchRepository.delete(id);
    }

    /**
     * Search for the pagina corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaginaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Paginas for query {}", query);
        Page<Pagina> result = paginaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(paginaMapper::toDto);
    }
}
