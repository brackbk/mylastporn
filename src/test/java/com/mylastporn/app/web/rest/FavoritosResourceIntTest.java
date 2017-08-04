package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Favoritos;
import com.mylastporn.app.repository.FavoritosRepository;
import com.mylastporn.app.service.FavoritosService;
import com.mylastporn.app.repository.search.FavoritosSearchRepository;
import com.mylastporn.app.service.dto.FavoritosDTO;
import com.mylastporn.app.service.mapper.FavoritosMapper;
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
 * Test class for the FavoritosResource REST controller.
 *
 * @see FavoritosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class FavoritosResourceIntTest {

    private static final Integer DEFAULT_IDCONTEUDO = 1;
    private static final Integer UPDATED_IDCONTEUDO = 2;

    private static final ZonedDateTime DEFAULT_DATACRIADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATACRIADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Autowired
    private FavoritosMapper favoritosMapper;

    @Autowired
    private FavoritosService favoritosService;

    @Autowired
    private FavoritosSearchRepository favoritosSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFavoritosMockMvc;

    private Favoritos favoritos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FavoritosResource favoritosResource = new FavoritosResource(favoritosService);
        this.restFavoritosMockMvc = MockMvcBuilders.standaloneSetup(favoritosResource)
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
    public static Favoritos createEntity(EntityManager em) {
        Favoritos favoritos = new Favoritos()
            .idconteudo(DEFAULT_IDCONTEUDO)
            .datacriado(DEFAULT_DATACRIADO);
        return favoritos;
    }

    @Before
    public void initTest() {
        favoritosSearchRepository.deleteAll();
        favoritos = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavoritos() throws Exception {
        int databaseSizeBeforeCreate = favoritosRepository.findAll().size();

        // Create the Favoritos
        FavoritosDTO favoritosDTO = favoritosMapper.toDto(favoritos);
        restFavoritosMockMvc.perform(post("/api/favoritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoritosDTO)))
            .andExpect(status().isCreated());

        // Validate the Favoritos in the database
        List<Favoritos> favoritosList = favoritosRepository.findAll();
        assertThat(favoritosList).hasSize(databaseSizeBeforeCreate + 1);
        Favoritos testFavoritos = favoritosList.get(favoritosList.size() - 1);
        assertThat(testFavoritos.getIdconteudo()).isEqualTo(DEFAULT_IDCONTEUDO);
        assertThat(testFavoritos.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);

        // Validate the Favoritos in Elasticsearch
        Favoritos favoritosEs = favoritosSearchRepository.findOne(testFavoritos.getId());
        assertThat(favoritosEs).isEqualToComparingFieldByField(testFavoritos);
    }

    @Test
    @Transactional
    public void createFavoritosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favoritosRepository.findAll().size();

        // Create the Favoritos with an existing ID
        favoritos.setId(1L);
        FavoritosDTO favoritosDTO = favoritosMapper.toDto(favoritos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavoritosMockMvc.perform(post("/api/favoritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoritosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Favoritos> favoritosList = favoritosRepository.findAll();
        assertThat(favoritosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdconteudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = favoritosRepository.findAll().size();
        // set the field null
        favoritos.setIdconteudo(null);

        // Create the Favoritos, which fails.
        FavoritosDTO favoritosDTO = favoritosMapper.toDto(favoritos);

        restFavoritosMockMvc.perform(post("/api/favoritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoritosDTO)))
            .andExpect(status().isBadRequest());

        List<Favoritos> favoritosList = favoritosRepository.findAll();
        assertThat(favoritosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = favoritosRepository.findAll().size();
        // set the field null
        favoritos.setDatacriado(null);

        // Create the Favoritos, which fails.
        FavoritosDTO favoritosDTO = favoritosMapper.toDto(favoritos);

        restFavoritosMockMvc.perform(post("/api/favoritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoritosDTO)))
            .andExpect(status().isBadRequest());

        List<Favoritos> favoritosList = favoritosRepository.findAll();
        assertThat(favoritosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFavoritos() throws Exception {
        // Initialize the database
        favoritosRepository.saveAndFlush(favoritos);

        // Get all the favoritosList
        restFavoritosMockMvc.perform(get("/api/favoritos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoritos.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void getFavoritos() throws Exception {
        // Initialize the database
        favoritosRepository.saveAndFlush(favoritos);

        // Get the favoritos
        restFavoritosMockMvc.perform(get("/api/favoritos/{id}", favoritos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(favoritos.getId().intValue()))
            .andExpect(jsonPath("$.idconteudo").value(DEFAULT_IDCONTEUDO))
            .andExpect(jsonPath("$.datacriado").value(sameInstant(DEFAULT_DATACRIADO)));
    }

    @Test
    @Transactional
    public void getNonExistingFavoritos() throws Exception {
        // Get the favoritos
        restFavoritosMockMvc.perform(get("/api/favoritos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavoritos() throws Exception {
        // Initialize the database
        favoritosRepository.saveAndFlush(favoritos);
        favoritosSearchRepository.save(favoritos);
        int databaseSizeBeforeUpdate = favoritosRepository.findAll().size();

        // Update the favoritos
        Favoritos updatedFavoritos = favoritosRepository.findOne(favoritos.getId());
        updatedFavoritos
            .idconteudo(UPDATED_IDCONTEUDO)
            .datacriado(UPDATED_DATACRIADO);
        FavoritosDTO favoritosDTO = favoritosMapper.toDto(updatedFavoritos);

        restFavoritosMockMvc.perform(put("/api/favoritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoritosDTO)))
            .andExpect(status().isOk());

        // Validate the Favoritos in the database
        List<Favoritos> favoritosList = favoritosRepository.findAll();
        assertThat(favoritosList).hasSize(databaseSizeBeforeUpdate);
        Favoritos testFavoritos = favoritosList.get(favoritosList.size() - 1);
        assertThat(testFavoritos.getIdconteudo()).isEqualTo(UPDATED_IDCONTEUDO);
        assertThat(testFavoritos.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);

        // Validate the Favoritos in Elasticsearch
        Favoritos favoritosEs = favoritosSearchRepository.findOne(testFavoritos.getId());
        assertThat(favoritosEs).isEqualToComparingFieldByField(testFavoritos);
    }

    @Test
    @Transactional
    public void updateNonExistingFavoritos() throws Exception {
        int databaseSizeBeforeUpdate = favoritosRepository.findAll().size();

        // Create the Favoritos
        FavoritosDTO favoritosDTO = favoritosMapper.toDto(favoritos);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFavoritosMockMvc.perform(put("/api/favoritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoritosDTO)))
            .andExpect(status().isCreated());

        // Validate the Favoritos in the database
        List<Favoritos> favoritosList = favoritosRepository.findAll();
        assertThat(favoritosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFavoritos() throws Exception {
        // Initialize the database
        favoritosRepository.saveAndFlush(favoritos);
        favoritosSearchRepository.save(favoritos);
        int databaseSizeBeforeDelete = favoritosRepository.findAll().size();

        // Get the favoritos
        restFavoritosMockMvc.perform(delete("/api/favoritos/{id}", favoritos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean favoritosExistsInEs = favoritosSearchRepository.exists(favoritos.getId());
        assertThat(favoritosExistsInEs).isFalse();

        // Validate the database is empty
        List<Favoritos> favoritosList = favoritosRepository.findAll();
        assertThat(favoritosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFavoritos() throws Exception {
        // Initialize the database
        favoritosRepository.saveAndFlush(favoritos);
        favoritosSearchRepository.save(favoritos);

        // Search the favoritos
        restFavoritosMockMvc.perform(get("/api/_search/favoritos?query=id:" + favoritos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoritos.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Favoritos.class);
        Favoritos favoritos1 = new Favoritos();
        favoritos1.setId(1L);
        Favoritos favoritos2 = new Favoritos();
        favoritos2.setId(favoritos1.getId());
        assertThat(favoritos1).isEqualTo(favoritos2);
        favoritos2.setId(2L);
        assertThat(favoritos1).isNotEqualTo(favoritos2);
        favoritos1.setId(null);
        assertThat(favoritos1).isNotEqualTo(favoritos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoritosDTO.class);
        FavoritosDTO favoritosDTO1 = new FavoritosDTO();
        favoritosDTO1.setId(1L);
        FavoritosDTO favoritosDTO2 = new FavoritosDTO();
        assertThat(favoritosDTO1).isNotEqualTo(favoritosDTO2);
        favoritosDTO2.setId(favoritosDTO1.getId());
        assertThat(favoritosDTO1).isEqualTo(favoritosDTO2);
        favoritosDTO2.setId(2L);
        assertThat(favoritosDTO1).isNotEqualTo(favoritosDTO2);
        favoritosDTO1.setId(null);
        assertThat(favoritosDTO1).isNotEqualTo(favoritosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(favoritosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(favoritosMapper.fromId(null)).isNull();
    }
}
