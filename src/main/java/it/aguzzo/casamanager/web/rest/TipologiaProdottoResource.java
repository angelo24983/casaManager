package it.aguzzo.casamanager.web.rest;

import it.aguzzo.casamanager.service.TipologiaProdottoService;
import it.aguzzo.casamanager.web.rest.errors.BadRequestAlertException;
import it.aguzzo.casamanager.service.dto.TipologiaProdottoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.aguzzo.casamanager.domain.TipologiaProdotto}.
 */
@RestController
@RequestMapping("/api")
public class TipologiaProdottoResource {

    private final Logger log = LoggerFactory.getLogger(TipologiaProdottoResource.class);

    private static final String ENTITY_NAME = "tipologiaProdotto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipologiaProdottoService tipologiaProdottoService;

    public TipologiaProdottoResource(TipologiaProdottoService tipologiaProdottoService) {
        this.tipologiaProdottoService = tipologiaProdottoService;
    }

    /**
     * {@code POST  /tipologia-prodottos} : Create a new tipologiaProdotto.
     *
     * @param tipologiaProdottoDTO the tipologiaProdottoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipologiaProdottoDTO, or with status {@code 400 (Bad Request)} if the tipologiaProdotto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipologia-prodottos")
    public ResponseEntity<TipologiaProdottoDTO> createTipologiaProdotto(@Valid @RequestBody TipologiaProdottoDTO tipologiaProdottoDTO) throws URISyntaxException {
        log.debug("REST request to save TipologiaProdotto : {}", tipologiaProdottoDTO);
        if (tipologiaProdottoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipologiaProdotto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipologiaProdottoDTO result = tipologiaProdottoService.save(tipologiaProdottoDTO);
        return ResponseEntity.created(new URI("/api/tipologia-prodottos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipologia-prodottos} : Updates an existing tipologiaProdotto.
     *
     * @param tipologiaProdottoDTO the tipologiaProdottoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipologiaProdottoDTO,
     * or with status {@code 400 (Bad Request)} if the tipologiaProdottoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipologiaProdottoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipologia-prodottos")
    public ResponseEntity<TipologiaProdottoDTO> updateTipologiaProdotto(@Valid @RequestBody TipologiaProdottoDTO tipologiaProdottoDTO) throws URISyntaxException {
        log.debug("REST request to update TipologiaProdotto : {}", tipologiaProdottoDTO);
        if (tipologiaProdottoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipologiaProdottoDTO result = tipologiaProdottoService.save(tipologiaProdottoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipologiaProdottoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipologia-prodottos} : get all the tipologiaProdottos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipologiaProdottos in body.
     */
    @GetMapping("/tipologia-prodottos")
    public ResponseEntity<List<TipologiaProdottoDTO>> getAllTipologiaProdottos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TipologiaProdottos");
        Page<TipologiaProdottoDTO> page = tipologiaProdottoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipologia-prodottos/:id} : get the "id" tipologiaProdotto.
     *
     * @param id the id of the tipologiaProdottoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipologiaProdottoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipologia-prodottos/{id}")
    public ResponseEntity<TipologiaProdottoDTO> getTipologiaProdotto(@PathVariable Long id) {
        log.debug("REST request to get TipologiaProdotto : {}", id);
        Optional<TipologiaProdottoDTO> tipologiaProdottoDTO = tipologiaProdottoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipologiaProdottoDTO);
    }

    /**
     * {@code DELETE  /tipologia-prodottos/:id} : delete the "id" tipologiaProdotto.
     *
     * @param id the id of the tipologiaProdottoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipologia-prodottos/{id}")
    public ResponseEntity<Void> deleteTipologiaProdotto(@PathVariable Long id) {
        log.debug("REST request to delete TipologiaProdotto : {}", id);
        tipologiaProdottoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
