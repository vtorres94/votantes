package mx.com.morena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mx.com.morena.IntegrationTest;
import mx.com.morena.domain.CCliente;
import mx.com.morena.domain.CDefensorVoto;
import mx.com.morena.domain.CVotante;
import mx.com.morena.repository.CVotanteRepository;
import mx.com.morena.service.criteria.CVotanteCriteria;
import mx.com.morena.service.dto.CVotanteDTO;
import mx.com.morena.service.mapper.CVotanteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CVotanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CVotanteResourceIT {

    private static final String DEFAULT_NOMBRE_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMPLETO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_MATERNO = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD = 999;
    private static final Integer UPDATED_EDAD = 998;
    private static final Integer SMALLER_EDAD = 999 - 1;

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE_ELECTOR = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE_ELECTOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_SECCION_ELECTORAL = 999999;
    private static final Integer UPDATED_SECCION_ELECTORAL = 999998;
    private static final Integer SMALLER_SECCION_ELECTORAL = 999999 - 1;

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_EXT = "AAAAAAAAAA";
    private static final String UPDATED_NUM_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_COLONIA = "AAAAAAAAAA";
    private static final String UPDATED_COLONIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_CP = 999999;
    private static final Integer UPDATED_CP = 999998;
    private static final Integer SMALLER_CP = 999999 - 1;

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTATUS = 1;
    private static final Integer UPDATED_ESTATUS = 0;
    private static final Integer SMALLER_ESTATUS = 1 - 1;

    private static final Integer DEFAULT_BORRADO = 1;
    private static final Integer UPDATED_BORRADO = 0;
    private static final Integer SMALLER_BORRADO = 1 - 1;

    private static final String ENTITY_API_URL = "/api/c-votantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CVotanteRepository cVotanteRepository;

    @Autowired
    private CVotanteMapper cVotanteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCVotanteMockMvc;

    private CVotante cVotante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVotante createEntity(EntityManager em) {
        CVotante cVotante = new CVotante()
            .nombreCompleto(DEFAULT_NOMBRE_COMPLETO)
            .nombre(DEFAULT_NOMBRE)
            .segundoNombre(DEFAULT_SEGUNDO_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .segundoMaterno(DEFAULT_SEGUNDO_MATERNO)
            .edad(DEFAULT_EDAD)
            .telefono(DEFAULT_TELEFONO)
            .claveElector(DEFAULT_CLAVE_ELECTOR)
            .seccionElectoral(DEFAULT_SECCION_ELECTORAL)
            .calle(DEFAULT_CALLE)
            .numExt(DEFAULT_NUM_EXT)
            .colonia(DEFAULT_COLONIA)
            .cp(DEFAULT_CP)
            .municipio(DEFAULT_MUNICIPIO)
            .estado(DEFAULT_ESTADO)
            .estatus(DEFAULT_ESTATUS)
            .borrado(DEFAULT_BORRADO);
        return cVotante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CVotante createUpdatedEntity(EntityManager em) {
        CVotante cVotante = new CVotante()
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .nombre(UPDATED_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .segundoMaterno(UPDATED_SEGUNDO_MATERNO)
            .edad(UPDATED_EDAD)
            .telefono(UPDATED_TELEFONO)
            .claveElector(UPDATED_CLAVE_ELECTOR)
            .seccionElectoral(UPDATED_SECCION_ELECTORAL)
            .calle(UPDATED_CALLE)
            .numExt(UPDATED_NUM_EXT)
            .colonia(UPDATED_COLONIA)
            .cp(UPDATED_CP)
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .borrado(UPDATED_BORRADO);
        return cVotante;
    }

    @BeforeEach
    public void initTest() {
        cVotante = createEntity(em);
    }

    @Test
    @Transactional
    void createCVotante() throws Exception {
        int databaseSizeBeforeCreate = cVotanteRepository.findAll().size();
        // Create the CVotante
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);
        restCVotanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isCreated());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeCreate + 1);
        CVotante testCVotante = cVotanteList.get(cVotanteList.size() - 1);
        assertThat(testCVotante.getNombreCompleto()).isEqualTo(DEFAULT_NOMBRE_COMPLETO);
        assertThat(testCVotante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCVotante.getSegundoNombre()).isEqualTo(DEFAULT_SEGUNDO_NOMBRE);
        assertThat(testCVotante.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testCVotante.getSegundoMaterno()).isEqualTo(DEFAULT_SEGUNDO_MATERNO);
        assertThat(testCVotante.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testCVotante.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCVotante.getClaveElector()).isEqualTo(DEFAULT_CLAVE_ELECTOR);
        assertThat(testCVotante.getSeccionElectoral()).isEqualTo(DEFAULT_SECCION_ELECTORAL);
        assertThat(testCVotante.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testCVotante.getNumExt()).isEqualTo(DEFAULT_NUM_EXT);
        assertThat(testCVotante.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testCVotante.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testCVotante.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testCVotante.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCVotante.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testCVotante.getBorrado()).isEqualTo(DEFAULT_BORRADO);
    }

    @Test
    @Transactional
    void createCVotanteWithExistingId() throws Exception {
        // Create the CVotante with an existing ID
        cVotante.setId(1L);
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        int databaseSizeBeforeCreate = cVotanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVotanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreCompletoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVotanteRepository.findAll().size();
        // set the field null
        cVotante.setNombreCompleto(null);

        // Create the CVotante, which fails.
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        restCVotanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isBadRequest());

        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVotanteRepository.findAll().size();
        // set the field null
        cVotante.setNombre(null);

        // Create the CVotante, which fails.
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        restCVotanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isBadRequest());

        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidoPaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVotanteRepository.findAll().size();
        // set the field null
        cVotante.setApellidoPaterno(null);

        // Create the CVotante, which fails.
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        restCVotanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isBadRequest());

        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVotanteRepository.findAll().size();
        // set the field null
        cVotante.setEstatus(null);

        // Create the CVotante, which fails.
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        restCVotanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isBadRequest());

        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBorradoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cVotanteRepository.findAll().size();
        // set the field null
        cVotante.setBorrado(null);

        // Create the CVotante, which fails.
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        restCVotanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isBadRequest());

        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCVotantes() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList
        restCVotanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVotante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].segundoNombre").value(hasItem(DEFAULT_SEGUNDO_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].segundoMaterno").value(hasItem(DEFAULT_SEGUNDO_MATERNO)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].claveElector").value(hasItem(DEFAULT_CLAVE_ELECTOR)))
            .andExpect(jsonPath("$.[*].seccionElectoral").value(hasItem(DEFAULT_SECCION_ELECTORAL)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].numExt").value(hasItem(DEFAULT_NUM_EXT)))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)))
            .andExpect(jsonPath("$.[*].borrado").value(hasItem(DEFAULT_BORRADO)));
    }

    @Test
    @Transactional
    void getCVotante() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get the cVotante
        restCVotanteMockMvc
            .perform(get(ENTITY_API_URL_ID, cVotante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cVotante.getId().intValue()))
            .andExpect(jsonPath("$.nombreCompleto").value(DEFAULT_NOMBRE_COMPLETO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.segundoNombre").value(DEFAULT_SEGUNDO_NOMBRE))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO))
            .andExpect(jsonPath("$.segundoMaterno").value(DEFAULT_SEGUNDO_MATERNO))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.claveElector").value(DEFAULT_CLAVE_ELECTOR))
            .andExpect(jsonPath("$.seccionElectoral").value(DEFAULT_SECCION_ELECTORAL))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.numExt").value(DEFAULT_NUM_EXT))
            .andExpect(jsonPath("$.colonia").value(DEFAULT_COLONIA))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS))
            .andExpect(jsonPath("$.borrado").value(DEFAULT_BORRADO));
    }

    @Test
    @Transactional
    void getCVotantesByIdFiltering() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        Long id = cVotante.getId();

        defaultCVotanteShouldBeFound("id.equals=" + id);
        defaultCVotanteShouldNotBeFound("id.notEquals=" + id);

        defaultCVotanteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCVotanteShouldNotBeFound("id.greaterThan=" + id);

        defaultCVotanteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCVotanteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombreCompleto equals to DEFAULT_NOMBRE_COMPLETO
        defaultCVotanteShouldBeFound("nombreCompleto.equals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cVotanteList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultCVotanteShouldNotBeFound("nombreCompleto.equals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreCompletoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombreCompleto not equals to DEFAULT_NOMBRE_COMPLETO
        defaultCVotanteShouldNotBeFound("nombreCompleto.notEquals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cVotanteList where nombreCompleto not equals to UPDATED_NOMBRE_COMPLETO
        defaultCVotanteShouldBeFound("nombreCompleto.notEquals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombreCompleto in DEFAULT_NOMBRE_COMPLETO or UPDATED_NOMBRE_COMPLETO
        defaultCVotanteShouldBeFound("nombreCompleto.in=" + DEFAULT_NOMBRE_COMPLETO + "," + UPDATED_NOMBRE_COMPLETO);

        // Get all the cVotanteList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultCVotanteShouldNotBeFound("nombreCompleto.in=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombreCompleto is not null
        defaultCVotanteShouldBeFound("nombreCompleto.specified=true");

        // Get all the cVotanteList where nombreCompleto is null
        defaultCVotanteShouldNotBeFound("nombreCompleto.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreCompletoContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombreCompleto contains DEFAULT_NOMBRE_COMPLETO
        defaultCVotanteShouldBeFound("nombreCompleto.contains=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cVotanteList where nombreCompleto contains UPDATED_NOMBRE_COMPLETO
        defaultCVotanteShouldNotBeFound("nombreCompleto.contains=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreCompletoNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombreCompleto does not contain DEFAULT_NOMBRE_COMPLETO
        defaultCVotanteShouldNotBeFound("nombreCompleto.doesNotContain=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cVotanteList where nombreCompleto does not contain UPDATED_NOMBRE_COMPLETO
        defaultCVotanteShouldBeFound("nombreCompleto.doesNotContain=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombre equals to DEFAULT_NOMBRE
        defaultCVotanteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the cVotanteList where nombre equals to UPDATED_NOMBRE
        defaultCVotanteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombre not equals to DEFAULT_NOMBRE
        defaultCVotanteShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the cVotanteList where nombre not equals to UPDATED_NOMBRE
        defaultCVotanteShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCVotanteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the cVotanteList where nombre equals to UPDATED_NOMBRE
        defaultCVotanteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombre is not null
        defaultCVotanteShouldBeFound("nombre.specified=true");

        // Get all the cVotanteList where nombre is null
        defaultCVotanteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombre contains DEFAULT_NOMBRE
        defaultCVotanteShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the cVotanteList where nombre contains UPDATED_NOMBRE
        defaultCVotanteShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where nombre does not contain DEFAULT_NOMBRE
        defaultCVotanteShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the cVotanteList where nombre does not contain UPDATED_NOMBRE
        defaultCVotanteShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoNombre equals to DEFAULT_SEGUNDO_NOMBRE
        defaultCVotanteShouldBeFound("segundoNombre.equals=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cVotanteList where segundoNombre equals to UPDATED_SEGUNDO_NOMBRE
        defaultCVotanteShouldNotBeFound("segundoNombre.equals=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoNombre not equals to DEFAULT_SEGUNDO_NOMBRE
        defaultCVotanteShouldNotBeFound("segundoNombre.notEquals=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cVotanteList where segundoNombre not equals to UPDATED_SEGUNDO_NOMBRE
        defaultCVotanteShouldBeFound("segundoNombre.notEquals=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoNombreIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoNombre in DEFAULT_SEGUNDO_NOMBRE or UPDATED_SEGUNDO_NOMBRE
        defaultCVotanteShouldBeFound("segundoNombre.in=" + DEFAULT_SEGUNDO_NOMBRE + "," + UPDATED_SEGUNDO_NOMBRE);

        // Get all the cVotanteList where segundoNombre equals to UPDATED_SEGUNDO_NOMBRE
        defaultCVotanteShouldNotBeFound("segundoNombre.in=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoNombre is not null
        defaultCVotanteShouldBeFound("segundoNombre.specified=true");

        // Get all the cVotanteList where segundoNombre is null
        defaultCVotanteShouldNotBeFound("segundoNombre.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoNombreContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoNombre contains DEFAULT_SEGUNDO_NOMBRE
        defaultCVotanteShouldBeFound("segundoNombre.contains=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cVotanteList where segundoNombre contains UPDATED_SEGUNDO_NOMBRE
        defaultCVotanteShouldNotBeFound("segundoNombre.contains=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoNombreNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoNombre does not contain DEFAULT_SEGUNDO_NOMBRE
        defaultCVotanteShouldNotBeFound("segundoNombre.doesNotContain=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cVotanteList where segundoNombre does not contain UPDATED_SEGUNDO_NOMBRE
        defaultCVotanteShouldBeFound("segundoNombre.doesNotContain=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCVotantesByApellidoPaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where apellidoPaterno equals to DEFAULT_APELLIDO_PATERNO
        defaultCVotanteShouldBeFound("apellidoPaterno.equals=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cVotanteList where apellidoPaterno equals to UPDATED_APELLIDO_PATERNO
        defaultCVotanteShouldNotBeFound("apellidoPaterno.equals=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesByApellidoPaternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where apellidoPaterno not equals to DEFAULT_APELLIDO_PATERNO
        defaultCVotanteShouldNotBeFound("apellidoPaterno.notEquals=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cVotanteList where apellidoPaterno not equals to UPDATED_APELLIDO_PATERNO
        defaultCVotanteShouldBeFound("apellidoPaterno.notEquals=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesByApellidoPaternoIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where apellidoPaterno in DEFAULT_APELLIDO_PATERNO or UPDATED_APELLIDO_PATERNO
        defaultCVotanteShouldBeFound("apellidoPaterno.in=" + DEFAULT_APELLIDO_PATERNO + "," + UPDATED_APELLIDO_PATERNO);

        // Get all the cVotanteList where apellidoPaterno equals to UPDATED_APELLIDO_PATERNO
        defaultCVotanteShouldNotBeFound("apellidoPaterno.in=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesByApellidoPaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where apellidoPaterno is not null
        defaultCVotanteShouldBeFound("apellidoPaterno.specified=true");

        // Get all the cVotanteList where apellidoPaterno is null
        defaultCVotanteShouldNotBeFound("apellidoPaterno.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByApellidoPaternoContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where apellidoPaterno contains DEFAULT_APELLIDO_PATERNO
        defaultCVotanteShouldBeFound("apellidoPaterno.contains=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cVotanteList where apellidoPaterno contains UPDATED_APELLIDO_PATERNO
        defaultCVotanteShouldNotBeFound("apellidoPaterno.contains=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesByApellidoPaternoNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where apellidoPaterno does not contain DEFAULT_APELLIDO_PATERNO
        defaultCVotanteShouldNotBeFound("apellidoPaterno.doesNotContain=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cVotanteList where apellidoPaterno does not contain UPDATED_APELLIDO_PATERNO
        defaultCVotanteShouldBeFound("apellidoPaterno.doesNotContain=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoMaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoMaterno equals to DEFAULT_SEGUNDO_MATERNO
        defaultCVotanteShouldBeFound("segundoMaterno.equals=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cVotanteList where segundoMaterno equals to UPDATED_SEGUNDO_MATERNO
        defaultCVotanteShouldNotBeFound("segundoMaterno.equals=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoMaternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoMaterno not equals to DEFAULT_SEGUNDO_MATERNO
        defaultCVotanteShouldNotBeFound("segundoMaterno.notEquals=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cVotanteList where segundoMaterno not equals to UPDATED_SEGUNDO_MATERNO
        defaultCVotanteShouldBeFound("segundoMaterno.notEquals=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoMaternoIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoMaterno in DEFAULT_SEGUNDO_MATERNO or UPDATED_SEGUNDO_MATERNO
        defaultCVotanteShouldBeFound("segundoMaterno.in=" + DEFAULT_SEGUNDO_MATERNO + "," + UPDATED_SEGUNDO_MATERNO);

        // Get all the cVotanteList where segundoMaterno equals to UPDATED_SEGUNDO_MATERNO
        defaultCVotanteShouldNotBeFound("segundoMaterno.in=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoMaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoMaterno is not null
        defaultCVotanteShouldBeFound("segundoMaterno.specified=true");

        // Get all the cVotanteList where segundoMaterno is null
        defaultCVotanteShouldNotBeFound("segundoMaterno.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoMaternoContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoMaterno contains DEFAULT_SEGUNDO_MATERNO
        defaultCVotanteShouldBeFound("segundoMaterno.contains=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cVotanteList where segundoMaterno contains UPDATED_SEGUNDO_MATERNO
        defaultCVotanteShouldNotBeFound("segundoMaterno.contains=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesBySegundoMaternoNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where segundoMaterno does not contain DEFAULT_SEGUNDO_MATERNO
        defaultCVotanteShouldNotBeFound("segundoMaterno.doesNotContain=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cVotanteList where segundoMaterno does not contain UPDATED_SEGUNDO_MATERNO
        defaultCVotanteShouldBeFound("segundoMaterno.doesNotContain=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad equals to DEFAULT_EDAD
        defaultCVotanteShouldBeFound("edad.equals=" + DEFAULT_EDAD);

        // Get all the cVotanteList where edad equals to UPDATED_EDAD
        defaultCVotanteShouldNotBeFound("edad.equals=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad not equals to DEFAULT_EDAD
        defaultCVotanteShouldNotBeFound("edad.notEquals=" + DEFAULT_EDAD);

        // Get all the cVotanteList where edad not equals to UPDATED_EDAD
        defaultCVotanteShouldBeFound("edad.notEquals=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad in DEFAULT_EDAD or UPDATED_EDAD
        defaultCVotanteShouldBeFound("edad.in=" + DEFAULT_EDAD + "," + UPDATED_EDAD);

        // Get all the cVotanteList where edad equals to UPDATED_EDAD
        defaultCVotanteShouldNotBeFound("edad.in=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad is not null
        defaultCVotanteShouldBeFound("edad.specified=true");

        // Get all the cVotanteList where edad is null
        defaultCVotanteShouldNotBeFound("edad.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad is greater than or equal to DEFAULT_EDAD
        defaultCVotanteShouldBeFound("edad.greaterThanOrEqual=" + DEFAULT_EDAD);

        // Get all the cVotanteList where edad is greater than or equal to (DEFAULT_EDAD + 1)
        defaultCVotanteShouldNotBeFound("edad.greaterThanOrEqual=" + (DEFAULT_EDAD + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad is less than or equal to DEFAULT_EDAD
        defaultCVotanteShouldBeFound("edad.lessThanOrEqual=" + DEFAULT_EDAD);

        // Get all the cVotanteList where edad is less than or equal to SMALLER_EDAD
        defaultCVotanteShouldNotBeFound("edad.lessThanOrEqual=" + SMALLER_EDAD);
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsLessThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad is less than DEFAULT_EDAD
        defaultCVotanteShouldNotBeFound("edad.lessThan=" + DEFAULT_EDAD);

        // Get all the cVotanteList where edad is less than (DEFAULT_EDAD + 1)
        defaultCVotanteShouldBeFound("edad.lessThan=" + (DEFAULT_EDAD + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByEdadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where edad is greater than DEFAULT_EDAD
        defaultCVotanteShouldNotBeFound("edad.greaterThan=" + DEFAULT_EDAD);

        // Get all the cVotanteList where edad is greater than SMALLER_EDAD
        defaultCVotanteShouldBeFound("edad.greaterThan=" + SMALLER_EDAD);
    }

    @Test
    @Transactional
    void getAllCVotantesByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where telefono equals to DEFAULT_TELEFONO
        defaultCVotanteShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the cVotanteList where telefono equals to UPDATED_TELEFONO
        defaultCVotanteShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCVotantesByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where telefono not equals to DEFAULT_TELEFONO
        defaultCVotanteShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the cVotanteList where telefono not equals to UPDATED_TELEFONO
        defaultCVotanteShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCVotantesByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultCVotanteShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the cVotanteList where telefono equals to UPDATED_TELEFONO
        defaultCVotanteShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCVotantesByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where telefono is not null
        defaultCVotanteShouldBeFound("telefono.specified=true");

        // Get all the cVotanteList where telefono is null
        defaultCVotanteShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where telefono contains DEFAULT_TELEFONO
        defaultCVotanteShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the cVotanteList where telefono contains UPDATED_TELEFONO
        defaultCVotanteShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCVotantesByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where telefono does not contain DEFAULT_TELEFONO
        defaultCVotanteShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the cVotanteList where telefono does not contain UPDATED_TELEFONO
        defaultCVotanteShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCVotantesByClaveElectorIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where claveElector equals to DEFAULT_CLAVE_ELECTOR
        defaultCVotanteShouldBeFound("claveElector.equals=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cVotanteList where claveElector equals to UPDATED_CLAVE_ELECTOR
        defaultCVotanteShouldNotBeFound("claveElector.equals=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCVotantesByClaveElectorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where claveElector not equals to DEFAULT_CLAVE_ELECTOR
        defaultCVotanteShouldNotBeFound("claveElector.notEquals=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cVotanteList where claveElector not equals to UPDATED_CLAVE_ELECTOR
        defaultCVotanteShouldBeFound("claveElector.notEquals=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCVotantesByClaveElectorIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where claveElector in DEFAULT_CLAVE_ELECTOR or UPDATED_CLAVE_ELECTOR
        defaultCVotanteShouldBeFound("claveElector.in=" + DEFAULT_CLAVE_ELECTOR + "," + UPDATED_CLAVE_ELECTOR);

        // Get all the cVotanteList where claveElector equals to UPDATED_CLAVE_ELECTOR
        defaultCVotanteShouldNotBeFound("claveElector.in=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCVotantesByClaveElectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where claveElector is not null
        defaultCVotanteShouldBeFound("claveElector.specified=true");

        // Get all the cVotanteList where claveElector is null
        defaultCVotanteShouldNotBeFound("claveElector.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByClaveElectorContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where claveElector contains DEFAULT_CLAVE_ELECTOR
        defaultCVotanteShouldBeFound("claveElector.contains=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cVotanteList where claveElector contains UPDATED_CLAVE_ELECTOR
        defaultCVotanteShouldNotBeFound("claveElector.contains=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCVotantesByClaveElectorNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where claveElector does not contain DEFAULT_CLAVE_ELECTOR
        defaultCVotanteShouldNotBeFound("claveElector.doesNotContain=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cVotanteList where claveElector does not contain UPDATED_CLAVE_ELECTOR
        defaultCVotanteShouldBeFound("claveElector.doesNotContain=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral equals to DEFAULT_SECCION_ELECTORAL
        defaultCVotanteShouldBeFound("seccionElectoral.equals=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cVotanteList where seccionElectoral equals to UPDATED_SECCION_ELECTORAL
        defaultCVotanteShouldNotBeFound("seccionElectoral.equals=" + UPDATED_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral not equals to DEFAULT_SECCION_ELECTORAL
        defaultCVotanteShouldNotBeFound("seccionElectoral.notEquals=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cVotanteList where seccionElectoral not equals to UPDATED_SECCION_ELECTORAL
        defaultCVotanteShouldBeFound("seccionElectoral.notEquals=" + UPDATED_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral in DEFAULT_SECCION_ELECTORAL or UPDATED_SECCION_ELECTORAL
        defaultCVotanteShouldBeFound("seccionElectoral.in=" + DEFAULT_SECCION_ELECTORAL + "," + UPDATED_SECCION_ELECTORAL);

        // Get all the cVotanteList where seccionElectoral equals to UPDATED_SECCION_ELECTORAL
        defaultCVotanteShouldNotBeFound("seccionElectoral.in=" + UPDATED_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral is not null
        defaultCVotanteShouldBeFound("seccionElectoral.specified=true");

        // Get all the cVotanteList where seccionElectoral is null
        defaultCVotanteShouldNotBeFound("seccionElectoral.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral is greater than or equal to DEFAULT_SECCION_ELECTORAL
        defaultCVotanteShouldBeFound("seccionElectoral.greaterThanOrEqual=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cVotanteList where seccionElectoral is greater than or equal to (DEFAULT_SECCION_ELECTORAL + 1)
        defaultCVotanteShouldNotBeFound("seccionElectoral.greaterThanOrEqual=" + (DEFAULT_SECCION_ELECTORAL + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral is less than or equal to DEFAULT_SECCION_ELECTORAL
        defaultCVotanteShouldBeFound("seccionElectoral.lessThanOrEqual=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cVotanteList where seccionElectoral is less than or equal to SMALLER_SECCION_ELECTORAL
        defaultCVotanteShouldNotBeFound("seccionElectoral.lessThanOrEqual=" + SMALLER_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsLessThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral is less than DEFAULT_SECCION_ELECTORAL
        defaultCVotanteShouldNotBeFound("seccionElectoral.lessThan=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cVotanteList where seccionElectoral is less than (DEFAULT_SECCION_ELECTORAL + 1)
        defaultCVotanteShouldBeFound("seccionElectoral.lessThan=" + (DEFAULT_SECCION_ELECTORAL + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesBySeccionElectoralIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where seccionElectoral is greater than DEFAULT_SECCION_ELECTORAL
        defaultCVotanteShouldNotBeFound("seccionElectoral.greaterThan=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cVotanteList where seccionElectoral is greater than SMALLER_SECCION_ELECTORAL
        defaultCVotanteShouldBeFound("seccionElectoral.greaterThan=" + SMALLER_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCVotantesByCalleIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where calle equals to DEFAULT_CALLE
        defaultCVotanteShouldBeFound("calle.equals=" + DEFAULT_CALLE);

        // Get all the cVotanteList where calle equals to UPDATED_CALLE
        defaultCVotanteShouldNotBeFound("calle.equals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCVotantesByCalleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where calle not equals to DEFAULT_CALLE
        defaultCVotanteShouldNotBeFound("calle.notEquals=" + DEFAULT_CALLE);

        // Get all the cVotanteList where calle not equals to UPDATED_CALLE
        defaultCVotanteShouldBeFound("calle.notEquals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCVotantesByCalleIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where calle in DEFAULT_CALLE or UPDATED_CALLE
        defaultCVotanteShouldBeFound("calle.in=" + DEFAULT_CALLE + "," + UPDATED_CALLE);

        // Get all the cVotanteList where calle equals to UPDATED_CALLE
        defaultCVotanteShouldNotBeFound("calle.in=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCVotantesByCalleIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where calle is not null
        defaultCVotanteShouldBeFound("calle.specified=true");

        // Get all the cVotanteList where calle is null
        defaultCVotanteShouldNotBeFound("calle.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByCalleContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where calle contains DEFAULT_CALLE
        defaultCVotanteShouldBeFound("calle.contains=" + DEFAULT_CALLE);

        // Get all the cVotanteList where calle contains UPDATED_CALLE
        defaultCVotanteShouldNotBeFound("calle.contains=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCVotantesByCalleNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where calle does not contain DEFAULT_CALLE
        defaultCVotanteShouldNotBeFound("calle.doesNotContain=" + DEFAULT_CALLE);

        // Get all the cVotanteList where calle does not contain UPDATED_CALLE
        defaultCVotanteShouldBeFound("calle.doesNotContain=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCVotantesByNumExtIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where numExt equals to DEFAULT_NUM_EXT
        defaultCVotanteShouldBeFound("numExt.equals=" + DEFAULT_NUM_EXT);

        // Get all the cVotanteList where numExt equals to UPDATED_NUM_EXT
        defaultCVotanteShouldNotBeFound("numExt.equals=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCVotantesByNumExtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where numExt not equals to DEFAULT_NUM_EXT
        defaultCVotanteShouldNotBeFound("numExt.notEquals=" + DEFAULT_NUM_EXT);

        // Get all the cVotanteList where numExt not equals to UPDATED_NUM_EXT
        defaultCVotanteShouldBeFound("numExt.notEquals=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCVotantesByNumExtIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where numExt in DEFAULT_NUM_EXT or UPDATED_NUM_EXT
        defaultCVotanteShouldBeFound("numExt.in=" + DEFAULT_NUM_EXT + "," + UPDATED_NUM_EXT);

        // Get all the cVotanteList where numExt equals to UPDATED_NUM_EXT
        defaultCVotanteShouldNotBeFound("numExt.in=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCVotantesByNumExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where numExt is not null
        defaultCVotanteShouldBeFound("numExt.specified=true");

        // Get all the cVotanteList where numExt is null
        defaultCVotanteShouldNotBeFound("numExt.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByNumExtContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where numExt contains DEFAULT_NUM_EXT
        defaultCVotanteShouldBeFound("numExt.contains=" + DEFAULT_NUM_EXT);

        // Get all the cVotanteList where numExt contains UPDATED_NUM_EXT
        defaultCVotanteShouldNotBeFound("numExt.contains=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCVotantesByNumExtNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where numExt does not contain DEFAULT_NUM_EXT
        defaultCVotanteShouldNotBeFound("numExt.doesNotContain=" + DEFAULT_NUM_EXT);

        // Get all the cVotanteList where numExt does not contain UPDATED_NUM_EXT
        defaultCVotanteShouldBeFound("numExt.doesNotContain=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCVotantesByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where colonia equals to DEFAULT_COLONIA
        defaultCVotanteShouldBeFound("colonia.equals=" + DEFAULT_COLONIA);

        // Get all the cVotanteList where colonia equals to UPDATED_COLONIA
        defaultCVotanteShouldNotBeFound("colonia.equals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCVotantesByColoniaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where colonia not equals to DEFAULT_COLONIA
        defaultCVotanteShouldNotBeFound("colonia.notEquals=" + DEFAULT_COLONIA);

        // Get all the cVotanteList where colonia not equals to UPDATED_COLONIA
        defaultCVotanteShouldBeFound("colonia.notEquals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCVotantesByColoniaIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where colonia in DEFAULT_COLONIA or UPDATED_COLONIA
        defaultCVotanteShouldBeFound("colonia.in=" + DEFAULT_COLONIA + "," + UPDATED_COLONIA);

        // Get all the cVotanteList where colonia equals to UPDATED_COLONIA
        defaultCVotanteShouldNotBeFound("colonia.in=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCVotantesByColoniaIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where colonia is not null
        defaultCVotanteShouldBeFound("colonia.specified=true");

        // Get all the cVotanteList where colonia is null
        defaultCVotanteShouldNotBeFound("colonia.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByColoniaContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where colonia contains DEFAULT_COLONIA
        defaultCVotanteShouldBeFound("colonia.contains=" + DEFAULT_COLONIA);

        // Get all the cVotanteList where colonia contains UPDATED_COLONIA
        defaultCVotanteShouldNotBeFound("colonia.contains=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCVotantesByColoniaNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where colonia does not contain DEFAULT_COLONIA
        defaultCVotanteShouldNotBeFound("colonia.doesNotContain=" + DEFAULT_COLONIA);

        // Get all the cVotanteList where colonia does not contain UPDATED_COLONIA
        defaultCVotanteShouldBeFound("colonia.doesNotContain=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp equals to DEFAULT_CP
        defaultCVotanteShouldBeFound("cp.equals=" + DEFAULT_CP);

        // Get all the cVotanteList where cp equals to UPDATED_CP
        defaultCVotanteShouldNotBeFound("cp.equals=" + UPDATED_CP);
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp not equals to DEFAULT_CP
        defaultCVotanteShouldNotBeFound("cp.notEquals=" + DEFAULT_CP);

        // Get all the cVotanteList where cp not equals to UPDATED_CP
        defaultCVotanteShouldBeFound("cp.notEquals=" + UPDATED_CP);
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp in DEFAULT_CP or UPDATED_CP
        defaultCVotanteShouldBeFound("cp.in=" + DEFAULT_CP + "," + UPDATED_CP);

        // Get all the cVotanteList where cp equals to UPDATED_CP
        defaultCVotanteShouldNotBeFound("cp.in=" + UPDATED_CP);
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp is not null
        defaultCVotanteShouldBeFound("cp.specified=true");

        // Get all the cVotanteList where cp is null
        defaultCVotanteShouldNotBeFound("cp.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp is greater than or equal to DEFAULT_CP
        defaultCVotanteShouldBeFound("cp.greaterThanOrEqual=" + DEFAULT_CP);

        // Get all the cVotanteList where cp is greater than or equal to (DEFAULT_CP + 1)
        defaultCVotanteShouldNotBeFound("cp.greaterThanOrEqual=" + (DEFAULT_CP + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp is less than or equal to DEFAULT_CP
        defaultCVotanteShouldBeFound("cp.lessThanOrEqual=" + DEFAULT_CP);

        // Get all the cVotanteList where cp is less than or equal to SMALLER_CP
        defaultCVotanteShouldNotBeFound("cp.lessThanOrEqual=" + SMALLER_CP);
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsLessThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp is less than DEFAULT_CP
        defaultCVotanteShouldNotBeFound("cp.lessThan=" + DEFAULT_CP);

        // Get all the cVotanteList where cp is less than (DEFAULT_CP + 1)
        defaultCVotanteShouldBeFound("cp.lessThan=" + (DEFAULT_CP + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByCpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where cp is greater than DEFAULT_CP
        defaultCVotanteShouldNotBeFound("cp.greaterThan=" + DEFAULT_CP);

        // Get all the cVotanteList where cp is greater than SMALLER_CP
        defaultCVotanteShouldBeFound("cp.greaterThan=" + SMALLER_CP);
    }

    @Test
    @Transactional
    void getAllCVotantesByMunicipioIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where municipio equals to DEFAULT_MUNICIPIO
        defaultCVotanteShouldBeFound("municipio.equals=" + DEFAULT_MUNICIPIO);

        // Get all the cVotanteList where municipio equals to UPDATED_MUNICIPIO
        defaultCVotanteShouldNotBeFound("municipio.equals=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCVotantesByMunicipioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where municipio not equals to DEFAULT_MUNICIPIO
        defaultCVotanteShouldNotBeFound("municipio.notEquals=" + DEFAULT_MUNICIPIO);

        // Get all the cVotanteList where municipio not equals to UPDATED_MUNICIPIO
        defaultCVotanteShouldBeFound("municipio.notEquals=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCVotantesByMunicipioIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where municipio in DEFAULT_MUNICIPIO or UPDATED_MUNICIPIO
        defaultCVotanteShouldBeFound("municipio.in=" + DEFAULT_MUNICIPIO + "," + UPDATED_MUNICIPIO);

        // Get all the cVotanteList where municipio equals to UPDATED_MUNICIPIO
        defaultCVotanteShouldNotBeFound("municipio.in=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCVotantesByMunicipioIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where municipio is not null
        defaultCVotanteShouldBeFound("municipio.specified=true");

        // Get all the cVotanteList where municipio is null
        defaultCVotanteShouldNotBeFound("municipio.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByMunicipioContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where municipio contains DEFAULT_MUNICIPIO
        defaultCVotanteShouldBeFound("municipio.contains=" + DEFAULT_MUNICIPIO);

        // Get all the cVotanteList where municipio contains UPDATED_MUNICIPIO
        defaultCVotanteShouldNotBeFound("municipio.contains=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCVotantesByMunicipioNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where municipio does not contain DEFAULT_MUNICIPIO
        defaultCVotanteShouldNotBeFound("municipio.doesNotContain=" + DEFAULT_MUNICIPIO);

        // Get all the cVotanteList where municipio does not contain UPDATED_MUNICIPIO
        defaultCVotanteShouldBeFound("municipio.doesNotContain=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estado equals to DEFAULT_ESTADO
        defaultCVotanteShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the cVotanteList where estado equals to UPDATED_ESTADO
        defaultCVotanteShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estado not equals to DEFAULT_ESTADO
        defaultCVotanteShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the cVotanteList where estado not equals to UPDATED_ESTADO
        defaultCVotanteShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultCVotanteShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the cVotanteList where estado equals to UPDATED_ESTADO
        defaultCVotanteShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estado is not null
        defaultCVotanteShouldBeFound("estado.specified=true");

        // Get all the cVotanteList where estado is null
        defaultCVotanteShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByEstadoContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estado contains DEFAULT_ESTADO
        defaultCVotanteShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the cVotanteList where estado contains UPDATED_ESTADO
        defaultCVotanteShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estado does not contain DEFAULT_ESTADO
        defaultCVotanteShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the cVotanteList where estado does not contain UPDATED_ESTADO
        defaultCVotanteShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus equals to DEFAULT_ESTATUS
        defaultCVotanteShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the cVotanteList where estatus equals to UPDATED_ESTATUS
        defaultCVotanteShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus not equals to DEFAULT_ESTATUS
        defaultCVotanteShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the cVotanteList where estatus not equals to UPDATED_ESTATUS
        defaultCVotanteShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultCVotanteShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the cVotanteList where estatus equals to UPDATED_ESTATUS
        defaultCVotanteShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus is not null
        defaultCVotanteShouldBeFound("estatus.specified=true");

        // Get all the cVotanteList where estatus is null
        defaultCVotanteShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus is greater than or equal to DEFAULT_ESTATUS
        defaultCVotanteShouldBeFound("estatus.greaterThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cVotanteList where estatus is greater than or equal to (DEFAULT_ESTATUS + 1)
        defaultCVotanteShouldNotBeFound("estatus.greaterThanOrEqual=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus is less than or equal to DEFAULT_ESTATUS
        defaultCVotanteShouldBeFound("estatus.lessThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cVotanteList where estatus is less than or equal to SMALLER_ESTATUS
        defaultCVotanteShouldNotBeFound("estatus.lessThanOrEqual=" + SMALLER_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsLessThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus is less than DEFAULT_ESTATUS
        defaultCVotanteShouldNotBeFound("estatus.lessThan=" + DEFAULT_ESTATUS);

        // Get all the cVotanteList where estatus is less than (DEFAULT_ESTATUS + 1)
        defaultCVotanteShouldBeFound("estatus.lessThan=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByEstatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where estatus is greater than DEFAULT_ESTATUS
        defaultCVotanteShouldNotBeFound("estatus.greaterThan=" + DEFAULT_ESTATUS);

        // Get all the cVotanteList where estatus is greater than SMALLER_ESTATUS
        defaultCVotanteShouldBeFound("estatus.greaterThan=" + SMALLER_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado equals to DEFAULT_BORRADO
        defaultCVotanteShouldBeFound("borrado.equals=" + DEFAULT_BORRADO);

        // Get all the cVotanteList where borrado equals to UPDATED_BORRADO
        defaultCVotanteShouldNotBeFound("borrado.equals=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado not equals to DEFAULT_BORRADO
        defaultCVotanteShouldNotBeFound("borrado.notEquals=" + DEFAULT_BORRADO);

        // Get all the cVotanteList where borrado not equals to UPDATED_BORRADO
        defaultCVotanteShouldBeFound("borrado.notEquals=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsInShouldWork() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado in DEFAULT_BORRADO or UPDATED_BORRADO
        defaultCVotanteShouldBeFound("borrado.in=" + DEFAULT_BORRADO + "," + UPDATED_BORRADO);

        // Get all the cVotanteList where borrado equals to UPDATED_BORRADO
        defaultCVotanteShouldNotBeFound("borrado.in=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado is not null
        defaultCVotanteShouldBeFound("borrado.specified=true");

        // Get all the cVotanteList where borrado is null
        defaultCVotanteShouldNotBeFound("borrado.specified=false");
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado is greater than or equal to DEFAULT_BORRADO
        defaultCVotanteShouldBeFound("borrado.greaterThanOrEqual=" + DEFAULT_BORRADO);

        // Get all the cVotanteList where borrado is greater than or equal to (DEFAULT_BORRADO + 1)
        defaultCVotanteShouldNotBeFound("borrado.greaterThanOrEqual=" + (DEFAULT_BORRADO + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado is less than or equal to DEFAULT_BORRADO
        defaultCVotanteShouldBeFound("borrado.lessThanOrEqual=" + DEFAULT_BORRADO);

        // Get all the cVotanteList where borrado is less than or equal to SMALLER_BORRADO
        defaultCVotanteShouldNotBeFound("borrado.lessThanOrEqual=" + SMALLER_BORRADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsLessThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado is less than DEFAULT_BORRADO
        defaultCVotanteShouldNotBeFound("borrado.lessThan=" + DEFAULT_BORRADO);

        // Get all the cVotanteList where borrado is less than (DEFAULT_BORRADO + 1)
        defaultCVotanteShouldBeFound("borrado.lessThan=" + (DEFAULT_BORRADO + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByBorradoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        // Get all the cVotanteList where borrado is greater than DEFAULT_BORRADO
        defaultCVotanteShouldNotBeFound("borrado.greaterThan=" + DEFAULT_BORRADO);

        // Get all the cVotanteList where borrado is greater than SMALLER_BORRADO
        defaultCVotanteShouldBeFound("borrado.greaterThan=" + SMALLER_BORRADO);
    }

    @Test
    @Transactional
    void getAllCVotantesByClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);
        CCliente cliente = CClienteResourceIT.createEntity(em);
        em.persist(cliente);
        em.flush();
        cVotante.setCliente(cliente);
        cVotanteRepository.saveAndFlush(cVotante);
        Long clienteId = cliente.getId();

        // Get all the cVotanteList where cliente equals to clienteId
        defaultCVotanteShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the cVotanteList where cliente equals to (clienteId + 1)
        defaultCVotanteShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }

    @Test
    @Transactional
    void getAllCVotantesByDefensorVotoIsEqualToSomething() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);
        CDefensorVoto defensorVoto = CDefensorVotoResourceIT.createEntity(em);
        em.persist(defensorVoto);
        em.flush();
        cVotante.setDefensorVoto(defensorVoto);
        cVotanteRepository.saveAndFlush(cVotante);
        Long defensorVotoId = defensorVoto.getId();

        // Get all the cVotanteList where defensorVoto equals to defensorVotoId
        defaultCVotanteShouldBeFound("defensorVotoId.equals=" + defensorVotoId);

        // Get all the cVotanteList where defensorVoto equals to (defensorVotoId + 1)
        defaultCVotanteShouldNotBeFound("defensorVotoId.equals=" + (defensorVotoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCVotanteShouldBeFound(String filter) throws Exception {
        restCVotanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cVotante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].segundoNombre").value(hasItem(DEFAULT_SEGUNDO_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].segundoMaterno").value(hasItem(DEFAULT_SEGUNDO_MATERNO)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].claveElector").value(hasItem(DEFAULT_CLAVE_ELECTOR)))
            .andExpect(jsonPath("$.[*].seccionElectoral").value(hasItem(DEFAULT_SECCION_ELECTORAL)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].numExt").value(hasItem(DEFAULT_NUM_EXT)))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)))
            .andExpect(jsonPath("$.[*].borrado").value(hasItem(DEFAULT_BORRADO)));

        // Check, that the count call also returns 1
        restCVotanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCVotanteShouldNotBeFound(String filter) throws Exception {
        restCVotanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCVotanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCVotante() throws Exception {
        // Get the cVotante
        restCVotanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCVotante() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();

        // Update the cVotante
        CVotante updatedCVotante = cVotanteRepository.findById(cVotante.getId()).get();
        // Disconnect from session so that the updates on updatedCVotante are not directly saved in db
        em.detach(updatedCVotante);
        updatedCVotante
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .nombre(UPDATED_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .segundoMaterno(UPDATED_SEGUNDO_MATERNO)
            .edad(UPDATED_EDAD)
            .telefono(UPDATED_TELEFONO)
            .claveElector(UPDATED_CLAVE_ELECTOR)
            .seccionElectoral(UPDATED_SECCION_ELECTORAL)
            .calle(UPDATED_CALLE)
            .numExt(UPDATED_NUM_EXT)
            .colonia(UPDATED_COLONIA)
            .cp(UPDATED_CP)
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .borrado(UPDATED_BORRADO);
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(updatedCVotante);

        restCVotanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cVotanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cVotanteDTO))
            )
            .andExpect(status().isOk());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
        CVotante testCVotante = cVotanteList.get(cVotanteList.size() - 1);
        assertThat(testCVotante.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testCVotante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCVotante.getSegundoNombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testCVotante.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testCVotante.getSegundoMaterno()).isEqualTo(UPDATED_SEGUNDO_MATERNO);
        assertThat(testCVotante.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testCVotante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCVotante.getClaveElector()).isEqualTo(UPDATED_CLAVE_ELECTOR);
        assertThat(testCVotante.getSeccionElectoral()).isEqualTo(UPDATED_SECCION_ELECTORAL);
        assertThat(testCVotante.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testCVotante.getNumExt()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testCVotante.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testCVotante.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testCVotante.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testCVotante.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCVotante.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCVotante.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void putNonExistingCVotante() throws Exception {
        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();
        cVotante.setId(count.incrementAndGet());

        // Create the CVotante
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVotanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cVotanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cVotanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCVotante() throws Exception {
        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();
        cVotante.setId(count.incrementAndGet());

        // Create the CVotante
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCVotanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cVotanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCVotante() throws Exception {
        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();
        cVotante.setId(count.incrementAndGet());

        // Create the CVotante
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCVotanteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cVotanteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCVotanteWithPatch() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();

        // Update the cVotante using partial update
        CVotante partialUpdatedCVotante = new CVotante();
        partialUpdatedCVotante.setId(cVotante.getId());

        partialUpdatedCVotante
            .nombre(UPDATED_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .telefono(UPDATED_TELEFONO)
            .calle(UPDATED_CALLE)
            .numExt(UPDATED_NUM_EXT)
            .estado(UPDATED_ESTADO)
            .borrado(UPDATED_BORRADO);

        restCVotanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCVotante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCVotante))
            )
            .andExpect(status().isOk());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
        CVotante testCVotante = cVotanteList.get(cVotanteList.size() - 1);
        assertThat(testCVotante.getNombreCompleto()).isEqualTo(DEFAULT_NOMBRE_COMPLETO);
        assertThat(testCVotante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCVotante.getSegundoNombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testCVotante.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testCVotante.getSegundoMaterno()).isEqualTo(DEFAULT_SEGUNDO_MATERNO);
        assertThat(testCVotante.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testCVotante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCVotante.getClaveElector()).isEqualTo(DEFAULT_CLAVE_ELECTOR);
        assertThat(testCVotante.getSeccionElectoral()).isEqualTo(DEFAULT_SECCION_ELECTORAL);
        assertThat(testCVotante.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testCVotante.getNumExt()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testCVotante.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testCVotante.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testCVotante.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testCVotante.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCVotante.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testCVotante.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void fullUpdateCVotanteWithPatch() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();

        // Update the cVotante using partial update
        CVotante partialUpdatedCVotante = new CVotante();
        partialUpdatedCVotante.setId(cVotante.getId());

        partialUpdatedCVotante
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .nombre(UPDATED_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .segundoMaterno(UPDATED_SEGUNDO_MATERNO)
            .edad(UPDATED_EDAD)
            .telefono(UPDATED_TELEFONO)
            .claveElector(UPDATED_CLAVE_ELECTOR)
            .seccionElectoral(UPDATED_SECCION_ELECTORAL)
            .calle(UPDATED_CALLE)
            .numExt(UPDATED_NUM_EXT)
            .colonia(UPDATED_COLONIA)
            .cp(UPDATED_CP)
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .borrado(UPDATED_BORRADO);

        restCVotanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCVotante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCVotante))
            )
            .andExpect(status().isOk());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
        CVotante testCVotante = cVotanteList.get(cVotanteList.size() - 1);
        assertThat(testCVotante.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testCVotante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCVotante.getSegundoNombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testCVotante.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testCVotante.getSegundoMaterno()).isEqualTo(UPDATED_SEGUNDO_MATERNO);
        assertThat(testCVotante.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testCVotante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCVotante.getClaveElector()).isEqualTo(UPDATED_CLAVE_ELECTOR);
        assertThat(testCVotante.getSeccionElectoral()).isEqualTo(UPDATED_SECCION_ELECTORAL);
        assertThat(testCVotante.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testCVotante.getNumExt()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testCVotante.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testCVotante.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testCVotante.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testCVotante.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCVotante.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCVotante.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void patchNonExistingCVotante() throws Exception {
        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();
        cVotante.setId(count.incrementAndGet());

        // Create the CVotante
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVotanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cVotanteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cVotanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCVotante() throws Exception {
        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();
        cVotante.setId(count.incrementAndGet());

        // Create the CVotante
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCVotanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cVotanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCVotante() throws Exception {
        int databaseSizeBeforeUpdate = cVotanteRepository.findAll().size();
        cVotante.setId(count.incrementAndGet());

        // Create the CVotante
        CVotanteDTO cVotanteDTO = cVotanteMapper.toDto(cVotante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCVotanteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cVotanteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CVotante in the database
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCVotante() throws Exception {
        // Initialize the database
        cVotanteRepository.saveAndFlush(cVotante);

        int databaseSizeBeforeDelete = cVotanteRepository.findAll().size();

        // Delete the cVotante
        restCVotanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, cVotante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CVotante> cVotanteList = cVotanteRepository.findAll();
        assertThat(cVotanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
