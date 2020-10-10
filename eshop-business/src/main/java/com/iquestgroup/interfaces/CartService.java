package com.iquestgroup.interfaces;

import com.iquestgroup.dtos.CartDTO;
import com.iquestgroup.dtos.CartItemDTO;
import com.iquestgroup.exceptions.InsufficientStockException;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;

import java.util.List;

/**
 * Interface that provides the service functionality of a Cart.
 */
public interface CartService {

    /**
     * Returns an instance of CartDTO representing a representation of an
     * existing cart.
     *
     * @param name name of the customer
     * @return cart of the user
     * @throws NotFoundException if the cart does not exist
     */
    CartDTO getCart(String name) throws NotFoundException;

    /**
     * Returns a list of CartItemDTOs representing the cart items from a
     * specific cart.
     *
     * @param username name of the customer where you want to retrieve items
     * @return list of all items from the targeted cart
     * @throws NotFoundException if the cart does not exist
     */
    List<CartItemDTO> getItems(String username) throws NotFoundException;

    /**
     * Add a product to cart.
     *
     * @param productId id of the desired product to add
     * @param username    name of the customer where you want to add a product
     * @throws InternalServerErrorException if there is an error in the persistence
     *                                      layer
     * @throws NotFoundException            if the cart or the product do no exist
     */
    CartDTO addProduct(long productId, String username) throws InternalServerErrorException, NotFoundException, InsufficientStockException;

    /**
     * Delete a product from cart.
     *
     * @param productId id of the desired product to add
     * @param username    name of the customer where you want to add a product
     * @throws InternalServerErrorException if there is an error in the persistence
     *                                      layer
     * @throws NotFoundException            if the cart or the product do no exist
     */
    CartDTO deleteProduct(long productId, String username) throws InternalServerErrorException, NotFoundException;
}
