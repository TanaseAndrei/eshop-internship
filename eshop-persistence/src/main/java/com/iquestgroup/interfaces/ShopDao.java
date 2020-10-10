package com.iquestgroup.interfaces;

import com.iquestgroup.exceptions.ShopNotFoundException;
import com.iquestgroup.models.Product;
import com.iquestgroup.models.Shop;

import java.util.List;

/**
 * Interface designed specifically for the Shop type domain object. Provides methods applicable only
 * on said domain object.
 */
public interface ShopDao extends GenericDao<Shop> {

    /**
     * Returns the inventory of a shop based on a given address.
     */
    List<Product> loadInventoryByAddress(String address) throws ShopNotFoundException;

    /**
     * Returns a list of all products in stock in a given shop.
     */
    List<Product> getAllProductsInStock(int id);

    /**
     * Returns a Shop entity given a id.
     */
    Shop getShopById(long id) throws ShopNotFoundException;
}
