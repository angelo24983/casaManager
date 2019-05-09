package it.aguzzo.casamanager.service.impl;

import it.aguzzo.casamanager.service.SpesaService;
import it.aguzzo.casamanager.domain.Spesa;
import it.aguzzo.casamanager.repository.SpesaRepository;
import it.aguzzo.casamanager.service.dto.SpesaDTO;
import it.aguzzo.casamanager.service.mapper.SpesaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Spesa}.
 */
@Service
@Transactional
public class SpesaServiceImpl implements SpesaService {

    private final Logger log = LoggerFactory.getLogger(SpesaServiceImpl.class);

    private final SpesaRepository spesaRepository;

    private final SpesaMapper spesaMapper;

    public SpesaServiceImpl(SpesaRepository spesaRepository, SpesaMapper spesaMapper) {
        this.spesaRepository = spesaRepository;
        this.spesaMapper = spesaMapper;
    }

    /**
     * Save a spesa.
     *
     * @param spesaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpesaDTO save(SpesaDTO spesaDTO) {
        log.debug("Request to save Spesa : {}", spesaDTO);
        Spesa spesa = spesaMapper.toEntity(spesaDTO);
        spesa = spesaRepository.save(spesa);
        return spesaMapper.toDto(spesa);
    }

    /**
     * Get all the spesas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SpesaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Spesas");
        return spesaRepository.findAll(pageable)
            .map(spesaMapper::toDto);
    }

    /**
     * Get all the spesas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SpesaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return spesaRepository.findAllWithEagerRelationships(pageable).map(spesaMapper::toDto);
    }
    

    /**
     * Get one spesa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpesaDTO> findOne(Long id) {
        log.debug("Request to get Spesa : {}", id);
        return spesaRepository.findOneWithEagerRelationships(id)
            .map(spesaMapper::toDto);
    }

    /**
     * Delete the spesa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Spesa : {}", id);
        spesaRepository.deleteById(id);
    }
}
