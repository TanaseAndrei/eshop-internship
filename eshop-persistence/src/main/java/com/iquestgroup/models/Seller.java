package com.iquestgroup.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seller")
public class Seller extends User {

    @ManyToMany(mappedBy = "sellers")
    private List<Shop> shops = new ArrayList<>();

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "shops=" + shops +
                '}';
    }

}
