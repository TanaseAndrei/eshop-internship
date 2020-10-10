package com.iquestgroup.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ {

	public static volatile SingularAttribute<Category, Category> parentCategory;
	public static volatile SingularAttribute<Category, String> categoryName;
	public static volatile SingularAttribute<Category, Integer> categoryID;

	public static final String PARENT_CATEGORY = "parentCategory";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String CATEGORY_ID = "categoryID";

}

