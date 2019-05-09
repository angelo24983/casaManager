package it.aguzzo.casamanager.web.rest;

import it.aguzzo.casamanager.CasaManagerApp;
import it.aguzzo.casamanager.domain.TipologiaProdotto;
import it.aguzzo.casamanager.repository.TipologiaProdottoRepository;
import it.aguzzo.casamanager.service.TipologiaProdottoService;
import it.aguzzo.casamanager.service.dto.TipologiaProdottoDTO;
import it.aguzzo.casamanager.service.mapper.TipologiaProdottoMapper;
import it.aguzzo.casamanager.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static it.aguzzo.casamanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TipologiaProdottoResource} REST controller.
 */
@SpringBootTest(classes = CasaManagerApp.class)
public class TipologiaProdottoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private TipologiaProdottoRepository tipologiaProdottoRepository;

    @Autowired
    private TipologiaProdottoMapper tipologiaProdottoMapper;

    @Autowired
    private TipologiaProdottoService tipologiaProdottoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTipologiaProdottoMockMvc;

    private TipologiaProdotto tipologiaProdotto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipologiaProdottoResource tipologiaProdottoResource = new TipologiaProdottoResource(tipologiaProdottoService);
        this.restTipologiaProdottoMockMvc = MockMvcBuilders.standaloneSetup(tipologiaProdottoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipologiaProdotto createEntity(EntityManager em) {
        TipologiaProdotto tipologiaProdotto = new TipologiaProdotto()
            .nome(DEFAULT_NOME)
            .descrizione(DEFAULT_DESCRIZIONE);
        return tipologiaProdotto;
    }

    @BeforeEach
    public void initTest() {
        tipologiaProdotto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipologiaProdotto() throws Exception {
        int databaseSizeBeforeCreate = tipologiaProdottoRepository.findAll().size();

        // Create the TipologiaProdotto
        TipologiaProdottoDTO tipologiaProdottoDTO = tipologiaProdottoMapper.toDto(tipologiaProdotto);
        restTipologiaProdottoMockMvc.perform(post("/api/tipologia-prodottos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipologiaProdottoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipologiaProdotto in the database
        List<TipologiaProdotto> tipologiaProdottoList = tipologiaProdottoRepository.findAll();
        assertThat(tipologiaProdottoList).hasSize(databaseSizeBeforeCreate + 1);
        TipologiaProdotto testTipologiaProdotto = tipologiaProdottoList.get(tipologiaProdottoList.size() - 1);
        assertThat(testTipologiaProdotto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipologiaProdotto.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void createTipologiaProdottoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipologiaProdottoRepository.findAll().size();

        // Create the TipologiaProdotto with an existing ID
        tipologiaProdotto.setId(1L);
        TipologiaProdottoDTO tipologiaProdottoDTO = tipologiaProdottoMapper.toDto(tipologiaProdotto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipologiaProdottoMockMvc.perform(post("/api/tipologia-prodottos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipologiaProdottoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipologiaProdotto in the database
        List<TipologiaProdotto> tipologiaProdottoList = tipologiaProdottoRepository.findAll();
        assertThat(tipologiaProdottoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaProdottoRepository.findAll().size();
        // set the field null
        tipologiaProdotto.setNome(null);

        // Create the TipologiaProdotto, which fails.
        TipologiaProdottoDTO tipologiaProdottoDTO = tipologiaProdottoMapper.toDto(tipologiaProdotto);

        restTipologiaProdottoMockMvc.perform(post("/api/tipologia-prodottos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipologiaProdottoDTO)))
            .andExpect(status().isBadRequest());

        List<TipologiaProdotto> tipologiaProdottoList = tipologiaProdottoRepository.findAll();
        assertThat(tipologiaProdottoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescrizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipologiaProdottoRepository.findAll().size();
        // set the field null
        tipologiaProdotto.setDescrizione(null);

        // Create the TipologiaProdotto, which fails.
        TipologiaProdottoDTO tipologiaProdottoDTO = tipologiaProdottoMapper.toDto(tipologiaProdotto);

        restTipologiaProdottoMockMvc.perform(post("/api/tipologia-prodottos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipologiaProdottoDTO)))
            .andExpect(status().isBadRequest());

        List<TipologiaProdotto> tipologiaProdottoList = tipologiaProdottoRepository.findAll();
        assertThat(tipologiaProdottoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipologiaProdottos() throws Exception {
        // Initialize the database
        tipologiaProdottoRepository.saveAndFlush(tipologiaProdotto);

        // Get all the tipologiaProdottoList
        restTipologiaProdottoMockMvc.perform(get("/api/tipologia-prodottos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipologiaProdotto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipologiaProdotto() throws Exception {
        // Initialize the database
        tipologiaProdottoRepository.saveAndFlush(tipologiaProdotto);

        // Get the tipologiaProdotto
        restTipologiaProdottoMockMvc.perform(get("/api/tipologia-prodottos/{id}", tipologiaProdotto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipologiaProdotto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipologiaProdotto() throws Exception {
        // Get the tipologiaProdotto
        restTipologiaProdottoMockMvc.perform(get("/api/tipologia-prodottos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipologiaProdotto() throws Exception {
        // Initialize the database
        tipologiaProdottoRepository.saveAndFlush(tipologiaProdotto);

        int databaseSizeBeforeUpdate = tipologiaProdottoRepository.findAll().size();

        // Update the tipologiaProdotto
        TipologiaProdotto updatedTipologiaProdotto = tipologiaProdottoRepository.findById(tipologiaProdotto.getId()).get();
        // Disconnect from session so that the updates on updatedTipologiaProdotto are not directly saved in db
        em.detach(updatedTipologiaProdotto);
        updatedTipologiaProdotto
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE);
        TipologiaProdottoDTO tipologiaProdottoDTO = tipologiaProdottoMapper.toDto(updatedTipologiaProdotto);

        restTipologiaProdottoMockMvc.perform(put("/api/tipologia-prodottos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipologiaProdottoDTO)))
            .andExpect(status().isOk());

        // Validate the TipologiaProdotto in the database
        List<TipologiaProdotto> tipologiaProdottoList = tipologiaProdottoRepository.findAll();
        assertThat(tipologiaProdottoList).hasSize(databaseSizeBeforeUpdate);
        TipologiaProdotto testTipologiaProdotto = tipologiaProdottoList.get(tipologiaProdottoList.size() - 1);
        assertThat(testTipologiaProdotto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipologiaProdotto.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipologiaProdotto() throws Exception {
        int databaseSizeBeforeUpdate = tipologiaProdottoRepository.findAll().size();

        // Create the TipologiaProdotto
        TipologiaProdottoDTO tipologiaProdottoDTO = tipologiaProdottoMapper.toDto(tipologiaProdotto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipologiaProdottoMockMvc.perform(put("/api/tipologia-prodottos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipologiaProdottoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipologiaProdotto in the database
        List<TipologiaProdotto> tipologiaProdottoList = tipologiaProdottoRepository.findAll();
        assertThat(tipologiaProdottoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipologiaProdotto() throws Exception {
        // Initialize the database
        tipologiaProdottoRepository.saveAndFlush(tipologiaProdotto);

        int databaseSizeBeforeDelete = tipologiaProdottoRepository.findAll().size();

        // Delete the tipologiaProdotto
        restTipologiaProdottoMockMvc.perform(delete("/api/tipologia-prodottos/{id}", tipologiaProdotto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TipologiaProdotto> tipologiaProdottoList = tipologiaProdottoRepository.findAll();
        assertThat(tipologiaProdottoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipologiaProdotto.class);
        TipologiaProdotto tipologiaProdotto1 = new TipologiaProdotto();
        tipologiaProdotto1.setId(1L);
        TipologiaProdotto tipologiaProdotto2 = new TipologiaProdotto();
        tipologiaProdotto2.setId(tipologiaProdotto1.getId());
        assertThat(tipologiaProdotto1).isEqualTo(tipologiaProdotto2);
        tipologiaProdotto2.setId(2L);
        assertThat(tipologiaProdotto1).isNotEqualTo(tipologiaProdotto2);
        tipologiaProdotto1.setId(null);
        assertThat(tipologiaProdotto1).isNotEqualTo(tipologiaProdotto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipologiaProdottoDTO.class);
        TipologiaProdottoDTO tipologiaProdottoDTO1 = new TipologiaProdottoDTO();
        tipologiaProdottoDTO1.setId(1L);
        TipologiaProdottoDTO tipologiaProdottoDTO2 = new TipologiaProdottoDTO();
        assertThat(tipologiaProdottoDTO1).isNotEqualTo(tipologiaProdottoDTO2);
        tipologiaProdottoDTO2.setId(tipologiaProdottoDTO1.getId());
        assertThat(tipologiaProdottoDTO1).isEqualTo(tipologiaProdottoDTO2);
        tipologiaProdottoDTO2.setId(2L);
        assertThat(tipologiaProdottoDTO1).isNotEqualTo(tipologiaProdottoDTO2);
        tipologiaProdottoDTO1.setId(null);
        assertThat(tipologiaProdottoDTO1).isNotEqualTo(tipologiaProdottoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipologiaProdottoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipologiaProdottoMapper.fromId(null)).isNull();
    }
}
