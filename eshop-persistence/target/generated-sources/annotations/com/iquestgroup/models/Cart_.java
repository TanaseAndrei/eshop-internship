package com.iquestgroup.models;

import com.iquestgroup.constants.PaymentMethod;
import com.iquestgroup.constants.ShippingMethod;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cart.class)
public abstract class Cart_ {

	public static volatile ListAttribute<Cart, CartItem> listOfCartItems;
	public static volatile SingularAttribute<Cart, String> contactInfo;
	public static volatile SingularAttribute<Cart, String> billingDetails;
	public static volatile SingularAttribute<Cart, Double> price;
	public static volatile SingularAttribute<Cart, ShippingMethod> shippingMethod;
	public static volatile SingularAttribute<Cart, PaymentMethod> paymentMethod;
	public static volatile SingularAttribute<Cart, Long> id;
	public static volatile SingularAttribute<Cart, Customer> customer;

	public static final String LIST_OF_CART_ITEMS = "listOfCartItems";
	public static final String CONTACT_INFO = "contactInfo";
	public static final String BILLING_DETAILS = "billingDetails";
	public static final String PRICE = "price";
	public static final String SHIPPING_METHOD = "shippingMethod";
	public static final String PAYMENT_METHOD = "paymentMethod";
	public static final String ID = "id";
	public static final String CUSTOMER = "customer";

}

