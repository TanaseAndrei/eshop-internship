package com.iquestgroup.dtos;

/**
 * Model used to encapsulate data of a FavoritesItem model entity.
 */
public class FavoritesItemDTO {

    private String productName;

    private String brandName;

    private double price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
