package io.dope.moneys.service.mapper;

import io.dope.moneys.domain.Money;
import io.dope.moneys.service.dto.MoneyDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-02-08T19:48:58+0100",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class MoneyMapperImpl implements MoneyMapper {

    @Override
    public Money toEntity(MoneyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Money money = new Money();

        money.setId( dto.getId() );
        money.setName( dto.getName() );
        money.setDate( dto.getDate() );
        money.setAmount( dto.getAmount() );
        money.setCommissionPct( dto.getCommissionPct() );

        return money;
    }

    @Override
    public MoneyDTO toDto(Money entity) {
        if ( entity == null ) {
            return null;
        }

        MoneyDTO moneyDTO = new MoneyDTO();

        moneyDTO.setId( entity.getId() );
        moneyDTO.setName( entity.getName() );
        moneyDTO.setDate( entity.getDate() );
        moneyDTO.setAmount( entity.getAmount() );
        moneyDTO.setCommissionPct( entity.getCommissionPct() );

        return moneyDTO;
    }

    @Override
    public List<Money> toEntity(List<MoneyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Money> list = new ArrayList<Money>( dtoList.size() );
        for ( MoneyDTO moneyDTO : dtoList ) {
            list.add( toEntity( moneyDTO ) );
        }

        return list;
    }

    @Override
    public List<MoneyDTO> toDto(List<Money> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MoneyDTO> list = new ArrayList<MoneyDTO>( entityList.size() );
        for ( Money money : entityList ) {
            list.add( toDto( money ) );
        }

        return list;
    }
}
