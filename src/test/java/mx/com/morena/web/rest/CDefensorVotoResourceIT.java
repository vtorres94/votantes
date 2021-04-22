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
import mx.com.morena.repository.CDefensorVotoRepository;
import mx.com.morena.service.criteria.CDefensorVotoCriteria;
import mx.com.morena.service.dto.CDefensorVotoDTO;
import mx.com.morena.service.mapper.CDefensorVotoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CDefensorVotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CDefensorVotoResourceIT {

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

    private static final String ENTITY_API_URL = "/api/c-defensor-votos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CDefensorVotoRepository cDefensorVotoRepository;

    @Autowired
    private CDefensorVotoMapper cDefensorVotoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCDefensorVotoMockMvc;

    private CDefensorVoto cDefensorVoto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CDefensorVoto createEntity(EntityManager em) {
        CDefensorVoto cDefensorVoto = new CDefensorVoto()
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
        return cDefensorVoto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CDefensorVoto createUpdatedEntity(EntityManager em) {
        CDefensorVoto cDefensorVoto = new CDefensorVoto()
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
        return cDefensorVoto;
    }

    @BeforeEach
    public void initTest() {
        cDefensorVoto = createEntity(em);
    }

    @Test
    @Transactional
    void createCDefensorVoto() throws Exception {
        int databaseSizeBeforeCreate = cDefensorVotoRepository.findAll().size();
        // Create the CDefensorVoto
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);
        restCDefensorVotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeCreate + 1);
        CDefensorVoto testCDefensorVoto = cDefensorVotoList.get(cDefensorVotoList.size() - 1);
        assertThat(testCDefensorVoto.getNombreCompleto()).isEqualTo(DEFAULT_NOMBRE_COMPLETO);
        assertThat(testCDefensorVoto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCDefensorVoto.getSegundoNombre()).isEqualTo(DEFAULT_SEGUNDO_NOMBRE);
        assertThat(testCDefensorVoto.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testCDefensorVoto.getSegundoMaterno()).isEqualTo(DEFAULT_SEGUNDO_MATERNO);
        assertThat(testCDefensorVoto.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testCDefensorVoto.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCDefensorVoto.getClaveElector()).isEqualTo(DEFAULT_CLAVE_ELECTOR);
        assertThat(testCDefensorVoto.getSeccionElectoral()).isEqualTo(DEFAULT_SECCION_ELECTORAL);
        assertThat(testCDefensorVoto.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testCDefensorVoto.getNumExt()).isEqualTo(DEFAULT_NUM_EXT);
        assertThat(testCDefensorVoto.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testCDefensorVoto.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testCDefensorVoto.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testCDefensorVoto.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCDefensorVoto.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testCDefensorVoto.getBorrado()).isEqualTo(DEFAULT_BORRADO);
    }

    @Test
    @Transactional
    void createCDefensorVotoWithExistingId() throws Exception {
        // Create the CDefensorVoto with an existing ID
        cDefensorVoto.setId(1L);
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        int databaseSizeBeforeCreate = cDefensorVotoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCDefensorVotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreCompletoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cDefensorVotoRepository.findAll().size();
        // set the field null
        cDefensorVoto.setNombreCompleto(null);

        // Create the CDefensorVoto, which fails.
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        restCDefensorVotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = cDefensorVotoRepository.findAll().size();
        // set the field null
        cDefensorVoto.setNombre(null);

        // Create the CDefensorVoto, which fails.
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        restCDefensorVotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidoPaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cDefensorVotoRepository.findAll().size();
        // set the field null
        cDefensorVoto.setApellidoPaterno(null);

        // Create the CDefensorVoto, which fails.
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        restCDefensorVotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cDefensorVotoRepository.findAll().size();
        // set the field null
        cDefensorVoto.setEstatus(null);

        // Create the CDefensorVoto, which fails.
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        restCDefensorVotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBorradoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cDefensorVotoRepository.findAll().size();
        // set the field null
        cDefensorVoto.setBorrado(null);

        // Create the CDefensorVoto, which fails.
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        restCDefensorVotoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCDefensorVotos() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList
        restCDefensorVotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cDefensorVoto.getId().intValue())))
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
    void getCDefensorVoto() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get the cDefensorVoto
        restCDefensorVotoMockMvc
            .perform(get(ENTITY_API_URL_ID, cDefensorVoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cDefensorVoto.getId().intValue()))
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
    void getCDefensorVotosByIdFiltering() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        Long id = cDefensorVoto.getId();

        defaultCDefensorVotoShouldBeFound("id.equals=" + id);
        defaultCDefensorVotoShouldNotBeFound("id.notEquals=" + id);

        defaultCDefensorVotoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCDefensorVotoShouldNotBeFound("id.greaterThan=" + id);

        defaultCDefensorVotoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCDefensorVotoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombreCompleto equals to DEFAULT_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldBeFound("nombreCompleto.equals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cDefensorVotoList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldNotBeFound("nombreCompleto.equals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreCompletoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombreCompleto not equals to DEFAULT_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldNotBeFound("nombreCompleto.notEquals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cDefensorVotoList where nombreCompleto not equals to UPDATED_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldBeFound("nombreCompleto.notEquals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombreCompleto in DEFAULT_NOMBRE_COMPLETO or UPDATED_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldBeFound("nombreCompleto.in=" + DEFAULT_NOMBRE_COMPLETO + "," + UPDATED_NOMBRE_COMPLETO);

        // Get all the cDefensorVotoList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldNotBeFound("nombreCompleto.in=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombreCompleto is not null
        defaultCDefensorVotoShouldBeFound("nombreCompleto.specified=true");

        // Get all the cDefensorVotoList where nombreCompleto is null
        defaultCDefensorVotoShouldNotBeFound("nombreCompleto.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreCompletoContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombreCompleto contains DEFAULT_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldBeFound("nombreCompleto.contains=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cDefensorVotoList where nombreCompleto contains UPDATED_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldNotBeFound("nombreCompleto.contains=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreCompletoNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombreCompleto does not contain DEFAULT_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldNotBeFound("nombreCompleto.doesNotContain=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the cDefensorVotoList where nombreCompleto does not contain UPDATED_NOMBRE_COMPLETO
        defaultCDefensorVotoShouldBeFound("nombreCompleto.doesNotContain=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombre equals to DEFAULT_NOMBRE
        defaultCDefensorVotoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the cDefensorVotoList where nombre equals to UPDATED_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombre not equals to DEFAULT_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the cDefensorVotoList where nombre not equals to UPDATED_NOMBRE
        defaultCDefensorVotoShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCDefensorVotoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the cDefensorVotoList where nombre equals to UPDATED_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombre is not null
        defaultCDefensorVotoShouldBeFound("nombre.specified=true");

        // Get all the cDefensorVotoList where nombre is null
        defaultCDefensorVotoShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombre contains DEFAULT_NOMBRE
        defaultCDefensorVotoShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the cDefensorVotoList where nombre contains UPDATED_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where nombre does not contain DEFAULT_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the cDefensorVotoList where nombre does not contain UPDATED_NOMBRE
        defaultCDefensorVotoShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoNombre equals to DEFAULT_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldBeFound("segundoNombre.equals=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cDefensorVotoList where segundoNombre equals to UPDATED_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("segundoNombre.equals=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoNombre not equals to DEFAULT_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("segundoNombre.notEquals=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cDefensorVotoList where segundoNombre not equals to UPDATED_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldBeFound("segundoNombre.notEquals=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoNombreIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoNombre in DEFAULT_SEGUNDO_NOMBRE or UPDATED_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldBeFound("segundoNombre.in=" + DEFAULT_SEGUNDO_NOMBRE + "," + UPDATED_SEGUNDO_NOMBRE);

        // Get all the cDefensorVotoList where segundoNombre equals to UPDATED_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("segundoNombre.in=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoNombre is not null
        defaultCDefensorVotoShouldBeFound("segundoNombre.specified=true");

        // Get all the cDefensorVotoList where segundoNombre is null
        defaultCDefensorVotoShouldNotBeFound("segundoNombre.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoNombreContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoNombre contains DEFAULT_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldBeFound("segundoNombre.contains=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cDefensorVotoList where segundoNombre contains UPDATED_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("segundoNombre.contains=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoNombreNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoNombre does not contain DEFAULT_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldNotBeFound("segundoNombre.doesNotContain=" + DEFAULT_SEGUNDO_NOMBRE);

        // Get all the cDefensorVotoList where segundoNombre does not contain UPDATED_SEGUNDO_NOMBRE
        defaultCDefensorVotoShouldBeFound("segundoNombre.doesNotContain=" + UPDATED_SEGUNDO_NOMBRE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByApellidoPaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where apellidoPaterno equals to DEFAULT_APELLIDO_PATERNO
        defaultCDefensorVotoShouldBeFound("apellidoPaterno.equals=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cDefensorVotoList where apellidoPaterno equals to UPDATED_APELLIDO_PATERNO
        defaultCDefensorVotoShouldNotBeFound("apellidoPaterno.equals=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByApellidoPaternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where apellidoPaterno not equals to DEFAULT_APELLIDO_PATERNO
        defaultCDefensorVotoShouldNotBeFound("apellidoPaterno.notEquals=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cDefensorVotoList where apellidoPaterno not equals to UPDATED_APELLIDO_PATERNO
        defaultCDefensorVotoShouldBeFound("apellidoPaterno.notEquals=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByApellidoPaternoIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where apellidoPaterno in DEFAULT_APELLIDO_PATERNO or UPDATED_APELLIDO_PATERNO
        defaultCDefensorVotoShouldBeFound("apellidoPaterno.in=" + DEFAULT_APELLIDO_PATERNO + "," + UPDATED_APELLIDO_PATERNO);

        // Get all the cDefensorVotoList where apellidoPaterno equals to UPDATED_APELLIDO_PATERNO
        defaultCDefensorVotoShouldNotBeFound("apellidoPaterno.in=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByApellidoPaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where apellidoPaterno is not null
        defaultCDefensorVotoShouldBeFound("apellidoPaterno.specified=true");

        // Get all the cDefensorVotoList where apellidoPaterno is null
        defaultCDefensorVotoShouldNotBeFound("apellidoPaterno.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByApellidoPaternoContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where apellidoPaterno contains DEFAULT_APELLIDO_PATERNO
        defaultCDefensorVotoShouldBeFound("apellidoPaterno.contains=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cDefensorVotoList where apellidoPaterno contains UPDATED_APELLIDO_PATERNO
        defaultCDefensorVotoShouldNotBeFound("apellidoPaterno.contains=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByApellidoPaternoNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where apellidoPaterno does not contain DEFAULT_APELLIDO_PATERNO
        defaultCDefensorVotoShouldNotBeFound("apellidoPaterno.doesNotContain=" + DEFAULT_APELLIDO_PATERNO);

        // Get all the cDefensorVotoList where apellidoPaterno does not contain UPDATED_APELLIDO_PATERNO
        defaultCDefensorVotoShouldBeFound("apellidoPaterno.doesNotContain=" + UPDATED_APELLIDO_PATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoMaternoIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoMaterno equals to DEFAULT_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldBeFound("segundoMaterno.equals=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cDefensorVotoList where segundoMaterno equals to UPDATED_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldNotBeFound("segundoMaterno.equals=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoMaternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoMaterno not equals to DEFAULT_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldNotBeFound("segundoMaterno.notEquals=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cDefensorVotoList where segundoMaterno not equals to UPDATED_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldBeFound("segundoMaterno.notEquals=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoMaternoIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoMaterno in DEFAULT_SEGUNDO_MATERNO or UPDATED_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldBeFound("segundoMaterno.in=" + DEFAULT_SEGUNDO_MATERNO + "," + UPDATED_SEGUNDO_MATERNO);

        // Get all the cDefensorVotoList where segundoMaterno equals to UPDATED_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldNotBeFound("segundoMaterno.in=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoMaternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoMaterno is not null
        defaultCDefensorVotoShouldBeFound("segundoMaterno.specified=true");

        // Get all the cDefensorVotoList where segundoMaterno is null
        defaultCDefensorVotoShouldNotBeFound("segundoMaterno.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoMaternoContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoMaterno contains DEFAULT_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldBeFound("segundoMaterno.contains=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cDefensorVotoList where segundoMaterno contains UPDATED_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldNotBeFound("segundoMaterno.contains=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySegundoMaternoNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where segundoMaterno does not contain DEFAULT_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldNotBeFound("segundoMaterno.doesNotContain=" + DEFAULT_SEGUNDO_MATERNO);

        // Get all the cDefensorVotoList where segundoMaterno does not contain UPDATED_SEGUNDO_MATERNO
        defaultCDefensorVotoShouldBeFound("segundoMaterno.doesNotContain=" + UPDATED_SEGUNDO_MATERNO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad equals to DEFAULT_EDAD
        defaultCDefensorVotoShouldBeFound("edad.equals=" + DEFAULT_EDAD);

        // Get all the cDefensorVotoList where edad equals to UPDATED_EDAD
        defaultCDefensorVotoShouldNotBeFound("edad.equals=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad not equals to DEFAULT_EDAD
        defaultCDefensorVotoShouldNotBeFound("edad.notEquals=" + DEFAULT_EDAD);

        // Get all the cDefensorVotoList where edad not equals to UPDATED_EDAD
        defaultCDefensorVotoShouldBeFound("edad.notEquals=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad in DEFAULT_EDAD or UPDATED_EDAD
        defaultCDefensorVotoShouldBeFound("edad.in=" + DEFAULT_EDAD + "," + UPDATED_EDAD);

        // Get all the cDefensorVotoList where edad equals to UPDATED_EDAD
        defaultCDefensorVotoShouldNotBeFound("edad.in=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad is not null
        defaultCDefensorVotoShouldBeFound("edad.specified=true");

        // Get all the cDefensorVotoList where edad is null
        defaultCDefensorVotoShouldNotBeFound("edad.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad is greater than or equal to DEFAULT_EDAD
        defaultCDefensorVotoShouldBeFound("edad.greaterThanOrEqual=" + DEFAULT_EDAD);

        // Get all the cDefensorVotoList where edad is greater than or equal to (DEFAULT_EDAD + 1)
        defaultCDefensorVotoShouldNotBeFound("edad.greaterThanOrEqual=" + (DEFAULT_EDAD + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad is less than or equal to DEFAULT_EDAD
        defaultCDefensorVotoShouldBeFound("edad.lessThanOrEqual=" + DEFAULT_EDAD);

        // Get all the cDefensorVotoList where edad is less than or equal to SMALLER_EDAD
        defaultCDefensorVotoShouldNotBeFound("edad.lessThanOrEqual=" + SMALLER_EDAD);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsLessThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad is less than DEFAULT_EDAD
        defaultCDefensorVotoShouldNotBeFound("edad.lessThan=" + DEFAULT_EDAD);

        // Get all the cDefensorVotoList where edad is less than (DEFAULT_EDAD + 1)
        defaultCDefensorVotoShouldBeFound("edad.lessThan=" + (DEFAULT_EDAD + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEdadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where edad is greater than DEFAULT_EDAD
        defaultCDefensorVotoShouldNotBeFound("edad.greaterThan=" + DEFAULT_EDAD);

        // Get all the cDefensorVotoList where edad is greater than SMALLER_EDAD
        defaultCDefensorVotoShouldBeFound("edad.greaterThan=" + SMALLER_EDAD);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where telefono equals to DEFAULT_TELEFONO
        defaultCDefensorVotoShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the cDefensorVotoList where telefono equals to UPDATED_TELEFONO
        defaultCDefensorVotoShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where telefono not equals to DEFAULT_TELEFONO
        defaultCDefensorVotoShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the cDefensorVotoList where telefono not equals to UPDATED_TELEFONO
        defaultCDefensorVotoShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultCDefensorVotoShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the cDefensorVotoList where telefono equals to UPDATED_TELEFONO
        defaultCDefensorVotoShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where telefono is not null
        defaultCDefensorVotoShouldBeFound("telefono.specified=true");

        // Get all the cDefensorVotoList where telefono is null
        defaultCDefensorVotoShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where telefono contains DEFAULT_TELEFONO
        defaultCDefensorVotoShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the cDefensorVotoList where telefono contains UPDATED_TELEFONO
        defaultCDefensorVotoShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where telefono does not contain DEFAULT_TELEFONO
        defaultCDefensorVotoShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the cDefensorVotoList where telefono does not contain UPDATED_TELEFONO
        defaultCDefensorVotoShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByClaveElectorIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where claveElector equals to DEFAULT_CLAVE_ELECTOR
        defaultCDefensorVotoShouldBeFound("claveElector.equals=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cDefensorVotoList where claveElector equals to UPDATED_CLAVE_ELECTOR
        defaultCDefensorVotoShouldNotBeFound("claveElector.equals=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByClaveElectorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where claveElector not equals to DEFAULT_CLAVE_ELECTOR
        defaultCDefensorVotoShouldNotBeFound("claveElector.notEquals=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cDefensorVotoList where claveElector not equals to UPDATED_CLAVE_ELECTOR
        defaultCDefensorVotoShouldBeFound("claveElector.notEquals=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByClaveElectorIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where claveElector in DEFAULT_CLAVE_ELECTOR or UPDATED_CLAVE_ELECTOR
        defaultCDefensorVotoShouldBeFound("claveElector.in=" + DEFAULT_CLAVE_ELECTOR + "," + UPDATED_CLAVE_ELECTOR);

        // Get all the cDefensorVotoList where claveElector equals to UPDATED_CLAVE_ELECTOR
        defaultCDefensorVotoShouldNotBeFound("claveElector.in=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByClaveElectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where claveElector is not null
        defaultCDefensorVotoShouldBeFound("claveElector.specified=true");

        // Get all the cDefensorVotoList where claveElector is null
        defaultCDefensorVotoShouldNotBeFound("claveElector.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByClaveElectorContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where claveElector contains DEFAULT_CLAVE_ELECTOR
        defaultCDefensorVotoShouldBeFound("claveElector.contains=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cDefensorVotoList where claveElector contains UPDATED_CLAVE_ELECTOR
        defaultCDefensorVotoShouldNotBeFound("claveElector.contains=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByClaveElectorNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where claveElector does not contain DEFAULT_CLAVE_ELECTOR
        defaultCDefensorVotoShouldNotBeFound("claveElector.doesNotContain=" + DEFAULT_CLAVE_ELECTOR);

        // Get all the cDefensorVotoList where claveElector does not contain UPDATED_CLAVE_ELECTOR
        defaultCDefensorVotoShouldBeFound("claveElector.doesNotContain=" + UPDATED_CLAVE_ELECTOR);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral equals to DEFAULT_SECCION_ELECTORAL
        defaultCDefensorVotoShouldBeFound("seccionElectoral.equals=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cDefensorVotoList where seccionElectoral equals to UPDATED_SECCION_ELECTORAL
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.equals=" + UPDATED_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral not equals to DEFAULT_SECCION_ELECTORAL
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.notEquals=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cDefensorVotoList where seccionElectoral not equals to UPDATED_SECCION_ELECTORAL
        defaultCDefensorVotoShouldBeFound("seccionElectoral.notEquals=" + UPDATED_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral in DEFAULT_SECCION_ELECTORAL or UPDATED_SECCION_ELECTORAL
        defaultCDefensorVotoShouldBeFound("seccionElectoral.in=" + DEFAULT_SECCION_ELECTORAL + "," + UPDATED_SECCION_ELECTORAL);

        // Get all the cDefensorVotoList where seccionElectoral equals to UPDATED_SECCION_ELECTORAL
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.in=" + UPDATED_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral is not null
        defaultCDefensorVotoShouldBeFound("seccionElectoral.specified=true");

        // Get all the cDefensorVotoList where seccionElectoral is null
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral is greater than or equal to DEFAULT_SECCION_ELECTORAL
        defaultCDefensorVotoShouldBeFound("seccionElectoral.greaterThanOrEqual=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cDefensorVotoList where seccionElectoral is greater than or equal to (DEFAULT_SECCION_ELECTORAL + 1)
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.greaterThanOrEqual=" + (DEFAULT_SECCION_ELECTORAL + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral is less than or equal to DEFAULT_SECCION_ELECTORAL
        defaultCDefensorVotoShouldBeFound("seccionElectoral.lessThanOrEqual=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cDefensorVotoList where seccionElectoral is less than or equal to SMALLER_SECCION_ELECTORAL
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.lessThanOrEqual=" + SMALLER_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsLessThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral is less than DEFAULT_SECCION_ELECTORAL
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.lessThan=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cDefensorVotoList where seccionElectoral is less than (DEFAULT_SECCION_ELECTORAL + 1)
        defaultCDefensorVotoShouldBeFound("seccionElectoral.lessThan=" + (DEFAULT_SECCION_ELECTORAL + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosBySeccionElectoralIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where seccionElectoral is greater than DEFAULT_SECCION_ELECTORAL
        defaultCDefensorVotoShouldNotBeFound("seccionElectoral.greaterThan=" + DEFAULT_SECCION_ELECTORAL);

        // Get all the cDefensorVotoList where seccionElectoral is greater than SMALLER_SECCION_ELECTORAL
        defaultCDefensorVotoShouldBeFound("seccionElectoral.greaterThan=" + SMALLER_SECCION_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCalleIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where calle equals to DEFAULT_CALLE
        defaultCDefensorVotoShouldBeFound("calle.equals=" + DEFAULT_CALLE);

        // Get all the cDefensorVotoList where calle equals to UPDATED_CALLE
        defaultCDefensorVotoShouldNotBeFound("calle.equals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCalleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where calle not equals to DEFAULT_CALLE
        defaultCDefensorVotoShouldNotBeFound("calle.notEquals=" + DEFAULT_CALLE);

        // Get all the cDefensorVotoList where calle not equals to UPDATED_CALLE
        defaultCDefensorVotoShouldBeFound("calle.notEquals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCalleIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where calle in DEFAULT_CALLE or UPDATED_CALLE
        defaultCDefensorVotoShouldBeFound("calle.in=" + DEFAULT_CALLE + "," + UPDATED_CALLE);

        // Get all the cDefensorVotoList where calle equals to UPDATED_CALLE
        defaultCDefensorVotoShouldNotBeFound("calle.in=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCalleIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where calle is not null
        defaultCDefensorVotoShouldBeFound("calle.specified=true");

        // Get all the cDefensorVotoList where calle is null
        defaultCDefensorVotoShouldNotBeFound("calle.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCalleContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where calle contains DEFAULT_CALLE
        defaultCDefensorVotoShouldBeFound("calle.contains=" + DEFAULT_CALLE);

        // Get all the cDefensorVotoList where calle contains UPDATED_CALLE
        defaultCDefensorVotoShouldNotBeFound("calle.contains=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCalleNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where calle does not contain DEFAULT_CALLE
        defaultCDefensorVotoShouldNotBeFound("calle.doesNotContain=" + DEFAULT_CALLE);

        // Get all the cDefensorVotoList where calle does not contain UPDATED_CALLE
        defaultCDefensorVotoShouldBeFound("calle.doesNotContain=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNumExtIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where numExt equals to DEFAULT_NUM_EXT
        defaultCDefensorVotoShouldBeFound("numExt.equals=" + DEFAULT_NUM_EXT);

        // Get all the cDefensorVotoList where numExt equals to UPDATED_NUM_EXT
        defaultCDefensorVotoShouldNotBeFound("numExt.equals=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNumExtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where numExt not equals to DEFAULT_NUM_EXT
        defaultCDefensorVotoShouldNotBeFound("numExt.notEquals=" + DEFAULT_NUM_EXT);

        // Get all the cDefensorVotoList where numExt not equals to UPDATED_NUM_EXT
        defaultCDefensorVotoShouldBeFound("numExt.notEquals=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNumExtIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where numExt in DEFAULT_NUM_EXT or UPDATED_NUM_EXT
        defaultCDefensorVotoShouldBeFound("numExt.in=" + DEFAULT_NUM_EXT + "," + UPDATED_NUM_EXT);

        // Get all the cDefensorVotoList where numExt equals to UPDATED_NUM_EXT
        defaultCDefensorVotoShouldNotBeFound("numExt.in=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNumExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where numExt is not null
        defaultCDefensorVotoShouldBeFound("numExt.specified=true");

        // Get all the cDefensorVotoList where numExt is null
        defaultCDefensorVotoShouldNotBeFound("numExt.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNumExtContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where numExt contains DEFAULT_NUM_EXT
        defaultCDefensorVotoShouldBeFound("numExt.contains=" + DEFAULT_NUM_EXT);

        // Get all the cDefensorVotoList where numExt contains UPDATED_NUM_EXT
        defaultCDefensorVotoShouldNotBeFound("numExt.contains=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByNumExtNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where numExt does not contain DEFAULT_NUM_EXT
        defaultCDefensorVotoShouldNotBeFound("numExt.doesNotContain=" + DEFAULT_NUM_EXT);

        // Get all the cDefensorVotoList where numExt does not contain UPDATED_NUM_EXT
        defaultCDefensorVotoShouldBeFound("numExt.doesNotContain=" + UPDATED_NUM_EXT);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where colonia equals to DEFAULT_COLONIA
        defaultCDefensorVotoShouldBeFound("colonia.equals=" + DEFAULT_COLONIA);

        // Get all the cDefensorVotoList where colonia equals to UPDATED_COLONIA
        defaultCDefensorVotoShouldNotBeFound("colonia.equals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByColoniaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where colonia not equals to DEFAULT_COLONIA
        defaultCDefensorVotoShouldNotBeFound("colonia.notEquals=" + DEFAULT_COLONIA);

        // Get all the cDefensorVotoList where colonia not equals to UPDATED_COLONIA
        defaultCDefensorVotoShouldBeFound("colonia.notEquals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByColoniaIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where colonia in DEFAULT_COLONIA or UPDATED_COLONIA
        defaultCDefensorVotoShouldBeFound("colonia.in=" + DEFAULT_COLONIA + "," + UPDATED_COLONIA);

        // Get all the cDefensorVotoList where colonia equals to UPDATED_COLONIA
        defaultCDefensorVotoShouldNotBeFound("colonia.in=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByColoniaIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where colonia is not null
        defaultCDefensorVotoShouldBeFound("colonia.specified=true");

        // Get all the cDefensorVotoList where colonia is null
        defaultCDefensorVotoShouldNotBeFound("colonia.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByColoniaContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where colonia contains DEFAULT_COLONIA
        defaultCDefensorVotoShouldBeFound("colonia.contains=" + DEFAULT_COLONIA);

        // Get all the cDefensorVotoList where colonia contains UPDATED_COLONIA
        defaultCDefensorVotoShouldNotBeFound("colonia.contains=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByColoniaNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where colonia does not contain DEFAULT_COLONIA
        defaultCDefensorVotoShouldNotBeFound("colonia.doesNotContain=" + DEFAULT_COLONIA);

        // Get all the cDefensorVotoList where colonia does not contain UPDATED_COLONIA
        defaultCDefensorVotoShouldBeFound("colonia.doesNotContain=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp equals to DEFAULT_CP
        defaultCDefensorVotoShouldBeFound("cp.equals=" + DEFAULT_CP);

        // Get all the cDefensorVotoList where cp equals to UPDATED_CP
        defaultCDefensorVotoShouldNotBeFound("cp.equals=" + UPDATED_CP);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp not equals to DEFAULT_CP
        defaultCDefensorVotoShouldNotBeFound("cp.notEquals=" + DEFAULT_CP);

        // Get all the cDefensorVotoList where cp not equals to UPDATED_CP
        defaultCDefensorVotoShouldBeFound("cp.notEquals=" + UPDATED_CP);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp in DEFAULT_CP or UPDATED_CP
        defaultCDefensorVotoShouldBeFound("cp.in=" + DEFAULT_CP + "," + UPDATED_CP);

        // Get all the cDefensorVotoList where cp equals to UPDATED_CP
        defaultCDefensorVotoShouldNotBeFound("cp.in=" + UPDATED_CP);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp is not null
        defaultCDefensorVotoShouldBeFound("cp.specified=true");

        // Get all the cDefensorVotoList where cp is null
        defaultCDefensorVotoShouldNotBeFound("cp.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp is greater than or equal to DEFAULT_CP
        defaultCDefensorVotoShouldBeFound("cp.greaterThanOrEqual=" + DEFAULT_CP);

        // Get all the cDefensorVotoList where cp is greater than or equal to (DEFAULT_CP + 1)
        defaultCDefensorVotoShouldNotBeFound("cp.greaterThanOrEqual=" + (DEFAULT_CP + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp is less than or equal to DEFAULT_CP
        defaultCDefensorVotoShouldBeFound("cp.lessThanOrEqual=" + DEFAULT_CP);

        // Get all the cDefensorVotoList where cp is less than or equal to SMALLER_CP
        defaultCDefensorVotoShouldNotBeFound("cp.lessThanOrEqual=" + SMALLER_CP);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsLessThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp is less than DEFAULT_CP
        defaultCDefensorVotoShouldNotBeFound("cp.lessThan=" + DEFAULT_CP);

        // Get all the cDefensorVotoList where cp is less than (DEFAULT_CP + 1)
        defaultCDefensorVotoShouldBeFound("cp.lessThan=" + (DEFAULT_CP + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByCpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where cp is greater than DEFAULT_CP
        defaultCDefensorVotoShouldNotBeFound("cp.greaterThan=" + DEFAULT_CP);

        // Get all the cDefensorVotoList where cp is greater than SMALLER_CP
        defaultCDefensorVotoShouldBeFound("cp.greaterThan=" + SMALLER_CP);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByMunicipioIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where municipio equals to DEFAULT_MUNICIPIO
        defaultCDefensorVotoShouldBeFound("municipio.equals=" + DEFAULT_MUNICIPIO);

        // Get all the cDefensorVotoList where municipio equals to UPDATED_MUNICIPIO
        defaultCDefensorVotoShouldNotBeFound("municipio.equals=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByMunicipioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where municipio not equals to DEFAULT_MUNICIPIO
        defaultCDefensorVotoShouldNotBeFound("municipio.notEquals=" + DEFAULT_MUNICIPIO);

        // Get all the cDefensorVotoList where municipio not equals to UPDATED_MUNICIPIO
        defaultCDefensorVotoShouldBeFound("municipio.notEquals=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByMunicipioIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where municipio in DEFAULT_MUNICIPIO or UPDATED_MUNICIPIO
        defaultCDefensorVotoShouldBeFound("municipio.in=" + DEFAULT_MUNICIPIO + "," + UPDATED_MUNICIPIO);

        // Get all the cDefensorVotoList where municipio equals to UPDATED_MUNICIPIO
        defaultCDefensorVotoShouldNotBeFound("municipio.in=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByMunicipioIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where municipio is not null
        defaultCDefensorVotoShouldBeFound("municipio.specified=true");

        // Get all the cDefensorVotoList where municipio is null
        defaultCDefensorVotoShouldNotBeFound("municipio.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByMunicipioContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where municipio contains DEFAULT_MUNICIPIO
        defaultCDefensorVotoShouldBeFound("municipio.contains=" + DEFAULT_MUNICIPIO);

        // Get all the cDefensorVotoList where municipio contains UPDATED_MUNICIPIO
        defaultCDefensorVotoShouldNotBeFound("municipio.contains=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByMunicipioNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where municipio does not contain DEFAULT_MUNICIPIO
        defaultCDefensorVotoShouldNotBeFound("municipio.doesNotContain=" + DEFAULT_MUNICIPIO);

        // Get all the cDefensorVotoList where municipio does not contain UPDATED_MUNICIPIO
        defaultCDefensorVotoShouldBeFound("municipio.doesNotContain=" + UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estado equals to DEFAULT_ESTADO
        defaultCDefensorVotoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the cDefensorVotoList where estado equals to UPDATED_ESTADO
        defaultCDefensorVotoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estado not equals to DEFAULT_ESTADO
        defaultCDefensorVotoShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the cDefensorVotoList where estado not equals to UPDATED_ESTADO
        defaultCDefensorVotoShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultCDefensorVotoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the cDefensorVotoList where estado equals to UPDATED_ESTADO
        defaultCDefensorVotoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estado is not null
        defaultCDefensorVotoShouldBeFound("estado.specified=true");

        // Get all the cDefensorVotoList where estado is null
        defaultCDefensorVotoShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstadoContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estado contains DEFAULT_ESTADO
        defaultCDefensorVotoShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the cDefensorVotoList where estado contains UPDATED_ESTADO
        defaultCDefensorVotoShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estado does not contain DEFAULT_ESTADO
        defaultCDefensorVotoShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the cDefensorVotoList where estado does not contain UPDATED_ESTADO
        defaultCDefensorVotoShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus equals to DEFAULT_ESTATUS
        defaultCDefensorVotoShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the cDefensorVotoList where estatus equals to UPDATED_ESTATUS
        defaultCDefensorVotoShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus not equals to DEFAULT_ESTATUS
        defaultCDefensorVotoShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the cDefensorVotoList where estatus not equals to UPDATED_ESTATUS
        defaultCDefensorVotoShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultCDefensorVotoShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the cDefensorVotoList where estatus equals to UPDATED_ESTATUS
        defaultCDefensorVotoShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus is not null
        defaultCDefensorVotoShouldBeFound("estatus.specified=true");

        // Get all the cDefensorVotoList where estatus is null
        defaultCDefensorVotoShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus is greater than or equal to DEFAULT_ESTATUS
        defaultCDefensorVotoShouldBeFound("estatus.greaterThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cDefensorVotoList where estatus is greater than or equal to (DEFAULT_ESTATUS + 1)
        defaultCDefensorVotoShouldNotBeFound("estatus.greaterThanOrEqual=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus is less than or equal to DEFAULT_ESTATUS
        defaultCDefensorVotoShouldBeFound("estatus.lessThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cDefensorVotoList where estatus is less than or equal to SMALLER_ESTATUS
        defaultCDefensorVotoShouldNotBeFound("estatus.lessThanOrEqual=" + SMALLER_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsLessThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus is less than DEFAULT_ESTATUS
        defaultCDefensorVotoShouldNotBeFound("estatus.lessThan=" + DEFAULT_ESTATUS);

        // Get all the cDefensorVotoList where estatus is less than (DEFAULT_ESTATUS + 1)
        defaultCDefensorVotoShouldBeFound("estatus.lessThan=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByEstatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where estatus is greater than DEFAULT_ESTATUS
        defaultCDefensorVotoShouldNotBeFound("estatus.greaterThan=" + DEFAULT_ESTATUS);

        // Get all the cDefensorVotoList where estatus is greater than SMALLER_ESTATUS
        defaultCDefensorVotoShouldBeFound("estatus.greaterThan=" + SMALLER_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado equals to DEFAULT_BORRADO
        defaultCDefensorVotoShouldBeFound("borrado.equals=" + DEFAULT_BORRADO);

        // Get all the cDefensorVotoList where borrado equals to UPDATED_BORRADO
        defaultCDefensorVotoShouldNotBeFound("borrado.equals=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado not equals to DEFAULT_BORRADO
        defaultCDefensorVotoShouldNotBeFound("borrado.notEquals=" + DEFAULT_BORRADO);

        // Get all the cDefensorVotoList where borrado not equals to UPDATED_BORRADO
        defaultCDefensorVotoShouldBeFound("borrado.notEquals=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsInShouldWork() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado in DEFAULT_BORRADO or UPDATED_BORRADO
        defaultCDefensorVotoShouldBeFound("borrado.in=" + DEFAULT_BORRADO + "," + UPDATED_BORRADO);

        // Get all the cDefensorVotoList where borrado equals to UPDATED_BORRADO
        defaultCDefensorVotoShouldNotBeFound("borrado.in=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado is not null
        defaultCDefensorVotoShouldBeFound("borrado.specified=true");

        // Get all the cDefensorVotoList where borrado is null
        defaultCDefensorVotoShouldNotBeFound("borrado.specified=false");
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado is greater than or equal to DEFAULT_BORRADO
        defaultCDefensorVotoShouldBeFound("borrado.greaterThanOrEqual=" + DEFAULT_BORRADO);

        // Get all the cDefensorVotoList where borrado is greater than or equal to (DEFAULT_BORRADO + 1)
        defaultCDefensorVotoShouldNotBeFound("borrado.greaterThanOrEqual=" + (DEFAULT_BORRADO + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado is less than or equal to DEFAULT_BORRADO
        defaultCDefensorVotoShouldBeFound("borrado.lessThanOrEqual=" + DEFAULT_BORRADO);

        // Get all the cDefensorVotoList where borrado is less than or equal to SMALLER_BORRADO
        defaultCDefensorVotoShouldNotBeFound("borrado.lessThanOrEqual=" + SMALLER_BORRADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsLessThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado is less than DEFAULT_BORRADO
        defaultCDefensorVotoShouldNotBeFound("borrado.lessThan=" + DEFAULT_BORRADO);

        // Get all the cDefensorVotoList where borrado is less than (DEFAULT_BORRADO + 1)
        defaultCDefensorVotoShouldBeFound("borrado.lessThan=" + (DEFAULT_BORRADO + 1));
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByBorradoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        // Get all the cDefensorVotoList where borrado is greater than DEFAULT_BORRADO
        defaultCDefensorVotoShouldNotBeFound("borrado.greaterThan=" + DEFAULT_BORRADO);

        // Get all the cDefensorVotoList where borrado is greater than SMALLER_BORRADO
        defaultCDefensorVotoShouldBeFound("borrado.greaterThan=" + SMALLER_BORRADO);
    }

    @Test
    @Transactional
    void getAllCDefensorVotosByClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);
        CCliente cliente = CClienteResourceIT.createEntity(em);
        em.persist(cliente);
        em.flush();
        cDefensorVoto.setCliente(cliente);
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);
        Long clienteId = cliente.getId();

        // Get all the cDefensorVotoList where cliente equals to clienteId
        defaultCDefensorVotoShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the cDefensorVotoList where cliente equals to (clienteId + 1)
        defaultCDefensorVotoShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCDefensorVotoShouldBeFound(String filter) throws Exception {
        restCDefensorVotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cDefensorVoto.getId().intValue())))
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
        restCDefensorVotoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCDefensorVotoShouldNotBeFound(String filter) throws Exception {
        restCDefensorVotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCDefensorVotoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCDefensorVoto() throws Exception {
        // Get the cDefensorVoto
        restCDefensorVotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCDefensorVoto() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();

        // Update the cDefensorVoto
        CDefensorVoto updatedCDefensorVoto = cDefensorVotoRepository.findById(cDefensorVoto.getId()).get();
        // Disconnect from session so that the updates on updatedCDefensorVoto are not directly saved in db
        em.detach(updatedCDefensorVoto);
        updatedCDefensorVoto
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
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(updatedCDefensorVoto);

        restCDefensorVotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cDefensorVotoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
        CDefensorVoto testCDefensorVoto = cDefensorVotoList.get(cDefensorVotoList.size() - 1);
        assertThat(testCDefensorVoto.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testCDefensorVoto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCDefensorVoto.getSegundoNombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testCDefensorVoto.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testCDefensorVoto.getSegundoMaterno()).isEqualTo(UPDATED_SEGUNDO_MATERNO);
        assertThat(testCDefensorVoto.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testCDefensorVoto.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCDefensorVoto.getClaveElector()).isEqualTo(UPDATED_CLAVE_ELECTOR);
        assertThat(testCDefensorVoto.getSeccionElectoral()).isEqualTo(UPDATED_SECCION_ELECTORAL);
        assertThat(testCDefensorVoto.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testCDefensorVoto.getNumExt()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testCDefensorVoto.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testCDefensorVoto.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testCDefensorVoto.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testCDefensorVoto.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCDefensorVoto.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCDefensorVoto.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void putNonExistingCDefensorVoto() throws Exception {
        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();
        cDefensorVoto.setId(count.incrementAndGet());

        // Create the CDefensorVoto
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCDefensorVotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cDefensorVotoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCDefensorVoto() throws Exception {
        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();
        cDefensorVoto.setId(count.incrementAndGet());

        // Create the CDefensorVoto
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCDefensorVotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCDefensorVoto() throws Exception {
        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();
        cDefensorVoto.setId(count.incrementAndGet());

        // Create the CDefensorVoto
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCDefensorVotoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCDefensorVotoWithPatch() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();

        // Update the cDefensorVoto using partial update
        CDefensorVoto partialUpdatedCDefensorVoto = new CDefensorVoto();
        partialUpdatedCDefensorVoto.setId(cDefensorVoto.getId());

        partialUpdatedCDefensorVoto
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .nombre(UPDATED_NOMBRE)
            .segundoMaterno(UPDATED_SEGUNDO_MATERNO)
            .claveElector(UPDATED_CLAVE_ELECTOR)
            .seccionElectoral(UPDATED_SECCION_ELECTORAL)
            .estatus(UPDATED_ESTATUS)
            .borrado(UPDATED_BORRADO);

        restCDefensorVotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCDefensorVoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCDefensorVoto))
            )
            .andExpect(status().isOk());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
        CDefensorVoto testCDefensorVoto = cDefensorVotoList.get(cDefensorVotoList.size() - 1);
        assertThat(testCDefensorVoto.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testCDefensorVoto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCDefensorVoto.getSegundoNombre()).isEqualTo(DEFAULT_SEGUNDO_NOMBRE);
        assertThat(testCDefensorVoto.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testCDefensorVoto.getSegundoMaterno()).isEqualTo(UPDATED_SEGUNDO_MATERNO);
        assertThat(testCDefensorVoto.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testCDefensorVoto.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCDefensorVoto.getClaveElector()).isEqualTo(UPDATED_CLAVE_ELECTOR);
        assertThat(testCDefensorVoto.getSeccionElectoral()).isEqualTo(UPDATED_SECCION_ELECTORAL);
        assertThat(testCDefensorVoto.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testCDefensorVoto.getNumExt()).isEqualTo(DEFAULT_NUM_EXT);
        assertThat(testCDefensorVoto.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testCDefensorVoto.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testCDefensorVoto.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testCDefensorVoto.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCDefensorVoto.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCDefensorVoto.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void fullUpdateCDefensorVotoWithPatch() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();

        // Update the cDefensorVoto using partial update
        CDefensorVoto partialUpdatedCDefensorVoto = new CDefensorVoto();
        partialUpdatedCDefensorVoto.setId(cDefensorVoto.getId());

        partialUpdatedCDefensorVoto
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

        restCDefensorVotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCDefensorVoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCDefensorVoto))
            )
            .andExpect(status().isOk());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
        CDefensorVoto testCDefensorVoto = cDefensorVotoList.get(cDefensorVotoList.size() - 1);
        assertThat(testCDefensorVoto.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testCDefensorVoto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCDefensorVoto.getSegundoNombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testCDefensorVoto.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testCDefensorVoto.getSegundoMaterno()).isEqualTo(UPDATED_SEGUNDO_MATERNO);
        assertThat(testCDefensorVoto.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testCDefensorVoto.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCDefensorVoto.getClaveElector()).isEqualTo(UPDATED_CLAVE_ELECTOR);
        assertThat(testCDefensorVoto.getSeccionElectoral()).isEqualTo(UPDATED_SECCION_ELECTORAL);
        assertThat(testCDefensorVoto.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testCDefensorVoto.getNumExt()).isEqualTo(UPDATED_NUM_EXT);
        assertThat(testCDefensorVoto.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testCDefensorVoto.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testCDefensorVoto.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testCDefensorVoto.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCDefensorVoto.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCDefensorVoto.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void patchNonExistingCDefensorVoto() throws Exception {
        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();
        cDefensorVoto.setId(count.incrementAndGet());

        // Create the CDefensorVoto
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCDefensorVotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cDefensorVotoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCDefensorVoto() throws Exception {
        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();
        cDefensorVoto.setId(count.incrementAndGet());

        // Create the CDefensorVoto
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCDefensorVotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCDefensorVoto() throws Exception {
        int databaseSizeBeforeUpdate = cDefensorVotoRepository.findAll().size();
        cDefensorVoto.setId(count.incrementAndGet());

        // Create the CDefensorVoto
        CDefensorVotoDTO cDefensorVotoDTO = cDefensorVotoMapper.toDto(cDefensorVoto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCDefensorVotoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cDefensorVotoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CDefensorVoto in the database
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCDefensorVoto() throws Exception {
        // Initialize the database
        cDefensorVotoRepository.saveAndFlush(cDefensorVoto);

        int databaseSizeBeforeDelete = cDefensorVotoRepository.findAll().size();

        // Delete the cDefensorVoto
        restCDefensorVotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, cDefensorVoto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CDefensorVoto> cDefensorVotoList = cDefensorVotoRepository.findAll();
        assertThat(cDefensorVotoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
