package mx.com.morena.service;

import java.util.Optional;
import mx.com.morena.domain.CVotante;
import mx.com.morena.repository.CVotanteRepository;
import mx.com.morena.service.dto.CVotanteDTO;
import mx.com.morena.service.mapper.CVotanteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CVotante}.
 */
@Service
@Transactional
public class CVotanteService {

    private final Logger log = LoggerFactory.getLogger(CVotanteService.class);

    private final CVotanteRepository cVotanteRepository;

    private final CVotanteMapper cVotanteMapper;

    public CVotanteService(CVotanteRepository cVotanteRepository, CVotanteMapper cVotanteMapper) {
        this.cVotanteRepository = cVotanteRepository;
        this.cVotanteMapper = cVotanteMapper;
    }

    /**
     * Save a cVotante.
     *
     * @param cVotanteDTO the entity to save.
     * @return the persisted entity.
     */
    public CVotanteDTO save(CVotanteDTO cVotanteDTO) {
        log.debug("Request to save CVotante : {}", cVotanteDTO);
        CVotante cVotante = cVotanteMapper.toEntity(cVotanteDTO);
        cVotante = cVotanteRepository.save(cVotante);
        return cVotanteMapper.toDto(cVotante);
    }

    /**
     * Partially update a cVotante.
     *
     * @param cVotanteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CVotanteDTO> partialUpdate(CVotanteDTO cVotanteDTO) {
        log.debug("Request to partially update CVotante : {}", cVotanteDTO);

        return cVotanteRepository
            .findById(cVotanteDTO.getId())
            .map(
                existingCVotante -> {
                    cVotanteMapper.partialUpdate(existingCVotante, cVotanteDTO);
                    return existingCVotante;
                }
            )
            .map(cVotanteRepository::save)
            .map(cVotanteMapper::toDto);
    }

    /**
     * Get all the cVotantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVotanteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVotantes");
        return cVotanteRepository.findAll(pageable).map(cVotanteMapper::toDto);
    }

    /**
     * Get one cVotante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVotanteDTO> findOne(Long id) {
        log.debug("Request to get CVotante : {}", id);
        return cVotanteRepository.findById(id).map(cVotanteMapper::toDto);
    }

    /**
     * Delete the cVotante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVotante : {}", id);
        cVotanteRepository.deleteById(id);
    }
}
