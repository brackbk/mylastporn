package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.VisibilidadeService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.VisibilidadeDTO;
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
 * REST controller for managing Visibilidade.
 */
@RestController
@RequestMapping("/api")
public class VisibilidadeResource {

    private final Logger log = LoggerFactory.getLogger(VisibilidadeResource.class);

    private static final String ENTITY_NAME = "visibilidade";

    private final VisibilidadeService visibilidadeService;

    public VisibilidadeResource(VisibilidadeService visibilidadeService) {
        this.visibilidadeService = visibilidadeService;
    }

    /**
     * POST  /visibilidades : Create a new visibilidade.
     *
     * @param visibilidadeDTO the visibilidadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visibilidadeDTO, or with status 400 (Bad Request) if the visibilidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visibilidades")
    @Timed
    public ResponseEntity<VisibilidadeDTO> createVisibilidade(@Valid @RequestBody VisibilidadeDTO visibilidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Visibilidade : {}", visibilidadeDTO);
        if (visibilidadeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visibilidade cannot already have an ID")).body(null);
        }
        VisibilidadeDTO result = visibilidadeService.save(visibilidadeDTO);
        return ResponseEntity.created(new URI("/api/visibilidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visibilidades : Updates an existing visibilidade.
     *
     * @param visibilidadeDTO the visibilidadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visibilidadeDTO,
     * or with status 400 (Bad Request) if the visibilidadeDTO is not valid,
     * or with status 500 (Internal Server Error) if the visibilidadeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visibilidades")
    @Timed
    public ResponseEntity<VisibilidadeDTO> updateVisibilidade(@Valid @RequestBody VisibilidadeDTO visibilidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Visibilidade : {}", visibilidadeDTO);
        if (visibilidadeDTO.getId() == null) {
            return createVisibilidade(visibilidadeDTO);
        }
        VisibilidadeDTO result = visibilidadeService.save(visibilidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visibilidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visibilidades : get all the visibilidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of visibilidades in body
     */
    @GetMapping("/visibilidades")
    @Timed
    public ResponseEntity<List<VisibilidadeDTO>> getAllVisibilidades(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Visibilidades");
        Page<VisibilidadeDTO> page = visibilidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/visibilidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /visibilidades/:id : get the "id" visibilidade.
     *
     * @param id the id of the visibilidadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visibilidadeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/visibilidades/{id}")
    @Timed
    public ResponseEntity<VisibilidadeDTO> getVisibilidade(@PathVariable Long id) {
        log.debug("REST request to get Visibilidade : {}", id);
        VisibilidadeDTO visibilidadeDTO = visibilidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visibilidadeDTO));
    }

    /**
     * DELETE  /visibilidades/:id : delete the "id" visibilidade.
     *
     * @param id the id of the visibilidadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visibilidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisibilidade(@PathVariable Long id) {
        log.debug("REST request to delete Visibilidade : {}", id);
        visibilidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/visibilidades?query=:query : search for the visibilidade corresponding
     * to the query.
     *
     * @param query the query of the visibilidade search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/visibilidades")
    @Timed
    public ResponseEntity<List<VisibilidadeDTO>> searchVisibilidades(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Visibilidades for query {}", query);
        Page<VisibilidadeDTO> page = visibilidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/visibilidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
