package com.iquestgroup.dtos;

/**
 * Model used to encapsulate data of a CartItem model entity.
 */
public class CartItemDTO {

    private double price;

    private int quantity;

    private String productName;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }
}
