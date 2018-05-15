package io.dope.moneys.service;

import io.dope.moneys.service.dto.MoneyDTO;
import java.util.List;

/**
 * Service Interface for managing Money.
 */
public interface MoneyService {

    /**
     * Save a money.
     *
     * @param moneyDTO the entity to save
     * @return the persisted entity
     */
    MoneyDTO save(MoneyDTO moneyDTO);

    /**
     * Get all the monies.
     *
     * @return the list of entities
     */
    List<MoneyDTO> findAll();

    /**
     * Get the "id" money.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MoneyDTO findOne(Long id);

    /**
     * Delete the "id" money.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
