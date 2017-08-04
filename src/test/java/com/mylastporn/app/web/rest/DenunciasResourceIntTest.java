package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Denuncias;
import com.mylastporn.app.repository.DenunciasRepository;
import com.mylastporn.app.service.DenunciasService;
import com.mylastporn.app.repository.search.DenunciasSearchRepository;
import com.mylastporn.app.service.dto.DenunciasDTO;
import com.mylastporn.app.service.mapper.DenunciasMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mylastporn.app.domain.enumeration.Status;
/**
 * Test class for the DenunciasResource REST controller.
 *
 * @see DenunciasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class DenunciasResourceIntTest {

    private static final Integer DEFAULT_IDCONTEUDO = 1;
    private static final Integer UPDATED_IDCONTEUDO = 2;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private DenunciasRepository denunciasRepository;

    @Autowired
    private DenunciasMapper denunciasMapper;

    @Autowired
    private DenunciasService denunciasService;

    @Autowired
    private DenunciasSearchRepository denunciasSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDenunciasMockMvc;

    private Denuncias denuncias;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DenunciasResource denunciasResource = new DenunciasResource(denunciasService);
        this.restDenunciasMockMvc = MockMvcBuilders.standaloneSetup(denunciasResource)
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
    public static Denuncias createEntity(EntityManager em) {
        Denuncias denuncias = new Denuncias()
            .idconteudo(DEFAULT_IDCONTEUDO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .status(DEFAULT_STATUS)
            .email(DEFAULT_EMAIL);
        return denuncias;
    }

    @Before
    public void initTest() {
        denunciasSearchRepository.deleteAll();
        denuncias = createEntity(em);
    }

    @Test
    @Transactional
    public void createDenuncias() throws Exception {
        int databaseSizeBeforeCreate = denunciasRepository.findAll().size();

        // Create the Denuncias
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(denuncias);
        restDenunciasMockMvc.perform(post("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isCreated());

        // Validate the Denuncias in the database
        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeCreate + 1);
        Denuncias testDenuncias = denunciasList.get(denunciasList.size() - 1);
        assertThat(testDenuncias.getIdconteudo()).isEqualTo(DEFAULT_IDCONTEUDO);
        assertThat(testDenuncias.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testDenuncias.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDenuncias.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDenuncias.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Denuncias in Elasticsearch
        Denuncias denunciasEs = denunciasSearchRepository.findOne(testDenuncias.getId());
        assertThat(denunciasEs).isEqualToComparingFieldByField(testDenuncias);
    }

    @Test
    @Transactional
    public void createDenunciasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = denunciasRepository.findAll().size();

        // Create the Denuncias with an existing ID
        denuncias.setId(1L);
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(denuncias);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDenunciasMockMvc.perform(post("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdconteudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = denunciasRepository.findAll().size();
        // set the field null
        denuncias.setIdconteudo(null);

        // Create the Denuncias, which fails.
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(denuncias);

        restDenunciasMockMvc.perform(post("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isBadRequest());

        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = denunciasRepository.findAll().size();
        // set the field null
        denuncias.setTitulo(null);

        // Create the Denuncias, which fails.
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(denuncias);

        restDenunciasMockMvc.perform(post("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isBadRequest());

        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = denunciasRepository.findAll().size();
        // set the field null
        denuncias.setDescricao(null);

        // Create the Denuncias, which fails.
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(denuncias);

        restDenunciasMockMvc.perform(post("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isBadRequest());

        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = denunciasRepository.findAll().size();
        // set the field null
        denuncias.setEmail(null);

        // Create the Denuncias, which fails.
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(denuncias);

        restDenunciasMockMvc.perform(post("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isBadRequest());

        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDenuncias() throws Exception {
        // Initialize the database
        denunciasRepository.saveAndFlush(denuncias);

        // Get all the denunciasList
        restDenunciasMockMvc.perform(get("/api/denuncias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(denuncias.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getDenuncias() throws Exception {
        // Initialize the database
        denunciasRepository.saveAndFlush(denuncias);

        // Get the denuncias
        restDenunciasMockMvc.perform(get("/api/denuncias/{id}", denuncias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(denuncias.getId().intValue()))
            .andExpect(jsonPath("$.idconteudo").value(DEFAULT_IDCONTEUDO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDenuncias() throws Exception {
        // Get the denuncias
        restDenunciasMockMvc.perform(get("/api/denuncias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDenuncias() throws Exception {
        // Initialize the database
        denunciasRepository.saveAndFlush(denuncias);
        denunciasSearchRepository.save(denuncias);
        int databaseSizeBeforeUpdate = denunciasRepository.findAll().size();

        // Update the denuncias
        Denuncias updatedDenuncias = denunciasRepository.findOne(denuncias.getId());
        updatedDenuncias
            .idconteudo(UPDATED_IDCONTEUDO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .email(UPDATED_EMAIL);
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(updatedDenuncias);

        restDenunciasMockMvc.perform(put("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isOk());

        // Validate the Denuncias in the database
        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeUpdate);
        Denuncias testDenuncias = denunciasList.get(denunciasList.size() - 1);
        assertThat(testDenuncias.getIdconteudo()).isEqualTo(UPDATED_IDCONTEUDO);
        assertThat(testDenuncias.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testDenuncias.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDenuncias.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDenuncias.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Denuncias in Elasticsearch
        Denuncias denunciasEs = denunciasSearchRepository.findOne(testDenuncias.getId());
        assertThat(denunciasEs).isEqualToComparingFieldByField(testDenuncias);
    }

    @Test
    @Transactional
    public void updateNonExistingDenuncias() throws Exception {
        int databaseSizeBeforeUpdate = denunciasRepository.findAll().size();

        // Create the Denuncias
        DenunciasDTO denunciasDTO = denunciasMapper.toDto(denuncias);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDenunciasMockMvc.perform(put("/api/denuncias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denunciasDTO)))
            .andExpect(status().isCreated());

        // Validate the Denuncias in the database
        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDenuncias() throws Exception {
        // Initialize the database
        denunciasRepository.saveAndFlush(denuncias);
        denunciasSearchRepository.save(denuncias);
        int databaseSizeBeforeDelete = denunciasRepository.findAll().size();

        // Get the denuncias
        restDenunciasMockMvc.perform(delete("/api/denuncias/{id}", denuncias.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean denunciasExistsInEs = denunciasSearchRepository.exists(denuncias.getId());
        assertThat(denunciasExistsInEs).isFalse();

        // Validate the database is empty
        List<Denuncias> denunciasList = denunciasRepository.findAll();
        assertThat(denunciasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDenuncias() throws Exception {
        // Initialize the database
        denunciasRepository.saveAndFlush(denuncias);
        denunciasSearchRepository.save(denuncias);

        // Search the denuncias
        restDenunciasMockMvc.perform(get("/api/_search/denuncias?query=id:" + denuncias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(denuncias.getId().intValue())))
            .andExpect(jsonPath("$.[*].idconteudo").value(hasItem(DEFAULT_IDCONTEUDO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Denuncias.class);
        Denuncias denuncias1 = new Denuncias();
        denuncias1.setId(1L);
        Denuncias denuncias2 = new Denuncias();
        denuncias2.setId(denuncias1.getId());
        assertThat(denuncias1).isEqualTo(denuncias2);
        denuncias2.setId(2L);
        assertThat(denuncias1).isNotEqualTo(denuncias2);
        denuncias1.setId(null);
        assertThat(denuncias1).isNotEqualTo(denuncias2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DenunciasDTO.class);
        DenunciasDTO denunciasDTO1 = new DenunciasDTO();
        denunciasDTO1.setId(1L);
        DenunciasDTO denunciasDTO2 = new DenunciasDTO();
        assertThat(denunciasDTO1).isNotEqualTo(denunciasDTO2);
        denunciasDTO2.setId(denunciasDTO1.getId());
        assertThat(denunciasDTO1).isEqualTo(denunciasDTO2);
        denunciasDTO2.setId(2L);
        assertThat(denunciasDTO1).isNotEqualTo(denunciasDTO2);
        denunciasDTO1.setId(null);
        assertThat(denunciasDTO1).isNotEqualTo(denunciasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(denunciasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(denunciasMapper.fromId(null)).isNull();
    }
}
