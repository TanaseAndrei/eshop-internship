package com.iquestgroup.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Shop.class)
public abstract class Shop_ {

	public static volatile ListAttribute<Shop, Product> stockProducts;
	public static volatile ListAttribute<Shop, Order> orders;
	public static volatile SingularAttribute<Shop, Integer> shopId;
	public static volatile SingularAttribute<Shop, String> shopAddress;
	public static volatile ListAttribute<Shop, Seller> sellers;

	public static final String STOCK_PRODUCTS = "stockProducts";
	public static final String ORDERS = "orders";
	public static final String SHOP_ID = "shopId";
	public static final String SHOP_ADDRESS = "shopAddress";
	public static final String SELLERS = "sellers";

}

