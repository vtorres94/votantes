package mx.com.morena.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import mx.com.morena.domain.*; // for static metamodels
import mx.com.morena.domain.CVotante;
import mx.com.morena.repository.CVotanteRepository;
import mx.com.morena.service.criteria.CVotanteCriteria;
import mx.com.morena.service.dto.CVotanteDTO;
import mx.com.morena.service.mapper.CVotanteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CVotante} entities in the database.
 * The main input is a {@link CVotanteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVotanteDTO} or a {@link Page} of {@link CVotanteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVotanteQueryService extends QueryService<CVotante> {

    private final Logger log = LoggerFactory.getLogger(CVotanteQueryService.class);

    private final CVotanteRepository cVotanteRepository;

    private final CVotanteMapper cVotanteMapper;

    public CVotanteQueryService(CVotanteRepository cVotanteRepository, CVotanteMapper cVotanteMapper) {
        this.cVotanteRepository = cVotanteRepository;
        this.cVotanteMapper = cVotanteMapper;
    }

    /**
     * Return a {@link List} of {@link CVotanteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVotanteDTO> findByCriteria(CVotanteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVotante> specification = createSpecification(criteria);
        return cVotanteMapper.toDto(cVotanteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVotanteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVotanteDTO> findByCriteria(CVotanteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVotante> specification = createSpecification(criteria);
        return cVotanteRepository.findAll(specification, page).map(cVotanteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVotanteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVotante> specification = createSpecification(criteria);
        return cVotanteRepository.count(specification);
    }

    /**
     * Function to convert {@link CVotanteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVotante> createSpecification(CVotanteCriteria criteria) {
        Specification<CVotante> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVotante_.id));
            }
            if (criteria.getNombreCompleto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreCompleto(), CVotante_.nombreCompleto));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), CVotante_.nombre));
            }
            if (criteria.getSegundoNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSegundoNombre(), CVotante_.segundoNombre));
            }
            if (criteria.getApellidoPaterno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellidoPaterno(), CVotante_.apellidoPaterno));
            }
            if (criteria.getSegundoMaterno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSegundoMaterno(), CVotante_.segundoMaterno));
            }
            if (criteria.getEdad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEdad(), CVotante_.edad));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), CVotante_.telefono));
            }
            if (criteria.getClaveElector() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClaveElector(), CVotante_.claveElector));
            }
            if (criteria.getSeccionElectoral() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeccionElectoral(), CVotante_.seccionElectoral));
            }
            if (criteria.getCalle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalle(), CVotante_.calle));
            }
            if (criteria.getNumExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumExt(), CVotante_.numExt));
            }
            if (criteria.getColonia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColonia(), CVotante_.colonia));
            }
            if (criteria.getCp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCp(), CVotante_.cp));
            }
            if (criteria.getMunicipio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMunicipio(), CVotante_.municipio));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), CVotante_.estado));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstatus(), CVotante_.estatus));
            }
            if (criteria.getBorrado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBorrado(), CVotante_.borrado));
            }
            if (criteria.getClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClienteId(), root -> root.join(CVotante_.cliente, JoinType.LEFT).get(CCliente_.id))
                    );
            }
            if (criteria.getDefensorVotoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDefensorVotoId(),
                            root -> root.join(CVotante_.defensorVoto, JoinType.LEFT).get(CDefensorVoto_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
