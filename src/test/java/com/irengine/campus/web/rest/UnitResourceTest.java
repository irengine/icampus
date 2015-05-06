package com.irengine.campus.web.rest;

import com.irengine.campus.Application;
import com.irengine.campus.domain.Unit;
import com.irengine.campus.repository.UnitRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UnitResource REST controller.
 *
 * @see UnitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UnitResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_URI_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_URI_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CATEGORY = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORY = "UPDATED_TEXT";

    private static final Integer DEFAULT_LEFT_ID = 0;
    private static final Integer UPDATED_LEFT_ID = 1;

    private static final Integer DEFAULT_RIGHT_ID = 0;
    private static final Integer UPDATED_RIGHT_ID = 1;

    @Inject
    private UnitRepository unitRepository;

    private MockMvc restUnitMockMvc;

    private Unit unit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UnitResource unitResource = new UnitResource();
        ReflectionTestUtils.setField(unitResource, "unitRepository", unitRepository);
        this.restUnitMockMvc = MockMvcBuilders.standaloneSetup(unitResource).build();
    }

    @Before
    public void initTest() {
        unit = new Unit();
        unit.setName(DEFAULT_NAME);
        unit.setUriName(DEFAULT_URI_NAME);
        unit.setCategory(DEFAULT_CATEGORY);
        unit.setLeftId(DEFAULT_LEFT_ID);
        unit.setRightId(DEFAULT_RIGHT_ID);
    }

    @Test
    @Transactional
    public void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit
        restUnitMockMvc.perform(post("/api/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = units.get(units.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUnit.getUriName()).isEqualTo(DEFAULT_URI_NAME);
        assertThat(testUnit.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testUnit.getLeftId()).isEqualTo(DEFAULT_LEFT_ID);
        assertThat(testUnit.getRightId()).isEqualTo(DEFAULT_RIGHT_ID);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(unitRepository.findAll()).hasSize(0);
        // set the field null
        unit.setName(null);

        // Create the Unit, which fails.
        restUnitMockMvc.perform(post("/api/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(0);
    }

    @Test
    @Transactional
    public void checkUriNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(unitRepository.findAll()).hasSize(0);
        // set the field null
        unit.setUriName(null);

        // Create the Unit, which fails.
        restUnitMockMvc.perform(post("/api/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(unitRepository.findAll()).hasSize(0);
        // set the field null
        unit.setCategory(null);

        // Create the Unit, which fails.
        restUnitMockMvc.perform(post("/api/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(0);
    }

    @Test
    @Transactional
    public void checkLeftIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(unitRepository.findAll()).hasSize(0);
        // set the field null
        unit.setLeftId(null);

        // Create the Unit, which fails.
        restUnitMockMvc.perform(post("/api/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(0);
    }

    @Test
    @Transactional
    public void checkRightIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(unitRepository.findAll()).hasSize(0);
        // set the field null
        unit.setRightId(null);

        // Create the Unit, which fails.
        restUnitMockMvc.perform(post("/api/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get all the units
        restUnitMockMvc.perform(get("/api/units"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].uriName").value(hasItem(DEFAULT_URI_NAME.toString())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].leftId").value(hasItem(DEFAULT_LEFT_ID)))
                .andExpect(jsonPath("$.[*].rightId").value(hasItem(DEFAULT_RIGHT_ID)));
    }

    @Test
    @Transactional
    public void getUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(unit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.uriName").value(DEFAULT_URI_NAME.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.leftId").value(DEFAULT_LEFT_ID))
            .andExpect(jsonPath("$.rightId").value(DEFAULT_RIGHT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

		int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        unit.setName(UPDATED_NAME);
        unit.setUriName(UPDATED_URI_NAME);
        unit.setCategory(UPDATED_CATEGORY);
        unit.setLeftId(UPDATED_LEFT_ID);
        unit.setRightId(UPDATED_RIGHT_ID);
        restUnitMockMvc.perform(put("/api/units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(unit)))
                .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = units.get(units.size() - 1);
        assertThat(testUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUnit.getUriName()).isEqualTo(UPDATED_URI_NAME);
        assertThat(testUnit.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testUnit.getLeftId()).isEqualTo(UPDATED_LEFT_ID);
        assertThat(testUnit.getRightId()).isEqualTo(UPDATED_RIGHT_ID);
    }

    @Test
    @Transactional
    public void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.saveAndFlush(unit);

		int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Get the unit
        restUnitMockMvc.perform(delete("/api/units/{id}", unit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Unit> units = unitRepository.findAll();
        assertThat(units).hasSize(databaseSizeBeforeDelete - 1);
    }
}
