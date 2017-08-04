package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.DenunciasService;
import com.mylastporn.app.domain.Denuncias;
import com.mylastporn.app.repository.DenunciasRepository;
import com.mylastporn.app.repository.search.DenunciasSearchRepository;
import com.mylastporn.app.service.dto.DenunciasDTO;
import com.mylastporn.app.service.mapper.DenunciasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Denuncias.
 */
@Service
@Transactional
public class DenunciasServiceImpl implements DenunciasService{

    private final Logger log = LoggerFactory.getLogger(DenunciasServiceImpl.class);

    private final DenunciasRepository denunciasRepository;

    private final DenunciasMapper denunciasMapper;

    private final DenunciasSearchRepository denunciasSearchRepository;

    public DenunciasServiceImpl(DenunciasRepository denunciasRepository, DenunciasMapper denunciasMapper, DenunciasSearchRepository denunciasSearchRepository) {
        this.denunciasRepository = denunciasRepository;
        this.denunciasMapper = denunciasMapper;
        this.denunciasSearchRepository = denunciasSearchRepository;
    }

    /**
     * Save a denuncias.
     *
     * @param denunciasDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DenunciasDTO save(DenunciasDTO denunciasDTO) {
        log.debug("Request to save Denuncias : {}", denunciasDTO);
        Denuncias denuncias = denunciasMapper.toEntity(denunciasDTO);
        denuncias = denunciasRepository.save(denuncias);
        DenunciasDTO result = denunciasMapper.toDto(denuncias);
        denunciasSearchRepository.save(denuncias);
        return result;
    }

    /**
     *  Get all the denuncias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DenunciasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Denuncias");
        return denunciasRepository.findAll(pageable)
            .map(denunciasMapper::toDto);
    }

    /**
     *  Get one denuncias by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DenunciasDTO findOne(Long id) {
        log.debug("Request to get Denuncias : {}", id);
        Denuncias denuncias = denunciasRepository.findOne(id);
        return denunciasMapper.toDto(denuncias);
    }

    /**
     *  Delete the  denuncias by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Denuncias : {}", id);
        denunciasRepository.delete(id);
        denunciasSearchRepository.delete(id);
    }

    /**
     * Search for the denuncias corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DenunciasDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Denuncias for query {}", query);
        Page<Denuncias> result = denunciasSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(denunciasMapper::toDto);
    }
}
