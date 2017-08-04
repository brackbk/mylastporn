package com.mylastporn.app.service.impl;

import com.mylastporn.app.service.VideoService;
import com.mylastporn.app.domain.Video;
import com.mylastporn.app.repository.VideoRepository;
import com.mylastporn.app.repository.search.VideoSearchRepository;
import com.mylastporn.app.service.dto.VideoDTO;
import com.mylastporn.app.service.mapper.VideoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Video.
 */
@Service
@Transactional
public class VideoServiceImpl implements VideoService{

    private final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final VideoRepository videoRepository;

    private final VideoMapper videoMapper;

    private final VideoSearchRepository videoSearchRepository;

    public VideoServiceImpl(VideoRepository videoRepository, VideoMapper videoMapper, VideoSearchRepository videoSearchRepository) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
        this.videoSearchRepository = videoSearchRepository;
    }

    /**
     * Save a video.
     *
     * @param videoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VideoDTO save(VideoDTO videoDTO) {

        log.debug("Request to save Video : {}", videoDTO);
        
        Video video = videoMapper.toEntity(videoDTO);
        video = videoRepository.save(video);
        VideoDTO result = videoMapper.toDto(video);
        videoSearchRepository.save(video);
        return result;
    }

    /**
     *  Get all the videos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VideoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Videos");
        return videoRepository.findAll(pageable)
            .map(videoMapper::toDto);
    }

    /**
     *  Get one video by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VideoDTO findOne(Long id) {
        log.debug("Request to get Video : {}", id);
        Video video = videoRepository.findOneWithEagerRelationships(id);
        return videoMapper.toDto(video);
    }

    /**
     *  Delete the  video by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Video : {}", id);
        videoRepository.delete(id);
        videoSearchRepository.delete(id);
    }

    /**
     * Search for the video corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VideoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Videos for query {}", query);
        Page<Video> result = videoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(videoMapper::toDto);
    }
}
