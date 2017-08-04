package com.mylastporn.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mylastporn.app.security.AuthoritiesConstants;
import com.mylastporn.app.service.VideoService;
import com.mylastporn.app.web.rest.util.HeaderUtil;
import com.mylastporn.app.web.rest.util.PaginationUtil;
import com.mylastporn.app.service.dto.VideoDTO;
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
import com.mylastporn.app.security.AuthoritiesConstants;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
/**
 * REST controller for managing Video.
 */
@RestController
@RequestMapping("/api")
public class VideoResource {

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);

    private static final String ENTITY_NAME = "video";

    private final VideoService videoService;

    public VideoResource(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * POST  /videos : Create a new video.
     *
     * @param videoDTO the videoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new videoDTO, or with status 400 (Bad Request) if the video has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/videos")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<VideoDTO> createVideo(@Valid @RequestBody VideoDTO videoDTO) throws URISyntaxException {
        log.debug("REST request to save Video : {}", videoDTO);
        if (videoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new video cannot already have an ID")).body(null);
        }
        VideoDTO result = videoService.save(videoDTO);
        return ResponseEntity.created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /videos : Updates an existing video.
     *
     * @param videoDTO the videoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated videoDTO,
     * or with status 400 (Bad Request) if the videoDTO is not valid,
     * or with status 500 (Internal Server Error) if the videoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/videos")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<VideoDTO> updateVideo(@Valid @RequestBody VideoDTO videoDTO) throws URISyntaxException {
        log.debug("REST request to update Video : {}", videoDTO);
        if (videoDTO.getId() == null) {
            return createVideo(videoDTO);
        }
        VideoDTO result = videoService.save(videoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, videoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /videos : get all the videos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of videos in body
     */
    @GetMapping("/videos")
    @Timed
    public ResponseEntity<List<VideoDTO>> getAllVideos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Videos");
        Page<VideoDTO> page = videoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/videos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /videos/:id : get the "id" video.
     *
     * @param id the id of the videoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the videoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/videos/{id}")
    @Timed
    public ResponseEntity<VideoDTO> getVideo(@PathVariable Long id) {
        log.debug("REST request to get Video : {}", id);
        VideoDTO videoDTO = videoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(videoDTO));
    }

    /**
     * DELETE  /videos/:id : delete the "id" video.
     *
     * @param id the id of the videoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/videos/{id}")
    @Timed
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        log.debug("REST request to delete Video : {}", id);
        videoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/videos?query=:query : search for the video corresponding
     * to the query.
     *
     * @param query the query of the video search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/videos")
    @Timed
    public ResponseEntity<List<VideoDTO>> searchVideos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Videos for query {}", query);
        Page<VideoDTO> page = videoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/videos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
