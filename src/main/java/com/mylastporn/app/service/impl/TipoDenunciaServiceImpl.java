package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.TipoDenunciaService;
import com.mylastporn.app.domain.TipoDenuncia;
import com.mylastporn.app.repository.TipoDenunciaRepository;
import com.mylastporn.app.repository.search.TipoDenunciaSearchRepository;
import com.mylastporn.app.service.dto.TipoDenunciaDTO;
import com.mylastporn.app.service.mapper.TipoDenunciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoDenuncia.
 */
@Service
@Transactional
public class TipoDenunciaServiceImpl implements TipoDenunciaService{

    private final Logger log = LoggerFactory.getLogger(TipoDenunciaServiceImpl.class);

    private final TipoDenunciaRepository tipoDenunciaRepository;

    private final TipoDenunciaMapper tipoDenunciaMapper;

    private final TipoDenunciaSearchRepository tipoDenunciaSearchRepository;

    public TipoDenunciaServiceImpl(TipoDenunciaRepository tipoDenunciaRepository, TipoDenunciaMapper tipoDenunciaMapper, TipoDenunciaSearchRepository tipoDenunciaSearchRepository) {
        this.tipoDenunciaRepository = tipoDenunciaRepository;
        this.tipoDenunciaMapper = tipoDenunciaMapper;
        this.tipoDenunciaSearchRepository = tipoDenunciaSearchRepository;
    }

    /**
     * Save a tipoDenuncia.
     *
     * @param tipoDenunciaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDenunciaDTO save(TipoDenunciaDTO tipoDenunciaDTO) {
        log.debug("Request to save TipoDenuncia : {}", tipoDenunciaDTO);
        TipoDenuncia tipoDenuncia = tipoDenunciaMapper.toEntity(tipoDenunciaDTO);
        tipoDenuncia = tipoDenunciaRepository.save(tipoDenuncia);
        TipoDenunciaDTO result = tipoDenunciaMapper.toDto(tipoDenuncia);
        tipoDenunciaSearchRepository.save(tipoDenuncia);
        return result;
    }

    /**
     *  Get all the tipoDenuncias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDenunciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDenuncias");
        return tipoDenunciaRepository.findAll(pageable)
            .map(tipoDenunciaMapper::toDto);
    }

    /**
     *  Get one tipoDenuncia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoDenunciaDTO findOne(Long id) {
        log.debug("Request to get TipoDenuncia : {}", id);
        TipoDenuncia tipoDenuncia = tipoDenunciaRepository.findOne(id);
        return tipoDenunciaMapper.toDto(tipoDenuncia);
    }

    /**
     *  Delete the  tipoDenuncia by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDenuncia : {}", id);
        tipoDenunciaRepository.delete(id);
        tipoDenunciaSearchRepository.delete(id);
    }

    /**
     * Search for the tipoDenuncia corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDenunciaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDenuncias for query {}", query);
        Page<TipoDenuncia> result = tipoDenunciaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(tipoDenunciaMapper::toDto);
    }
}
