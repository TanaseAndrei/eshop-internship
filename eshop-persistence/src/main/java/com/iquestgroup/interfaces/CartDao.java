package com.iquestgroup.interfaces;

import com.iquestgroup.exceptions.NoCartFoundException;
import com.iquestgroup.exceptions.NoCustomerFoundException;
import com.iquestgroup.exceptions.NoUserFoundException;
import com.iquestgroup.models.Cart;
import com.iquestgroup.models.CartItem;

import java.util.List;

/**
 * Interface that provides functionality for the communication between
 * the customer's cart and the database.
 */
public interface CartDao extends GenericDao<Cart> {

    /**
     * Returns an instance of <code>Cart</code> of an existing customer.
     *
     * @param username name of an existing customer
     * @return cart of the customer targeted by the given name
     * @throws NoCartFoundException if the searched cart does not exist
     */
    Cart getCart(String username) throws NoCartFoundException;

    /**
     * Returns a list with the cart items of an existing customer.
     *
     * @param username name of an existing customer
     * @return list with his cart items
     * @throws NoCartFoundException if the searched cart does not exist
     */
    List<CartItem> getItems(String username) throws NoCartFoundException;

}
