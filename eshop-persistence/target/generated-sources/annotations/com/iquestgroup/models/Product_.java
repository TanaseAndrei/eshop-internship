package com.iquestgroup.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Shop> shop;
	public static volatile SingularAttribute<Product, Long> productID;
	public static volatile SingularAttribute<Product, Double> price;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Category> category;
	public static volatile SingularAttribute<Product, Integer> stock;
	public static volatile SingularAttribute<Product, String> brand;

	public static final String SHOP = "shop";
	public static final String PRODUCT_ID = "productID";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String CATEGORY = "category";
	public static final String STOCK = "stock";
	public static final String BRAND = "brand";

}

