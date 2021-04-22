package mx.com.morena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mx.com.morena.IntegrationTest;
import mx.com.morena.domain.CCliente;
import mx.com.morena.repository.CClienteRepository;
import mx.com.morena.service.criteria.CClienteCriteria;
import mx.com.morena.service.dto.CClienteDTO;
import mx.com.morena.service.mapper.CClienteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CClienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CClienteResourceIT {

    private static final String DEFAULT_CLAVE_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANIO_ELECTORAL = 9999;
    private static final Integer UPDATED_ANIO_ELECTORAL = 9998;
    private static final Integer SMALLER_ANIO_ELECTORAL = 9999 - 1;

    private static final Long DEFAULT_ID_USUARIO_CREACION = 999999999999L;
    private static final Long UPDATED_ID_USUARIO_CREACION = 999999999998L;
    private static final Long SMALLER_ID_USUARIO_CREACION = 999999999999L - 1L;

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ID_USUARIO_ACTUALIZACION = 999999999999L;
    private static final Long UPDATED_ID_USUARIO_ACTUALIZACION = 999999999998L;
    private static final Long SMALLER_ID_USUARIO_ACTUALIZACION = 999999999999L - 1L;

    private static final Instant DEFAULT_FECHA_ACTUALIZACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ACTUALIZACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTATUS = 1;
    private static final Integer UPDATED_ESTATUS = 0;
    private static final Integer SMALLER_ESTATUS = 1 - 1;

    private static final Integer DEFAULT_BORRADO = 1;
    private static final Integer UPDATED_BORRADO = 0;
    private static final Integer SMALLER_BORRADO = 1 - 1;

    private static final String ENTITY_API_URL = "/api/c-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CClienteRepository cClienteRepository;

    @Autowired
    private CClienteMapper cClienteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCClienteMockMvc;

    private CCliente cCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCliente createEntity(EntityManager em) {
        CCliente cCliente = new CCliente()
            .claveCliente(DEFAULT_CLAVE_CLIENTE)
            .cliente(DEFAULT_CLIENTE)
            .anioElectoral(DEFAULT_ANIO_ELECTORAL)
            .idUsuarioCreacion(DEFAULT_ID_USUARIO_CREACION)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .idUsuarioActualizacion(DEFAULT_ID_USUARIO_ACTUALIZACION)
            .fechaActualizacion(DEFAULT_FECHA_ACTUALIZACION)
            .notas(DEFAULT_NOTAS)
            .estatus(DEFAULT_ESTATUS)
            .borrado(DEFAULT_BORRADO);
        return cCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCliente createUpdatedEntity(EntityManager em) {
        CCliente cCliente = new CCliente()
            .claveCliente(UPDATED_CLAVE_CLIENTE)
            .cliente(UPDATED_CLIENTE)
            .anioElectoral(UPDATED_ANIO_ELECTORAL)
            .idUsuarioCreacion(UPDATED_ID_USUARIO_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .idUsuarioActualizacion(UPDATED_ID_USUARIO_ACTUALIZACION)
            .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
            .notas(UPDATED_NOTAS)
            .estatus(UPDATED_ESTATUS)
            .borrado(UPDATED_BORRADO);
        return cCliente;
    }

    @BeforeEach
    public void initTest() {
        cCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createCCliente() throws Exception {
        int databaseSizeBeforeCreate = cClienteRepository.findAll().size();
        // Create the CCliente
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);
        restCClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cClienteDTO)))
            .andExpect(status().isCreated());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeCreate + 1);
        CCliente testCCliente = cClienteList.get(cClienteList.size() - 1);
        assertThat(testCCliente.getClaveCliente()).isEqualTo(DEFAULT_CLAVE_CLIENTE);
        assertThat(testCCliente.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testCCliente.getAnioElectoral()).isEqualTo(DEFAULT_ANIO_ELECTORAL);
        assertThat(testCCliente.getIdUsuarioCreacion()).isEqualTo(DEFAULT_ID_USUARIO_CREACION);
        assertThat(testCCliente.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testCCliente.getIdUsuarioActualizacion()).isEqualTo(DEFAULT_ID_USUARIO_ACTUALIZACION);
        assertThat(testCCliente.getFechaActualizacion()).isEqualTo(DEFAULT_FECHA_ACTUALIZACION);
        assertThat(testCCliente.getNotas()).isEqualTo(DEFAULT_NOTAS);
        assertThat(testCCliente.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testCCliente.getBorrado()).isEqualTo(DEFAULT_BORRADO);
    }

    @Test
    @Transactional
    void createCClienteWithExistingId() throws Exception {
        // Create the CCliente with an existing ID
        cCliente.setId(1L);
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        int databaseSizeBeforeCreate = cClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cClienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdUsuarioCreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cClienteRepository.findAll().size();
        // set the field null
        cCliente.setIdUsuarioCreacion(null);

        // Create the CCliente, which fails.
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        restCClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cClienteDTO)))
            .andExpect(status().isBadRequest());

        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaCreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cClienteRepository.findAll().size();
        // set the field null
        cCliente.setFechaCreacion(null);

        // Create the CCliente, which fails.
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        restCClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cClienteDTO)))
            .andExpect(status().isBadRequest());

        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cClienteRepository.findAll().size();
        // set the field null
        cCliente.setEstatus(null);

        // Create the CCliente, which fails.
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        restCClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cClienteDTO)))
            .andExpect(status().isBadRequest());

        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBorradoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cClienteRepository.findAll().size();
        // set the field null
        cCliente.setBorrado(null);

        // Create the CCliente, which fails.
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        restCClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cClienteDTO)))
            .andExpect(status().isBadRequest());

        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCClientes() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList
        restCClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].claveCliente").value(hasItem(DEFAULT_CLAVE_CLIENTE)))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].anioElectoral").value(hasItem(DEFAULT_ANIO_ELECTORAL)))
            .andExpect(jsonPath("$.[*].idUsuarioCreacion").value(hasItem(DEFAULT_ID_USUARIO_CREACION.intValue())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].idUsuarioActualizacion").value(hasItem(DEFAULT_ID_USUARIO_ACTUALIZACION.intValue())))
            .andExpect(jsonPath("$.[*].fechaActualizacion").value(hasItem(DEFAULT_FECHA_ACTUALIZACION.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)))
            .andExpect(jsonPath("$.[*].borrado").value(hasItem(DEFAULT_BORRADO)));
    }

    @Test
    @Transactional
    void getCCliente() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get the cCliente
        restCClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, cCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCliente.getId().intValue()))
            .andExpect(jsonPath("$.claveCliente").value(DEFAULT_CLAVE_CLIENTE))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.anioElectoral").value(DEFAULT_ANIO_ELECTORAL))
            .andExpect(jsonPath("$.idUsuarioCreacion").value(DEFAULT_ID_USUARIO_CREACION.intValue()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.idUsuarioActualizacion").value(DEFAULT_ID_USUARIO_ACTUALIZACION.intValue()))
            .andExpect(jsonPath("$.fechaActualizacion").value(DEFAULT_FECHA_ACTUALIZACION.toString()))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS))
            .andExpect(jsonPath("$.borrado").value(DEFAULT_BORRADO));
    }

    @Test
    @Transactional
    void getCClientesByIdFiltering() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        Long id = cCliente.getId();

        defaultCClienteShouldBeFound("id.equals=" + id);
        defaultCClienteShouldNotBeFound("id.notEquals=" + id);

        defaultCClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultCClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCClienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCClientesByClaveClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where claveCliente equals to DEFAULT_CLAVE_CLIENTE
        defaultCClienteShouldBeFound("claveCliente.equals=" + DEFAULT_CLAVE_CLIENTE);

        // Get all the cClienteList where claveCliente equals to UPDATED_CLAVE_CLIENTE
        defaultCClienteShouldNotBeFound("claveCliente.equals=" + UPDATED_CLAVE_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClaveClienteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where claveCliente not equals to DEFAULT_CLAVE_CLIENTE
        defaultCClienteShouldNotBeFound("claveCliente.notEquals=" + DEFAULT_CLAVE_CLIENTE);

        // Get all the cClienteList where claveCliente not equals to UPDATED_CLAVE_CLIENTE
        defaultCClienteShouldBeFound("claveCliente.notEquals=" + UPDATED_CLAVE_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClaveClienteIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where claveCliente in DEFAULT_CLAVE_CLIENTE or UPDATED_CLAVE_CLIENTE
        defaultCClienteShouldBeFound("claveCliente.in=" + DEFAULT_CLAVE_CLIENTE + "," + UPDATED_CLAVE_CLIENTE);

        // Get all the cClienteList where claveCliente equals to UPDATED_CLAVE_CLIENTE
        defaultCClienteShouldNotBeFound("claveCliente.in=" + UPDATED_CLAVE_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClaveClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where claveCliente is not null
        defaultCClienteShouldBeFound("claveCliente.specified=true");

        // Get all the cClienteList where claveCliente is null
        defaultCClienteShouldNotBeFound("claveCliente.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByClaveClienteContainsSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where claveCliente contains DEFAULT_CLAVE_CLIENTE
        defaultCClienteShouldBeFound("claveCliente.contains=" + DEFAULT_CLAVE_CLIENTE);

        // Get all the cClienteList where claveCliente contains UPDATED_CLAVE_CLIENTE
        defaultCClienteShouldNotBeFound("claveCliente.contains=" + UPDATED_CLAVE_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClaveClienteNotContainsSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where claveCliente does not contain DEFAULT_CLAVE_CLIENTE
        defaultCClienteShouldNotBeFound("claveCliente.doesNotContain=" + DEFAULT_CLAVE_CLIENTE);

        // Get all the cClienteList where claveCliente does not contain UPDATED_CLAVE_CLIENTE
        defaultCClienteShouldBeFound("claveCliente.doesNotContain=" + UPDATED_CLAVE_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where cliente equals to DEFAULT_CLIENTE
        defaultCClienteShouldBeFound("cliente.equals=" + DEFAULT_CLIENTE);

        // Get all the cClienteList where cliente equals to UPDATED_CLIENTE
        defaultCClienteShouldNotBeFound("cliente.equals=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClienteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where cliente not equals to DEFAULT_CLIENTE
        defaultCClienteShouldNotBeFound("cliente.notEquals=" + DEFAULT_CLIENTE);

        // Get all the cClienteList where cliente not equals to UPDATED_CLIENTE
        defaultCClienteShouldBeFound("cliente.notEquals=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClienteIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where cliente in DEFAULT_CLIENTE or UPDATED_CLIENTE
        defaultCClienteShouldBeFound("cliente.in=" + DEFAULT_CLIENTE + "," + UPDATED_CLIENTE);

        // Get all the cClienteList where cliente equals to UPDATED_CLIENTE
        defaultCClienteShouldNotBeFound("cliente.in=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where cliente is not null
        defaultCClienteShouldBeFound("cliente.specified=true");

        // Get all the cClienteList where cliente is null
        defaultCClienteShouldNotBeFound("cliente.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByClienteContainsSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where cliente contains DEFAULT_CLIENTE
        defaultCClienteShouldBeFound("cliente.contains=" + DEFAULT_CLIENTE);

        // Get all the cClienteList where cliente contains UPDATED_CLIENTE
        defaultCClienteShouldNotBeFound("cliente.contains=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByClienteNotContainsSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where cliente does not contain DEFAULT_CLIENTE
        defaultCClienteShouldNotBeFound("cliente.doesNotContain=" + DEFAULT_CLIENTE);

        // Get all the cClienteList where cliente does not contain UPDATED_CLIENTE
        defaultCClienteShouldBeFound("cliente.doesNotContain=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral equals to DEFAULT_ANIO_ELECTORAL
        defaultCClienteShouldBeFound("anioElectoral.equals=" + DEFAULT_ANIO_ELECTORAL);

        // Get all the cClienteList where anioElectoral equals to UPDATED_ANIO_ELECTORAL
        defaultCClienteShouldNotBeFound("anioElectoral.equals=" + UPDATED_ANIO_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral not equals to DEFAULT_ANIO_ELECTORAL
        defaultCClienteShouldNotBeFound("anioElectoral.notEquals=" + DEFAULT_ANIO_ELECTORAL);

        // Get all the cClienteList where anioElectoral not equals to UPDATED_ANIO_ELECTORAL
        defaultCClienteShouldBeFound("anioElectoral.notEquals=" + UPDATED_ANIO_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral in DEFAULT_ANIO_ELECTORAL or UPDATED_ANIO_ELECTORAL
        defaultCClienteShouldBeFound("anioElectoral.in=" + DEFAULT_ANIO_ELECTORAL + "," + UPDATED_ANIO_ELECTORAL);

        // Get all the cClienteList where anioElectoral equals to UPDATED_ANIO_ELECTORAL
        defaultCClienteShouldNotBeFound("anioElectoral.in=" + UPDATED_ANIO_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral is not null
        defaultCClienteShouldBeFound("anioElectoral.specified=true");

        // Get all the cClienteList where anioElectoral is null
        defaultCClienteShouldNotBeFound("anioElectoral.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral is greater than or equal to DEFAULT_ANIO_ELECTORAL
        defaultCClienteShouldBeFound("anioElectoral.greaterThanOrEqual=" + DEFAULT_ANIO_ELECTORAL);

        // Get all the cClienteList where anioElectoral is greater than or equal to (DEFAULT_ANIO_ELECTORAL + 1)
        defaultCClienteShouldNotBeFound("anioElectoral.greaterThanOrEqual=" + (DEFAULT_ANIO_ELECTORAL + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral is less than or equal to DEFAULT_ANIO_ELECTORAL
        defaultCClienteShouldBeFound("anioElectoral.lessThanOrEqual=" + DEFAULT_ANIO_ELECTORAL);

        // Get all the cClienteList where anioElectoral is less than or equal to SMALLER_ANIO_ELECTORAL
        defaultCClienteShouldNotBeFound("anioElectoral.lessThanOrEqual=" + SMALLER_ANIO_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsLessThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral is less than DEFAULT_ANIO_ELECTORAL
        defaultCClienteShouldNotBeFound("anioElectoral.lessThan=" + DEFAULT_ANIO_ELECTORAL);

        // Get all the cClienteList where anioElectoral is less than (DEFAULT_ANIO_ELECTORAL + 1)
        defaultCClienteShouldBeFound("anioElectoral.lessThan=" + (DEFAULT_ANIO_ELECTORAL + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByAnioElectoralIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where anioElectoral is greater than DEFAULT_ANIO_ELECTORAL
        defaultCClienteShouldNotBeFound("anioElectoral.greaterThan=" + DEFAULT_ANIO_ELECTORAL);

        // Get all the cClienteList where anioElectoral is greater than SMALLER_ANIO_ELECTORAL
        defaultCClienteShouldBeFound("anioElectoral.greaterThan=" + SMALLER_ANIO_ELECTORAL);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion equals to DEFAULT_ID_USUARIO_CREACION
        defaultCClienteShouldBeFound("idUsuarioCreacion.equals=" + DEFAULT_ID_USUARIO_CREACION);

        // Get all the cClienteList where idUsuarioCreacion equals to UPDATED_ID_USUARIO_CREACION
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.equals=" + UPDATED_ID_USUARIO_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion not equals to DEFAULT_ID_USUARIO_CREACION
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.notEquals=" + DEFAULT_ID_USUARIO_CREACION);

        // Get all the cClienteList where idUsuarioCreacion not equals to UPDATED_ID_USUARIO_CREACION
        defaultCClienteShouldBeFound("idUsuarioCreacion.notEquals=" + UPDATED_ID_USUARIO_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion in DEFAULT_ID_USUARIO_CREACION or UPDATED_ID_USUARIO_CREACION
        defaultCClienteShouldBeFound("idUsuarioCreacion.in=" + DEFAULT_ID_USUARIO_CREACION + "," + UPDATED_ID_USUARIO_CREACION);

        // Get all the cClienteList where idUsuarioCreacion equals to UPDATED_ID_USUARIO_CREACION
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.in=" + UPDATED_ID_USUARIO_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion is not null
        defaultCClienteShouldBeFound("idUsuarioCreacion.specified=true");

        // Get all the cClienteList where idUsuarioCreacion is null
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion is greater than or equal to DEFAULT_ID_USUARIO_CREACION
        defaultCClienteShouldBeFound("idUsuarioCreacion.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_CREACION);

        // Get all the cClienteList where idUsuarioCreacion is greater than or equal to (DEFAULT_ID_USUARIO_CREACION + 1)
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.greaterThanOrEqual=" + (DEFAULT_ID_USUARIO_CREACION + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion is less than or equal to DEFAULT_ID_USUARIO_CREACION
        defaultCClienteShouldBeFound("idUsuarioCreacion.lessThanOrEqual=" + DEFAULT_ID_USUARIO_CREACION);

        // Get all the cClienteList where idUsuarioCreacion is less than or equal to SMALLER_ID_USUARIO_CREACION
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.lessThanOrEqual=" + SMALLER_ID_USUARIO_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsLessThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion is less than DEFAULT_ID_USUARIO_CREACION
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.lessThan=" + DEFAULT_ID_USUARIO_CREACION);

        // Get all the cClienteList where idUsuarioCreacion is less than (DEFAULT_ID_USUARIO_CREACION + 1)
        defaultCClienteShouldBeFound("idUsuarioCreacion.lessThan=" + (DEFAULT_ID_USUARIO_CREACION + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioCreacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioCreacion is greater than DEFAULT_ID_USUARIO_CREACION
        defaultCClienteShouldNotBeFound("idUsuarioCreacion.greaterThan=" + DEFAULT_ID_USUARIO_CREACION);

        // Get all the cClienteList where idUsuarioCreacion is greater than SMALLER_ID_USUARIO_CREACION
        defaultCClienteShouldBeFound("idUsuarioCreacion.greaterThan=" + SMALLER_ID_USUARIO_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaCreacionIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaCreacion equals to DEFAULT_FECHA_CREACION
        defaultCClienteShouldBeFound("fechaCreacion.equals=" + DEFAULT_FECHA_CREACION);

        // Get all the cClienteList where fechaCreacion equals to UPDATED_FECHA_CREACION
        defaultCClienteShouldNotBeFound("fechaCreacion.equals=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaCreacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaCreacion not equals to DEFAULT_FECHA_CREACION
        defaultCClienteShouldNotBeFound("fechaCreacion.notEquals=" + DEFAULT_FECHA_CREACION);

        // Get all the cClienteList where fechaCreacion not equals to UPDATED_FECHA_CREACION
        defaultCClienteShouldBeFound("fechaCreacion.notEquals=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaCreacionIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaCreacion in DEFAULT_FECHA_CREACION or UPDATED_FECHA_CREACION
        defaultCClienteShouldBeFound("fechaCreacion.in=" + DEFAULT_FECHA_CREACION + "," + UPDATED_FECHA_CREACION);

        // Get all the cClienteList where fechaCreacion equals to UPDATED_FECHA_CREACION
        defaultCClienteShouldNotBeFound("fechaCreacion.in=" + UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaCreacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaCreacion is not null
        defaultCClienteShouldBeFound("fechaCreacion.specified=true");

        // Get all the cClienteList where fechaCreacion is null
        defaultCClienteShouldNotBeFound("fechaCreacion.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion equals to DEFAULT_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldBeFound("idUsuarioActualizacion.equals=" + DEFAULT_ID_USUARIO_ACTUALIZACION);

        // Get all the cClienteList where idUsuarioActualizacion equals to UPDATED_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.equals=" + UPDATED_ID_USUARIO_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion not equals to DEFAULT_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.notEquals=" + DEFAULT_ID_USUARIO_ACTUALIZACION);

        // Get all the cClienteList where idUsuarioActualizacion not equals to UPDATED_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldBeFound("idUsuarioActualizacion.notEquals=" + UPDATED_ID_USUARIO_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion in DEFAULT_ID_USUARIO_ACTUALIZACION or UPDATED_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldBeFound(
            "idUsuarioActualizacion.in=" + DEFAULT_ID_USUARIO_ACTUALIZACION + "," + UPDATED_ID_USUARIO_ACTUALIZACION
        );

        // Get all the cClienteList where idUsuarioActualizacion equals to UPDATED_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.in=" + UPDATED_ID_USUARIO_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion is not null
        defaultCClienteShouldBeFound("idUsuarioActualizacion.specified=true");

        // Get all the cClienteList where idUsuarioActualizacion is null
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion is greater than or equal to DEFAULT_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldBeFound("idUsuarioActualizacion.greaterThanOrEqual=" + DEFAULT_ID_USUARIO_ACTUALIZACION);

        // Get all the cClienteList where idUsuarioActualizacion is greater than or equal to (DEFAULT_ID_USUARIO_ACTUALIZACION + 1)
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.greaterThanOrEqual=" + (DEFAULT_ID_USUARIO_ACTUALIZACION + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion is less than or equal to DEFAULT_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldBeFound("idUsuarioActualizacion.lessThanOrEqual=" + DEFAULT_ID_USUARIO_ACTUALIZACION);

        // Get all the cClienteList where idUsuarioActualizacion is less than or equal to SMALLER_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.lessThanOrEqual=" + SMALLER_ID_USUARIO_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsLessThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion is less than DEFAULT_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.lessThan=" + DEFAULT_ID_USUARIO_ACTUALIZACION);

        // Get all the cClienteList where idUsuarioActualizacion is less than (DEFAULT_ID_USUARIO_ACTUALIZACION + 1)
        defaultCClienteShouldBeFound("idUsuarioActualizacion.lessThan=" + (DEFAULT_ID_USUARIO_ACTUALIZACION + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByIdUsuarioActualizacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where idUsuarioActualizacion is greater than DEFAULT_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldNotBeFound("idUsuarioActualizacion.greaterThan=" + DEFAULT_ID_USUARIO_ACTUALIZACION);

        // Get all the cClienteList where idUsuarioActualizacion is greater than SMALLER_ID_USUARIO_ACTUALIZACION
        defaultCClienteShouldBeFound("idUsuarioActualizacion.greaterThan=" + SMALLER_ID_USUARIO_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaActualizacionIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaActualizacion equals to DEFAULT_FECHA_ACTUALIZACION
        defaultCClienteShouldBeFound("fechaActualizacion.equals=" + DEFAULT_FECHA_ACTUALIZACION);

        // Get all the cClienteList where fechaActualizacion equals to UPDATED_FECHA_ACTUALIZACION
        defaultCClienteShouldNotBeFound("fechaActualizacion.equals=" + UPDATED_FECHA_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaActualizacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaActualizacion not equals to DEFAULT_FECHA_ACTUALIZACION
        defaultCClienteShouldNotBeFound("fechaActualizacion.notEquals=" + DEFAULT_FECHA_ACTUALIZACION);

        // Get all the cClienteList where fechaActualizacion not equals to UPDATED_FECHA_ACTUALIZACION
        defaultCClienteShouldBeFound("fechaActualizacion.notEquals=" + UPDATED_FECHA_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaActualizacionIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaActualizacion in DEFAULT_FECHA_ACTUALIZACION or UPDATED_FECHA_ACTUALIZACION
        defaultCClienteShouldBeFound("fechaActualizacion.in=" + DEFAULT_FECHA_ACTUALIZACION + "," + UPDATED_FECHA_ACTUALIZACION);

        // Get all the cClienteList where fechaActualizacion equals to UPDATED_FECHA_ACTUALIZACION
        defaultCClienteShouldNotBeFound("fechaActualizacion.in=" + UPDATED_FECHA_ACTUALIZACION);
    }

    @Test
    @Transactional
    void getAllCClientesByFechaActualizacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where fechaActualizacion is not null
        defaultCClienteShouldBeFound("fechaActualizacion.specified=true");

        // Get all the cClienteList where fechaActualizacion is null
        defaultCClienteShouldNotBeFound("fechaActualizacion.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByNotasIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where notas equals to DEFAULT_NOTAS
        defaultCClienteShouldBeFound("notas.equals=" + DEFAULT_NOTAS);

        // Get all the cClienteList where notas equals to UPDATED_NOTAS
        defaultCClienteShouldNotBeFound("notas.equals=" + UPDATED_NOTAS);
    }

    @Test
    @Transactional
    void getAllCClientesByNotasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where notas not equals to DEFAULT_NOTAS
        defaultCClienteShouldNotBeFound("notas.notEquals=" + DEFAULT_NOTAS);

        // Get all the cClienteList where notas not equals to UPDATED_NOTAS
        defaultCClienteShouldBeFound("notas.notEquals=" + UPDATED_NOTAS);
    }

    @Test
    @Transactional
    void getAllCClientesByNotasIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where notas in DEFAULT_NOTAS or UPDATED_NOTAS
        defaultCClienteShouldBeFound("notas.in=" + DEFAULT_NOTAS + "," + UPDATED_NOTAS);

        // Get all the cClienteList where notas equals to UPDATED_NOTAS
        defaultCClienteShouldNotBeFound("notas.in=" + UPDATED_NOTAS);
    }

    @Test
    @Transactional
    void getAllCClientesByNotasIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where notas is not null
        defaultCClienteShouldBeFound("notas.specified=true");

        // Get all the cClienteList where notas is null
        defaultCClienteShouldNotBeFound("notas.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByNotasContainsSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where notas contains DEFAULT_NOTAS
        defaultCClienteShouldBeFound("notas.contains=" + DEFAULT_NOTAS);

        // Get all the cClienteList where notas contains UPDATED_NOTAS
        defaultCClienteShouldNotBeFound("notas.contains=" + UPDATED_NOTAS);
    }

    @Test
    @Transactional
    void getAllCClientesByNotasNotContainsSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where notas does not contain DEFAULT_NOTAS
        defaultCClienteShouldNotBeFound("notas.doesNotContain=" + DEFAULT_NOTAS);

        // Get all the cClienteList where notas does not contain UPDATED_NOTAS
        defaultCClienteShouldBeFound("notas.doesNotContain=" + UPDATED_NOTAS);
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus equals to DEFAULT_ESTATUS
        defaultCClienteShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the cClienteList where estatus equals to UPDATED_ESTATUS
        defaultCClienteShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus not equals to DEFAULT_ESTATUS
        defaultCClienteShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the cClienteList where estatus not equals to UPDATED_ESTATUS
        defaultCClienteShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultCClienteShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the cClienteList where estatus equals to UPDATED_ESTATUS
        defaultCClienteShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus is not null
        defaultCClienteShouldBeFound("estatus.specified=true");

        // Get all the cClienteList where estatus is null
        defaultCClienteShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus is greater than or equal to DEFAULT_ESTATUS
        defaultCClienteShouldBeFound("estatus.greaterThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cClienteList where estatus is greater than or equal to (DEFAULT_ESTATUS + 1)
        defaultCClienteShouldNotBeFound("estatus.greaterThanOrEqual=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus is less than or equal to DEFAULT_ESTATUS
        defaultCClienteShouldBeFound("estatus.lessThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cClienteList where estatus is less than or equal to SMALLER_ESTATUS
        defaultCClienteShouldNotBeFound("estatus.lessThanOrEqual=" + SMALLER_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsLessThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus is less than DEFAULT_ESTATUS
        defaultCClienteShouldNotBeFound("estatus.lessThan=" + DEFAULT_ESTATUS);

        // Get all the cClienteList where estatus is less than (DEFAULT_ESTATUS + 1)
        defaultCClienteShouldBeFound("estatus.lessThan=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByEstatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where estatus is greater than DEFAULT_ESTATUS
        defaultCClienteShouldNotBeFound("estatus.greaterThan=" + DEFAULT_ESTATUS);

        // Get all the cClienteList where estatus is greater than SMALLER_ESTATUS
        defaultCClienteShouldBeFound("estatus.greaterThan=" + SMALLER_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado equals to DEFAULT_BORRADO
        defaultCClienteShouldBeFound("borrado.equals=" + DEFAULT_BORRADO);

        // Get all the cClienteList where borrado equals to UPDATED_BORRADO
        defaultCClienteShouldNotBeFound("borrado.equals=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado not equals to DEFAULT_BORRADO
        defaultCClienteShouldNotBeFound("borrado.notEquals=" + DEFAULT_BORRADO);

        // Get all the cClienteList where borrado not equals to UPDATED_BORRADO
        defaultCClienteShouldBeFound("borrado.notEquals=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsInShouldWork() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado in DEFAULT_BORRADO or UPDATED_BORRADO
        defaultCClienteShouldBeFound("borrado.in=" + DEFAULT_BORRADO + "," + UPDATED_BORRADO);

        // Get all the cClienteList where borrado equals to UPDATED_BORRADO
        defaultCClienteShouldNotBeFound("borrado.in=" + UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado is not null
        defaultCClienteShouldBeFound("borrado.specified=true");

        // Get all the cClienteList where borrado is null
        defaultCClienteShouldNotBeFound("borrado.specified=false");
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado is greater than or equal to DEFAULT_BORRADO
        defaultCClienteShouldBeFound("borrado.greaterThanOrEqual=" + DEFAULT_BORRADO);

        // Get all the cClienteList where borrado is greater than or equal to (DEFAULT_BORRADO + 1)
        defaultCClienteShouldNotBeFound("borrado.greaterThanOrEqual=" + (DEFAULT_BORRADO + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado is less than or equal to DEFAULT_BORRADO
        defaultCClienteShouldBeFound("borrado.lessThanOrEqual=" + DEFAULT_BORRADO);

        // Get all the cClienteList where borrado is less than or equal to SMALLER_BORRADO
        defaultCClienteShouldNotBeFound("borrado.lessThanOrEqual=" + SMALLER_BORRADO);
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsLessThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado is less than DEFAULT_BORRADO
        defaultCClienteShouldNotBeFound("borrado.lessThan=" + DEFAULT_BORRADO);

        // Get all the cClienteList where borrado is less than (DEFAULT_BORRADO + 1)
        defaultCClienteShouldBeFound("borrado.lessThan=" + (DEFAULT_BORRADO + 1));
    }

    @Test
    @Transactional
    void getAllCClientesByBorradoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        // Get all the cClienteList where borrado is greater than DEFAULT_BORRADO
        defaultCClienteShouldNotBeFound("borrado.greaterThan=" + DEFAULT_BORRADO);

        // Get all the cClienteList where borrado is greater than SMALLER_BORRADO
        defaultCClienteShouldBeFound("borrado.greaterThan=" + SMALLER_BORRADO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCClienteShouldBeFound(String filter) throws Exception {
        restCClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].claveCliente").value(hasItem(DEFAULT_CLAVE_CLIENTE)))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].anioElectoral").value(hasItem(DEFAULT_ANIO_ELECTORAL)))
            .andExpect(jsonPath("$.[*].idUsuarioCreacion").value(hasItem(DEFAULT_ID_USUARIO_CREACION.intValue())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].idUsuarioActualizacion").value(hasItem(DEFAULT_ID_USUARIO_ACTUALIZACION.intValue())))
            .andExpect(jsonPath("$.[*].fechaActualizacion").value(hasItem(DEFAULT_FECHA_ACTUALIZACION.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)))
            .andExpect(jsonPath("$.[*].borrado").value(hasItem(DEFAULT_BORRADO)));

        // Check, that the count call also returns 1
        restCClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCClienteShouldNotBeFound(String filter) throws Exception {
        restCClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCCliente() throws Exception {
        // Get the cCliente
        restCClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCCliente() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();

        // Update the cCliente
        CCliente updatedCCliente = cClienteRepository.findById(cCliente.getId()).get();
        // Disconnect from session so that the updates on updatedCCliente are not directly saved in db
        em.detach(updatedCCliente);
        updatedCCliente
            .claveCliente(UPDATED_CLAVE_CLIENTE)
            .cliente(UPDATED_CLIENTE)
            .anioElectoral(UPDATED_ANIO_ELECTORAL)
            .idUsuarioCreacion(UPDATED_ID_USUARIO_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .idUsuarioActualizacion(UPDATED_ID_USUARIO_ACTUALIZACION)
            .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
            .notas(UPDATED_NOTAS)
            .estatus(UPDATED_ESTATUS)
            .borrado(UPDATED_BORRADO);
        CClienteDTO cClienteDTO = cClienteMapper.toDto(updatedCCliente);

        restCClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cClienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
        CCliente testCCliente = cClienteList.get(cClienteList.size() - 1);
        assertThat(testCCliente.getClaveCliente()).isEqualTo(UPDATED_CLAVE_CLIENTE);
        assertThat(testCCliente.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testCCliente.getAnioElectoral()).isEqualTo(UPDATED_ANIO_ELECTORAL);
        assertThat(testCCliente.getIdUsuarioCreacion()).isEqualTo(UPDATED_ID_USUARIO_CREACION);
        assertThat(testCCliente.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testCCliente.getIdUsuarioActualizacion()).isEqualTo(UPDATED_ID_USUARIO_ACTUALIZACION);
        assertThat(testCCliente.getFechaActualizacion()).isEqualTo(UPDATED_FECHA_ACTUALIZACION);
        assertThat(testCCliente.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testCCliente.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCCliente.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void putNonExistingCCliente() throws Exception {
        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();
        cCliente.setId(count.incrementAndGet());

        // Create the CCliente
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCCliente() throws Exception {
        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();
        cCliente.setId(count.incrementAndGet());

        // Create the CCliente
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCCliente() throws Exception {
        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();
        cCliente.setId(count.incrementAndGet());

        // Create the CCliente
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCClienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cClienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCClienteWithPatch() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();

        // Update the cCliente using partial update
        CCliente partialUpdatedCCliente = new CCliente();
        partialUpdatedCCliente.setId(cCliente.getId());

        partialUpdatedCCliente
            .claveCliente(UPDATED_CLAVE_CLIENTE)
            .cliente(UPDATED_CLIENTE)
            .anioElectoral(UPDATED_ANIO_ELECTORAL)
            .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
            .estatus(UPDATED_ESTATUS);

        restCClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCliente))
            )
            .andExpect(status().isOk());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
        CCliente testCCliente = cClienteList.get(cClienteList.size() - 1);
        assertThat(testCCliente.getClaveCliente()).isEqualTo(UPDATED_CLAVE_CLIENTE);
        assertThat(testCCliente.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testCCliente.getAnioElectoral()).isEqualTo(UPDATED_ANIO_ELECTORAL);
        assertThat(testCCliente.getIdUsuarioCreacion()).isEqualTo(DEFAULT_ID_USUARIO_CREACION);
        assertThat(testCCliente.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testCCliente.getIdUsuarioActualizacion()).isEqualTo(DEFAULT_ID_USUARIO_ACTUALIZACION);
        assertThat(testCCliente.getFechaActualizacion()).isEqualTo(UPDATED_FECHA_ACTUALIZACION);
        assertThat(testCCliente.getNotas()).isEqualTo(DEFAULT_NOTAS);
        assertThat(testCCliente.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCCliente.getBorrado()).isEqualTo(DEFAULT_BORRADO);
    }

    @Test
    @Transactional
    void fullUpdateCClienteWithPatch() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();

        // Update the cCliente using partial update
        CCliente partialUpdatedCCliente = new CCliente();
        partialUpdatedCCliente.setId(cCliente.getId());

        partialUpdatedCCliente
            .claveCliente(UPDATED_CLAVE_CLIENTE)
            .cliente(UPDATED_CLIENTE)
            .anioElectoral(UPDATED_ANIO_ELECTORAL)
            .idUsuarioCreacion(UPDATED_ID_USUARIO_CREACION)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .idUsuarioActualizacion(UPDATED_ID_USUARIO_ACTUALIZACION)
            .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
            .notas(UPDATED_NOTAS)
            .estatus(UPDATED_ESTATUS)
            .borrado(UPDATED_BORRADO);

        restCClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCCliente))
            )
            .andExpect(status().isOk());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
        CCliente testCCliente = cClienteList.get(cClienteList.size() - 1);
        assertThat(testCCliente.getClaveCliente()).isEqualTo(UPDATED_CLAVE_CLIENTE);
        assertThat(testCCliente.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testCCliente.getAnioElectoral()).isEqualTo(UPDATED_ANIO_ELECTORAL);
        assertThat(testCCliente.getIdUsuarioCreacion()).isEqualTo(UPDATED_ID_USUARIO_CREACION);
        assertThat(testCCliente.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testCCliente.getIdUsuarioActualizacion()).isEqualTo(UPDATED_ID_USUARIO_ACTUALIZACION);
        assertThat(testCCliente.getFechaActualizacion()).isEqualTo(UPDATED_FECHA_ACTUALIZACION);
        assertThat(testCCliente.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testCCliente.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testCCliente.getBorrado()).isEqualTo(UPDATED_BORRADO);
    }

    @Test
    @Transactional
    void patchNonExistingCCliente() throws Exception {
        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();
        cCliente.setId(count.incrementAndGet());

        // Create the CCliente
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cClienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCCliente() throws Exception {
        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();
        cCliente.setId(count.incrementAndGet());

        // Create the CCliente
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCCliente() throws Exception {
        int databaseSizeBeforeUpdate = cClienteRepository.findAll().size();
        cCliente.setId(count.incrementAndGet());

        // Create the CCliente
        CClienteDTO cClienteDTO = cClienteMapper.toDto(cCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCClienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CCliente in the database
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCCliente() throws Exception {
        // Initialize the database
        cClienteRepository.saveAndFlush(cCliente);

        int databaseSizeBeforeDelete = cClienteRepository.findAll().size();

        // Delete the cCliente
        restCClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, cCliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCliente> cClienteList = cClienteRepository.findAll();
        assertThat(cClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
