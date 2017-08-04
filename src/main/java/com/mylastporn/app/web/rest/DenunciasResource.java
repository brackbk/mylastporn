package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.DenunciasService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.DenunciasDTO;
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
 * REST controller for managing Denuncias.
 */
@RestController
@RequestMapping("/api")
public class DenunciasResource {

    private final Logger log = LoggerFactory.getLogger(DenunciasResource.class);

    private static final String ENTITY_NAME = "denuncias";

    private final DenunciasService denunciasService;

    public DenunciasResource(DenunciasService denunciasService) {
        this.denunciasService = denunciasService;
    }

    /**
     * POST  /denuncias : Create a new denuncias.
     *
     * @param denunciasDTO the denunciasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new denunciasDTO, or with status 400 (Bad Request) if the denuncias has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/denuncias")
    @Timed
    public ResponseEntity<DenunciasDTO> createDenuncias(@Valid @RequestBody DenunciasDTO denunciasDTO) throws URISyntaxException {
        log.debug("REST request to save Denuncias : {}", denunciasDTO);
        if (denunciasDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new denuncias cannot already have an ID")).body(null);
        }
        DenunciasDTO result = denunciasService.save(denunciasDTO);
        return ResponseEntity.created(new URI("/api/denuncias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /denuncias : Updates an existing denuncias.
     *
     * @param denunciasDTO the denunciasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated denunciasDTO,
     * or with status 400 (Bad Request) if the denunciasDTO is not valid,
     * or with status 500 (Internal Server Error) if the denunciasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/denuncias")
    @Timed
    public ResponseEntity<DenunciasDTO> updateDenuncias(@Valid @RequestBody DenunciasDTO denunciasDTO) throws URISyntaxException {
        log.debug("REST request to update Denuncias : {}", denunciasDTO);
        if (denunciasDTO.getId() == null) {
            return createDenuncias(denunciasDTO);
        }
        DenunciasDTO result = denunciasService.save(denunciasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, denunciasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /denuncias : get all the denuncias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of denuncias in body
     */
    @GetMapping("/denuncias")
    @Timed
    public ResponseEntity<List<DenunciasDTO>> getAllDenuncias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Denuncias");
        Page<DenunciasDTO> page = denunciasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/denuncias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /denuncias/:id : get the "id" denuncias.
     *
     * @param id the id of the denunciasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the denunciasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/denuncias/{id}")
    @Timed
    public ResponseEntity<DenunciasDTO> getDenuncias(@PathVariable Long id) {
        log.debug("REST request to get Denuncias : {}", id);
        DenunciasDTO denunciasDTO = denunciasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(denunciasDTO));
    }

    /**
     * DELETE  /denuncias/:id : delete the "id" denuncias.
     *
     * @param id the id of the denunciasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/denuncias/{id}")
    @Timed
    public ResponseEntity<Void> deleteDenuncias(@PathVariable Long id) {
        log.debug("REST request to delete Denuncias : {}", id);
        denunciasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/denuncias?query=:query : search for the denuncias corresponding
     * to the query.
     *
     * @param query the query of the denuncias search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/denuncias")
    @Timed
    public ResponseEntity<List<DenunciasDTO>> searchDenuncias(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Denuncias for query {}", query);
        Page<DenunciasDTO> page = denunciasService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/denuncias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
