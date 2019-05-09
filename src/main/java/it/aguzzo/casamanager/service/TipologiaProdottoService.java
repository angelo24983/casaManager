package it.aguzzo.casamanager.service;

import it.aguzzo.casamanager.domain.TipologiaProdotto;
import it.aguzzo.casamanager.repository.TipologiaProdottoRepository;
import it.aguzzo.casamanager.service.dto.TipologiaProdottoDTO;
import it.aguzzo.casamanager.service.mapper.TipologiaProdottoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipologiaProdotto}.
 */
@Service
@Transactional
public class TipologiaProdottoService {

    private final Logger log = LoggerFactory.getLogger(TipologiaProdottoService.class);

    private final TipologiaProdottoRepository tipologiaProdottoRepository;

    private final TipologiaProdottoMapper tipologiaProdottoMapper;

    public TipologiaProdottoService(TipologiaProdottoRepository tipologiaProdottoRepository, TipologiaProdottoMapper tipologiaProdottoMapper) {
        this.tipologiaProdottoRepository = tipologiaProdottoRepository;
        this.tipologiaProdottoMapper = tipologiaProdottoMapper;
    }

    /**
     * Save a tipologiaProdotto.
     *
     * @param tipologiaProdottoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipologiaProdottoDTO save(TipologiaProdottoDTO tipologiaProdottoDTO) {
        log.debug("Request to save TipologiaProdotto : {}", tipologiaProdottoDTO);
        TipologiaProdotto tipologiaProdotto = tipologiaProdottoMapper.toEntity(tipologiaProdottoDTO);
        tipologiaProdotto = tipologiaProdottoRepository.save(tipologiaProdotto);
        return tipologiaProdottoMapper.toDto(tipologiaProdotto);
    }

    /**
     * Get all the tipologiaProdottos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipologiaProdottoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipologiaProdottos");
        return tipologiaProdottoRepository.findAll(pageable)
            .map(tipologiaProdottoMapper::toDto);
    }


    /**
     * Get one tipologiaProdotto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipologiaProdottoDTO> findOne(Long id) {
        log.debug("Request to get TipologiaProdotto : {}", id);
        return tipologiaProdottoRepository.findById(id)
            .map(tipologiaProdottoMapper::toDto);
    }

    /**
     * Delete the tipologiaProdotto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipologiaProdotto : {}", id);
        tipologiaProdottoRepository.deleteById(id);
    }
}
