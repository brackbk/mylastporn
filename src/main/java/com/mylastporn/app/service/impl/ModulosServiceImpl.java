package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.ModulosService;
import com.mylastporn.app.domain.Modulos;
import com.mylastporn.app.repository.ModulosRepository;
import com.mylastporn.app.repository.search.ModulosSearchRepository;
import com.mylastporn.app.service.dto.ModulosDTO;
import com.mylastporn.app.service.mapper.ModulosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Modulos.
 */
@Service
@Transactional
public class ModulosServiceImpl implements ModulosService{

    private final Logger log = LoggerFactory.getLogger(ModulosServiceImpl.class);

    private final ModulosRepository modulosRepository;

    private final ModulosMapper modulosMapper;

    private final ModulosSearchRepository modulosSearchRepository;

    public ModulosServiceImpl(ModulosRepository modulosRepository, ModulosMapper modulosMapper, ModulosSearchRepository modulosSearchRepository) {
        this.modulosRepository = modulosRepository;
        this.modulosMapper = modulosMapper;
        this.modulosSearchRepository = modulosSearchRepository;
    }

    /**
     * Save a modulos.
     *
     * @param modulosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModulosDTO save(ModulosDTO modulosDTO) {
        log.debug("Request to save Modulos : {}", modulosDTO);
        Modulos modulos = modulosMapper.toEntity(modulosDTO);
        modulos = modulosRepository.save(modulos);
        ModulosDTO result = modulosMapper.toDto(modulos);
        modulosSearchRepository.save(modulos);
        return result;
    }

    /**
     *  Get all the modulos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModulosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modulos");
        return modulosRepository.findAll(pageable)
            .map(modulosMapper::toDto);
    }

    /**
     *  Get one modulos by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ModulosDTO findOne(Long id) {
        log.debug("Request to get Modulos : {}", id);
        Modulos modulos = modulosRepository.findOne(id);
        return modulosMapper.toDto(modulos);
    }

    /**
     *  Delete the  modulos by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Modulos : {}", id);
        modulosRepository.delete(id);
        modulosSearchRepository.delete(id);
    }

    /**
     * Search for the modulos corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModulosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Modulos for query {}", query);
        Page<Modulos> result = modulosSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(modulosMapper::toDto);
    }
}
