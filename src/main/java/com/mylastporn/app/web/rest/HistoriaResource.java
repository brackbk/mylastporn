package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.HistoriaService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.HistoriaDTO;
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
 * REST controller for managing Historia.
 */
@RestController
@RequestMapping("/api")
public class HistoriaResource {

    private final Logger log = LoggerFactory.getLogger(HistoriaResource.class);

    private static final String ENTITY_NAME = "historia";

    private final HistoriaService historiaService;

    public HistoriaResource(HistoriaService historiaService) {
        this.historiaService = historiaService;
    }

    /**
     * POST  /historias : Create a new historia.
     *
     * @param historiaDTO the historiaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historiaDTO, or with status 400 (Bad Request) if the historia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historias")
    @Timed
    public ResponseEntity<HistoriaDTO> createHistoria(@Valid @RequestBody HistoriaDTO historiaDTO) throws URISyntaxException {
        log.debug("REST request to save Historia : {}", historiaDTO);
        if (historiaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new historia cannot already have an ID")).body(null);
        }
        HistoriaDTO result = historiaService.save(historiaDTO);
        return ResponseEntity.created(new URI("/api/historias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historias : Updates an existing historia.
     *
     * @param historiaDTO the historiaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historiaDTO,
     * or with status 400 (Bad Request) if the historiaDTO is not valid,
     * or with status 500 (Internal Server Error) if the historiaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historias")
    @Timed
    public ResponseEntity<HistoriaDTO> updateHistoria(@Valid @RequestBody HistoriaDTO historiaDTO) throws URISyntaxException {
        log.debug("REST request to update Historia : {}", historiaDTO);
        if (historiaDTO.getId() == null) {
            return createHistoria(historiaDTO);
        }
        HistoriaDTO result = historiaService.save(historiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historias : get all the historias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of historias in body
     */
    @GetMapping("/historias")
    @Timed
    public ResponseEntity<List<HistoriaDTO>> getAllHistorias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Historias");
        Page<HistoriaDTO> page = historiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /historias/:id : get the "id" historia.
     *
     * @param id the id of the historiaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historiaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/historias/{id}")
    @Timed
    public ResponseEntity<HistoriaDTO> getHistoria(@PathVariable Long id) {
        log.debug("REST request to get Historia : {}", id);
        HistoriaDTO historiaDTO = historiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(historiaDTO));
    }

    /**
     * DELETE  /historias/:id : delete the "id" historia.
     *
     * @param id the id of the historiaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historias/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistoria(@PathVariable Long id) {
        log.debug("REST request to delete Historia : {}", id);
        historiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/historias?query=:query : search for the historia corresponding
     * to the query.
     *
     * @param query the query of the historia search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/historias")
    @Timed
    public ResponseEntity<List<HistoriaDTO>> searchHistorias(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Historias for query {}", query);
        Page<HistoriaDTO> page = historiaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/historias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
