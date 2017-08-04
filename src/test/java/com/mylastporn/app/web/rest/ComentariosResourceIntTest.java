package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Comentarios;
import com.mylastporn.app.repository.ComentariosRepository;
import com.mylastporn.app.service.ComentariosService;
import com.mylastporn.app.repository.search.ComentariosSearchRepository;
import com.mylastporn.app.service.dto.ComentariosDTO;
import com.mylastporn.app.service.mapper.ComentariosMapper;
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
 * Test class for the ComentariosResource REST controller.
 *
 * @see ComentariosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class ComentariosResourceIntTest {

    private static final Integer DEFAULT_IDCONTEUDO = 1;
    private static final Integer UPDATED_IDCONTEUDO = 2;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATACRIADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATACRIADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    @Autowired
    private ComentariosRepository comentariosRepository;

    @Autowired
    private ComentariosMapper comentariosMapper;

    @Autowired
    private ComentariosService comentariosService;

    @Autowired
    private ComentariosSearchRepository comentariosSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComentariosMockMvc;

    private Comentarios comentarios;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComentariosResource comentariosResource = new ComentariosResource(comentariosService);
        this.restComentariosMockMvc = MockMvcBuilders.standaloneSetup(comentariosResource)
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
    public static Comentarios createEntity(EntityManager em) {
        Comentarios comentarios = new Comentarios()
            .idconteudo(DEFAULT_IDCONTEUDO)
            .titulo(DEFAULT_TITULO)
            .comentario(DEFAULT_COMENTARIO)
            .datacriado(DEFAULT_DATACRIADO)
            .status(DEFAULT_STATUS);
        return comentarios;
    }

    @Before
    public void initTest() {
        comentariosSearchRepository.deleteAll();
        comentarios = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarios() throws Exception {
        int databaseSizeBeforeCreate = comentariosRepository.findAll().size();

        // Create the Comentarios
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(comentarios);
        restComentariosMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isCreated());

        // Validate the Comentarios in the database
        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeCreate + 1);
        Comentarios testComentarios = comentariosList.get(comentariosList.size() - 1);
        assertThat(testComentarios.getIdconteudo()).isEqualTo(DEFAULT_IDCONTEUDO);
        assertThat(testComentarios.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testComentarios.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentarios.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);
        assertThat(testComentarios.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Comentarios in Elasticsearch
        Comentarios comentariosEs = comentariosSearchRepository.findOne(testComentarios.getId());
        assertThat(comentariosEs).isEqualToComparingFieldByField(testComentarios);
    }

    @Test
    @Transactional
    public void createComentariosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentariosRepository.findAll().size();

        // Create the Comentarios with an existing ID
        comentarios.setId(1L);
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(comentarios);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentariosMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdconteudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = comentariosRepository.findAll().size();
        // set the field null
        comentarios.setIdconteudo(null);

        // Create the Comentarios, which fails.
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(comentarios);

        restComentariosMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isBadRequest());

        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = comentariosRepository.findAll().size();
        // set the field null
        comentarios.setTitulo(null);

        // Create the Comentarios, which fails.
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(comentarios);

        restComentariosMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isBadRequest());

        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComentarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = comentariosRepository.findAll().size();
        // set the field null
        comentarios.setComentario(null);

        // Create the Comentarios, which fails.
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(comentarios);

        restComentariosMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isBadRequest());

        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = comentariosRepository.findAll().size();
        // set the field null
        comentarios.setDatacriado(null);

        // Create the Comentarios, which fails.
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(comentarios);

        restComentariosMockMvc.perform(post("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isBadRequest());

        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);

        // Get all the comentariosList
        restComentariosMockMvc.perform(get("/api/comentarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);

        // Get the comentarios
        restComentariosMockMvc.perform(get("/api/comentarios/{id}", comentarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentarios.getId().intValue()))
            .andExpect(jsonPath("$.idconteudo").value(DEFAULT_IDCONTEUDO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.datacriado").value(sameInstant(DEFAULT_DATACRIADO)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComentarios() throws Exception {
        // Get the comentarios
        restComentariosMockMvc.perform(get("/api/comentarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);
        comentariosSearchRepository.save(comentarios);
        int databaseSizeBeforeUpdate = comentariosRepository.findAll().size();

        // Update the comentarios
        Comentarios updatedComentarios = comentariosRepository.findOne(comentarios.getId());
        updatedComentarios
            .idconteudo(UPDATED_IDCONTEUDO)
            .titulo(UPDATED_TITULO)
            .comentario(UPDATED_COMENTARIO)
            .datacriado(UPDATED_DATACRIADO)
            .status(UPDATED_STATUS);
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(updatedComentarios);

        restComentariosMockMvc.perform(put("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isOk());

        // Validate the Comentarios in the database
        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeUpdate);
        Comentarios testComentarios = comentariosList.get(comentariosList.size() - 1);
        assertThat(testComentarios.getIdconteudo()).isEqualTo(UPDATED_IDCONTEUDO);
        assertThat(testComentarios.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testComentarios.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentarios.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);
        assertThat(testComentarios.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Comentarios in Elasticsearch
        Comentarios comentariosEs = comentariosSearchRepository.findOne(testComentarios.getId());
        assertThat(comentariosEs).isEqualToComparingFieldByField(testComentarios);
    }

    @Test
    @Transactional
    public void updateNonExistingComentarios() throws Exception {
        int databaseSizeBeforeUpdate = comentariosRepository.findAll().size();

        // Create the Comentarios
        ComentariosDTO comentariosDTO = comentariosMapper.toDto(comentarios);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComentariosMockMvc.perform(put("/api/comentarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentariosDTO)))
            .andExpect(status().isCreated());

        // Validate the Comentarios in the database
        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);
        comentariosSearchRepository.save(comentarios);
        int databaseSizeBeforeDelete = comentariosRepository.findAll().size();

        // Get the comentarios
        restComentariosMockMvc.perform(delete("/api/comentarios/{id}", comentarios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean comentariosExistsInEs = comentariosSearchRepository.exists(comentarios.getId());
        assertThat(comentariosExistsInEs).isFalse();

        // Validate the database is empty
        List<Comentarios> comentariosList = comentariosRepository.findAll();
        assertThat(comentariosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);
        comentariosSearchRepository.save(comentarios);

        // Search the comentarios
        restComentariosMockMvc.perform(get("/api/_search/comentarios?query=id:" + comentarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].datacriado").value(hasItem(sameInstant(DEFAULT_DATACRIADO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comentarios.class);
        Comentarios comentarios1 = new Comentarios();
        comentarios1.setId(1L);
        Comentarios comentarios2 = new Comentarios();
        comentarios2.setId(comentarios1.getId());
        assertThat(comentarios1).isEqualTo(comentarios2);
        comentarios2.setId(2L);
        assertThat(comentarios1).isNotEqualTo(comentarios2);
        comentarios1.setId(null);
        assertThat(comentarios1).isNotEqualTo(comentarios2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentariosDTO.class);
        ComentariosDTO comentariosDTO1 = new ComentariosDTO();
        comentariosDTO1.setId(1L);
        ComentariosDTO comentariosDTO2 = new ComentariosDTO();
        assertThat(comentariosDTO1).isNotEqualTo(comentariosDTO2);
        comentariosDTO2.setId(comentariosDTO1.getId());
        assertThat(comentariosDTO1).isEqualTo(comentariosDTO2);
        comentariosDTO2.setId(2L);
        assertThat(comentariosDTO1).isNotEqualTo(comentariosDTO2);
        comentariosDTO1.setId(null);
        assertThat(comentariosDTO1).isNotEqualTo(comentariosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(comentariosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(comentariosMapper.fromId(null)).isNull();
    }
}
