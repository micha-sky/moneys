package io.dope.moneys.service.mapper;

import io.dope.moneys.domain.*;
import io.dope.moneys.service.dto.MoneyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Money and its DTO MoneyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MoneyMapper extends EntityMapper<MoneyDTO, Money> {



    default Money fromId(Long id) {
        if (id == null) {
            return null;
        }
        Money money = new Money();
        money.setId(id);
        return money;
    }
}
