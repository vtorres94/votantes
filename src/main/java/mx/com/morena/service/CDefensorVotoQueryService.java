package mx.com.morena.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import mx.com.morena.domain.*; // for static metamodels
import mx.com.morena.domain.CDefensorVoto;
import mx.com.morena.repository.CDefensorVotoRepository;
import mx.com.morena.service.criteria.CDefensorVotoCriteria;
import mx.com.morena.service.dto.CDefensorVotoDTO;
import mx.com.morena.service.mapper.CDefensorVotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CDefensorVoto} entities in the database.
 * The main input is a {@link CDefensorVotoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CDefensorVotoDTO} or a {@link Page} of {@link CDefensorVotoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CDefensorVotoQueryService extends QueryService<CDefensorVoto> {

    private final Logger log = LoggerFactory.getLogger(CDefensorVotoQueryService.class);

    private final CDefensorVotoRepository cDefensorVotoRepository;

    private final CDefensorVotoMapper cDefensorVotoMapper;

    public CDefensorVotoQueryService(CDefensorVotoRepository cDefensorVotoRepository, CDefensorVotoMapper cDefensorVotoMapper) {
        this.cDefensorVotoRepository = cDefensorVotoRepository;
        this.cDefensorVotoMapper = cDefensorVotoMapper;
    }

    /**
     * Return a {@link List} of {@link CDefensorVotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CDefensorVotoDTO> findByCriteria(CDefensorVotoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CDefensorVoto> specification = createSpecification(criteria);
        return cDefensorVotoMapper.toDto(cDefensorVotoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CDefensorVotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CDefensorVotoDTO> findByCriteria(CDefensorVotoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CDefensorVoto> specification = createSpecification(criteria);
        return cDefensorVotoRepository.findAll(specification, page).map(cDefensorVotoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CDefensorVotoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CDefensorVoto> specification = createSpecification(criteria);
        return cDefensorVotoRepository.count(specification);
    }

    /**
     * Function to convert {@link CDefensorVotoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CDefensorVoto> createSpecification(CDefensorVotoCriteria criteria) {
        Specification<CDefensorVoto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CDefensorVoto_.id));
            }
            if (criteria.getNombreCompleto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreCompleto(), CDefensorVoto_.nombreCompleto));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), CDefensorVoto_.nombre));
            }
            if (criteria.getSegundoNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSegundoNombre(), CDefensorVoto_.segundoNombre));
            }
            if (criteria.getApellidoPaterno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellidoPaterno(), CDefensorVoto_.apellidoPaterno));
            }
            if (criteria.getSegundoMaterno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSegundoMaterno(), CDefensorVoto_.segundoMaterno));
            }
            if (criteria.getEdad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEdad(), CDefensorVoto_.edad));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), CDefensorVoto_.telefono));
            }
            if (criteria.getClaveElector() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClaveElector(), CDefensorVoto_.claveElector));
            }
            if (criteria.getSeccionElectoral() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeccionElectoral(), CDefensorVoto_.seccionElectoral));
            }
            if (criteria.getCalle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalle(), CDefensorVoto_.calle));
            }
            if (criteria.getNumExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumExt(), CDefensorVoto_.numExt));
            }
            if (criteria.getColonia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColonia(), CDefensorVoto_.colonia));
            }
            if (criteria.getCp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCp(), CDefensorVoto_.cp));
            }
            if (criteria.getMunicipio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMunicipio(), CDefensorVoto_.municipio));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), CDefensorVoto_.estado));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstatus(), CDefensorVoto_.estatus));
            }
            if (criteria.getBorrado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBorrado(), CDefensorVoto_.borrado));
            }
            if (criteria.getClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteId(),
                            root -> root.join(CDefensorVoto_.cliente, JoinType.LEFT).get(CCliente_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
