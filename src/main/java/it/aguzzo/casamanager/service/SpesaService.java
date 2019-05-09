package it.aguzzo.casamanager.service;

import it.aguzzo.casamanager.service.dto.SpesaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.aguzzo.casamanager.domain.Spesa}.
 */
public interface SpesaService {

    /**
     * Save a spesa.
     *
     * @param spesaDTO the entity to save.
     * @return the persisted entity.
     */
    SpesaDTO save(SpesaDTO spesaDTO);

    /**
     * Get all the spesas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpesaDTO> findAll(Pageable pageable);

    /**
     * Get all the spesas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SpesaDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" spesa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpesaDTO> findOne(Long id);

    /**
     * Delete the "id" spesa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
