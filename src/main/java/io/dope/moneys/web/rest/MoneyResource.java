package io.dope.moneys.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dope.moneys.service.MoneyService;
import io.dope.moneys.web.rest.errors.BadRequestAlertException;
import io.dope.moneys.web.rest.util.HeaderUtil;
import io.dope.moneys.service.dto.MoneyDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Money.
 */
@RestController
@RequestMapping("/api")
public class MoneyResource {

    private final Logger log = LoggerFactory.getLogger(MoneyResource.class);

    private static final String ENTITY_NAME = "money";

    private final MoneyService moneyService;

    public MoneyResource(MoneyService moneyService) {
        this.moneyService = moneyService;
    }

    /**
     * POST  /monies : Create a new money.
     *
     * @param moneyDTO the moneyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moneyDTO, or with status 400 (Bad Request) if the money has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monies")
    @Timed
    public ResponseEntity<MoneyDTO> createMoney(@RequestBody MoneyDTO moneyDTO) throws URISyntaxException {
        log.debug("REST request to save Money : {}", moneyDTO);
        if (moneyDTO.getId() != null) {
            throw new BadRequestAlertException("A new money cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoneyDTO result = moneyService.save(moneyDTO);
        return ResponseEntity.created(new URI("/api/monies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monies : Updates an existing money.
     *
     * @param moneyDTO the moneyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moneyDTO,
     * or with status 400 (Bad Request) if the moneyDTO is not valid,
     * or with status 500 (Internal Server Error) if the moneyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monies")
    @Timed
    public ResponseEntity<MoneyDTO> updateMoney(@RequestBody MoneyDTO moneyDTO) throws URISyntaxException {
        log.debug("REST request to update Money : {}", moneyDTO);
        if (moneyDTO.getId() == null) {
            return createMoney(moneyDTO);
        }
        MoneyDTO result = moneyService.save(moneyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moneyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monies : get all the monies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monies in body
     */
    @GetMapping("/monies")
    @Timed
    public List<MoneyDTO> getAllMonies() {
        log.debug("REST request to get all Monies");
        return moneyService.findAll();
        }

    /**
     * GET  /monies/:id : get the "id" money.
     *
     * @param id the id of the moneyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moneyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monies/{id}")
    @Timed
    public ResponseEntity<MoneyDTO> getMoney(@PathVariable Long id) {
        log.debug("REST request to get Money : {}", id);
        MoneyDTO moneyDTO = moneyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(moneyDTO));
    }

    /**
     * DELETE  /monies/:id : delete the "id" money.
     *
     * @param id the id of the moneyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monies/{id}")
    @Timed
    public ResponseEntity<Void> deleteMoney(@PathVariable Long id) {
        log.debug("REST request to delete Money : {}", id);
        moneyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
