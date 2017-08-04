package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.ComentariosService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.ComentariosDTO;
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
 * REST controller for managing Comentarios.
 */
@RestController
@RequestMapping("/api")
public class ComentariosResource {

    private final Logger log = LoggerFactory.getLogger(ComentariosResource.class);

    private static final String ENTITY_NAME = "comentarios";

    private final ComentariosService comentariosService;

    public ComentariosResource(ComentariosService comentariosService) {
        this.comentariosService = comentariosService;
    }

    /**
     * POST  /comentarios : Create a new comentarios.
     *
     * @param comentariosDTO the comentariosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comentariosDTO, or with status 400 (Bad Request) if the comentarios has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comentarios")
    @Timed
    public ResponseEntity<ComentariosDTO> createComentarios(@Valid @RequestBody ComentariosDTO comentariosDTO) throws URISyntaxException {
        log.debug("REST request to save Comentarios : {}", comentariosDTO);
        if (comentariosDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comentarios cannot already have an ID")).body(null);
        }
        ComentariosDTO result = comentariosService.save(comentariosDTO);
        return ResponseEntity.created(new URI("/api/comentarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comentarios : Updates an existing comentarios.
     *
     * @param comentariosDTO the comentariosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comentariosDTO,
     * or with status 400 (Bad Request) if the comentariosDTO is not valid,
     * or with status 500 (Internal Server Error) if the comentariosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comentarios")
    @Timed
    public ResponseEntity<ComentariosDTO> updateComentarios(@Valid @RequestBody ComentariosDTO comentariosDTO) throws URISyntaxException {
        log.debug("REST request to update Comentarios : {}", comentariosDTO);
        if (comentariosDTO.getId() == null) {
            return createComentarios(comentariosDTO);
        }
        ComentariosDTO result = comentariosService.save(comentariosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comentariosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comentarios : get all the comentarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comentarios in body
     */
    @GetMapping("/comentarios")
    @Timed
    public ResponseEntity<List<ComentariosDTO>> getAllComentarios(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Comentarios");
        Page<ComentariosDTO> page = comentariosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comentarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comentarios/:id : get the "id" comentarios.
     *
     * @param id the id of the comentariosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comentariosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<ComentariosDTO> getComentarios(@PathVariable Long id) {
        log.debug("REST request to get Comentarios : {}", id);
        ComentariosDTO comentariosDTO = comentariosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comentariosDTO));
    }

    /**
     * DELETE  /comentarios/:id : delete the "id" comentarios.
     *
     * @param id the id of the comentariosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteComentarios(@PathVariable Long id) {
        log.debug("REST request to delete Comentarios : {}", id);
        comentariosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/comentarios?query=:query : search for the comentarios corresponding
     * to the query.
     *
     * @param query the query of the comentarios search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/comentarios")
    @Timed
    public ResponseEntity<List<ComentariosDTO>> searchComentarios(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Comentarios for query {}", query);
        Page<ComentariosDTO> page = comentariosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/comentarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
