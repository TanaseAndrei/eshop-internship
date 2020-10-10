package com.iquestgroup.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Favorites.class)
public abstract class Favorites_ {

	public static volatile SingularAttribute<Favorites, Long> id;
	public static volatile ListAttribute<Favorites, FavoritesItem> favoritesItemList;
	public static volatile SingularAttribute<Favorites, Customer> customer;

	public static final String ID = "id";
	public static final String FAVORITES_ITEM_LIST = "favoritesItemList";
	public static final String CUSTOMER = "customer";

}

