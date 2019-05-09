package it.aguzzo.casamanager.service;

import it.aguzzo.casamanager.service.dto.ProdottoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.aguzzo.casamanager.domain.Prodotto}.
 */
public interface ProdottoService {

    /**
     * Save a prodotto.
     *
     * @param prodottoDTO the entity to save.
     * @return the persisted entity.
     */
    ProdottoDTO save(ProdottoDTO prodottoDTO);

    /**
     * Get all the prodottos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProdottoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prodotto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProdottoDTO> findOne(Long id);

    /**
     * Delete the "id" prodotto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
