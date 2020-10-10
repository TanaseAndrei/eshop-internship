package com.iquestgroup.models;

import com.iquestgroup.constants.PaymentMethod;
import com.iquestgroup.constants.ShippingMethod;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, LocalDateTime> date;
	public static volatile SingularAttribute<Order, Shop> shop;
	public static volatile SingularAttribute<Order, String> contactInfo;
	public static volatile SingularAttribute<Order, String> billingDetails;
	public static volatile SingularAttribute<Order, Long> orderId;
	public static volatile ListAttribute<Order, OrderItem> orderItemList;
	public static volatile SingularAttribute<Order, Double> price;
	public static volatile SingularAttribute<Order, ShippingMethod> shippingMethod;
	public static volatile SingularAttribute<Order, PaymentMethod> paymentMethod;
	public static volatile SingularAttribute<Order, Customer> customer;

	public static final String DATE = "date";
	public static final String SHOP = "shop";
	public static final String CONTACT_INFO = "contactInfo";
	public static final String BILLING_DETAILS = "billingDetails";
	public static final String ORDER_ID = "orderId";
	public static final String ORDER_ITEM_LIST = "orderItemList";
	public static final String PRICE = "price";
	public static final String SHIPPING_METHOD = "shippingMethod";
	public static final String PAYMENT_METHOD = "paymentMethod";
	public static final String CUSTOMER = "customer";

}

