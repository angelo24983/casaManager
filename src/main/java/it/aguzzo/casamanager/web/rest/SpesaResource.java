package it.aguzzo.casamanager.web.rest;

import it.aguzzo.casamanager.service.SpesaService;
import it.aguzzo.casamanager.web.rest.errors.BadRequestAlertException;
import it.aguzzo.casamanager.service.dto.SpesaDTO;

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
 * REST controller for managing {@link it.aguzzo.casamanager.domain.Spesa}.
 */
@RestController
@RequestMapping("/api")
public class SpesaResource {

    private final Logger log = LoggerFactory.getLogger(SpesaResource.class);

    private static final String ENTITY_NAME = "spesa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpesaService spesaService;

    public SpesaResource(SpesaService spesaService) {
        this.spesaService = spesaService;
    }

    /**
     * {@code POST  /spesas} : Create a new spesa.
     *
     * @param spesaDTO the spesaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spesaDTO, or with status {@code 400 (Bad Request)} if the spesa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spesas")
    public ResponseEntity<SpesaDTO> createSpesa(@Valid @RequestBody SpesaDTO spesaDTO) throws URISyntaxException {
        log.debug("REST request to save Spesa : {}", spesaDTO);
        if (spesaDTO.getId() != null) {
            throw new BadRequestAlertException("A new spesa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpesaDTO result = spesaService.save(spesaDTO);
        return ResponseEntity.created(new URI("/api/spesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /spesas} : Updates an existing spesa.
     *
     * @param spesaDTO the spesaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spesaDTO,
     * or with status {@code 400 (Bad Request)} if the spesaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spesaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spesas")
    public ResponseEntity<SpesaDTO> updateSpesa(@Valid @RequestBody SpesaDTO spesaDTO) throws URISyntaxException {
        log.debug("REST request to update Spesa : {}", spesaDTO);
        if (spesaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpesaDTO result = spesaService.save(spesaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spesaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /spesas} : get all the spesas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spesas in body.
     */
    @GetMapping("/spesas")
    public ResponseEntity<List<SpesaDTO>> getAllSpesas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Spesas");
        Page<SpesaDTO> page;
        if (eagerload) {
            page = spesaService.findAllWithEagerRelationships(pageable);
        } else {
            page = spesaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /spesas/:id} : get the "id" spesa.
     *
     * @param id the id of the spesaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spesaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/spesas/{id}")
    public ResponseEntity<SpesaDTO> getSpesa(@PathVariable Long id) {
        log.debug("REST request to get Spesa : {}", id);
        Optional<SpesaDTO> spesaDTO = spesaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spesaDTO);
    }

    /**
     * {@code DELETE  /spesas/:id} : delete the "id" spesa.
     *
     * @param id the id of the spesaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/spesas/{id}")
    public ResponseEntity<Void> deleteSpesa(@PathVariable Long id) {
        log.debug("REST request to delete Spesa : {}", id);
        spesaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
