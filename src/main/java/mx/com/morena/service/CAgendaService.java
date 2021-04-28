package mx.com.morena.service;

import java.util.Optional;
import mx.com.morena.domain.CAgenda;
import mx.com.morena.repository.CAgendaRepository;
import mx.com.morena.service.dto.CAgendaDTO;
import mx.com.morena.service.mapper.CAgendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CAgenda}.
 */
@Service
@Transactional
public class CAgendaService {

    private final Logger log = LoggerFactory.getLogger(CAgendaService.class);

    private final CAgendaRepository cAgendaRepository;

    private final CAgendaMapper cAgendaMapper;

    public CAgendaService(CAgendaRepository cAgendaRepository, CAgendaMapper cAgendaMapper) {
        this.cAgendaRepository = cAgendaRepository;
        this.cAgendaMapper = cAgendaMapper;
    }

    /**
     * Save a cAgenda.
     *
     * @param cAgendaDTO the entity to save.
     * @return the persisted entity.
     */
    public CAgendaDTO save(CAgendaDTO cAgendaDTO) {
        log.debug("Request to save CAgenda : {}", cAgendaDTO);
        CAgenda cAgenda = cAgendaMapper.toEntity(cAgendaDTO);
        cAgenda = cAgendaRepository.save(cAgenda);
        return cAgendaMapper.toDto(cAgenda);
    }

    /**
     * Partially update a cAgenda.
     *
     * @param cAgendaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CAgendaDTO> partialUpdate(CAgendaDTO cAgendaDTO) {
        log.debug("Request to partially update CAgenda : {}", cAgendaDTO);

        return cAgendaRepository
            .findById(cAgendaDTO.getId())
            .map(
                existingCAgenda -> {
                    cAgendaMapper.partialUpdate(existingCAgenda, cAgendaDTO);
                    return existingCAgenda;
                }
            )
            .map(cAgendaRepository::save)
            .map(cAgendaMapper::toDto);
    }

    /**
     * Get all the cAgenda.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CAgendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CAgenda");
        return cAgendaRepository.findAll(pageable).map(cAgendaMapper::toDto);
    }

    /**
     * Get one cAgenda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CAgendaDTO> findOne(Long id) {
        log.debug("Request to get CAgenda : {}", id);
        return cAgendaRepository.findById(id).map(cAgendaMapper::toDto);
    }

    /**
     * Delete the cAgenda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CAgenda : {}", id);
        cAgendaRepository.deleteById(id);
    }
}
