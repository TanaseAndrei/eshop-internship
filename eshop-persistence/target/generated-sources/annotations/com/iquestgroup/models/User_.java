package com.iquestgroup.models;

import com.iquestgroup.constants.UserRole;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> address;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, UserRole> userRole;
	public static volatile SingularAttribute<User, String> username;

	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";
	public static final String ID = "id";
	public static final String USER_ROLE = "userRole";
	public static final String USERNAME = "username";

}

