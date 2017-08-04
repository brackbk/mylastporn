package com.mylastporn.app.service;

import com.mylastporn.app.service.dto.TipoDenunciaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TipoDenuncia.
 */
public interface TipoDenunciaService {

    /**
     * Save a tipoDenuncia.
     *
     * @param tipoDenunciaDTO the entity to save
     * @return the persisted entity
     */
    TipoDenunciaDTO save(TipoDenunciaDTO tipoDenunciaDTO);

    /**
     *  Get all the tipoDenuncias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TipoDenunciaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tipoDenuncia.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TipoDenunciaDTO findOne(Long id);

    /**
     *  Delete the "id" tipoDenuncia.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoDenuncia corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TipoDenunciaDTO> search(String query, Pageable pageable);
}
