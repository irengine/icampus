package com.irengine.campus.web.rest;

import com.irengine.campus.Application;
import com.irengine.campus.domain.Device;
import com.irengine.campus.repository.DeviceRepository;

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
 * Test class for the DeviceResource REST controller.
 *
 * @see DeviceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeviceResourceTest {

    private static final String DEFAULT_CATEGORY = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORY = "UPDATED_TEXT";
    private static final String DEFAULT_SERIAL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_SERIAL_NUMBER = "UPDATED_TEXT";

    @Inject
    private DeviceRepository deviceRepository;

    private MockMvc restDeviceMockMvc;

    private Device device;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceResource deviceResource = new DeviceResource();
        ReflectionTestUtils.setField(deviceResource, "deviceRepository", deviceRepository);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource).build();
    }

    @Before
    public void initTest() {
        device = new Device();
        device.setCategory(DEFAULT_CATEGORY);
        device.setSerialNumber(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device
        restDeviceMockMvc.perform(post("/api/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(device)))
                .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = devices.get(devices.size() - 1);
        assertThat(testDevice.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testDevice.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(deviceRepository.findAll()).hasSize(0);
        // set the field null
        device.setCategory(null);

        // Create the Device, which fails.
        restDeviceMockMvc.perform(post("/api/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(device)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(0);
    }

    @Test
    @Transactional
    public void checkSerialNumberIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(deviceRepository.findAll()).hasSize(0);
        // set the field null
        device.setSerialNumber(null);

        // Create the Device, which fails.
        restDeviceMockMvc.perform(post("/api/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(device)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the devices
        restDeviceMockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

		int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        device.setCategory(UPDATED_CATEGORY);
        device.setSerialNumber(UPDATED_SERIAL_NUMBER);
        restDeviceMockMvc.perform(put("/api/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(device)))
                .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = devices.get(devices.size() - 1);
        assertThat(testDevice.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testDevice.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

		int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Get the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
