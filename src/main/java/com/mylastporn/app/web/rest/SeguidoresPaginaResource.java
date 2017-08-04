package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.SeguidoresPaginaService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.SeguidoresPaginaDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SeguidoresPagina.
 */
@RestController
@RequestMapping("/api")
public class SeguidoresPaginaResource {

    private final Logger log = LoggerFactory.getLogger(SeguidoresPaginaResource.class);

    private static final String ENTITY_NAME = "seguidoresPagina";

    private final SeguidoresPaginaService seguidoresPaginaService;

    public SeguidoresPaginaResource(SeguidoresPaginaService seguidoresPaginaService) {
        this.seguidoresPaginaService = seguidoresPaginaService;
    }

    /**
     * POST  /seguidores-paginas : Create a new seguidoresPagina.
     *
     * @param seguidoresPaginaDTO the seguidoresPaginaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seguidoresPaginaDTO, or with status 400 (Bad Request) if the seguidoresPagina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seguidores-paginas")
    @Timed
    public ResponseEntity<SeguidoresPaginaDTO> createSeguidoresPagina(@RequestBody SeguidoresPaginaDTO seguidoresPaginaDTO) throws URISyntaxException {
        log.debug("REST request to save SeguidoresPagina : {}", seguidoresPaginaDTO);
        if (seguidoresPaginaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seguidoresPagina cannot already have an ID")).body(null);
        }
        SeguidoresPaginaDTO result = seguidoresPaginaService.save(seguidoresPaginaDTO);
        return ResponseEntity.created(new URI("/api/seguidores-paginas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seguidores-paginas : Updates an existing seguidoresPagina.
     *
     * @param seguidoresPaginaDTO the seguidoresPaginaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seguidoresPaginaDTO,
     * or with status 400 (Bad Request) if the seguidoresPaginaDTO is not valid,
     * or with status 500 (Internal Server Error) if the seguidoresPaginaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seguidores-paginas")
    @Timed
    public ResponseEntity<SeguidoresPaginaDTO> updateSeguidoresPagina(@RequestBody SeguidoresPaginaDTO seguidoresPaginaDTO) throws URISyntaxException {
        log.debug("REST request to update SeguidoresPagina : {}", seguidoresPaginaDTO);
        if (seguidoresPaginaDTO.getId() == null) {
            return createSeguidoresPagina(seguidoresPaginaDTO);
        }
        SeguidoresPaginaDTO result = seguidoresPaginaService.save(seguidoresPaginaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seguidoresPaginaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seguidores-paginas : get all the seguidoresPaginas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seguidoresPaginas in body
     */
    @GetMapping("/seguidores-paginas")
    @Timed
    public ResponseEntity<List<SeguidoresPaginaDTO>> getAllSeguidoresPaginas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SeguidoresPaginas");
        Page<SeguidoresPaginaDTO> page = seguidoresPaginaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seguidores-paginas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seguidores-paginas/:id : get the "id" seguidoresPagina.
     *
     * @param id the id of the seguidoresPaginaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seguidoresPaginaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seguidores-paginas/{id}")
    @Timed
    public ResponseEntity<SeguidoresPaginaDTO> getSeguidoresPagina(@PathVariable Long id) {
        log.debug("REST request to get SeguidoresPagina : {}", id);
        SeguidoresPaginaDTO seguidoresPaginaDTO = seguidoresPaginaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seguidoresPaginaDTO));
    }

    /**
     * DELETE  /seguidores-paginas/:id : delete the "id" seguidoresPagina.
     *
     * @param id the id of the seguidoresPaginaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seguidores-paginas/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeguidoresPagina(@PathVariable Long id) {
        log.debug("REST request to delete SeguidoresPagina : {}", id);
        seguidoresPaginaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/seguidores-paginas?query=:query : search for the seguidoresPagina corresponding
     * to the query.
     *
     * @param query the query of the seguidoresPagina search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/seguidores-paginas")
    @Timed
    public ResponseEntity<List<SeguidoresPaginaDTO>> searchSeguidoresPaginas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SeguidoresPaginas for query {}", query);
        Page<SeguidoresPaginaDTO> page = seguidoresPaginaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/seguidores-paginas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
