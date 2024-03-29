package mx.com.morena.service;

import java.util.Optional;
import mx.com.morena.domain.CCliente;
import mx.com.morena.repository.CClienteRepository;
import mx.com.morena.service.dto.CClienteDTO;
import mx.com.morena.service.mapper.CClienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CCliente}.
 */
@Service
@Transactional
public class CClienteService {

    private final Logger log = LoggerFactory.getLogger(CClienteService.class);

    private final CClienteRepository cClienteRepository;

    private final CClienteMapper cClienteMapper;

    public CClienteService(CClienteRepository cClienteRepository, CClienteMapper cClienteMapper) {
        this.cClienteRepository = cClienteRepository;
        this.cClienteMapper = cClienteMapper;
    }

    /**
     * Save a cCliente.
     *
     * @param cClienteDTO the entity to save.
     * @return the persisted entity.
     */
    public CClienteDTO save(CClienteDTO cClienteDTO) {
        log.debug("Request to save CCliente : {}", cClienteDTO);
        CCliente cCliente = cClienteMapper.toEntity(cClienteDTO);
        cCliente = cClienteRepository.save(cCliente);
        return cClienteMapper.toDto(cCliente);
    }

    /**
     * Partially update a cCliente.
     *
     * @param cClienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CClienteDTO> partialUpdate(CClienteDTO cClienteDTO) {
        log.debug("Request to partially update CCliente : {}", cClienteDTO);

        return cClienteRepository
            .findById(cClienteDTO.getId())
            .map(
                existingCCliente -> {
                    cClienteMapper.partialUpdate(existingCCliente, cClienteDTO);
                    return existingCCliente;
                }
            )
            .map(cClienteRepository::save)
            .map(cClienteMapper::toDto);
    }

    /**
     * Get all the cClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CClientes");
        return cClienteRepository.findAll(pageable).map(cClienteMapper::toDto);
    }

    /**
     * Get one cCliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CClienteDTO> findOne(Long id) {
        log.debug("Request to get CCliente : {}", id);
        return cClienteRepository.findById(id).map(cClienteMapper::toDto);
    }

    /**
     * Delete the cCliente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CCliente : {}", id);
        cClienteRepository.deleteById(id);
    }
}
