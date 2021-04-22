package mx.com.morena.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.com.morena.repository.CVotanteRepository;
import mx.com.morena.service.CVotanteQueryService;
import mx.com.morena.service.CVotanteService;
import mx.com.morena.service.criteria.CVotanteCriteria;
import mx.com.morena.service.dto.CVotanteDTO;
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
 * REST controller for managing {@link mx.com.morena.domain.CVotante}.
 */
@RestController
@RequestMapping("/api")
public class CVotanteResource {

    private final Logger log = LoggerFactory.getLogger(CVotanteResource.class);

    private static final String ENTITY_NAME = "cVotante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVotanteService cVotanteService;

    private final CVotanteRepository cVotanteRepository;

    private final CVotanteQueryService cVotanteQueryService;

    public CVotanteResource(
        CVotanteService cVotanteService,
        CVotanteRepository cVotanteRepository,
        CVotanteQueryService cVotanteQueryService
    ) {
        this.cVotanteService = cVotanteService;
        this.cVotanteRepository = cVotanteRepository;
        this.cVotanteQueryService = cVotanteQueryService;
    }

    /**
     * {@code POST  /c-votantes} : Create a new cVotante.
     *
     * @param cVotanteDTO the cVotanteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVotanteDTO, or with status {@code 400 (Bad Request)} if the cVotante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-votantes")
    public ResponseEntity<CVotanteDTO> createCVotante(@Valid @RequestBody CVotanteDTO cVotanteDTO) throws URISyntaxException {
        log.debug("REST request to save CVotante : {}", cVotanteDTO);
        if (cVotanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVotante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVotanteDTO result = cVotanteService.save(cVotanteDTO);
        return ResponseEntity
            .created(new URI("/api/c-votantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-votantes/:id} : Updates an existing cVotante.
     *
     * @param id the id of the cVotanteDTO to save.
     * @param cVotanteDTO the cVotanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVotanteDTO,
     * or with status {@code 400 (Bad Request)} if the cVotanteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVotanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-votantes/{id}")
    public ResponseEntity<CVotanteDTO> updateCVotante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CVotanteDTO cVotanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CVotante : {}, {}", id, cVotanteDTO);
        if (cVotanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cVotanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cVotanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CVotanteDTO result = cVotanteService.save(cVotanteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cVotanteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /c-votantes/:id} : Partial updates given fields of an existing cVotante, field will ignore if it is null
     *
     * @param id the id of the cVotanteDTO to save.
     * @param cVotanteDTO the cVotanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVotanteDTO,
     * or with status {@code 400 (Bad Request)} if the cVotanteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cVotanteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cVotanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/c-votantes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CVotanteDTO> partialUpdateCVotante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CVotanteDTO cVotanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CVotante partially : {}, {}", id, cVotanteDTO);
        if (cVotanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cVotanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cVotanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CVotanteDTO> result = cVotanteService.partialUpdate(cVotanteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cVotanteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /c-votantes} : get all the cVotantes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVotantes in body.
     */
    @GetMapping("/c-votantes")
    public ResponseEntity<List<CVotanteDTO>> getAllCVotantes(CVotanteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVotantes by criteria: {}", criteria);
        Page<CVotanteDTO> page = cVotanteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-votantes/count} : count all the cVotantes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-votantes/count")
    public ResponseEntity<Long> countCVotantes(CVotanteCriteria criteria) {
        log.debug("REST request to count CVotantes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVotanteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-votantes/:id} : get the "id" cVotante.
     *
     * @param id the id of the cVotanteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVotanteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-votantes/{id}")
    public ResponseEntity<CVotanteDTO> getCVotante(@PathVariable Long id) {
        log.debug("REST request to get CVotante : {}", id);
        Optional<CVotanteDTO> cVotanteDTO = cVotanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVotanteDTO);
    }

    /**
     * {@code DELETE  /c-votantes/:id} : delete the "id" cVotante.
     *
     * @param id the id of the cVotanteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-votantes/{id}")
    public ResponseEntity<Void> deleteCVotante(@PathVariable Long id) {
        log.debug("REST request to delete CVotante : {}", id);
        cVotanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
