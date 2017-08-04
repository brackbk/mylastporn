package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.ModulosService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.ModulosDTO;
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
 * REST controller for managing Modulos.
 */
@RestController
@RequestMapping("/api")
public class ModulosResource {

    private final Logger log = LoggerFactory.getLogger(ModulosResource.class);

    private static final String ENTITY_NAME = "modulos";

    private final ModulosService modulosService;

    public ModulosResource(ModulosService modulosService) {
        this.modulosService = modulosService;
    }

    /**
     * POST  /modulos : Create a new modulos.
     *
     * @param modulosDTO the modulosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modulosDTO, or with status 400 (Bad Request) if the modulos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modulos")
    @Timed
    public ResponseEntity<ModulosDTO> createModulos(@Valid @RequestBody ModulosDTO modulosDTO) throws URISyntaxException {
        log.debug("REST request to save Modulos : {}", modulosDTO);
        if (modulosDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new modulos cannot already have an ID")).body(null);
        }
        ModulosDTO result = modulosService.save(modulosDTO);
        return ResponseEntity.created(new URI("/api/modulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modulos : Updates an existing modulos.
     *
     * @param modulosDTO the modulosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modulosDTO,
     * or with status 400 (Bad Request) if the modulosDTO is not valid,
     * or with status 500 (Internal Server Error) if the modulosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modulos")
    @Timed
    public ResponseEntity<ModulosDTO> updateModulos(@Valid @RequestBody ModulosDTO modulosDTO) throws URISyntaxException {
        log.debug("REST request to update Modulos : {}", modulosDTO);
        if (modulosDTO.getId() == null) {
            return createModulos(modulosDTO);
        }
        ModulosDTO result = modulosService.save(modulosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modulosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modulos : get all the modulos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of modulos in body
     */
    @GetMapping("/modulos")
    @Timed
    public ResponseEntity<List<ModulosDTO>> getAllModulos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Modulos");
        Page<ModulosDTO> page = modulosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modulos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /modulos/:id : get the "id" modulos.
     *
     * @param id the id of the modulosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modulosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/modulos/{id}")
    @Timed
    public ResponseEntity<ModulosDTO> getModulos(@PathVariable Long id) {
        log.debug("REST request to get Modulos : {}", id);
        ModulosDTO modulosDTO = modulosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modulosDTO));
    }

    /**
     * DELETE  /modulos/:id : delete the "id" modulos.
     *
     * @param id the id of the modulosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modulos/{id}")
    @Timed
    public ResponseEntity<Void> deleteModulos(@PathVariable Long id) {
        log.debug("REST request to delete Modulos : {}", id);
        modulosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/modulos?query=:query : search for the modulos corresponding
     * to the query.
     *
     * @param query the query of the modulos search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/modulos")
    @Timed
    public ResponseEntity<List<ModulosDTO>> searchModulos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Modulos for query {}", query);
        Page<ModulosDTO> page = modulosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/modulos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
