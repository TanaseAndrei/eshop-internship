package com.iquestgroup.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CartItem.class)
public abstract class CartItem_ {

	public static volatile SingularAttribute<CartItem, Product> product;
	public static volatile SingularAttribute<CartItem, Integer> quantity;
	public static volatile SingularAttribute<CartItem, Double> price;
	public static volatile SingularAttribute<CartItem, Long> id;

	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price";
	public static final String ID = "id";

}

