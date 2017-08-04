package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Modulos;
import com.mylastporn.app.repository.ModulosRepository;
import com.mylastporn.app.service.ModulosService;
import com.mylastporn.app.repository.search.ModulosSearchRepository;
import com.mylastporn.app.service.dto.ModulosDTO;
import com.mylastporn.app.service.mapper.ModulosMapper;
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
 * Test class for the ModulosResource REST controller.
 *
 * @see ModulosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class ModulosResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CAMINHO = "AAAAAAAAAA";
    private static final String UPDATED_CAMINHO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    private static final ZonedDateTime DEFAULT_DATACRIADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATACRIADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ModulosRepository modulosRepository;

    @Autowired
    private ModulosMapper modulosMapper;

    @Autowired
    private ModulosService modulosService;

    @Autowired
    private ModulosSearchRepository modulosSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModulosMockMvc;

    private Modulos modulos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModulosResource modulosResource = new ModulosResource(modulosService);
        this.restModulosMockMvc = MockMvcBuilders.standaloneSetup(modulosResource)
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
    public static Modulos createEntity(EntityManager em) {
        Modulos modulos = new Modulos()
            .nome(DEFAULT_NOME)
            .caminho(DEFAULT_CAMINHO)
            .status(DEFAULT_STATUS)
            .datacriado(DEFAULT_DATACRIADO);
        return modulos;
    }

    @Before
    public void initTest() {
        modulosSearchRepository.deleteAll();
        modulos = createEntity(em);
    }

    @Test
    @Transactional
    public void createModulos() throws Exception {
        int databaseSizeBeforeCreate = modulosRepository.findAll().size();

        // Create the Modulos
        ModulosDTO modulosDTO = modulosMapper.toDto(modulos);
        restModulosMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulosDTO)))
            .andExpect(status().isCreated());

        // Validate the Modulos in the database
        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeCreate + 1);
        Modulos testModulos = modulosList.get(modulosList.size() - 1);
        assertThat(testModulos.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testModulos.getCaminho()).isEqualTo(DEFAULT_CAMINHO);
        assertThat(testModulos.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testModulos.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);

        // Validate the Modulos in Elasticsearch
        Modulos modulosEs = modulosSearchRepository.findOne(testModulos.getId());
        assertThat(modulosEs).isEqualToComparingFieldByField(testModulos);
    }

    @Test
    @Transactional
    public void createModulosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modulosRepository.findAll().size();

        // Create the Modulos with an existing ID
        modulos.setId(1L);
        ModulosDTO modulosDTO = modulosMapper.toDto(modulos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModulosMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modulosRepository.findAll().size();
        // set the field null
        modulos.setNome(null);

        // Create the Modulos, which fails.
        ModulosDTO modulosDTO = modulosMapper.toDto(modulos);

        restModulosMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulosDTO)))
            .andExpect(status().isBadRequest());

        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaminhoIsRequired() throws Exception {
        int databaseSizeBeforeTest = modulosRepository.findAll().size();
        // set the field null
        modulos.setCaminho(null);

        // Create the Modulos, which fails.
        ModulosDTO modulosDTO = modulosMapper.toDto(modulos);

        restModulosMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulosDTO)))
            .andExpect(status().isBadRequest());

        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = modulosRepository.findAll().size();
        // set the field null
        modulos.setDatacriado(null);

        // Create the Modulos, which fails.
        ModulosDTO modulosDTO = modulosMapper.toDto(modulos);

        restModulosMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulosDTO)))
            .andExpect(status().isBadRequest());

        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModulos() throws Exception {
        // Initialize the database
        modulosRepository.saveAndFlush(modulos);

        // Get all the modulosList
        restModulosMockMvc.perform(get("/api/modulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void getModulos() throws Exception {
        // Initialize the database
        modulosRepository.saveAndFlush(modulos);

        // Get the modulos
        restModulosMockMvc.perform(get("/api/modulos/{id}", modulos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modulos.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.caminho").value(DEFAULT_CAMINHO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.datacriado").value(sameInstant(DEFAULT_DATACRIADO)));
    }

    @Test
    @Transactional
    public void getNonExistingModulos() throws Exception {
        // Get the modulos
        restModulosMockMvc.perform(get("/api/modulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModulos() throws Exception {
        // Initialize the database
        modulosRepository.saveAndFlush(modulos);
        modulosSearchRepository.save(modulos);
        int databaseSizeBeforeUpdate = modulosRepository.findAll().size();

        // Update the modulos
        Modulos updatedModulos = modulosRepository.findOne(modulos.getId());
        updatedModulos
            .nome(UPDATED_NOME)
            .caminho(UPDATED_CAMINHO)
            .status(UPDATED_STATUS)
            .datacriado(UPDATED_DATACRIADO);
        ModulosDTO modulosDTO = modulosMapper.toDto(updatedModulos);

        restModulosMockMvc.perform(put("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulosDTO)))
            .andExpect(status().isOk());

        // Validate the Modulos in the database
        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeUpdate);
        Modulos testModulos = modulosList.get(modulosList.size() - 1);
        assertThat(testModulos.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testModulos.getCaminho()).isEqualTo(UPDATED_CAMINHO);
        assertThat(testModulos.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testModulos.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);

        // Validate the Modulos in Elasticsearch
        Modulos modulosEs = modulosSearchRepository.findOne(testModulos.getId());
        assertThat(modulosEs).isEqualToComparingFieldByField(testModulos);
    }

    @Test
    @Transactional
    public void updateNonExistingModulos() throws Exception {
        int databaseSizeBeforeUpdate = modulosRepository.findAll().size();

        // Create the Modulos
        ModulosDTO modulosDTO = modulosMapper.toDto(modulos);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModulosMockMvc.perform(put("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulosDTO)))
            .andExpect(status().isCreated());

        // Validate the Modulos in the database
        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModulos() throws Exception {
        // Initialize the database
        modulosRepository.saveAndFlush(modulos);
        modulosSearchRepository.save(modulos);
        int databaseSizeBeforeDelete = modulosRepository.findAll().size();

        // Get the modulos
        restModulosMockMvc.perform(delete("/api/modulos/{id}", modulos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean modulosExistsInEs = modulosSearchRepository.exists(modulos.getId());
        assertThat(modulosExistsInEs).isFalse();

        // Validate the database is empty
        List<Modulos> modulosList = modulosRepository.findAll();
        assertThat(modulosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchModulos() throws Exception {
        // Initialize the database
        modulosRepository.saveAndFlush(modulos);
        modulosSearchRepository.save(modulos);

        // Search the modulos
        restModulosMockMvc.perform(get("/api/_search/modulos?query=id:" + modulos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modulos.class);
        Modulos modulos1 = new Modulos();
        modulos1.setId(1L);
        Modulos modulos2 = new Modulos();
        modulos2.setId(modulos1.getId());
        assertThat(modulos1).isEqualTo(modulos2);
        modulos2.setId(2L);
        assertThat(modulos1).isNotEqualTo(modulos2);
        modulos1.setId(null);
        assertThat(modulos1).isNotEqualTo(modulos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModulosDTO.class);
        ModulosDTO modulosDTO1 = new ModulosDTO();
        modulosDTO1.setId(1L);
        ModulosDTO modulosDTO2 = new ModulosDTO();
        assertThat(modulosDTO1).isNotEqualTo(modulosDTO2);
        modulosDTO2.setId(modulosDTO1.getId());
        assertThat(modulosDTO1).isEqualTo(modulosDTO2);
        modulosDTO2.setId(2L);
        assertThat(modulosDTO1).isNotEqualTo(modulosDTO2);
        modulosDTO1.setId(null);
        assertThat(modulosDTO1).isNotEqualTo(modulosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modulosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modulosMapper.fromId(null)).isNull();
    }
}
