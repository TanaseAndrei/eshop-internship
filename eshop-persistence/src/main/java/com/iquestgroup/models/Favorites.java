package com.iquestgroup.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "favorites")
public class Favorites {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "favorites_id")
    private List<FavoritesItem> favoritesItemList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setFavoritesItemList(List<FavoritesItem> favoritesItemList){
        this.favoritesItemList = favoritesItemList;
    }

    public List<FavoritesItem> getFavoritesItemList() { return favoritesItemList; }

}
