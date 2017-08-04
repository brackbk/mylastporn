package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Historia;
import com.mylastporn.app.repository.HistoriaRepository;
import com.mylastporn.app.service.HistoriaService;
import com.mylastporn.app.repository.search.HistoriaSearchRepository;
import com.mylastporn.app.service.dto.HistoriaDTO;
import com.mylastporn.app.service.mapper.HistoriaMapper;
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
 * Test class for the HistoriaResource REST controller.
 *
 * @see HistoriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class HistoriaResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GAY = false;
    private static final Boolean UPDATED_GAY = true;

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    private static final Integer DEFAULT_VISITADO = 1;
    private static final Integer UPDATED_VISITADO = 2;

    private static final ZonedDateTime DEFAULT_DATACRIADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATACRIADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HistoriaRepository historiaRepository;

    @Autowired
    private HistoriaMapper historiaMapper;

    @Autowired
    private HistoriaService historiaService;

    @Autowired
    private HistoriaSearchRepository historiaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHistoriaMockMvc;

    private Historia historia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HistoriaResource historiaResource = new HistoriaResource(historiaService);
        this.restHistoriaMockMvc = MockMvcBuilders.standaloneSetup(historiaResource)
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
    public static Historia createEntity(EntityManager em) {
        Historia historia = new Historia()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .gay(DEFAULT_GAY)
            .imagem(DEFAULT_IMAGEM)
            .status(DEFAULT_STATUS)
            .visitado(DEFAULT_VISITADO)
            .datacriado(DEFAULT_DATACRIADO);
        return historia;
    }

    @Before
    public void initTest() {
        historiaSearchRepository.deleteAll();
        historia = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoria() throws Exception {
        int databaseSizeBeforeCreate = historiaRepository.findAll().size();

        // Create the Historia
        HistoriaDTO historiaDTO = historiaMapper.toDto(historia);
        restHistoriaMockMvc.perform(post("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Historia in the database
        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeCreate + 1);
        Historia testHistoria = historiaList.get(historiaList.size() - 1);
        assertThat(testHistoria.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testHistoria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testHistoria.isGay()).isEqualTo(DEFAULT_GAY);
        assertThat(testHistoria.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testHistoria.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testHistoria.getVisitado()).isEqualTo(DEFAULT_VISITADO);
        assertThat(testHistoria.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);

        // Validate the Historia in Elasticsearch
        Historia historiaEs = historiaSearchRepository.findOne(testHistoria.getId());
        assertThat(historiaEs).isEqualToComparingFieldByField(testHistoria);
    }

    @Test
    @Transactional
    public void createHistoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiaRepository.findAll().size();

        // Create the Historia with an existing ID
        historia.setId(1L);
        HistoriaDTO historiaDTO = historiaMapper.toDto(historia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriaMockMvc.perform(post("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiaRepository.findAll().size();
        // set the field null
        historia.setTitulo(null);

        // Create the Historia, which fails.
        HistoriaDTO historiaDTO = historiaMapper.toDto(historia);

        restHistoriaMockMvc.perform(post("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isBadRequest());

        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiaRepository.findAll().size();
        // set the field null
        historia.setDescricao(null);

        // Create the Historia, which fails.
        HistoriaDTO historiaDTO = historiaMapper.toDto(historia);

        restHistoriaMockMvc.perform(post("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isBadRequest());

        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImagemIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiaRepository.findAll().size();
        // set the field null
        historia.setImagem(null);

        // Create the Historia, which fails.
        HistoriaDTO historiaDTO = historiaMapper.toDto(historia);

        restHistoriaMockMvc.perform(post("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isBadRequest());

        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiaRepository.findAll().size();
        // set the field null
        historia.setDatacriado(null);

        // Create the Historia, which fails.
        HistoriaDTO historiaDTO = historiaMapper.toDto(historia);

        restHistoriaMockMvc.perform(post("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isBadRequest());

        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistorias() throws Exception {
        // Initialize the database
        historiaRepository.saveAndFlush(historia);

        // Get all the historiaList
        restHistoriaMockMvc.perform(get("/api/historias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].gay").value(hasItem(DEFAULT_GAY.booleanValue())))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].visitado").value(hasItem(DEFAULT_VISITADO)))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void getHistoria() throws Exception {
        // Initialize the database
        historiaRepository.saveAndFlush(historia);

        // Get the historia
        restHistoriaMockMvc.perform(get("/api/historias/{id}", historia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historia.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.gay").value(DEFAULT_GAY.booleanValue()))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.visitado").value(DEFAULT_VISITADO))
            .andExpect(jsonPath("$.datacriado").value(sameInstant(DEFAULT_DATACRIADO)));
    }

    @Test
    @Transactional
    public void getNonExistingHistoria() throws Exception {
        // Get the historia
        restHistoriaMockMvc.perform(get("/api/historias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoria() throws Exception {
        // Initialize the database
        historiaRepository.saveAndFlush(historia);
        historiaSearchRepository.save(historia);
        int databaseSizeBeforeUpdate = historiaRepository.findAll().size();

        // Update the historia
        Historia updatedHistoria = historiaRepository.findOne(historia.getId());
        updatedHistoria
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .gay(UPDATED_GAY)
            .imagem(UPDATED_IMAGEM)
            .status(UPDATED_STATUS)
            .visitado(UPDATED_VISITADO)
            .datacriado(UPDATED_DATACRIADO);
        HistoriaDTO historiaDTO = historiaMapper.toDto(updatedHistoria);

        restHistoriaMockMvc.perform(put("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isOk());

        // Validate the Historia in the database
        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeUpdate);
        Historia testHistoria = historiaList.get(historiaList.size() - 1);
        assertThat(testHistoria.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testHistoria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testHistoria.isGay()).isEqualTo(UPDATED_GAY);
        assertThat(testHistoria.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testHistoria.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHistoria.getVisitado()).isEqualTo(UPDATED_VISITADO);
        assertThat(testHistoria.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);

        // Validate the Historia in Elasticsearch
        Historia historiaEs = historiaSearchRepository.findOne(testHistoria.getId());
        assertThat(historiaEs).isEqualToComparingFieldByField(testHistoria);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoria() throws Exception {
        int databaseSizeBeforeUpdate = historiaRepository.findAll().size();

        // Create the Historia
        HistoriaDTO historiaDTO = historiaMapper.toDto(historia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHistoriaMockMvc.perform(put("/api/historias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Historia in the database
        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHistoria() throws Exception {
        // Initialize the database
        historiaRepository.saveAndFlush(historia);
        historiaSearchRepository.save(historia);
        int databaseSizeBeforeDelete = historiaRepository.findAll().size();

        // Get the historia
        restHistoriaMockMvc.perform(delete("/api/historias/{id}", historia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean historiaExistsInEs = historiaSearchRepository.exists(historia.getId());
        assertThat(historiaExistsInEs).isFalse();

        // Validate the database is empty
        List<Historia> historiaList = historiaRepository.findAll();
        assertThat(historiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHistoria() throws Exception {
        // Initialize the database
        historiaRepository.saveAndFlush(historia);
        historiaSearchRepository.save(historia);

        // Search the historia
        restHistoriaMockMvc.perform(get("/api/_search/historias?query=id:" + historia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].gay").value(hasItem(DEFAULT_GAY.booleanValue())))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].visitado").value(hasItem(DEFAULT_VISITADO)))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historia.class);
        Historia historia1 = new Historia();
        historia1.setId(1L);
        Historia historia2 = new Historia();
        historia2.setId(historia1.getId());
        assertThat(historia1).isEqualTo(historia2);
        historia2.setId(2L);
        assertThat(historia1).isNotEqualTo(historia2);
        historia1.setId(null);
        assertThat(historia1).isNotEqualTo(historia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriaDTO.class);
        HistoriaDTO historiaDTO1 = new HistoriaDTO();
        historiaDTO1.setId(1L);
        HistoriaDTO historiaDTO2 = new HistoriaDTO();
        assertThat(historiaDTO1).isNotEqualTo(historiaDTO2);
        historiaDTO2.setId(historiaDTO1.getId());
        assertThat(historiaDTO1).isEqualTo(historiaDTO2);
        historiaDTO2.setId(2L);
        assertThat(historiaDTO1).isNotEqualTo(historiaDTO2);
        historiaDTO1.setId(null);
        assertThat(historiaDTO1).isNotEqualTo(historiaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(historiaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(historiaMapper.fromId(null)).isNull();
    }
}
