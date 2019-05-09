package it.aguzzo.casamanager.web.rest;

import it.aguzzo.casamanager.CasaManagerApp;
import it.aguzzo.casamanager.domain.Spesa;
import it.aguzzo.casamanager.repository.SpesaRepository;
import it.aguzzo.casamanager.service.SpesaService;
import it.aguzzo.casamanager.service.dto.SpesaDTO;
import it.aguzzo.casamanager.service.mapper.SpesaMapper;
import it.aguzzo.casamanager.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static it.aguzzo.casamanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SpesaResource} REST controller.
 */
@SpringBootTest(classes = CasaManagerApp.class)
public class SpesaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SpesaRepository spesaRepository;

    @Mock
    private SpesaRepository spesaRepositoryMock;

    @Autowired
    private SpesaMapper spesaMapper;

    @Mock
    private SpesaService spesaServiceMock;

    @Autowired
    private SpesaService spesaService;

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

    private MockMvc restSpesaMockMvc;

    private Spesa spesa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpesaResource spesaResource = new SpesaResource(spesaService);
        this.restSpesaMockMvc = MockMvcBuilders.standaloneSetup(spesaResource)
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
    public static Spesa createEntity(EntityManager em) {
        Spesa spesa = new Spesa()
            .nome(DEFAULT_NOME)
            .data(DEFAULT_DATA);
        return spesa;
    }

    @BeforeEach
    public void initTest() {
        spesa = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpesa() throws Exception {
        int databaseSizeBeforeCreate = spesaRepository.findAll().size();

        // Create the Spesa
        SpesaDTO spesaDTO = spesaMapper.toDto(spesa);
        restSpesaMockMvc.perform(post("/api/spesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spesaDTO)))
            .andExpect(status().isCreated());

        // Validate the Spesa in the database
        List<Spesa> spesaList = spesaRepository.findAll();
        assertThat(spesaList).hasSize(databaseSizeBeforeCreate + 1);
        Spesa testSpesa = spesaList.get(spesaList.size() - 1);
        assertThat(testSpesa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSpesa.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createSpesaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spesaRepository.findAll().size();

        // Create the Spesa with an existing ID
        spesa.setId(1L);
        SpesaDTO spesaDTO = spesaMapper.toDto(spesa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpesaMockMvc.perform(post("/api/spesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spesaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Spesa in the database
        List<Spesa> spesaList = spesaRepository.findAll();
        assertThat(spesaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = spesaRepository.findAll().size();
        // set the field null
        spesa.setNome(null);

        // Create the Spesa, which fails.
        SpesaDTO spesaDTO = spesaMapper.toDto(spesa);

        restSpesaMockMvc.perform(post("/api/spesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spesaDTO)))
            .andExpect(status().isBadRequest());

        List<Spesa> spesaList = spesaRepository.findAll();
        assertThat(spesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpesas() throws Exception {
        // Initialize the database
        spesaRepository.saveAndFlush(spesa);

        // Get all the spesaList
        restSpesaMockMvc.perform(get("/api/spesas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSpesasWithEagerRelationshipsIsEnabled() throws Exception {
        SpesaResource spesaResource = new SpesaResource(spesaServiceMock);
        when(spesaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSpesaMockMvc = MockMvcBuilders.standaloneSetup(spesaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSpesaMockMvc.perform(get("/api/spesas?eagerload=true"))
        .andExpect(status().isOk());

        verify(spesaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSpesasWithEagerRelationshipsIsNotEnabled() throws Exception {
        SpesaResource spesaResource = new SpesaResource(spesaServiceMock);
            when(spesaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSpesaMockMvc = MockMvcBuilders.standaloneSetup(spesaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSpesaMockMvc.perform(get("/api/spesas?eagerload=true"))
        .andExpect(status().isOk());

            verify(spesaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSpesa() throws Exception {
        // Initialize the database
        spesaRepository.saveAndFlush(spesa);

        // Get the spesa
        restSpesaMockMvc.perform(get("/api/spesas/{id}", spesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spesa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpesa() throws Exception {
        // Get the spesa
        restSpesaMockMvc.perform(get("/api/spesas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpesa() throws Exception {
        // Initialize the database
        spesaRepository.saveAndFlush(spesa);

        int databaseSizeBeforeUpdate = spesaRepository.findAll().size();

        // Update the spesa
        Spesa updatedSpesa = spesaRepository.findById(spesa.getId()).get();
        // Disconnect from session so that the updates on updatedSpesa are not directly saved in db
        em.detach(updatedSpesa);
        updatedSpesa
            .nome(UPDATED_NOME)
            .data(UPDATED_DATA);
        SpesaDTO spesaDTO = spesaMapper.toDto(updatedSpesa);

        restSpesaMockMvc.perform(put("/api/spesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spesaDTO)))
            .andExpect(status().isOk());

        // Validate the Spesa in the database
        List<Spesa> spesaList = spesaRepository.findAll();
        assertThat(spesaList).hasSize(databaseSizeBeforeUpdate);
        Spesa testSpesa = spesaList.get(spesaList.size() - 1);
        assertThat(testSpesa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSpesa.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingSpesa() throws Exception {
        int databaseSizeBeforeUpdate = spesaRepository.findAll().size();

        // Create the Spesa
        SpesaDTO spesaDTO = spesaMapper.toDto(spesa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpesaMockMvc.perform(put("/api/spesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spesaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Spesa in the database
        List<Spesa> spesaList = spesaRepository.findAll();
        assertThat(spesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpesa() throws Exception {
        // Initialize the database
        spesaRepository.saveAndFlush(spesa);

        int databaseSizeBeforeDelete = spesaRepository.findAll().size();

        // Delete the spesa
        restSpesaMockMvc.perform(delete("/api/spesas/{id}", spesa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Spesa> spesaList = spesaRepository.findAll();
        assertThat(spesaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spesa.class);
        Spesa spesa1 = new Spesa();
        spesa1.setId(1L);
        Spesa spesa2 = new Spesa();
        spesa2.setId(spesa1.getId());
        assertThat(spesa1).isEqualTo(spesa2);
        spesa2.setId(2L);
        assertThat(spesa1).isNotEqualTo(spesa2);
        spesa1.setId(null);
        assertThat(spesa1).isNotEqualTo(spesa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpesaDTO.class);
        SpesaDTO spesaDTO1 = new SpesaDTO();
        spesaDTO1.setId(1L);
        SpesaDTO spesaDTO2 = new SpesaDTO();
        assertThat(spesaDTO1).isNotEqualTo(spesaDTO2);
        spesaDTO2.setId(spesaDTO1.getId());
        assertThat(spesaDTO1).isEqualTo(spesaDTO2);
        spesaDTO2.setId(2L);
        assertThat(spesaDTO1).isNotEqualTo(spesaDTO2);
        spesaDTO1.setId(null);
        assertThat(spesaDTO1).isNotEqualTo(spesaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(spesaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(spesaMapper.fromId(null)).isNull();
    }
}
