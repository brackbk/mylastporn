package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Tipo;
import com.mylastporn.app.repository.TipoRepository;
import com.mylastporn.app.service.TipoService;
import com.mylastporn.app.repository.search.TipoSearchRepository;
import com.mylastporn.app.service.dto.TipoDTO;
import com.mylastporn.app.service.mapper.TipoMapper;
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

import com.mylastporn.app.domain.enumeration.Status;
/**
 * Test class for the TipoResource REST controller.
 *
 * @see TipoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class TipoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    private static final ZonedDateTime DEFAULT_DATACRIADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATACRIADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private TipoMapper tipoMapper;

    @Autowired
    private TipoService tipoService;

    @Autowired
    private TipoSearchRepository tipoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoMockMvc;

    private Tipo tipo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoResource tipoResource = new TipoResource(tipoService);
        this.restTipoMockMvc = MockMvcBuilders.standaloneSetup(tipoResource)
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
    public static Tipo createEntity(EntityManager em) {
        Tipo tipo = new Tipo()
            .nome(DEFAULT_NOME)
            .status(DEFAULT_STATUS)
            .datacriado(DEFAULT_DATACRIADO);
        return tipo;
    }

    @Before
    public void initTest() {
        tipoSearchRepository.deleteAll();
        tipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipo() throws Exception {
        int databaseSizeBeforeCreate = tipoRepository.findAll().size();

        // Create the Tipo
        TipoDTO tipoDTO = tipoMapper.toDto(tipo);
        restTipoMockMvc.perform(post("/api/tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDTO)))
            .andExpect(status().isCreated());

        // Validate the Tipo in the database
        List<Tipo> tipoList = tipoRepository.findAll();
        assertThat(tipoList).hasSize(databaseSizeBeforeCreate + 1);
        Tipo testTipo = tipoList.get(tipoList.size() - 1);
        assertThat(testTipo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTipo.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);

        // Validate the Tipo in Elasticsearch
        Tipo tipoEs = tipoSearchRepository.findOne(testTipo.getId());
        assertThat(tipoEs).isEqualToComparingFieldByField(testTipo);
    }

    @Test
    @Transactional
    public void createTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRepository.findAll().size();

        // Create the Tipo with an existing ID
        tipo.setId(1L);
        TipoDTO tipoDTO = tipoMapper.toDto(tipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoMockMvc.perform(post("/api/tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tipo> tipoList = tipoRepository.findAll();
        assertThat(tipoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoRepository.findAll().size();
        // set the field null
        tipo.setNome(null);

        // Create the Tipo, which fails.
        TipoDTO tipoDTO = tipoMapper.toDto(tipo);

        restTipoMockMvc.perform(post("/api/tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDTO)))
            .andExpect(status().isBadRequest());

        List<Tipo> tipoList = tipoRepository.findAll();
        assertThat(tipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoRepository.findAll().size();
        // set the field null
        tipo.setDatacriado(null);

        // Create the Tipo, which fails.
        TipoDTO tipoDTO = tipoMapper.toDto(tipo);

        restTipoMockMvc.perform(post("/api/tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDTO)))
            .andExpect(status().isBadRequest());

        List<Tipo> tipoList = tipoRepository.findAll();
        assertThat(tipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipos() throws Exception {
        // Initialize the database
        tipoRepository.saveAndFlush(tipo);

        // Get all the tipoList
        restTipoMockMvc.perform(get("/api/tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void getTipo() throws Exception {
        // Initialize the database
        tipoRepository.saveAndFlush(tipo);

        // Get the tipo
        restTipoMockMvc.perform(get("/api/tipos/{id}", tipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.datacriado").value(sameInstant(DEFAULT_DATACRIADO)));
    }

    @Test
    @Transactional
    public void getNonExistingTipo() throws Exception {
        // Get the tipo
        restTipoMockMvc.perform(get("/api/tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipo() throws Exception {
        // Initialize the database
        tipoRepository.saveAndFlush(tipo);
        tipoSearchRepository.save(tipo);
        int databaseSizeBeforeUpdate = tipoRepository.findAll().size();

        // Update the tipo
        Tipo updatedTipo = tipoRepository.findOne(tipo.getId());
        updatedTipo
            .nome(UPDATED_NOME)
            .status(UPDATED_STATUS)
            .datacriado(UPDATED_DATACRIADO);
        TipoDTO tipoDTO = tipoMapper.toDto(updatedTipo);

        restTipoMockMvc.perform(put("/api/tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDTO)))
            .andExpect(status().isOk());

        // Validate the Tipo in the database
        List<Tipo> tipoList = tipoRepository.findAll();
        assertThat(tipoList).hasSize(databaseSizeBeforeUpdate);
        Tipo testTipo = tipoList.get(tipoList.size() - 1);
        assertThat(testTipo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTipo.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);

        // Validate the Tipo in Elasticsearch
        Tipo tipoEs = tipoSearchRepository.findOne(testTipo.getId());
        assertThat(tipoEs).isEqualToComparingFieldByField(testTipo);
    }

    @Test
    @Transactional
    public void updateNonExistingTipo() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepository.findAll().size();

        // Create the Tipo
        TipoDTO tipoDTO = tipoMapper.toDto(tipo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoMockMvc.perform(put("/api/tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDTO)))
            .andExpect(status().isCreated());

        // Validate the Tipo in the database
        List<Tipo> tipoList = tipoRepository.findAll();
        assertThat(tipoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipo() throws Exception {
        // Initialize the database
        tipoRepository.saveAndFlush(tipo);
        tipoSearchRepository.save(tipo);
        int databaseSizeBeforeDelete = tipoRepository.findAll().size();

        // Get the tipo
        restTipoMockMvc.perform(delete("/api/tipos/{id}", tipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tipoExistsInEs = tipoSearchRepository.exists(tipo.getId());
        assertThat(tipoExistsInEs).isFalse();

        // Validate the database is empty
        List<Tipo> tipoList = tipoRepository.findAll();
        assertThat(tipoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTipo() throws Exception {
        // Initialize the database
        tipoRepository.saveAndFlush(tipo);
        tipoSearchRepository.save(tipo);

        // Search the tipo
        restTipoMockMvc.perform(get("/api/_search/tipos?query=id:" + tipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tipo.class);
        Tipo tipo1 = new Tipo();
        tipo1.setId(1L);
        Tipo tipo2 = new Tipo();
        tipo2.setId(tipo1.getId());
        assertThat(tipo1).isEqualTo(tipo2);
        tipo2.setId(2L);
        assertThat(tipo1).isNotEqualTo(tipo2);
        tipo1.setId(null);
        assertThat(tipo1).isNotEqualTo(tipo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDTO.class);
        TipoDTO tipoDTO1 = new TipoDTO();
        tipoDTO1.setId(1L);
        TipoDTO tipoDTO2 = new TipoDTO();
        assertThat(tipoDTO1).isNotEqualTo(tipoDTO2);
        tipoDTO2.setId(tipoDTO1.getId());
        assertThat(tipoDTO1).isEqualTo(tipoDTO2);
        tipoDTO2.setId(2L);
        assertThat(tipoDTO1).isNotEqualTo(tipoDTO2);
        tipoDTO1.setId(null);
        assertThat(tipoDTO1).isNotEqualTo(tipoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoMapper.fromId(null)).isNull();
    }
}
