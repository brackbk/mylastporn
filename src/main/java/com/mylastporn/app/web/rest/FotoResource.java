package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.FotoService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.FotoDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Foto.
 */
@RestController
@RequestMapping("/api")
public class FotoResource {

    private final Logger log = LoggerFactory.getLogger(FotoResource.class);

    private static final String ENTITY_NAME = "foto";

    private final FotoService fotoService;

    public FotoResource(FotoService fotoService) {
        this.fotoService = fotoService;
    }

    /**
     * POST  /fotos : Create a new foto.
     *
     * @param fotoDTO the fotoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fotoDTO, or with status 400 (Bad Request) if the foto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fotos")
    @Timed
    public ResponseEntity<FotoDTO> createFoto(@Valid @RequestBody FotoDTO fotoDTO) throws URISyntaxException {
        log.debug("REST request to save Foto : {}", fotoDTO);
        if (fotoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new foto cannot already have an ID")).body(null);
        }
        FotoDTO result = fotoService.save(fotoDTO);
        return ResponseEntity.created(new URI("/api/fotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fotos : Updates an existing foto.
     *
     * @param fotoDTO the fotoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fotoDTO,
     * or with status 400 (Bad Request) if the fotoDTO is not valid,
     * or with status 500 (Internal Server Error) if the fotoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fotos")
    @Timed
    public ResponseEntity<FotoDTO> updateFoto(@Valid @RequestBody FotoDTO fotoDTO) throws URISyntaxException {
        log.debug("REST request to update Foto : {}", fotoDTO);
        if (fotoDTO.getId() == null) {
            return createFoto(fotoDTO);
        }
        FotoDTO result = fotoService.save(fotoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fotos : get all the fotos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fotos in body
     */
    @GetMapping("/fotos")
    @Timed
    public ResponseEntity<List<FotoDTO>> getAllFotos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Fotos");
        Page<FotoDTO> page = fotoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fotos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fotos/:id : get the "id" foto.
     *
     * @param id the id of the fotoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fotoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<FotoDTO> getFoto(@PathVariable Long id) {
        log.debug("REST request to get Foto : {}", id);
        FotoDTO fotoDTO = fotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fotoDTO));
    }

    /**
     * DELETE  /fotos/:id : delete the "id" foto.
     *
     * @param id the id of the fotoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoto(@PathVariable Long id) {
        log.debug("REST request to delete Foto : {}", id);
        fotoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fotos?query=:query : search for the foto corresponding
     * to the query.
     *
     * @param query the query of the foto search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fotos")
    @Timed
    public ResponseEntity<List<FotoDTO>> searchFotos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Fotos for query {}", query);
        Page<FotoDTO> page = fotoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fotos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
