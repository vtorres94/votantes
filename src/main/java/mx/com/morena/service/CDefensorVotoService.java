package mx.com.morena.service;

import java.util.Optional;
import mx.com.morena.domain.CDefensorVoto;
import mx.com.morena.repository.CDefensorVotoRepository;
import mx.com.morena.service.dto.CDefensorVotoDTO;
import mx.com.morena.service.mapper.CDefensorVotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CDefensorVoto}.
 */
@Service
@Transactional
public class CDefensorVotoService {

    private final Logger log = LoggerFactory.getLogger(CDefensorVotoService.class);

    private final CDefensorVotoRepository cDefensorVotoRepository;

    private final CDefensorVotoMapper cDefensorVotoMapper;

    public CDefensorVotoService(CDefensorVotoRepository cDefensorVotoRepository, CDefensorVotoMapper cDefensorVotoMapper) {
        this.cDefensorVotoRepository = cDefensorVotoRepository;
        this.cDefensorVotoMapper = cDefensorVotoMapper;
    }

    /**
     * Save a cDefensorVoto.
     *
     * @param cDefensorVotoDTO the entity to save.
     * @return the persisted entity.
     */
    public CDefensorVotoDTO save(CDefensorVotoDTO cDefensorVotoDTO) {
        log.debug("Request to save CDefensorVoto : {}", cDefensorVotoDTO);
        CDefensorVoto cDefensorVoto = cDefensorVotoMapper.toEntity(cDefensorVotoDTO);
        cDefensorVoto = cDefensorVotoRepository.save(cDefensorVoto);
        return cDefensorVotoMapper.toDto(cDefensorVoto);
    }

    /**
     * Partially update a cDefensorVoto.
     *
     * @param cDefensorVotoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CDefensorVotoDTO> partialUpdate(CDefensorVotoDTO cDefensorVotoDTO) {
        log.debug("Request to partially update CDefensorVoto : {}", cDefensorVotoDTO);

        return cDefensorVotoRepository
            .findById(cDefensorVotoDTO.getId())
            .map(
                existingCDefensorVoto -> {
                    cDefensorVotoMapper.partialUpdate(existingCDefensorVoto, cDefensorVotoDTO);
                    return existingCDefensorVoto;
                }
            )
            .map(cDefensorVotoRepository::save)
            .map(cDefensorVotoMapper::toDto);
    }

    /**
     * Get all the cDefensorVotos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CDefensorVotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CDefensorVotos");
        return cDefensorVotoRepository.findAll(pageable).map(cDefensorVotoMapper::toDto);
    }

    /**
     * Get one cDefensorVoto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CDefensorVotoDTO> findOne(Long id) {
        log.debug("Request to get CDefensorVoto : {}", id);
        return cDefensorVotoRepository.findById(id).map(cDefensorVotoMapper::toDto);
    }

    /**
     * Delete the cDefensorVoto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CDefensorVoto : {}", id);
        cDefensorVotoRepository.deleteById(id);
    }
}
