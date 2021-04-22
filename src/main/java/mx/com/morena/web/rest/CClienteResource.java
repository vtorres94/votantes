package mx.com.morena.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.com.morena.repository.CClienteRepository;
import mx.com.morena.service.CClienteQueryService;
import mx.com.morena.service.CClienteService;
import mx.com.morena.service.criteria.CClienteCriteria;
import mx.com.morena.service.dto.CClienteDTO;
import mx.com.morena.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mx.com.morena.domain.CCliente}.
 */
@RestController
@RequestMapping("/api")
public class CClienteResource {

    private final Logger log = LoggerFactory.getLogger(CClienteResource.class);

    private static final String ENTITY_NAME = "cCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CClienteService cClienteService;

    private final CClienteRepository cClienteRepository;

    private final CClienteQueryService cClienteQueryService;

    public CClienteResource(
        CClienteService cClienteService,
        CClienteRepository cClienteRepository,
        CClienteQueryService cClienteQueryService
    ) {
        this.cClienteService = cClienteService;
        this.cClienteRepository = cClienteRepository;
        this.cClienteQueryService = cClienteQueryService;
    }

    /**
     * {@code POST  /c-clientes} : Create a new cCliente.
     *
     * @param cClienteDTO the cClienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cClienteDTO, or with status {@code 400 (Bad Request)} if the cCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-clientes")
    public ResponseEntity<CClienteDTO> createCCliente(@Valid @RequestBody CClienteDTO cClienteDTO) throws URISyntaxException {
        log.debug("REST request to save CCliente : {}", cClienteDTO);
        if (cClienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CClienteDTO result = cClienteService.save(cClienteDTO);
        return ResponseEntity
            .created(new URI("/api/c-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-clientes/:id} : Updates an existing cCliente.
     *
     * @param id the id of the cClienteDTO to save.
     * @param cClienteDTO the cClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cClienteDTO,
     * or with status {@code 400 (Bad Request)} if the cClienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-clientes/{id}")
    public ResponseEntity<CClienteDTO> updateCCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CClienteDTO cClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CCliente : {}, {}", id, cClienteDTO);
        if (cClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CClienteDTO result = cClienteService.save(cClienteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cClienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /c-clientes/:id} : Partial updates given fields of an existing cCliente, field will ignore if it is null
     *
     * @param id the id of the cClienteDTO to save.
     * @param cClienteDTO the cClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cClienteDTO,
     * or with status {@code 400 (Bad Request)} if the cClienteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cClienteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/c-clientes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CClienteDTO> partialUpdateCCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CClienteDTO cClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CCliente partially : {}, {}", id, cClienteDTO);
        if (cClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CClienteDTO> result = cClienteService.partialUpdate(cClienteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cClienteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /c-clientes} : get all the cClientes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cClientes in body.
     */
    @GetMapping("/c-clientes")
    public ResponseEntity<List<CClienteDTO>> getAllCDefensorVotos(
        CClienteCriteria criteria,
        Pageable pageable,
        @RequestParam MultiValueMap<String, String> queryParams,
        UriComponentsBuilder uriBuilder
    ) {
        log.debug("REST request to get CClientes by criteria: {}", criteria);
        Page<CClienteDTO> page = cClienteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-clientes/count} : count all the cClientes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-clientes/count")
    public ResponseEntity<Long> countCClientes(CClienteCriteria criteria) {
        log.debug("REST request to count CClientes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cClienteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-clientes/:id} : get the "id" cCliente.
     *
     * @param id the id of the cClienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cClienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-clientes/{id}")
    public ResponseEntity<CClienteDTO> getCCliente(@PathVariable Long id) {
        log.debug("REST request to get CCliente : {}", id);
        Optional<CClienteDTO> cClienteDTO = cClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cClienteDTO);
    }

    /**
     * {@code DELETE  /c-clientes/:id} : delete the "id" cCliente.
     *
     * @param id the id of the cClienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-clientes/{id}")
    public ResponseEntity<Void> deleteCCliente(@PathVariable Long id) {
        log.debug("REST request to delete CCliente : {}", id);
        cClienteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
