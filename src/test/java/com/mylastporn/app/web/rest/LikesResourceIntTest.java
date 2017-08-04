package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Likes;
import com.mylastporn.app.repository.LikesRepository;
import com.mylastporn.app.service.LikesService;
import com.mylastporn.app.repository.search.LikesSearchRepository;
import com.mylastporn.app.service.dto.LikesDTO;
import com.mylastporn.app.service.mapper.LikesMapper;
import com.mylastporn.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mylastporn.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LikesResource REST controller.
 *
 * @see LikesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class LikesResourceIntTest {

    private static final Integer DEFAULT_IDCONTEUDO = 1;
    private static final Integer UPDATED_IDCONTEUDO = 2;

    private static final Boolean DEFAULT_LIKE = false;
    private static final Boolean UPDATED_LIKE = true;

    private static final Boolean DEFAULT_DISLIKE = false;
    private static final Boolean UPDATED_DISLIKE = true;

    private static final ZonedDateTime DEFAULT_DATACRIADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATACRIADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private LikesService likesService;

    @Autowired
    private LikesSearchRepository likesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLikesMockMvc;

    private Likes likes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LikesResource likesResource = new LikesResource(likesService);
        this.restLikesMockMvc = MockMvcBuilders.standaloneSetup(likesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Likes createEntity(EntityManager em) {
        Likes likes = new Likes()
            .idconteudo(DEFAULT_IDCONTEUDO)
            .like(DEFAULT_LIKE)
            .dislike(DEFAULT_DISLIKE)
            .datacriado(DEFAULT_DATACRIADO);
        return likes;
    }

    @Before
    public void initTest() {
        likesSearchRepository.deleteAll();
        likes = createEntity(em);
    }

    @Test
    @Transactional
    public void createLikes() throws Exception {
        int databaseSizeBeforeCreate = likesRepository.findAll().size();

        // Create the Likes
        LikesDTO likesDTO = likesMapper.toDto(likes);
        restLikesMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isCreated());

        // Validate the Likes in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeCreate + 1);
        Likes testLikes = likesList.get(likesList.size() - 1);
        assertThat(testLikes.getIdconteudo()).isEqualTo(DEFAULT_IDCONTEUDO);
        assertThat(testLikes.isLike()).isEqualTo(DEFAULT_LIKE);
        assertThat(testLikes.isDislike()).isEqualTo(DEFAULT_DISLIKE);
        assertThat(testLikes.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);

        // Validate the Likes in Elasticsearch
        Likes likesEs = likesSearchRepository.findOne(testLikes.getId());
        assertThat(likesEs).isEqualToComparingFieldByField(testLikes);
    }

    @Test
    @Transactional
    public void createLikesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likesRepository.findAll().size();

        // Create the Likes with an existing ID
        likes.setId(1L);
        LikesDTO likesDTO = likesMapper.toDto(likes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikesMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdconteudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = likesRepository.findAll().size();
        // set the field null
        likes.setIdconteudo(null);

        // Create the Likes, which fails.
        LikesDTO likesDTO = likesMapper.toDto(likes);

        restLikesMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isBadRequest());

        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = likesRepository.findAll().size();
        // set the field null
        likes.setDatacriado(null);

        // Create the Likes, which fails.
        LikesDTO likesDTO = likesMapper.toDto(likes);

        restLikesMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isBadRequest());

        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get all the likesList
        restLikesMockMvc.perform(get("/api/likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likes.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE.booleanValue())))
            .andExpect(jsonPath("$.[*].dislike").value(hasItem(DEFAULT_DISLIKE.booleanValue())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void getLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get the likes
        restLikesMockMvc.perform(get("/api/likes/{id}", likes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(likes.getId().intValue()))
            .andExpect(jsonPath("$.idconteudo").value(DEFAULT_IDCONTEUDO))
            .andExpect(jsonPath("$.like").value(DEFAULT_LIKE.booleanValue()))
            .andExpect(jsonPath("$.dislike").value(DEFAULT_DISLIKE.booleanValue()))
            .andExpect(jsonPath("$.datacriado").value(sameInstant(DEFAULT_DATACRIADO)));
    }

    @Test
    @Transactional
    public void getNonExistingLikes() throws Exception {
        // Get the likes
        restLikesMockMvc.perform(get("/api/likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);
        likesSearchRepository.save(likes);
        int databaseSizeBeforeUpdate = likesRepository.findAll().size();

        // Update the likes
        Likes updatedLikes = likesRepository.findOne(likes.getId());
        updatedLikes
            .idconteudo(UPDATED_IDCONTEUDO)
            .like(UPDATED_LIKE)
            .dislike(UPDATED_DISLIKE)
            .datacriado(UPDATED_DATACRIADO);
        LikesDTO likesDTO = likesMapper.toDto(updatedLikes);

        restLikesMockMvc.perform(put("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isOk());

        // Validate the Likes in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeUpdate);
        Likes testLikes = likesList.get(likesList.size() - 1);
        assertThat(testLikes.getIdconteudo()).isEqualTo(UPDATED_IDCONTEUDO);
        assertThat(testLikes.isLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testLikes.isDislike()).isEqualTo(UPDATED_DISLIKE);
        assertThat(testLikes.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);

        // Validate the Likes in Elasticsearch
        Likes likesEs = likesSearchRepository.findOne(testLikes.getId());
        assertThat(likesEs).isEqualToComparingFieldByField(testLikes);
    }

    @Test
    @Transactional
    public void updateNonExistingLikes() throws Exception {
        int databaseSizeBeforeUpdate = likesRepository.findAll().size();

        // Create the Likes
        LikesDTO likesDTO = likesMapper.toDto(likes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLikesMockMvc.perform(put("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isCreated());

        // Validate the Likes in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);
        likesSearchRepository.save(likes);
        int databaseSizeBeforeDelete = likesRepository.findAll().size();

        // Get the likes
        restLikesMockMvc.perform(delete("/api/likes/{id}", likes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean likesExistsInEs = likesSearchRepository.exists(likes.getId());
        assertThat(likesExistsInEs).isFalse();

        // Validate the database is empty
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);
        likesSearchRepository.save(likes);

        // Search the likes
        restLikesMockMvc.perform(get("/api/_search/likes?query=id:" + likes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likes.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE.booleanValue())))
            .andExpect(jsonPath("$.[*].dislike").value(hasItem(DEFAULT_DISLIKE.booleanValue())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Likes.class);
        Likes likes1 = new Likes();
        likes1.setId(1L);
        Likes likes2 = new Likes();
        likes2.setId(likes1.getId());
        assertThat(likes1).isEqualTo(likes2);
        likes2.setId(2L);
        assertThat(likes1).isNotEqualTo(likes2);
        likes1.setId(null);
        assertThat(likes1).isNotEqualTo(likes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikesDTO.class);
        LikesDTO likesDTO1 = new LikesDTO();
        likesDTO1.setId(1L);
        LikesDTO likesDTO2 = new LikesDTO();
        assertThat(likesDTO1).isNotEqualTo(likesDTO2);
        likesDTO2.setId(likesDTO1.getId());
        assertThat(likesDTO1).isEqualTo(likesDTO2);
        likesDTO2.setId(2L);
        assertThat(likesDTO1).isNotEqualTo(likesDTO2);
        likesDTO1.setId(null);
        assertThat(likesDTO1).isNotEqualTo(likesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(likesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(likesMapper.fromId(null)).isNull();
    }
}
