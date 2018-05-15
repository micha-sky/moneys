package io.dope.moneys.service.impl;

import io.dope.moneys.service.MoneyService;
import io.dope.moneys.domain.Money;
import io.dope.moneys.repository.MoneyRepository;
import io.dope.moneys.service.dto.MoneyDTO;
import io.dope.moneys.service.mapper.MoneyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Money.
 */
@Service
@Transactional
public class MoneyServiceImpl implements MoneyService {

    private final Logger log = LoggerFactory.getLogger(MoneyServiceImpl.class);

    private final MoneyRepository moneyRepository;

    private final MoneyMapper moneyMapper;

    public MoneyServiceImpl(MoneyRepository moneyRepository, MoneyMapper moneyMapper) {
        this.moneyRepository = moneyRepository;
        this.moneyMapper = moneyMapper;
    }

    /**
     * Save a money.
     *
     * @param moneyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MoneyDTO save(MoneyDTO moneyDTO) {
        log.debug("Request to save Money : {}", moneyDTO);
        Money money = moneyMapper.toEntity(moneyDTO);
        money = moneyRepository.save(money);
        return moneyMapper.toDto(money);
    }

    /**
     * Get all the monies.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MoneyDTO> findAll() {
        log.debug("Request to get all Monies");
        return moneyRepository.findAll().stream()
            .map(moneyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one money by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MoneyDTO findOne(Long id) {
        log.debug("Request to get Money : {}", id);
        Money money = moneyRepository.findOne(id);
        return moneyMapper.toDto(money);
    }

    /**
     * Delete the money by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Money : {}", id);
        moneyRepository.delete(id);
    }
}
