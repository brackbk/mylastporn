package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.service.LikesService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.LikesDTO;
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
 * REST controller for managing Likes.
 */
@RestController
@RequestMapping("/api")
public class LikesResource {

    private final Logger log = LoggerFactory.getLogger(LikesResource.class);

    private static final String ENTITY_NAME = "likes";

    private final LikesService likesService;

    public LikesResource(LikesService likesService) {
        this.likesService = likesService;
    }

    /**
     * POST  /likes : Create a new likes.
     *
     * @param likesDTO the likesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new likesDTO, or with status 400 (Bad Request) if the likes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/likes")
    @Timed
    public ResponseEntity<LikesDTO> createLikes(@Valid @RequestBody LikesDTO likesDTO) throws URISyntaxException {
        log.debug("REST request to save Likes : {}", likesDTO);
        if (likesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new likes cannot already have an ID")).body(null);
        }
        LikesDTO result = likesService.save(likesDTO);
        return ResponseEntity.created(new URI("/api/likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /likes : Updates an existing likes.
     *
     * @param likesDTO the likesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated likesDTO,
     * or with status 400 (Bad Request) if the likesDTO is not valid,
     * or with status 500 (Internal Server Error) if the likesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/likes")
    @Timed
    public ResponseEntity<LikesDTO> updateLikes(@Valid @RequestBody LikesDTO likesDTO) throws URISyntaxException {
        log.debug("REST request to update Likes : {}", likesDTO);
        if (likesDTO.getId() == null) {
            return createLikes(likesDTO);
        }
        LikesDTO result = likesService.save(likesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, likesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /likes : get all the likes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of likes in body
     */
    @GetMapping("/likes")
    @Timed
    public ResponseEntity<List<LikesDTO>> getAllLikes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Likes");
        Page<LikesDTO> page = likesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/likes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /likes/:id : get the "id" likes.
     *
     * @param id the id of the likesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the likesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/likes/{id}")
    @Timed
    public ResponseEntity<LikesDTO> getLikes(@PathVariable Long id) {
        log.debug("REST request to get Likes : {}", id);
        LikesDTO likesDTO = likesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(likesDTO));
    }

    /**
     * DELETE  /likes/:id : delete the "id" likes.
     *
     * @param id the id of the likesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/likes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLikes(@PathVariable Long id) {
        log.debug("REST request to delete Likes : {}", id);
        likesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/likes?query=:query : search for the likes corresponding
     * to the query.
     *
     * @param query the query of the likes search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/likes")
    @Timed
    public ResponseEntity<List<LikesDTO>> searchLikes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Likes for query {}", query);
        Page<LikesDTO> page = likesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/likes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
