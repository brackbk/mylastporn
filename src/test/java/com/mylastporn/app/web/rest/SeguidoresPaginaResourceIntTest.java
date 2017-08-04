package com.mylastporn.app.web.rest;

import com.mylastporn.app.MylastpornApp;

import com.mylastporn.app.domain.SeguidoresPagina;
import com.mylastporn.app.repository.SeguidoresPaginaRepository;
import com.mylastporn.app.service.SeguidoresPaginaService;
import com.mylastporn.app.repository.search.SeguidoresPaginaSearchRepository;
import com.mylastporn.app.service.dto.SeguidoresPaginaDTO;
import com.mylastporn.app.service.mapper.SeguidoresPaginaMapper;
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
 * Test class for the SeguidoresPaginaResource REST controller.
 *
 * @see SeguidoresPaginaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MylastpornApp.class)
public class SeguidoresPaginaResourceIntTest {

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INATIVE;

    @Autowired
    private SeguidoresPaginaRepository seguidoresPaginaRepository;

    @Autowired
    private SeguidoresPaginaMapper seguidoresPaginaMapper;

    @Autowired
    private SeguidoresPaginaService seguidoresPaginaService;

    @Autowired
    private SeguidoresPaginaSearchRepository seguidoresPaginaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeguidoresPaginaMockMvc;

    private SeguidoresPagina seguidoresPagina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeguidoresPaginaResource seguidoresPaginaResource = new SeguidoresPaginaResource(seguidoresPaginaService);
        this.restSeguidoresPaginaMockMvc = MockMvcBuilders.standaloneSetup(seguidoresPaginaResource)
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
    public static SeguidoresPagina createEntity(EntityManager em) {
        SeguidoresPagina seguidoresPagina = new SeguidoresPagina()
            .status(DEFAULT_STATUS);
        return seguidoresPagina;
    }

