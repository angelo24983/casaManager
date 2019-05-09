package it.aguzzo.casamanager.service.impl;

import it.aguzzo.casamanager.service.ProdottoService;
import it.aguzzo.casamanager.domain.Prodotto;
import it.aguzzo.casamanager.repository.ProdottoRepository;
import it.aguzzo.casamanager.service.dto.ProdottoDTO;
import it.aguzzo.casamanager.service.mapper.ProdottoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prodotto}.
 */
@Service
@Transactional
public class ProdottoServiceImpl implements ProdottoService {

    private final Logger log = LoggerFactory.getLogger(ProdottoServiceImpl.class);

    private final ProdottoRepository prodottoRepository;

    private final ProdottoMapper prodottoMapper;

    public ProdottoServiceImpl(ProdottoRepository prodottoRepository, ProdottoMapper prodottoMapper) {
        this.prodottoRepository = prodottoRepository;
        this.prodottoMapper = prodottoMapper;
    }

    /**
     * Save a prodotto.
     *
     * @param prodottoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProdottoDTO save(ProdottoDTO prodottoDTO) {
        log.debug("Request to save Prodotto : {}", prodottoDTO);
        Prodotto prodotto = prodottoMapper.toEntity(prodottoDTO);
        prodotto = prodottoRepository.save(prodotto);
        return prodottoMapper.toDto(prodotto);
    }

    /**
     * Get all the prodottos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProdottoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prodottos");
        return prodottoRepository.findAll(pageable)
            .map(prodottoMapper::toDto);
    }


    /**
     * Get one prodotto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProdottoDTO> findOne(Long id) {
        log.debug("Request to get Prodotto : {}", id);
        return prodottoRepository.findById(id)
            .map(prodottoMapper::toDto);
    }

    /**
     * Delete the prodotto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prodotto : {}", id);
        prodottoRepository.deleteById(id);
    }
}
