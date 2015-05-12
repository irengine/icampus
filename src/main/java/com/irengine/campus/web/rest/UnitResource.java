package com.irengine.campus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.irengine.campus.domain.Unit;
import com.irengine.campus.repository.UnitRepository;
import com.irengine.campus.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Unit.
 */
@RestController
@RequestMapping("/api")
public class UnitResource {

    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    @Inject
    private UnitRepository unitRepository;

    /**
     * POST  /units -> Create a new unit.
     */
    @RequestMapping(value = "/units",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Unit unit) throws URISyntaxException {
        log.debug("REST request to save Unit : {}", unit);
        if (unit.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new unit cannot already have an ID").build();
        }
        unitRepository.save(unit);
        return ResponseEntity.created(new URI("/api/units/" + unit.getId())).build();
    }

    /**
     * PUT  /units -> Updates an existing unit.
     */
    @RequestMapping(value = "/units",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Unit unit) throws URISyntaxException {
        log.debug("REST request to update Unit : {}", unit);
        if (unit.getId() == null) {
            return create(unit);
        }
        unitRepository.save(unit);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /units -> get all the units.
     */
    @RequestMapping(value = "/units",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Unit>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                             @RequestParam(value = "per_page", required = false) Integer limit,
                                             @RequestParam(value = "searchCondition", required = false) String searchCondition
                                             )
        throws URISyntaxException {

        // StringUtils.isEmpty()
        // like
        searchCondition = searchCondition + "%";

        Page<Unit> page = unitRepository.findAllByName(searchCondition, PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/units", offset, limit);
        return new ResponseEntity<List<Unit>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /units/:id -> get the "id" unit.
     */
    @RequestMapping(value = "/units/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Unit> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Unit : {}", id);
        Unit unit = unitRepository.findOne(id);
        if (unit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(unit, HttpStatus.OK);
    }

    /**
     * DELETE  /units/:id -> delete the "id" unit.
     */
    @RequestMapping(value = "/units/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Unit : {}", id);
        unitRepository.delete(id);
    }
}
