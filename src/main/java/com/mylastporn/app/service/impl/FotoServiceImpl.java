package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.FotoService;
import com.mylastporn.app.domain.Foto;
import com.mylastporn.app.repository.FotoRepository;
import com.mylastporn.app.repository.search.FotoSearchRepository;
import com.mylastporn.app.service.dto.FotoDTO;
import com.mylastporn.app.service.mapper.FotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Foto.
 */
@Service
@Transactional
public class FotoServiceImpl implements FotoService{

    private final Logger log = LoggerFactory.getLogger(FotoServiceImpl.class);

    private final FotoRepository fotoRepository;

    private final FotoMapper fotoMapper;

    private final FotoSearchRepository fotoSearchRepository;

    public FotoServiceImpl(FotoRepository fotoRepository, FotoMapper fotoMapper, FotoSearchRepository fotoSearchRepository) {
        this.fotoRepository = fotoRepository;
        this.fotoMapper = fotoMapper;
        this.fotoSearchRepository = fotoSearchRepository;
    }

    /**
     * Save a foto.
     *
     * @param fotoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FotoDTO save(FotoDTO fotoDTO) {
        log.debug("Request to save Foto : {}", fotoDTO);
        Foto foto = fotoMapper.toEntity(fotoDTO);
        foto = fotoRepository.save(foto);
        FotoDTO result = fotoMapper.toDto(foto);
        fotoSearchRepository.save(foto);
        return result;
    }

    /**
     *  Get all the fotos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fotos");
        return fotoRepository.findAll(pageable)
            .map(fotoMapper::toDto);
    }

    /**
     *  Get one foto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FotoDTO findOne(Long id) {
        log.debug("Request to get Foto : {}", id);
        Foto foto = fotoRepository.findOneWithEagerRelationships(id);
        return fotoMapper.toDto(foto);
    }

    /**
     *  Delete the  foto by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Foto : {}", id);
        fotoRepository.delete(id);
        fotoSearchRepository.delete(id);
    }

    /**
     * Search for the foto corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FotoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fotos for query {}", query);
        Page<Foto> result = fotoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(fotoMapper::toDto);
    }
}
