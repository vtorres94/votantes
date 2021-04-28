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
import mx.com.morena.domain.CAgenda;
import mx.com.morena.repository.CAgendaRepository;
import mx.com.morena.service.criteria.CAgendaCriteria;
import mx.com.morena.service.dto.CAgendaDTO;
import mx.com.morena.service.mapper.CAgendaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CAgendaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CAgendaResourceIT {

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HORA = "AAAAA";
    private static final String UPDATED_HORA = "BBBBB";

    private static final String DEFAULT_ENCARGADO = "AAAAAAAAAA";
    private static final String UPDATED_ENCARGADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTATUS = 1;
    private static final Integer UPDATED_ESTATUS = 0;
    private static final Integer SMALLER_ESTATUS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/c-agenda";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CAgendaRepository cAgendaRepository;

    @Autowired
    private CAgendaMapper cAgendaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCAgendaMockMvc;

    private CAgenda cAgenda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAgenda createEntity(EntityManager em) {
        CAgenda cAgenda = new CAgenda()
            .direccion(DEFAULT_DIRECCION)
            .fecha(DEFAULT_FECHA)
            .hora(DEFAULT_HORA)
            .encargado(DEFAULT_ENCARGADO)
            .estatus(DEFAULT_ESTATUS);
        return cAgenda;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAgenda createUpdatedEntity(EntityManager em) {
        CAgenda cAgenda = new CAgenda()
            .direccion(UPDATED_DIRECCION)
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .encargado(UPDATED_ENCARGADO)
            .estatus(UPDATED_ESTATUS);
        return cAgenda;
    }

    @BeforeEach
    public void initTest() {
        cAgenda = createEntity(em);
    }

    @Test
    @Transactional
    void createCAgenda() throws Exception {
        int databaseSizeBeforeCreate = cAgendaRepository.findAll().size();
        // Create the CAgenda
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);
        restCAgendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cAgendaDTO)))
            .andExpect(status().isCreated());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeCreate + 1);
        CAgenda testCAgenda = cAgendaList.get(cAgendaList.size() - 1);
        assertThat(testCAgenda.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testCAgenda.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCAgenda.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testCAgenda.getEncargado()).isEqualTo(DEFAULT_ENCARGADO);
        assertThat(testCAgenda.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    void createCAgendaWithExistingId() throws Exception {
        // Create the CAgenda with an existing ID
        cAgenda.setId(1L);
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);

        int databaseSizeBeforeCreate = cAgendaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCAgendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cAgendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCAgenda() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList
        restCAgendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAgenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA)))
            .andExpect(jsonPath("$.[*].encargado").value(hasItem(DEFAULT_ENCARGADO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));
    }

    @Test
    @Transactional
    void getCAgenda() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get the cAgenda
        restCAgendaMockMvc
            .perform(get(ENTITY_API_URL_ID, cAgenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cAgenda.getId().intValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.hora").value(DEFAULT_HORA))
            .andExpect(jsonPath("$.encargado").value(DEFAULT_ENCARGADO))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS));
    }

    @Test
    @Transactional
    void getCAgendaByIdFiltering() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        Long id = cAgenda.getId();

        defaultCAgendaShouldBeFound("id.equals=" + id);
        defaultCAgendaShouldNotBeFound("id.notEquals=" + id);

        defaultCAgendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCAgendaShouldNotBeFound("id.greaterThan=" + id);

        defaultCAgendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCAgendaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCAgendaByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where direccion equals to DEFAULT_DIRECCION
        defaultCAgendaShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the cAgendaList where direccion equals to UPDATED_DIRECCION
        defaultCAgendaShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllCAgendaByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where direccion not equals to DEFAULT_DIRECCION
        defaultCAgendaShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the cAgendaList where direccion not equals to UPDATED_DIRECCION
        defaultCAgendaShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllCAgendaByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultCAgendaShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the cAgendaList where direccion equals to UPDATED_DIRECCION
        defaultCAgendaShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllCAgendaByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where direccion is not null
        defaultCAgendaShouldBeFound("direccion.specified=true");

        // Get all the cAgendaList where direccion is null
        defaultCAgendaShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    void getAllCAgendaByDireccionContainsSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where direccion contains DEFAULT_DIRECCION
        defaultCAgendaShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the cAgendaList where direccion contains UPDATED_DIRECCION
        defaultCAgendaShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllCAgendaByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where direccion does not contain DEFAULT_DIRECCION
        defaultCAgendaShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the cAgendaList where direccion does not contain UPDATED_DIRECCION
        defaultCAgendaShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllCAgendaByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where fecha equals to DEFAULT_FECHA
        defaultCAgendaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the cAgendaList where fecha equals to UPDATED_FECHA
        defaultCAgendaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCAgendaByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where fecha not equals to DEFAULT_FECHA
        defaultCAgendaShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the cAgendaList where fecha not equals to UPDATED_FECHA
        defaultCAgendaShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCAgendaByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultCAgendaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the cAgendaList where fecha equals to UPDATED_FECHA
        defaultCAgendaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCAgendaByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where fecha is not null
        defaultCAgendaShouldBeFound("fecha.specified=true");

        // Get all the cAgendaList where fecha is null
        defaultCAgendaShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    void getAllCAgendaByHoraIsEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where hora equals to DEFAULT_HORA
        defaultCAgendaShouldBeFound("hora.equals=" + DEFAULT_HORA);

        // Get all the cAgendaList where hora equals to UPDATED_HORA
        defaultCAgendaShouldNotBeFound("hora.equals=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    void getAllCAgendaByHoraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where hora not equals to DEFAULT_HORA
        defaultCAgendaShouldNotBeFound("hora.notEquals=" + DEFAULT_HORA);

        // Get all the cAgendaList where hora not equals to UPDATED_HORA
        defaultCAgendaShouldBeFound("hora.notEquals=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    void getAllCAgendaByHoraIsInShouldWork() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where hora in DEFAULT_HORA or UPDATED_HORA
        defaultCAgendaShouldBeFound("hora.in=" + DEFAULT_HORA + "," + UPDATED_HORA);

        // Get all the cAgendaList where hora equals to UPDATED_HORA
        defaultCAgendaShouldNotBeFound("hora.in=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    void getAllCAgendaByHoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where hora is not null
        defaultCAgendaShouldBeFound("hora.specified=true");

        // Get all the cAgendaList where hora is null
        defaultCAgendaShouldNotBeFound("hora.specified=false");
    }

    @Test
    @Transactional
    void getAllCAgendaByHoraContainsSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where hora contains DEFAULT_HORA
        defaultCAgendaShouldBeFound("hora.contains=" + DEFAULT_HORA);

        // Get all the cAgendaList where hora contains UPDATED_HORA
        defaultCAgendaShouldNotBeFound("hora.contains=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    void getAllCAgendaByHoraNotContainsSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where hora does not contain DEFAULT_HORA
        defaultCAgendaShouldNotBeFound("hora.doesNotContain=" + DEFAULT_HORA);

        // Get all the cAgendaList where hora does not contain UPDATED_HORA
        defaultCAgendaShouldBeFound("hora.doesNotContain=" + UPDATED_HORA);
    }

    @Test
    @Transactional
    void getAllCAgendaByEncargadoIsEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where encargado equals to DEFAULT_ENCARGADO
        defaultCAgendaShouldBeFound("encargado.equals=" + DEFAULT_ENCARGADO);

        // Get all the cAgendaList where encargado equals to UPDATED_ENCARGADO
        defaultCAgendaShouldNotBeFound("encargado.equals=" + UPDATED_ENCARGADO);
    }

    @Test
    @Transactional
    void getAllCAgendaByEncargadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where encargado not equals to DEFAULT_ENCARGADO
        defaultCAgendaShouldNotBeFound("encargado.notEquals=" + DEFAULT_ENCARGADO);

        // Get all the cAgendaList where encargado not equals to UPDATED_ENCARGADO
        defaultCAgendaShouldBeFound("encargado.notEquals=" + UPDATED_ENCARGADO);
    }

    @Test
    @Transactional
    void getAllCAgendaByEncargadoIsInShouldWork() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where encargado in DEFAULT_ENCARGADO or UPDATED_ENCARGADO
        defaultCAgendaShouldBeFound("encargado.in=" + DEFAULT_ENCARGADO + "," + UPDATED_ENCARGADO);

        // Get all the cAgendaList where encargado equals to UPDATED_ENCARGADO
        defaultCAgendaShouldNotBeFound("encargado.in=" + UPDATED_ENCARGADO);
    }

    @Test
    @Transactional
    void getAllCAgendaByEncargadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where encargado is not null
        defaultCAgendaShouldBeFound("encargado.specified=true");

        // Get all the cAgendaList where encargado is null
        defaultCAgendaShouldNotBeFound("encargado.specified=false");
    }

    @Test
    @Transactional
    void getAllCAgendaByEncargadoContainsSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where encargado contains DEFAULT_ENCARGADO
        defaultCAgendaShouldBeFound("encargado.contains=" + DEFAULT_ENCARGADO);

        // Get all the cAgendaList where encargado contains UPDATED_ENCARGADO
        defaultCAgendaShouldNotBeFound("encargado.contains=" + UPDATED_ENCARGADO);
    }

    @Test
    @Transactional
    void getAllCAgendaByEncargadoNotContainsSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where encargado does not contain DEFAULT_ENCARGADO
        defaultCAgendaShouldNotBeFound("encargado.doesNotContain=" + DEFAULT_ENCARGADO);

        // Get all the cAgendaList where encargado does not contain UPDATED_ENCARGADO
        defaultCAgendaShouldBeFound("encargado.doesNotContain=" + UPDATED_ENCARGADO);
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus equals to DEFAULT_ESTATUS
        defaultCAgendaShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the cAgendaList where estatus equals to UPDATED_ESTATUS
        defaultCAgendaShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus not equals to DEFAULT_ESTATUS
        defaultCAgendaShouldNotBeFound("estatus.notEquals=" + DEFAULT_ESTATUS);

        // Get all the cAgendaList where estatus not equals to UPDATED_ESTATUS
        defaultCAgendaShouldBeFound("estatus.notEquals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultCAgendaShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the cAgendaList where estatus equals to UPDATED_ESTATUS
        defaultCAgendaShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus is not null
        defaultCAgendaShouldBeFound("estatus.specified=true");

        // Get all the cAgendaList where estatus is null
        defaultCAgendaShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus is greater than or equal to DEFAULT_ESTATUS
        defaultCAgendaShouldBeFound("estatus.greaterThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cAgendaList where estatus is greater than or equal to (DEFAULT_ESTATUS + 1)
        defaultCAgendaShouldNotBeFound("estatus.greaterThanOrEqual=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus is less than or equal to DEFAULT_ESTATUS
        defaultCAgendaShouldBeFound("estatus.lessThanOrEqual=" + DEFAULT_ESTATUS);

        // Get all the cAgendaList where estatus is less than or equal to SMALLER_ESTATUS
        defaultCAgendaShouldNotBeFound("estatus.lessThanOrEqual=" + SMALLER_ESTATUS);
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsLessThanSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus is less than DEFAULT_ESTATUS
        defaultCAgendaShouldNotBeFound("estatus.lessThan=" + DEFAULT_ESTATUS);

        // Get all the cAgendaList where estatus is less than (DEFAULT_ESTATUS + 1)
        defaultCAgendaShouldBeFound("estatus.lessThan=" + (DEFAULT_ESTATUS + 1));
    }

    @Test
    @Transactional
    void getAllCAgendaByEstatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        // Get all the cAgendaList where estatus is greater than DEFAULT_ESTATUS
        defaultCAgendaShouldNotBeFound("estatus.greaterThan=" + DEFAULT_ESTATUS);

        // Get all the cAgendaList where estatus is greater than SMALLER_ESTATUS
        defaultCAgendaShouldBeFound("estatus.greaterThan=" + SMALLER_ESTATUS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCAgendaShouldBeFound(String filter) throws Exception {
        restCAgendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAgenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA)))
            .andExpect(jsonPath("$.[*].encargado").value(hasItem(DEFAULT_ENCARGADO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));

        // Check, that the count call also returns 1
        restCAgendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCAgendaShouldNotBeFound(String filter) throws Exception {
        restCAgendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCAgendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCAgenda() throws Exception {
        // Get the cAgenda
        restCAgendaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCAgenda() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();

        // Update the cAgenda
        CAgenda updatedCAgenda = cAgendaRepository.findById(cAgenda.getId()).get();
        // Disconnect from session so that the updates on updatedCAgenda are not directly saved in db
        em.detach(updatedCAgenda);
        updatedCAgenda
            .direccion(UPDATED_DIRECCION)
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .encargado(UPDATED_ENCARGADO)
            .estatus(UPDATED_ESTATUS);
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(updatedCAgenda);

        restCAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cAgendaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cAgendaDTO))
            )
            .andExpect(status().isOk());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
        CAgenda testCAgenda = cAgendaList.get(cAgendaList.size() - 1);
        assertThat(testCAgenda.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testCAgenda.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCAgenda.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testCAgenda.getEncargado()).isEqualTo(UPDATED_ENCARGADO);
        assertThat(testCAgenda.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void putNonExistingCAgenda() throws Exception {
        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();
        cAgenda.setId(count.incrementAndGet());

        // Create the CAgenda
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cAgendaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cAgendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCAgenda() throws Exception {
        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();
        cAgenda.setId(count.incrementAndGet());

        // Create the CAgenda
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cAgendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCAgenda() throws Exception {
        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();
        cAgenda.setId(count.incrementAndGet());

        // Create the CAgenda
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCAgendaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cAgendaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCAgendaWithPatch() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();

        // Update the cAgenda using partial update
        CAgenda partialUpdatedCAgenda = new CAgenda();
        partialUpdatedCAgenda.setId(cAgenda.getId());

        partialUpdatedCAgenda.direccion(UPDATED_DIRECCION).fecha(UPDATED_FECHA).hora(UPDATED_HORA);

        restCAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCAgenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCAgenda))
            )
            .andExpect(status().isOk());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
        CAgenda testCAgenda = cAgendaList.get(cAgendaList.size() - 1);
        assertThat(testCAgenda.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testCAgenda.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCAgenda.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testCAgenda.getEncargado()).isEqualTo(DEFAULT_ENCARGADO);
        assertThat(testCAgenda.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    void fullUpdateCAgendaWithPatch() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();

        // Update the cAgenda using partial update
        CAgenda partialUpdatedCAgenda = new CAgenda();
        partialUpdatedCAgenda.setId(cAgenda.getId());

        partialUpdatedCAgenda
            .direccion(UPDATED_DIRECCION)
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .encargado(UPDATED_ENCARGADO)
            .estatus(UPDATED_ESTATUS);

        restCAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCAgenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCAgenda))
            )
            .andExpect(status().isOk());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
        CAgenda testCAgenda = cAgendaList.get(cAgendaList.size() - 1);
        assertThat(testCAgenda.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testCAgenda.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCAgenda.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testCAgenda.getEncargado()).isEqualTo(UPDATED_ENCARGADO);
        assertThat(testCAgenda.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCAgenda() throws Exception {
        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();
        cAgenda.setId(count.incrementAndGet());

        // Create the CAgenda
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cAgendaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cAgendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCAgenda() throws Exception {
        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();
        cAgenda.setId(count.incrementAndGet());

        // Create the CAgenda
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cAgendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCAgenda() throws Exception {
        int databaseSizeBeforeUpdate = cAgendaRepository.findAll().size();
        cAgenda.setId(count.incrementAndGet());

        // Create the CAgenda
        CAgendaDTO cAgendaDTO = cAgendaMapper.toDto(cAgenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cAgendaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CAgenda in the database
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCAgenda() throws Exception {
        // Initialize the database
        cAgendaRepository.saveAndFlush(cAgenda);

        int databaseSizeBeforeDelete = cAgendaRepository.findAll().size();

        // Delete the cAgenda
        restCAgendaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cAgenda.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CAgenda> cAgendaList = cAgendaRepository.findAll();
        assertThat(cAgendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
