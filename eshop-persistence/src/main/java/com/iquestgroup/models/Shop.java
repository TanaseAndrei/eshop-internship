package com.iquestgroup.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopId", updatable = false, nullable = false)
    private int shopId;

    @Column(name = "shop_address", updatable = false, nullable = false)
    private String shopAddress;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Product> stockProducts = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    })
    @JoinTable(name = "shop_seller",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "seller_id")
    )
    private List<Seller> sellers = new ArrayList<>();

    public void addSeller(Seller seller) {
        sellers.add(seller);
    }

    public Shop() {
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public List<Product> getStockedProducts() {
        return stockProducts;
    }

    public void setStockedProducts(List<Product> stockedProducts) {
        this.stockProducts = stockedProducts;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", shopAddress='" + shopAddress + '\'' +
                '}';
    }

}
