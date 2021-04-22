package mx.com.morena.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.com.morena.repository.CDefensorVotoRepository;
import mx.com.morena.service.CDefensorVotoQueryService;
import mx.com.morena.service.CDefensorVotoService;
import mx.com.morena.service.criteria.CDefensorVotoCriteria;
import mx.com.morena.service.dto.CDefensorVotoDTO;
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
 * REST controller for managing {@link mx.com.morena.domain.CDefensorVoto}.
 */
@RestController
@RequestMapping("/api")
public class CDefensorVotoResource {

    private final Logger log = LoggerFactory.getLogger(CDefensorVotoResource.class);

    private static final String ENTITY_NAME = "cDefensorVoto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CDefensorVotoService cDefensorVotoService;

    private final CDefensorVotoRepository cDefensorVotoRepository;

    private final CDefensorVotoQueryService cDefensorVotoQueryService;

    public CDefensorVotoResource(
        CDefensorVotoService cDefensorVotoService,
        CDefensorVotoRepository cDefensorVotoRepository,
        CDefensorVotoQueryService cDefensorVotoQueryService
    ) {
        this.cDefensorVotoService = cDefensorVotoService;
        this.cDefensorVotoRepository = cDefensorVotoRepository;
        this.cDefensorVotoQueryService = cDefensorVotoQueryService;
    }

    /**
     * {@code POST  /c-defensor-votos} : Create a new cDefensorVoto.
     *
     * @param cDefensorVotoDTO the cDefensorVotoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cDefensorVotoDTO, or with status {@code 400 (Bad Request)} if the cDefensorVoto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-defensor-votos")
    public ResponseEntity<CDefensorVotoDTO> createCDefensorVoto(@Valid @RequestBody CDefensorVotoDTO cDefensorVotoDTO)
        throws URISyntaxException {
        log.debug("REST request to save CDefensorVoto : {}", cDefensorVotoDTO);
        if (cDefensorVotoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cDefensorVoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CDefensorVotoDTO result = cDefensorVotoService.save(cDefensorVotoDTO);
        return ResponseEntity
            .created(new URI("/api/c-defensor-votos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-defensor-votos/:id} : Updates an existing cDefensorVoto.
     *
     * @param id the id of the cDefensorVotoDTO to save.
     * @param cDefensorVotoDTO the cDefensorVotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cDefensorVotoDTO,
     * or with status {@code 400 (Bad Request)} if the cDefensorVotoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cDefensorVotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-defensor-votos/{id}")
    public ResponseEntity<CDefensorVotoDTO> updateCDefensorVoto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CDefensorVotoDTO cDefensorVotoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CDefensorVoto : {}, {}", id, cDefensorVotoDTO);
        if (cDefensorVotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cDefensorVotoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cDefensorVotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CDefensorVotoDTO result = cDefensorVotoService.save(cDefensorVotoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cDefensorVotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /c-defensor-votos/:id} : Partial updates given fields of an existing cDefensorVoto, field will ignore if it is null
     *
     * @param id the id of the cDefensorVotoDTO to save.
     * @param cDefensorVotoDTO the cDefensorVotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cDefensorVotoDTO,
     * or with status {@code 400 (Bad Request)} if the cDefensorVotoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cDefensorVotoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cDefensorVotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/c-defensor-votos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CDefensorVotoDTO> partialUpdateCDefensorVoto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CDefensorVotoDTO cDefensorVotoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CDefensorVoto partially : {}, {}", id, cDefensorVotoDTO);
        if (cDefensorVotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cDefensorVotoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cDefensorVotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CDefensorVotoDTO> result = cDefensorVotoService.partialUpdate(cDefensorVotoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cDefensorVotoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /c-defensor-votos} : get all the cDefensorVotos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cDefensorVotos in body.
     */
    @GetMapping("/c-defensor-votos")
    public ResponseEntity<List<CDefensorVotoDTO>> getAllCDefensorVotos(CDefensorVotoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CDefensorVotos by criteria: {}", criteria);
        Page<CDefensorVotoDTO> page = cDefensorVotoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-defensor-votos/count} : count all the cDefensorVotos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-defensor-votos/count")
    public ResponseEntity<Long> countCDefensorVotos(CDefensorVotoCriteria criteria) {
        log.debug("REST request to count CDefensorVotos by criteria: {}", criteria);
        return ResponseEntity.ok().body(cDefensorVotoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-defensor-votos/:id} : get the "id" cDefensorVoto.
     *
     * @param id the id of the cDefensorVotoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cDefensorVotoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-defensor-votos/{id}")
    public ResponseEntity<CDefensorVotoDTO> getCDefensorVoto(@PathVariable Long id) {
        log.debug("REST request to get CDefensorVoto : {}", id);
        Optional<CDefensorVotoDTO> cDefensorVotoDTO = cDefensorVotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cDefensorVotoDTO);
    }

    /**
     * {@code DELETE  /c-defensor-votos/:id} : delete the "id" cDefensorVoto.
     *
     * @param id the id of the cDefensorVotoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-defensor-votos/{id}")
    public ResponseEntity<Void> deleteCDefensorVoto(@PathVariable Long id) {
        log.debug("REST request to delete CDefensorVoto : {}", id);
        cDefensorVotoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
