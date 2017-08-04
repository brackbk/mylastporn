package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Foto;
import com.mylastporn.app.repository.FotoRepository;
import com.mylastporn.app.service.FotoService;
import com.mylastporn.app.repository.search.FotoSearchRepository;
import com.mylastporn.app.service.dto.FotoDTO;
import com.mylastporn.app.service.mapper.FotoMapper;
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
 * Test class for the FotoResource REST controller.
 *
 * @see FotoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class FotoResourceIntTest {

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
    private FotoRepository fotoRepository;

    @Autowired
    private FotoMapper fotoMapper;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private FotoSearchRepository fotoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFotoMockMvc;

    private Foto foto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FotoResource fotoResource = new FotoResource(fotoService);
        this.restFotoMockMvc = MockMvcBuilders.standaloneSetup(fotoResource)
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
    public static Foto createEntity(EntityManager em) {
        Foto foto = new Foto()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .gay(DEFAULT_GAY)
            .imagem(DEFAULT_IMAGEM)
            .status(DEFAULT_STATUS)
            .visitado(DEFAULT_VISITADO)
            .datacriado(DEFAULT_DATACRIADO);
        return foto;
    }

    @Before
    public void initTest() {
        fotoSearchRepository.deleteAll();
        foto = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoto() throws Exception {
        int databaseSizeBeforeCreate = fotoRepository.findAll().size();

        // Create the Foto
        FotoDTO fotoDTO = fotoMapper.toDto(foto);
        restFotoMockMvc.perform(post("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isCreated());

        // Validate the Foto in the database
        List<Foto> fotoList = fotoRepository.findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeCreate + 1);
        Foto testFoto = fotoList.get(fotoList.size() - 1);
        assertThat(testFoto.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testFoto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFoto.isGay()).isEqualTo(DEFAULT_GAY);
        assertThat(testFoto.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testFoto.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFoto.getVisitado()).isEqualTo(DEFAULT_VISITADO);
        assertThat(testFoto.getDatacriado()).isEqualTo(DEFAULT_DATACRIADO);

        // Validate the Foto in Elasticsearch
        Foto fotoEs = fotoSearchRepository.findOne(testFoto.getId());
        assertThat(fotoEs).isEqualToComparingFieldByField(testFoto);
    }

    @Test
    @Transactional
    public void createFotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fotoRepository.findAll().size();

        // Create the Foto with an existing ID
        foto.setId(1L);
        FotoDTO fotoDTO = fotoMapper.toDto(foto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoMockMvc.perform(post("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Foto> fotoList = fotoRepository.findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = fotoRepository.findAll().size();
        // set the field null
        foto.setTitulo(null);

        // Create the Foto, which fails.
        FotoDTO fotoDTO = fotoMapper.toDto(foto);

        restFotoMockMvc.perform(post("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isBadRequest());

        List<Foto> fotoList = fotoRepository.findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatacriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fotoRepository.findAll().size();
        // set the field null
        foto.setDatacriado(null);

        // Create the Foto, which fails.
        FotoDTO fotoDTO = fotoMapper.toDto(foto);

        restFotoMockMvc.perform(post("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isBadRequest());

        List<Foto> fotoList = fotoRepository.findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFotos() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        // Get all the fotoList
        restFotoMockMvc.perform(get("/api/fotos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foto.getId().intValue())))
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
    public void getFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);

        // Get the foto
        restFotoMockMvc.perform(get("/api/fotos/{id}", foto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foto.getId().intValue()))
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
    public void getNonExistingFoto() throws Exception {
        // Get the foto
        restFotoMockMvc.perform(get("/api/fotos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);
        fotoSearchRepository.save(foto);
        int databaseSizeBeforeUpdate = fotoRepository.findAll().size();

        // Update the foto
        Foto updatedFoto = fotoRepository.findOne(foto.getId());
        updatedFoto
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .gay(UPDATED_GAY)
            .imagem(UPDATED_IMAGEM)
            .status(UPDATED_STATUS)
            .visitado(UPDATED_VISITADO)
            .datacriado(UPDATED_DATACRIADO);
        FotoDTO fotoDTO = fotoMapper.toDto(updatedFoto);

        restFotoMockMvc.perform(put("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isOk());

        // Validate the Foto in the database
        List<Foto> fotoList = fotoRepository.findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeUpdate);
        Foto testFoto = fotoList.get(fotoList.size() - 1);
        assertThat(testFoto.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testFoto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFoto.isGay()).isEqualTo(UPDATED_GAY);
        assertThat(testFoto.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testFoto.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFoto.getVisitado()).isEqualTo(UPDATED_VISITADO);
        assertThat(testFoto.getDatacriado()).isEqualTo(UPDATED_DATACRIADO);

        // Validate the Foto in Elasticsearch
        Foto fotoEs = fotoSearchRepository.findOne(testFoto.getId());
        assertThat(fotoEs).isEqualToComparingFieldByField(testFoto);
    }

    @Test
    @Transactional
    public void updateNonExistingFoto() throws Exception {
        int databaseSizeBeforeUpdate = fotoRepository.findAll().size();

        // Create the Foto
        FotoDTO fotoDTO = fotoMapper.toDto(foto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFotoMockMvc.perform(put("/api/fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fotoDTO)))
            .andExpect(status().isCreated());

        // Validate the Foto in the database
        List<Foto> fotoList = fotoRepository.findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);
        fotoSearchRepository.save(foto);
        int databaseSizeBeforeDelete = fotoRepository.findAll().size();

        // Get the foto
        restFotoMockMvc.perform(delete("/api/fotos/{id}", foto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean fotoExistsInEs = fotoSearchRepository.exists(foto.getId());
        assertThat(fotoExistsInEs).isFalse();

        // Validate the database is empty
        List<Foto> fotoList = fotoRepository.findAll();
        assertThat(fotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFoto() throws Exception {
        // Initialize the database
        fotoRepository.saveAndFlush(foto);
        fotoSearchRepository.save(foto);

        // Search the foto
        restFotoMockMvc.perform(get("/api/_search/fotos?query=id:" + foto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foto.getId().intValue())))
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
        TestUtil.equalsVerifier(Foto.class);
        Foto foto1 = new Foto();
        foto1.setId(1L);
        Foto foto2 = new Foto();
        foto2.setId(foto1.getId());
        assertThat(foto1).isEqualTo(foto2);
        foto2.setId(2L);
        assertThat(foto1).isNotEqualTo(foto2);
        foto1.setId(null);
        assertThat(foto1).isNotEqualTo(foto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoDTO.class);
        FotoDTO fotoDTO1 = new FotoDTO();
        fotoDTO1.setId(1L);
        FotoDTO fotoDTO2 = new FotoDTO();
        assertThat(fotoDTO1).isNotEqualTo(fotoDTO2);
        fotoDTO2.setId(fotoDTO1.getId());
        assertThat(fotoDTO1).isEqualTo(fotoDTO2);
        fotoDTO2.setId(2L);
        assertThat(fotoDTO1).isNotEqualTo(fotoDTO2);
        fotoDTO1.setId(null);
        assertThat(fotoDTO1).isNotEqualTo(fotoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fotoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fotoMapper.fromId(null)).isNull();
    }
}
