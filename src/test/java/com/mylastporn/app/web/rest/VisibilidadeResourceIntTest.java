package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.Visibilidade;
import com.mylastporn.app.repository.VisibilidadeRepository;
import com.mylastporn.app.service.VisibilidadeService;
import com.mylastporn.app.repository.search.VisibilidadeSearchRepository;
import com.mylastporn.app.service.dto.VisibilidadeDTO;
import com.mylastporn.app.service.mapper.VisibilidadeMapper;
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

/**
 * Test class for the VisibilidadeResource REST controller.
 *
 * @see VisibilidadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class VisibilidadeResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private VisibilidadeRepository visibilidadeRepository;

    @Autowired
    private VisibilidadeMapper visibilidadeMapper;

    @Autowired
    private VisibilidadeService visibilidadeService;

    @Autowired
    private VisibilidadeSearchRepository visibilidadeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisibilidadeMockMvc;

    private Visibilidade visibilidade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VisibilidadeResource visibilidadeResource = new VisibilidadeResource(visibilidadeService);
        this.restVisibilidadeMockMvc = MockMvcBuilders.standaloneSetup(visibilidadeResource)
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
    public static Visibilidade createEntity(EntityManager em) {
        Visibilidade visibilidade = new Visibilidade()
            .nome(DEFAULT_NOME);
        return visibilidade;
    }

    @Before
    public void initTest() {
        visibilidadeSearchRepository.deleteAll();
        visibilidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisibilidade() throws Exception {
        int databaseSizeBeforeCreate = visibilidadeRepository.findAll().size();

        // Create the Visibilidade
        VisibilidadeDTO visibilidadeDTO = visibilidadeMapper.toDto(visibilidade);
        restVisibilidadeMockMvc.perform(post("/api/visibilidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visibilidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Visibilidade in the database
        List<Visibilidade> visibilidadeList = visibilidadeRepository.findAll();
        assertThat(visibilidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Visibilidade testVisibilidade = visibilidadeList.get(visibilidadeList.size() - 1);
        assertThat(testVisibilidade.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Visibilidade in Elasticsearch
        Visibilidade visibilidadeEs = visibilidadeSearchRepository.findOne(testVisibilidade.getId());
        assertThat(visibilidadeEs).isEqualToComparingFieldByField(testVisibilidade);
    }

    @Test
    @Transactional
    public void createVisibilidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visibilidadeRepository.findAll().size();

        // Create the Visibilidade with an existing ID
        visibilidade.setId(1L);
        VisibilidadeDTO visibilidadeDTO = visibilidadeMapper.toDto(visibilidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisibilidadeMockMvc.perform(post("/api/visibilidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visibilidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Visibilidade> visibilidadeList = visibilidadeRepository.findAll();
        assertThat(visibilidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = visibilidadeRepository.findAll().size();
        // set the field null
        visibilidade.setNome(null);

        // Create the Visibilidade, which fails.
        VisibilidadeDTO visibilidadeDTO = visibilidadeMapper.toDto(visibilidade);

        restVisibilidadeMockMvc.perform(post("/api/visibilidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visibilidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Visibilidade> visibilidadeList = visibilidadeRepository.findAll();
        assertThat(visibilidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisibilidades() throws Exception {
        // Initialize the database
        visibilidadeRepository.saveAndFlush(visibilidade);

        // Get all the visibilidadeList
        restVisibilidadeMockMvc.perform(get("/api/visibilidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visibilidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getVisibilidade() throws Exception {
        // Initialize the database
        visibilidadeRepository.saveAndFlush(visibilidade);

        // Get the visibilidade
        restVisibilidadeMockMvc.perform(get("/api/visibilidades/{id}", visibilidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visibilidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVisibilidade() throws Exception {
        // Get the visibilidade
        restVisibilidadeMockMvc.perform(get("/api/visibilidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisibilidade() throws Exception {
        // Initialize the database
        visibilidadeRepository.saveAndFlush(visibilidade);
        visibilidadeSearchRepository.save(visibilidade);
        int databaseSizeBeforeUpdate = visibilidadeRepository.findAll().size();

        // Update the visibilidade
        Visibilidade updatedVisibilidade = visibilidadeRepository.findOne(visibilidade.getId());
        updatedVisibilidade
            .nome(UPDATED_NOME);
        VisibilidadeDTO visibilidadeDTO = visibilidadeMapper.toDto(updatedVisibilidade);

        restVisibilidadeMockMvc.perform(put("/api/visibilidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visibilidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Visibilidade in the database
        List<Visibilidade> visibilidadeList = visibilidadeRepository.findAll();
        assertThat(visibilidadeList).hasSize(databaseSizeBeforeUpdate);
        Visibilidade testVisibilidade = visibilidadeList.get(visibilidadeList.size() - 1);
        assertThat(testVisibilidade.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Visibilidade in Elasticsearch
        Visibilidade visibilidadeEs = visibilidadeSearchRepository.findOne(testVisibilidade.getId());
        assertThat(visibilidadeEs).isEqualToComparingFieldByField(testVisibilidade);
    }

    @Test
    @Transactional
    public void updateNonExistingVisibilidade() throws Exception {
        int databaseSizeBeforeUpdate = visibilidadeRepository.findAll().size();

        // Create the Visibilidade
        VisibilidadeDTO visibilidadeDTO = visibilidadeMapper.toDto(visibilidade);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisibilidadeMockMvc.perform(put("/api/visibilidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visibilidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Visibilidade in the database
        List<Visibilidade> visibilidadeList = visibilidadeRepository.findAll();
        assertThat(visibilidadeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisibilidade() throws Exception {
        // Initialize the database
        visibilidadeRepository.saveAndFlush(visibilidade);
        visibilidadeSearchRepository.save(visibilidade);
        int databaseSizeBeforeDelete = visibilidadeRepository.findAll().size();

        // Get the visibilidade
        restVisibilidadeMockMvc.perform(delete("/api/visibilidades/{id}", visibilidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean visibilidadeExistsInEs = visibilidadeSearchRepository.exists(visibilidade.getId());
        assertThat(visibilidadeExistsInEs).isFalse();

        // Validate the database is empty
        List<Visibilidade> visibilidadeList = visibilidadeRepository.findAll();
        assertThat(visibilidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVisibilidade() throws Exception {
        // Initialize the database
        visibilidadeRepository.saveAndFlush(visibilidade);
        visibilidadeSearchRepository.save(visibilidade);

        // Search the visibilidade
        restVisibilidadeMockMvc.perform(get("/api/_search/visibilidades?query=id:" + visibilidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visibilidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visibilidade.class);
        Visibilidade visibilidade1 = new Visibilidade();
        visibilidade1.setId(1L);
        Visibilidade visibilidade2 = new Visibilidade();
        visibilidade2.setId(visibilidade1.getId());
        assertThat(visibilidade1).isEqualTo(visibilidade2);
        visibilidade2.setId(2L);
        assertThat(visibilidade1).isNotEqualTo(visibilidade2);
        visibilidade1.setId(null);
        assertThat(visibilidade1).isNotEqualTo(visibilidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisibilidadeDTO.class);
        VisibilidadeDTO visibilidadeDTO1 = new VisibilidadeDTO();
        visibilidadeDTO1.setId(1L);
        VisibilidadeDTO visibilidadeDTO2 = new VisibilidadeDTO();
        assertThat(visibilidadeDTO1).isNotEqualTo(visibilidadeDTO2);
        visibilidadeDTO2.setId(visibilidadeDTO1.getId());
        assertThat(visibilidadeDTO1).isEqualTo(visibilidadeDTO2);
        visibilidadeDTO2.setId(2L);
        assertThat(visibilidadeDTO1).isNotEqualTo(visibilidadeDTO2);
        visibilidadeDTO1.setId(null);
        assertThat(visibilidadeDTO1).isNotEqualTo(visibilidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(visibilidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(visibilidadeMapper.fromId(null)).isNull();
    }
}
