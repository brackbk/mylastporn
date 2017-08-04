package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Amigos;
import com.mylastporn.app.repository.AmigosRepository;
import com.mylastporn.app.service.AmigosService;
import com.mylastporn.app.repository.search.AmigosSearchRepository;
import com.mylastporn.app.service.dto.AmigosDTO;
import com.mylastporn.app.service.mapper.AmigosMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mylastporn.app.domain.enumeration.Status;
/**
 * Test class for the AmigosResource REST controller.
 *
 * @see AmigosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class AmigosResourceIntTest {

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    @Autowired
    private AmigosRepository amigosRepository;

    @Autowired
    private AmigosMapper amigosMapper;

    @Autowired
    private AmigosService amigosService;

    @Autowired
    private AmigosSearchRepository amigosSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmigosMockMvc;

    private Amigos amigos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AmigosResource amigosResource = new AmigosResource(amigosService);
        this.restAmigosMockMvc = MockMvcBuilders.standaloneSetup(amigosResource)
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
    public static Amigos createEntity(EntityManager em) {
        Amigos amigos = new Amigos()
            .status(DEFAULT_STATUS);
        return amigos;
    }

    @Before
    public void initTest() {
        amigosSearchRepository.deleteAll();
        amigos = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmigos() throws Exception {
        int databaseSizeBeforeCreate = amigosRepository.findAll().size();

        // Create the Amigos
        AmigosDTO amigosDTO = amigosMapper.toDto(amigos);
        restAmigosMockMvc.perform(post("/api/amigos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amigosDTO)))
            .andExpect(status().isCreated());

        // Validate the Amigos in the database
        List<Amigos> amigosList = amigosRepository.findAll();
        assertThat(amigosList).hasSize(databaseSizeBeforeCreate + 1);
        Amigos testAmigos = amigosList.get(amigosList.size() - 1);
        assertThat(testAmigos.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Amigos in Elasticsearch
        Amigos amigosEs = amigosSearchRepository.findOne(testAmigos.getId());
        assertThat(amigosEs).isEqualToComparingFieldByField(testAmigos);
    }

    @Test
    @Transactional
    public void createAmigosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amigosRepository.findAll().size();

        // Create the Amigos with an existing ID
        amigos.setId(1L);
        AmigosDTO amigosDTO = amigosMapper.toDto(amigos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmigosMockMvc.perform(post("/api/amigos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amigosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Amigos> amigosList = amigosRepository.findAll();
        assertThat(amigosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);

        // Get all the amigosList
        restAmigosMockMvc.perform(get("/api/amigos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amigos.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);

        // Get the amigos
        restAmigosMockMvc.perform(get("/api/amigos/{id}", amigos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amigos.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmigos() throws Exception {
        // Get the amigos
        restAmigosMockMvc.perform(get("/api/amigos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);
        amigosSearchRepository.save(amigos);
        int databaseSizeBeforeUpdate = amigosRepository.findAll().size();

        // Update the amigos
        Amigos updatedAmigos = amigosRepository.findOne(amigos.getId());
        updatedAmigos
            .status(UPDATED_STATUS);
        AmigosDTO amigosDTO = amigosMapper.toDto(updatedAmigos);

        restAmigosMockMvc.perform(put("/api/amigos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amigosDTO)))
            .andExpect(status().isOk());

        // Validate the Amigos in the database
        List<Amigos> amigosList = amigosRepository.findAll();
        assertThat(amigosList).hasSize(databaseSizeBeforeUpdate);
        Amigos testAmigos = amigosList.get(amigosList.size() - 1);
        assertThat(testAmigos.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Amigos in Elasticsearch
        Amigos amigosEs = amigosSearchRepository.findOne(testAmigos.getId());
        assertThat(amigosEs).isEqualToComparingFieldByField(testAmigos);
    }

    @Test
    @Transactional
    public void updateNonExistingAmigos() throws Exception {
        int databaseSizeBeforeUpdate = amigosRepository.findAll().size();

        // Create the Amigos
        AmigosDTO amigosDTO = amigosMapper.toDto(amigos);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAmigosMockMvc.perform(put("/api/amigos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amigosDTO)))
            .andExpect(status().isCreated());

        // Validate the Amigos in the database
        List<Amigos> amigosList = amigosRepository.findAll();
        assertThat(amigosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);
        amigosSearchRepository.save(amigos);
        int databaseSizeBeforeDelete = amigosRepository.findAll().size();

        // Get the amigos
        restAmigosMockMvc.perform(delete("/api/amigos/{id}", amigos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean amigosExistsInEs = amigosSearchRepository.exists(amigos.getId());
        assertThat(amigosExistsInEs).isFalse();

        // Validate the database is empty
        List<Amigos> amigosList = amigosRepository.findAll();
        assertThat(amigosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAmigos() throws Exception {
        // Initialize the database
        amigosRepository.saveAndFlush(amigos);
        amigosSearchRepository.save(amigos);

        // Search the amigos
        restAmigosMockMvc.perform(get("/api/_search/amigos?query=id:" + amigos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amigos.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amigos.class);
        Amigos amigos1 = new Amigos();
        amigos1.setId(1L);
        Amigos amigos2 = new Amigos();
        amigos2.setId(amigos1.getId());
        assertThat(amigos1).isEqualTo(amigos2);
        amigos2.setId(2L);
        assertThat(amigos1).isNotEqualTo(amigos2);
        amigos1.setId(null);
        assertThat(amigos1).isNotEqualTo(amigos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmigosDTO.class);
        AmigosDTO amigosDTO1 = new AmigosDTO();
        amigosDTO1.setId(1L);
        AmigosDTO amigosDTO2 = new AmigosDTO();
        assertThat(amigosDTO1).isNotEqualTo(amigosDTO2);
        amigosDTO2.setId(amigosDTO1.getId());
        assertThat(amigosDTO1).isEqualTo(amigosDTO2);
        amigosDTO2.setId(2L);
        assertThat(amigosDTO1).isNotEqualTo(amigosDTO2);
        amigosDTO1.setId(null);
        assertThat(amigosDTO1).isNotEqualTo(amigosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amigosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amigosMapper.fromId(null)).isNull();
    }
}
