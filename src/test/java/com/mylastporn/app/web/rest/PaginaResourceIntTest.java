package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Pagina;
import com.mylastporn.app.repository.PaginaRepository;
import com.mylastporn.app.service.PaginaService;
import com.mylastporn.app.repository.search.PaginaSearchRepository;
import com.mylastporn.app.service.dto.PaginaDTO;
import com.mylastporn.app.service.mapper.PaginaMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the PaginaResource REST controller.
 *
 * @see PaginaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class PaginaResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GAY = false;
    private static final Boolean UPDATED_GAY = true;

    private static final String DEFAULT_CAPA = "AAAAAAAAAA";
    private static final String UPDATED_CAPA = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    private static final Boolean DEFAULT_OFFICIAL = false;
    private static final Boolean UPDATED_OFFICIAL = true;

    private static final Integer DEFAULT_VISITADO = 1;
    private static final Integer UPDATED_VISITADO = 2;

    private static final ZonedDateTime DEFAULT_DATACRIADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATACRIADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PaginaRepository paginaRepository;

    @Autowired
    private PaginaMapper paginaMapper;

    @Autowired
    private PaginaService paginaService;

    @Autowired
    private PaginaSearchRepository paginaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaginaMockMvc;

    private Pagina pagina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaginaResource paginaResource = new PaginaResource(paginaService);
        this.restPaginaMockMvc = MockMvcBuilders.standaloneSetup(paginaResource)
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
    public static Pagina createEntity(EntityManager em) {
        Pagina pagina = new Pagina()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .gay(DEFAULT_GAY)
            .capa(DEFAULT_CAPA)
            .status(DEFAULT_STATUS)
            .official(DEFAULT_OFFICIAL)
            .visitado(DEFAULT_VISITADO)
            .datacriado(DEFAULT_DATACRIADO);
        return pagina;
    }

    @Before
    public void initTest() {
        paginaSearchRepository.deleteAll();
        pagina = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagina() throws Exception {
        int databaseSizeBeforeCreate = paginaRepository.findAll().size();

        // Create the Pagina
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);
        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeCreate + 1);
        Pagina testPagina = paginaList.get(paginaList.size() - 1);
        assertThat(testPagina.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPagina.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPagina.isGay()).isEqualTo(DEFAULT_GAY);
        assertThat(testPagina.getCapa()).isEqualTo(DEFAULT_CAPA);
        assertThat(testPagina.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPagina.isOfficial()).isEqualTo(DEFAULT_OFFICIAL);
        assertThat(testPagina.getVisitado()).isEqualTo(DEFAULT_VISITADO);
        assertThat(testPagina.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);

        // Validate the Pagina in Elasticsearch
        Pagina paginaEs = paginaSearchRepository.findOne(testPagina.getId());
        assertThat(paginaEs).isEqualToComparingFieldByField(testPagina);
    }

    @Test
    @Transactional
    public void createPaginaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paginaRepository.findAll().size();

        // Create the Pagina with an existing ID
        pagina.setId(1L);
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = paginaRepository.findAll().size();
        // set the field null
        pagina.setTitulo(null);

        // Create the Pagina, which fails.
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = paginaRepository.findAll().size();
        // set the field null
        pagina.setDescricao(null);

        // Create the Pagina, which fails.
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = paginaRepository.findAll().size();
        // set the field null
        pagina.setDatacriado(null);

        // Create the Pagina, which fails.
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaginas() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        // Get all the paginaList
        restPaginaMockMvc.perform(get("/api/paginas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagina.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].gay").value(hasItem(DEFAULT_GAY.booleanValue())))
            .andExpect(jsonPath("$.[*].capa").value(hasItem(DEFAULT_CAPA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].official").value(hasItem(DEFAULT_OFFICIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].visitado").value(hasItem(DEFAULT_VISITADO)))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void getPagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        // Get the pagina
        restPaginaMockMvc.perform(get("/api/paginas/{id}", pagina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagina.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.gay").value(DEFAULT_GAY.booleanValue()))
            .andExpect(jsonPath("$.capa").value(DEFAULT_CAPA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.official").value(DEFAULT_OFFICIAL.booleanValue()))
            .andExpect(jsonPath("$.visitado").value(DEFAULT_VISITADO))
            .andExpect(jsonPath("$.datacriado").value(sameInstant(DEFAULT_DATACRIADO)));
    }

    @Test
    @Transactional
    public void getNonExistingPagina() throws Exception {
        // Get the pagina
        restPaginaMockMvc.perform(get("/api/paginas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);
        paginaSearchRepository.save(pagina);
        int databaseSizeBeforeUpdate = paginaRepository.findAll().size();

        // Update the pagina
        Pagina updatedPagina = paginaRepository.findOne(pagina.getId());
        updatedPagina
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .gay(UPDATED_GAY)
            .capa(UPDATED_CAPA)
            .status(UPDATED_STATUS)
            .official(UPDATED_OFFICIAL)
            .visitado(UPDATED_VISITADO)
            .datacriado(UPDATED_DATACRIADO);
        PaginaDTO paginaDTO = paginaMapper.toDto(updatedPagina);

        restPaginaMockMvc.perform(put("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isOk());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeUpdate);
        Pagina testPagina = paginaList.get(paginaList.size() - 1);
        assertThat(testPagina.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPagina.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPagina.isGay()).isEqualTo(UPDATED_GAY);
        assertThat(testPagina.getCapa()).isEqualTo(UPDATED_CAPA);
        assertThat(testPagina.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPagina.isOfficial()).isEqualTo(UPDATED_OFFICIAL);
        assertThat(testPagina.getVisitado()).isEqualTo(UPDATED_VISITADO);
        assertThat(testPagina.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);

        // Validate the Pagina in Elasticsearch
        Pagina paginaEs = paginaSearchRepository.findOne(testPagina.getId());
        assertThat(paginaEs).isEqualToComparingFieldByField(testPagina);
    }

    @Test
    @Transactional
    public void updateNonExistingPagina() throws Exception {
        int databaseSizeBeforeUpdate = paginaRepository.findAll().size();

        // Create the Pagina
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaginaMockMvc.perform(put("/api/paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);
        paginaSearchRepository.save(pagina);
        int databaseSizeBeforeDelete = paginaRepository.findAll().size();

        // Get the pagina
        restPaginaMockMvc.perform(delete("/api/paginas/{id}", pagina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean paginaExistsInEs = paginaSearchRepository.exists(pagina.getId());
        assertThat(paginaExistsInEs).isFalse();

        // Validate the database is empty
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);
        paginaSearchRepository.save(pagina);

        // Search the pagina
        restPaginaMockMvc.perform(get("/api/_search/paginas?query=id:" + pagina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagina.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].gay").value(hasItem(DEFAULT_GAY.booleanValue())))
            .andExpect(jsonPath("$.[*].capa").value(hasItem(DEFAULT_CAPA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].official").value(hasItem(DEFAULT_OFFICIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].visitado").value(hasItem(DEFAULT_VISITADO)))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagina.class);
        Pagina pagina1 = new Pagina();
        pagina1.setId(1L);
        Pagina pagina2 = new Pagina();
        pagina2.setId(pagina1.getId());
        assertThat(pagina1).isEqualTo(pagina2);
        pagina2.setId(2L);
        assertThat(pagina1).isNotEqualTo(pagina2);
        pagina1.setId(null);
        assertThat(pagina1).isNotEqualTo(pagina2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaginaDTO.class);
        PaginaDTO paginaDTO1 = new PaginaDTO();
        paginaDTO1.setId(1L);
        PaginaDTO paginaDTO2 = new PaginaDTO();
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
        paginaDTO2.setId(paginaDTO1.getId());
        assertThat(paginaDTO1).isEqualTo(paginaDTO2);
        paginaDTO2.setId(2L);
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
        paginaDTO1.setId(null);
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paginaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paginaMapper.fromId(null)).isNull();
    }
}
