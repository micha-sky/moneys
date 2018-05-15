package io.dope.moneys.web.rest;

import io.dope.moneys.MoneysApp;

import io.dope.moneys.domain.Money;
import io.dope.moneys.repository.MoneyRepository;
import io.dope.moneys.service.MoneyService;
import io.dope.moneys.service.dto.MoneyDTO;
import io.dope.moneys.service.mapper.MoneyMapper;
import io.dope.moneys.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.dope.moneys.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MoneyResource REST controller.
 *
 * @see MoneyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoneysApp.class)
public class MoneyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final Long DEFAULT_COMMISSION_PCT = 1L;
    private static final Long UPDATED_COMMISSION_PCT = 2L;

    @Autowired
    private MoneyRepository moneyRepository;

    @Autowired
    private MoneyMapper moneyMapper;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMoneyMockMvc;

    private Money money;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoneyResource moneyResource = new MoneyResource(moneyService);
        this.restMoneyMockMvc = MockMvcBuilders.standaloneSetup(moneyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Money createEntity(EntityManager em) {
        Money money = new Money()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE)
            .amount(DEFAULT_AMOUNT)
            .commissionPct(DEFAULT_COMMISSION_PCT);
        return money;
    }

    @Before
    public void initTest() {
        money = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoney() throws Exception {
        int databaseSizeBeforeCreate = moneyRepository.findAll().size();

        // Create the Money
        MoneyDTO moneyDTO = moneyMapper.toDto(money);
        restMoneyMockMvc.perform(post("/api/monies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyDTO)))
            .andExpect(status().isCreated());

        // Validate the Money in the database
        List<Money> moneyList = moneyRepository.findAll();
        assertThat(moneyList).hasSize(databaseSizeBeforeCreate + 1);
        Money testMoney = moneyList.get(moneyList.size() - 1);
        assertThat(testMoney.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMoney.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMoney.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMoney.getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);
    }

    @Test
    @Transactional
    public void createMoneyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moneyRepository.findAll().size();

        // Create the Money with an existing ID
        money.setId(1L);
        MoneyDTO moneyDTO = moneyMapper.toDto(money);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoneyMockMvc.perform(post("/api/monies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Money in the database
        List<Money> moneyList = moneyRepository.findAll();
        assertThat(moneyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMonies() throws Exception {
        // Initialize the database
        moneyRepository.saveAndFlush(money);

        // Get all the moneyList
        restMoneyMockMvc.perform(get("/api/monies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(money.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].commissionPct").value(hasItem(DEFAULT_COMMISSION_PCT.intValue())));
    }

    @Test
    @Transactional
    public void getMoney() throws Exception {
        // Initialize the database
        moneyRepository.saveAndFlush(money);

        // Get the money
        restMoneyMockMvc.perform(get("/api/monies/{id}", money.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(money.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.commissionPct").value(DEFAULT_COMMISSION_PCT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMoney() throws Exception {
        // Get the money
        restMoneyMockMvc.perform(get("/api/monies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoney() throws Exception {
        // Initialize the database
        moneyRepository.saveAndFlush(money);
        int databaseSizeBeforeUpdate = moneyRepository.findAll().size();

        // Update the money
        Money updatedMoney = moneyRepository.findOne(money.getId());
        // Disconnect from session so that the updates on updatedMoney are not directly saved in db
        em.detach(updatedMoney);
        updatedMoney
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .commissionPct(UPDATED_COMMISSION_PCT);
        MoneyDTO moneyDTO = moneyMapper.toDto(updatedMoney);

        restMoneyMockMvc.perform(put("/api/monies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyDTO)))
            .andExpect(status().isOk());

        // Validate the Money in the database
        List<Money> moneyList = moneyRepository.findAll();
        assertThat(moneyList).hasSize(databaseSizeBeforeUpdate);
        Money testMoney = moneyList.get(moneyList.size() - 1);
        assertThat(testMoney.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMoney.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMoney.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMoney.getCommissionPct()).isEqualTo(UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    public void updateNonExistingMoney() throws Exception {
        int databaseSizeBeforeUpdate = moneyRepository.findAll().size();

        // Create the Money
        MoneyDTO moneyDTO = moneyMapper.toDto(money);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMoneyMockMvc.perform(put("/api/monies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyDTO)))
            .andExpect(status().isCreated());

        // Validate the Money in the database
        List<Money> moneyList = moneyRepository.findAll();
        assertThat(moneyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMoney() throws Exception {
        // Initialize the database
        moneyRepository.saveAndFlush(money);
        int databaseSizeBeforeDelete = moneyRepository.findAll().size();

        // Get the money
        restMoneyMockMvc.perform(delete("/api/monies/{id}", money.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Money> moneyList = moneyRepository.findAll();
        assertThat(moneyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Money.class);
        Money money1 = new Money();
        money1.setId(1L);
        Money money2 = new Money();
        money2.setId(money1.getId());
        assertThat(money1).isEqualTo(money2);
        money2.setId(2L);
        assertThat(money1).isNotEqualTo(money2);
        money1.setId(null);
        assertThat(money1).isNotEqualTo(money2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoneyDTO.class);
        MoneyDTO moneyDTO1 = new MoneyDTO();
        moneyDTO1.setId(1L);
        MoneyDTO moneyDTO2 = new MoneyDTO();
        assertThat(moneyDTO1).isNotEqualTo(moneyDTO2);
        moneyDTO2.setId(moneyDTO1.getId());
        assertThat(moneyDTO1).isEqualTo(moneyDTO2);
        moneyDTO2.setId(2L);
        assertThat(moneyDTO1).isNotEqualTo(moneyDTO2);
        moneyDTO1.setId(null);
        assertThat(moneyDTO1).isNotEqualTo(moneyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(moneyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(moneyMapper.fromId(null)).isNull();
    }
}
