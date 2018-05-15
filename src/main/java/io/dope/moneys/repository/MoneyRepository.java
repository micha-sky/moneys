package io.dope.moneys.repository;

import io.dope.moneys.domain.Money;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Money entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoneyRepository extends JpaRepository<Money, Long> {

}
