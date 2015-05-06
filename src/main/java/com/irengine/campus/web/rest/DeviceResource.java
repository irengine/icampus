package com.irengine.campus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.irengine.campus.domain.Device;
import com.irengine.campus.repository.DeviceRepository;
import com.irengine.campus.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/api")
public class DeviceResource {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);

    @Inject
    private DeviceRepository deviceRepository;

    /**
     * POST  /devices -> Create a new device.
     */
    @RequestMapping(value = "/devices",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to save Device : {}", device);
        if (device.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new device cannot already have an ID").build();
        }
        deviceRepository.save(device);
        return ResponseEntity.created(new URI("/api/devices/" + device.getId())).build();
    }

    /**
     * PUT  /devices -> Updates an existing device.
     */
    @RequestMapping(value = "/devices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to update Device : {}", device);
        if (device.getId() == null) {
            return create(device);
        }
        deviceRepository.save(device);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /devices -> get all the devices.
     */
    @RequestMapping(value = "/devices",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Device>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Device> page = deviceRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devices", offset, limit);
        return new ResponseEntity<List<Device>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /devices/:id -> get the "id" device.
     */
    @RequestMapping(value = "/devices/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Device> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Device : {}", id);
        Device device = deviceRepository.findOne(id);
        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    /**
     * DELETE  /devices/:id -> delete the "id" device.
     */
    @RequestMapping(value = "/devices/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceRepository.delete(id);
    }
}
