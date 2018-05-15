package io.dope.moneys.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Money.class)
public abstract class Money_ {

	public static volatile SingularAttribute<Money, Instant> date;
	public static volatile SingularAttribute<Money, Long> amount;
	public static volatile SingularAttribute<Money, String> name;
	public static volatile SingularAttribute<Money, Long> id;
	public static volatile SingularAttribute<Money, Long> commissionPct;

}

