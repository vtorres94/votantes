package mx.com.morena.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.com.morena.repository.CAgendaRepository;
import mx.com.morena.service.CAgendaQueryService;
import mx.com.morena.service.CAgendaService;
import mx.com.morena.service.criteria.CAgendaCriteria;
import mx.com.morena.service.dto.CAgendaDTO;
import mx.com.morena.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mx.com.morena.domain.CAgenda}.
 */
@RestController
@RequestMapping("/api")
public class CAgendaResource {

    private final Logger log = LoggerFactory.getLogger(CAgendaResource.class);

    private static final String ENTITY_NAME = "cAgenda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CAgendaService cAgendaService;

    private final CAgendaRepository cAgendaRepository;

    private final CAgendaQueryService cAgendaQueryService;

    public CAgendaResource(CAgendaService cAgendaService, CAgendaRepository cAgendaRepository, CAgendaQueryService cAgendaQueryService) {
        this.cAgendaService = cAgendaService;
        this.cAgendaRepository = cAgendaRepository;
        this.cAgendaQueryService = cAgendaQueryService;
    }

    /**
     * {@code POST  /c-agenda} : Create a new cAgenda.
     *
     * @param cAgendaDTO the cAgendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cAgendaDTO, or with status {@code 400 (Bad Request)} if the cAgenda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-agenda")
    public ResponseEntity<CAgendaDTO> createCAgenda(@Valid @RequestBody CAgendaDTO cAgendaDTO) throws URISyntaxException {
        log.debug("REST request to save CAgenda : {}", cAgendaDTO);
        if (cAgendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cAgenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CAgendaDTO result = cAgendaService.save(cAgendaDTO);
        return ResponseEntity
            .created(new URI("/api/c-agenda/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-agenda/:id} : Updates an existing cAgenda.
     *
     * @param id the id of the cAgendaDTO to save.
     * @param cAgendaDTO the cAgendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cAgendaDTO,
     * or with status {@code 400 (Bad Request)} if the cAgendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cAgendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-agenda/{id}")
    public ResponseEntity<CAgendaDTO> updateCAgenda(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CAgendaDTO cAgendaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CAgenda : {}, {}", id, cAgendaDTO);
        if (cAgendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cAgendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cAgendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CAgendaDTO result = cAgendaService.save(cAgendaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cAgendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /c-agenda/:id} : Partial updates given fields of an existing cAgenda, field will ignore if it is null
     *
     * @param id the id of the cAgendaDTO to save.
     * @param cAgendaDTO the cAgendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cAgendaDTO,
     * or with status {@code 400 (Bad Request)} if the cAgendaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cAgendaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cAgendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/c-agenda/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CAgendaDTO> partialUpdateCAgenda(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CAgendaDTO cAgendaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CAgenda partially : {}, {}", id, cAgendaDTO);
        if (cAgendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cAgendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cAgendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CAgendaDTO> result = cAgendaService.partialUpdate(cAgendaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cAgendaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /c-agenda} : get all the cAgenda.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cAgenda in body.
     */
    @GetMapping("/c-agenda")
    public ResponseEntity<List<CAgendaDTO>> getAllCAgenda(CAgendaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CAgenda by criteria: {}", criteria);
        Page<CAgendaDTO> page = cAgendaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-agenda/count} : count all the cAgenda.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-agenda/count")
    public ResponseEntity<Long> countCAgenda(CAgendaCriteria criteria) {
        log.debug("REST request to count CAgenda by criteria: {}", criteria);
        return ResponseEntity.ok().body(cAgendaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-agenda/:id} : get the "id" cAgenda.
     *
     * @param id the id of the cAgendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cAgendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-agenda/{id}")
    public ResponseEntity<CAgendaDTO> getCAgenda(@PathVariable Long id) {
        log.debug("REST request to get CAgenda : {}", id);
        Optional<CAgendaDTO> cAgendaDTO = cAgendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cAgendaDTO);
    }

    /**
     * {@code DELETE  /c-agenda/:id} : delete the "id" cAgenda.
     *
     * @param id the id of the cAgendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-agenda/{id}")
    public ResponseEntity<Void> deleteCAgenda(@PathVariable Long id) {
        log.debug("REST request to delete CAgenda : {}", id);
        cAgendaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
