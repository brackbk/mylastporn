package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.FavoritosService;
import com.mylastporn.app.domain.Favoritos;
import com.mylastporn.app.repository.FavoritosRepository;
import com.mylastporn.app.repository.search.FavoritosSearchRepository;
import com.mylastporn.app.service.dto.FavoritosDTO;
import com.mylastporn.app.service.mapper.FavoritosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Favoritos.
 */
@Service
@Transactional
public class FavoritosServiceImpl implements FavoritosService{

    private final Logger log = LoggerFactory.getLogger(FavoritosServiceImpl.class);

    private final FavoritosRepository favoritosRepository;

    private final FavoritosMapper favoritosMapper;

    private final FavoritosSearchRepository favoritosSearchRepository;

    public FavoritosServiceImpl(FavoritosRepository favoritosRepository, FavoritosMapper favoritosMapper, FavoritosSearchRepository favoritosSearchRepository) {
        this.favoritosRepository = favoritosRepository;
        this.favoritosMapper = favoritosMapper;
        this.favoritosSearchRepository = favoritosSearchRepository;
    }

    /**
     * Save a favoritos.
     *
     * @param favoritosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FavoritosDTO save(FavoritosDTO favoritosDTO) {
        log.debug("Request to save Favoritos : {}", favoritosDTO);
        Favoritos favoritos = favoritosMapper.toEntity(favoritosDTO);
        favoritos = favoritosRepository.save(favoritos);
        FavoritosDTO result = favoritosMapper.toDto(favoritos);
        favoritosSearchRepository.save(favoritos);
        return result;
    }

    /**
     *  Get all the favoritos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FavoritosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Favoritos");
        return favoritosRepository.findAll(pageable)
            .map(favoritosMapper::toDto);
    }

    /**
     *  Get one favoritos by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FavoritosDTO findOne(Long id) {
        log.debug("Request to get Favoritos : {}", id);
        Favoritos favoritos = favoritosRepository.findOne(id);
        return favoritosMapper.toDto(favoritos);
    }

    /**
     *  Delete the  favoritos by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Favoritos : {}", id);
        favoritosRepository.delete(id);
        favoritosSearchRepository.delete(id);
    }

    /**
     * Search for the favoritos corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FavoritosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Favoritos for query {}", query);
        Page<Favoritos> result = favoritosSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(favoritosMapper::toDto);
    }
}
