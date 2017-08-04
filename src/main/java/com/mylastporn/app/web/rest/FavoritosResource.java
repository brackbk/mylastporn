package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.FavoritosService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.FavoritosDTO;
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
 * REST controller for managing Favoritos.
 */
@RestController
@RequestMapping("/api")
public class FavoritosResource {

    private final Logger log = LoggerFactory.getLogger(FavoritosResource.class);

    private static final String ENTITY_NAME = "favoritos";

    private final FavoritosService favoritosService;

    public FavoritosResource(FavoritosService favoritosService) {
        this.favoritosService = favoritosService;
    }

    /**
     * POST  /favoritos : Create a new favoritos.
     *
     * @param favoritosDTO the favoritosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new favoritosDTO, or with status 400 (Bad Request) if the favoritos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/favoritos")
    @Timed
    public ResponseEntity<FavoritosDTO> createFavoritos(@Valid @RequestBody FavoritosDTO favoritosDTO) throws URISyntaxException {
        log.debug("REST request to save Favoritos : {}", favoritosDTO);
        if (favoritosDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new favoritos cannot already have an ID")).body(null);
        }
        FavoritosDTO result = favoritosService.save(favoritosDTO);
        return ResponseEntity.created(new URI("/api/favoritos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /favoritos : Updates an existing favoritos.
     *
     * @param favoritosDTO the favoritosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated favoritosDTO,
     * or with status 400 (Bad Request) if the favoritosDTO is not valid,
     * or with status 500 (Internal Server Error) if the favoritosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/favoritos")
    @Timed
    public ResponseEntity<FavoritosDTO> updateFavoritos(@Valid @RequestBody FavoritosDTO favoritosDTO) throws URISyntaxException {
        log.debug("REST request to update Favoritos : {}", favoritosDTO);
        if (favoritosDTO.getId() == null) {
            return createFavoritos(favoritosDTO);
        }
        FavoritosDTO result = favoritosService.save(favoritosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, favoritosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /favoritos : get all the favoritos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of favoritos in body
     */
    @GetMapping("/favoritos")
    @Timed
    public ResponseEntity<List<FavoritosDTO>> getAllFavoritos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Favoritos");
        Page<FavoritosDTO> page = favoritosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/favoritos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /favoritos/:id : get the "id" favoritos.
     *
     * @param id the id of the favoritosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the favoritosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/favoritos/{id}")
    @Timed
    public ResponseEntity<FavoritosDTO> getFavoritos(@PathVariable Long id) {
        log.debug("REST request to get Favoritos : {}", id);
        FavoritosDTO favoritosDTO = favoritosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favoritosDTO));
    }

    /**
     * DELETE  /favoritos/:id : delete the "id" favoritos.
     *
     * @param id the id of the favoritosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/favoritos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFavoritos(@PathVariable Long id) {
        log.debug("REST request to delete Favoritos : {}", id);
        favoritosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/favoritos?query=:query : search for the favoritos corresponding
     * to the query.
     *
     * @param query the query of the favoritos search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/favoritos")
    @Timed
    public ResponseEntity<List<FavoritosDTO>> searchFavoritos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Favoritos for query {}", query);
        Page<FavoritosDTO> page = favoritosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/favoritos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
