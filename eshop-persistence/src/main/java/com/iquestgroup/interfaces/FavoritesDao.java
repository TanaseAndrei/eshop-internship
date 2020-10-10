package com.iquestgroup.interfaces;

import com.iquestgroup.exceptions.NoCustomerFoundException;
import com.iquestgroup.exceptions.NoFavoritesCartFoundException;
import com.iquestgroup.models.Favorites;
import com.iquestgroup.models.FavoritesItem;

import java.util.List;

/**
 * Interface that provides functionality for the communication between
 * the customer's favorites cart and the database.
 */
public interface FavoritesDao extends GenericDao<Favorites> {

    /**
     * Returns an instance of <code>Favorites</code> of an existing customer
     *
     * @param username name of an existing customer
     * @return favorite's cart of the customer targeted by the given name
     * @throws NoFavoritesCartFoundException if the searched cart does not exist
     */
    Favorites getFavoritesCart(String username) throws NoFavoritesCartFoundException;

    /**
     * Returns a list with the items from the favorites cart of an existing customer.
     *
     * @param username the name of the customer
     * @return list with his favorite items
     * @throws NoFavoritesCartFoundException if the searched customer does not exist
     */
    List<FavoritesItem> getFavoritesItems(String username) throws NoFavoritesCartFoundException;
}
