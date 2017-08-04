package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.AmigosService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.AmigosDTO;
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
 * REST controller for managing Amigos.
 */
@RestController
@RequestMapping("/api")
public class AmigosResource {

    private final Logger log = LoggerFactory.getLogger(AmigosResource.class);

    private static final String ENTITY_NAME = "amigos";

    private final AmigosService amigosService;

    public AmigosResource(AmigosService amigosService) {
        this.amigosService = amigosService;
    }

    /**
     * POST  /amigos : Create a new amigos.
     *
     * @param amigosDTO the amigosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amigosDTO, or with status 400 (Bad Request) if the amigos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amigos")
    @Timed
    public ResponseEntity<AmigosDTO> createAmigos(@RequestBody AmigosDTO amigosDTO) throws URISyntaxException {
        log.debug("REST request to save Amigos : {}", amigosDTO);
        if (amigosDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new amigos cannot already have an ID")).body(null);
        }
        AmigosDTO result = amigosService.save(amigosDTO);
        return ResponseEntity.created(new URI("/api/amigos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amigos : Updates an existing amigos.
     *
     * @param amigosDTO the amigosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amigosDTO,
     * or with status 400 (Bad Request) if the amigosDTO is not valid,
     * or with status 500 (Internal Server Error) if the amigosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amigos")
    @Timed
    public ResponseEntity<AmigosDTO> updateAmigos(@RequestBody AmigosDTO amigosDTO) throws URISyntaxException {
        log.debug("REST request to update Amigos : {}", amigosDTO);
        if (amigosDTO.getId() == null) {
            return createAmigos(amigosDTO);
        }
        AmigosDTO result = amigosService.save(amigosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amigosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amigos : get all the amigos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of amigos in body
     */
    @GetMapping("/amigos")
    @Timed
    public ResponseEntity<List<AmigosDTO>> getAllAmigos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Amigos");
        Page<AmigosDTO> page = amigosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amigos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amigos/:id : get the "id" amigos.
     *
     * @param id the id of the amigosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amigosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amigos/{id}")
    @Timed
    public ResponseEntity<AmigosDTO> getAmigos(@PathVariable Long id) {
        log.debug("REST request to get Amigos : {}", id);
        AmigosDTO amigosDTO = amigosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(amigosDTO));
    }

    /**
     * DELETE  /amigos/:id : delete the "id" amigos.
     *
     * @param id the id of the amigosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amigos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmigos(@PathVariable Long id) {
        log.debug("REST request to delete Amigos : {}", id);
        amigosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/amigos?query=:query : search for the amigos corresponding
     * to the query.
     *
     * @param query the query of the amigos search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/amigos")
    @Timed
    public ResponseEntity<List<AmigosDTO>> searchAmigos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Amigos for query {}", query);
        Page<AmigosDTO> page = amigosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/amigos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