    @Before
    public void initTest() {
        seguidoresPaginaSearchRepository.deleteAll();
        seguidoresPagina = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeguidoresPagina() throws Exception {
        int databaseSizeBeforeCreate = seguidoresPaginaRepository.findAll().size();

        // Create the SeguidoresPagina
        SeguidoresPaginaDTO seguidoresPaginaDTO = seguidoresPaginaMapper.toDto(seguidoresPagina);
        restSeguidoresPaginaMockMvc.perform(post("/api/seguidores-paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidoresPaginaDTO)))
            .andExpect(status().isCreated());

        // Validate the SeguidoresPagina in the database
        List<SeguidoresPagina> seguidoresPaginaList = seguidoresPaginaRepository.findAll();
        assertThat(seguidoresPaginaList).hasSize(databaseSizeBeforeCreate + 1);
        SeguidoresPagina testSeguidoresPagina = seguidoresPaginaList.get(seguidoresPaginaList.size() - 1);
        assertThat(testSeguidoresPagina.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the SeguidoresPagina in Elasticsearch
        SeguidoresPagina seguidoresPaginaEs = seguidoresPaginaSearchRepository.findOne(testSeguidoresPagina.getId());
        assertThat(seguidoresPaginaEs).isEqualToComparingFieldByField(testSeguidoresPagina);
    }

    @Test
    @Transactional
    public void createSeguidoresPaginaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seguidoresPaginaRepository.findAll().size();

        // Create the SeguidoresPagina with an existing ID
        seguidoresPagina.setId(1L);
        SeguidoresPaginaDTO seguidoresPaginaDTO = seguidoresPaginaMapper.toDto(seguidoresPagina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguidoresPaginaMockMvc.perform(post("/api/seguidores-paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidoresPaginaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SeguidoresPagina> seguidoresPaginaList = seguidoresPaginaRepository.findAll();
        assertThat(seguidoresPaginaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeguidoresPaginas() throws Exception {
        // Initialize the database
        seguidoresPaginaRepository.saveAndFlush(seguidoresPagina);

        // Get all the seguidoresPaginaList
        restSeguidoresPaginaMockMvc.perform(get("/api/seguidores-paginas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguidoresPagina.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSeguidoresPagina() throws Exception {
        // Initialize the database
        seguidoresPaginaRepository.saveAndFlush(seguidoresPagina);

        // Get the seguidoresPagina
        restSeguidoresPaginaMockMvc.perform(get("/api/seguidores-paginas/{id}", seguidoresPagina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seguidoresPagina.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeguidoresPagina() throws Exception {
        // Get the seguidoresPagina
        restSeguidoresPaginaMockMvc.perform(get("/api/seguidores-paginas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeguidoresPagina() throws Exception {
        // Initialize the database
        seguidoresPaginaRepository.saveAndFlush(seguidoresPagina);
        seguidoresPaginaSearchRepository.save(seguidoresPagina);
        int databaseSizeBeforeUpdate = seguidoresPaginaRepository.findAll().size();

        // Update the seguidoresPagina
        SeguidoresPagina updatedSeguidoresPagina = seguidoresPaginaRepository.findOne(seguidoresPagina.getId());
        updatedSeguidoresPagina
            .status(UPDATED_STATUS);
        SeguidoresPaginaDTO seguidoresPaginaDTO = seguidoresPaginaMapper.toDto(updatedSeguidoresPagina);

        restSeguidoresPaginaMockMvc.perform(put("/api/seguidores-paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidoresPaginaDTO)))
            .andExpect(status().isOk());

        // Validate the SeguidoresPagina in the database
        List<SeguidoresPagina> seguidoresPaginaList = seguidoresPaginaRepository.findAll();
        assertThat(seguidoresPaginaList).hasSize(databaseSizeBeforeUpdate);
        SeguidoresPagina testSeguidoresPagina = seguidoresPaginaList.get(seguidoresPaginaList.size() - 1);
        assertThat(testSeguidoresPagina.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the SeguidoresPagina in Elasticsearch
        SeguidoresPagina seguidoresPaginaEs = seguidoresPaginaSearchRepository.findOne(testSeguidoresPagina.getId());
        assertThat(seguidoresPaginaEs).isEqualToComparingFieldByField(testSeguidoresPagina);
    }

    @Test
    @Transactional
    public void updateNonExistingSeguidoresPagina() throws Exception {
        int databaseSizeBeforeUpdate = seguidoresPaginaRepository.findAll().size();

        // Create the SeguidoresPagina
        SeguidoresPaginaDTO seguidoresPaginaDTO = seguidoresPaginaMapper.toDto(seguidoresPagina);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeguidoresPaginaMockMvc.perform(put("/api/seguidores-paginas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguidoresPaginaDTO)))
            .andExpect(status().isCreated());

        // Validate the SeguidoresPagina in the database
        List<SeguidoresPagina> seguidoresPaginaList = seguidoresPaginaRepository.findAll();
        assertThat(seguidoresPaginaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeguidoresPagina() throws Exception {
        // Initialize the database
        seguidoresPaginaRepository.saveAndFlush(seguidoresPagina);
        seguidoresPaginaSearchRepository.save(seguidoresPagina);
        int databaseSizeBeforeDelete = seguidoresPaginaRepository.findAll().size();

        // Get the seguidoresPagina
        restSeguidoresPaginaMockMvc.perform(delete("/api/seguidores-paginas/{id}", seguidoresPagina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean seguidoresPaginaExistsInEs = seguidoresPaginaSearchRepository.exists(seguidoresPagina.getId());
        assertThat(seguidoresPaginaExistsInEs).isFalse();

        // Validate the database is empty
        List<SeguidoresPagina> seguidoresPaginaList = seguidoresPaginaRepository.findAll();
        assertThat(seguidoresPaginaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSeguidoresPagina() throws Exception {
        // Initialize the database
        seguidoresPaginaRepository.saveAndFlush(seguidoresPagina);
        seguidoresPaginaSearchRepository.save(seguidoresPagina);

        // Search the seguidoresPagina
        restSeguidoresPaginaMockMvc.perform(get("/api/_search/seguidores-paginas?query=id:" + seguidoresPagina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguidoresPagina.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeguidoresPagina.class);
        SeguidoresPagina seguidoresPagina1 = new SeguidoresPagina();
        seguidoresPagina1.setId(1L);
        SeguidoresPagina seguidoresPagina2 = new SeguidoresPagina();
        seguidoresPagina2.setId(seguidoresPagina1.getId());
        assertThat(seguidoresPagina1).isEqualTo(seguidoresPagina2);
        seguidoresPagina2.setId(2L);
        assertThat(seguidoresPagina1).isNotEqualTo(seguidoresPagina2);
        seguidoresPagina1.setId(null);
        assertThat(seguidoresPagina1).isNotEqualTo(seguidoresPagina2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeguidoresPaginaDTO.class);
        SeguidoresPaginaDTO seguidoresPaginaDTO1 = new SeguidoresPaginaDTO();
        seguidoresPaginaDTO1.setId(1L);
        SeguidoresPaginaDTO seguidoresPaginaDTO2 = new SeguidoresPaginaDTO();
        assertThat(seguidoresPaginaDTO1).isNotEqualTo(seguidoresPaginaDTO2);
        seguidoresPaginaDTO2.setId(seguidoresPaginaDTO1.getId());
        assertThat(seguidoresPaginaDTO1).isEqualTo(seguidoresPaginaDTO2);
        seguidoresPaginaDTO2.setId(2L);
        assertThat(seguidoresPaginaDTO1).isNotEqualTo(seguidoresPaginaDTO2);
        seguidoresPaginaDTO1.setId(null);
        assertThat(seguidoresPaginaDTO1).isNotEqualTo(seguidoresPaginaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seguidoresPaginaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seguidoresPaginaMapper.fromId(null)).isNull();
    }
}
