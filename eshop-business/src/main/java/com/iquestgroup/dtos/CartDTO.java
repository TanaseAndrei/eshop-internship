package com.iquestgroup.dtos;

import com.iquestgroup.constants.PaymentMethod;
import com.iquestgroup.constants.ShippingMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Model used to encapsulate data of a Cart model entity.
 */
public class CartDTO {

    private ShippingMethod shippingMethod;

    private PaymentMethod paymentMethod;

    private String contactPerson;

    private String billingDetails;

    private List<CartItemDTO> cartItemDTOList = new ArrayList<>();

    private double price;

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(String billingDetails) {
        this.billingDetails = billingDetails;
    }

    public List<CartItemDTO> getListOfCartItemDTOs() {
        return cartItemDTOList;
    }

    public void setListOfCartItemDTOs(List<CartItemDTO> listOfCartItemDTOs) {
        this.cartItemDTOList = listOfCartItemDTOs;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
