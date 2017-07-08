package com.aantivero.infi.web.rest;

import com.aantivero.infi.InfiApp;

import com.aantivero.infi.domain.EntidadFinanciera;
import com.aantivero.infi.repository.EntidadFinancieraRepository;
import com.aantivero.infi.service.EntidadFinancieraService;
import com.aantivero.infi.web.rest.errors.ExceptionTranslator;

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
 * Test class for the EntidadFinancieraResource REST controller.
 *
 * @see EntidadFinancieraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfiApp.class)
public class EntidadFinancieraResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAA";
    private static final String UPDATED_CODIGO = "BBBBB";

    private static final String DEFAULT_DENOMINACION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINACION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_NUMERICO = 1;
    private static final Integer UPDATED_CODIGO_NUMERICO = 2;

    @Autowired
    private EntidadFinancieraRepository entidadFinancieraRepository;

    @Autowired
    private EntidadFinancieraService entidadFinancieraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntidadFinancieraMockMvc;

    private EntidadFinanciera entidadFinanciera;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntidadFinancieraResource entidadFinancieraResource = new EntidadFinancieraResource(entidadFinancieraService);
        this.restEntidadFinancieraMockMvc = MockMvcBuilders.standaloneSetup(entidadFinancieraResource)
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
    public static EntidadFinanciera createEntity(EntityManager em) {
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera()
            .codigo(DEFAULT_CODIGO)
            .denominacion(DEFAULT_DENOMINACION)
            .codigoNumerico(DEFAULT_CODIGO_NUMERICO);
        return entidadFinanciera;
    }

    @Before
    public void initTest() {
        entidadFinanciera = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntidadFinanciera() throws Exception {
        int databaseSizeBeforeCreate = entidadFinancieraRepository.findAll().size();

        // Create the EntidadFinanciera
        restEntidadFinancieraMockMvc.perform(post("/api/entidad-financieras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciera)))
            .andExpect(status().isCreated());

        // Validate the EntidadFinanciera in the database
        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeCreate + 1);
        EntidadFinanciera testEntidadFinanciera = entidadFinancieraList.get(entidadFinancieraList.size() - 1);
        assertThat(testEntidadFinanciera.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testEntidadFinanciera.getDenominacion()).isEqualTo(DEFAULT_DENOMINACION);
        assertThat(testEntidadFinanciera.getCodigoNumerico()).isEqualTo(DEFAULT_CODIGO_NUMERICO);
    }

    @Test
    @Transactional
    public void createEntidadFinancieraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entidadFinancieraRepository.findAll().size();

        // Create the EntidadFinanciera with an existing ID
        entidadFinanciera.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadFinancieraMockMvc.perform(post("/api/entidad-financieras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciera)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = entidadFinancieraRepository.findAll().size();
        // set the field null
        entidadFinanciera.setCodigo(null);

        // Create the EntidadFinanciera, which fails.

        restEntidadFinancieraMockMvc.perform(post("/api/entidad-financieras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciera)))
            .andExpect(status().isBadRequest());

        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDenominacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = entidadFinancieraRepository.findAll().size();
        // set the field null
        entidadFinanciera.setDenominacion(null);

        // Create the EntidadFinanciera, which fails.

        restEntidadFinancieraMockMvc.perform(post("/api/entidad-financieras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciera)))
            .andExpect(status().isBadRequest());

        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoNumericoIsRequired() throws Exception {
        int databaseSizeBeforeTest = entidadFinancieraRepository.findAll().size();
        // set the field null
        entidadFinanciera.setCodigoNumerico(null);

        // Create the EntidadFinanciera, which fails.

        restEntidadFinancieraMockMvc.perform(post("/api/entidad-financieras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciera)))
            .andExpect(status().isBadRequest());

        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntidadFinancieras() throws Exception {
        // Initialize the database
        entidadFinancieraRepository.saveAndFlush(entidadFinanciera);

        // Get all the entidadFinancieraList
        restEntidadFinancieraMockMvc.perform(get("/api/entidad-financieras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidadFinanciera.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].denominacion").value(hasItem(DEFAULT_DENOMINACION.toString())))
            .andExpect(jsonPath("$.[*].codigoNumerico").value(hasItem(DEFAULT_CODIGO_NUMERICO)));
    }

    @Test
    @Transactional
    public void getEntidadFinanciera() throws Exception {
        // Initialize the database
        entidadFinancieraRepository.saveAndFlush(entidadFinanciera);

        // Get the entidadFinanciera
        restEntidadFinancieraMockMvc.perform(get("/api/entidad-financieras/{id}", entidadFinanciera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entidadFinanciera.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.denominacion").value(DEFAULT_DENOMINACION.toString()))
            .andExpect(jsonPath("$.codigoNumerico").value(DEFAULT_CODIGO_NUMERICO));
    }

    @Test
    @Transactional
    public void getNonExistingEntidadFinanciera() throws Exception {
        // Get the entidadFinanciera
        restEntidadFinancieraMockMvc.perform(get("/api/entidad-financieras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntidadFinanciera() throws Exception {
        // Initialize the database
        entidadFinancieraService.save(entidadFinanciera);

        int databaseSizeBeforeUpdate = entidadFinancieraRepository.findAll().size();

        // Update the entidadFinanciera
        EntidadFinanciera updatedEntidadFinanciera = entidadFinancieraRepository.findOne(entidadFinanciera.getId());
        updatedEntidadFinanciera
            .codigo(UPDATED_CODIGO)
            .denominacion(UPDATED_DENOMINACION)
            .codigoNumerico(UPDATED_CODIGO_NUMERICO);

        restEntidadFinancieraMockMvc.perform(put("/api/entidad-financieras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntidadFinanciera)))
            .andExpect(status().isOk());

        // Validate the EntidadFinanciera in the database
        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeUpdate);
        EntidadFinanciera testEntidadFinanciera = entidadFinancieraList.get(entidadFinancieraList.size() - 1);
        assertThat(testEntidadFinanciera.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testEntidadFinanciera.getDenominacion()).isEqualTo(UPDATED_DENOMINACION);
        assertThat(testEntidadFinanciera.getCodigoNumerico()).isEqualTo(UPDATED_CODIGO_NUMERICO);
    }

    @Test
    @Transactional
    public void updateNonExistingEntidadFinanciera() throws Exception {
        int databaseSizeBeforeUpdate = entidadFinancieraRepository.findAll().size();

        // Create the EntidadFinanciera

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntidadFinancieraMockMvc.perform(put("/api/entidad-financieras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciera)))
            .andExpect(status().isCreated());

        // Validate the EntidadFinanciera in the database
        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntidadFinanciera() throws Exception {
        // Initialize the database
        entidadFinancieraService.save(entidadFinanciera);

        int databaseSizeBeforeDelete = entidadFinancieraRepository.findAll().size();

        // Get the entidadFinanciera
        restEntidadFinancieraMockMvc.perform(delete("/api/entidad-financieras/{id}", entidadFinanciera.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntidadFinanciera> entidadFinancieraList = entidadFinancieraRepository.findAll();
        assertThat(entidadFinancieraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadFinanciera.class);
        EntidadFinanciera entidadFinanciera1 = new EntidadFinanciera();
        entidadFinanciera1.setId(1L);
        EntidadFinanciera entidadFinanciera2 = new EntidadFinanciera();
        entidadFinanciera2.setId(entidadFinanciera1.getId());
        assertThat(entidadFinanciera1).isEqualTo(entidadFinanciera2);
        entidadFinanciera2.setId(2L);
        assertThat(entidadFinanciera1).isNotEqualTo(entidadFinanciera2);
        entidadFinanciera1.setId(null);
        assertThat(entidadFinanciera1).isNotEqualTo(entidadFinanciera2);
    }
}
