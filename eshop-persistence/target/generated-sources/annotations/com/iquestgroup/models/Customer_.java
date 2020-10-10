package com.iquestgroup.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ extends com.iquestgroup.models.User_ {

	public static volatile SingularAttribute<Customer, Favorites> favorites;
	public static volatile SingularAttribute<Customer, String> address;
	public static volatile SingularAttribute<Customer, Boolean> consentGiven;
	public static volatile ListAttribute<Customer, Order> orders;
	public static volatile SingularAttribute<Customer, Cart> cart;

	public static final String FAVORITES = "favorites";
	public static final String ADDRESS = "address";
	public static final String CONSENT_GIVEN = "consentGiven";
	public static final String ORDERS = "orders";
	public static final String CART = "cart";

}

