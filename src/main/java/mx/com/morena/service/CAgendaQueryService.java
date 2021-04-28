package mx.com.morena.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import mx.com.morena.domain.*; // for static metamodels
import mx.com.morena.domain.CAgenda;
import mx.com.morena.repository.CAgendaRepository;
import mx.com.morena.service.criteria.CAgendaCriteria;
import mx.com.morena.service.dto.CAgendaDTO;
import mx.com.morena.service.mapper.CAgendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CAgenda} entities in the database.
 * The main input is a {@link CAgendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CAgendaDTO} or a {@link Page} of {@link CAgendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CAgendaQueryService extends QueryService<CAgenda> {

    private final Logger log = LoggerFactory.getLogger(CAgendaQueryService.class);

    private final CAgendaRepository cAgendaRepository;

    private final CAgendaMapper cAgendaMapper;

    public CAgendaQueryService(CAgendaRepository cAgendaRepository, CAgendaMapper cAgendaMapper) {
        this.cAgendaRepository = cAgendaRepository;
        this.cAgendaMapper = cAgendaMapper;
    }

    /**
     * Return a {@link List} of {@link CAgendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CAgendaDTO> findByCriteria(CAgendaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CAgenda> specification = createSpecification(criteria);
        return cAgendaMapper.toDto(cAgendaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CAgendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CAgendaDTO> findByCriteria(CAgendaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CAgenda> specification = createSpecification(criteria);
        return cAgendaRepository.findAll(specification, page).map(cAgendaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CAgendaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CAgenda> specification = createSpecification(criteria);
        return cAgendaRepository.count(specification);
    }

    /**
     * Function to convert {@link CAgendaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CAgenda> createSpecification(CAgendaCriteria criteria) {
        Specification<CAgenda> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CAgenda_.id));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), CAgenda_.direccion));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), CAgenda_.fecha));
            }
            if (criteria.getHora() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHora(), CAgenda_.hora));
            }
            if (criteria.getEncargado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEncargado(), CAgenda_.encargado));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstatus(), CAgenda_.estatus));
            }
        }
        return specification;
    }
}
