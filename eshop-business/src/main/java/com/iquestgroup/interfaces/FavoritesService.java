package com.iquestgroup.interfaces;

import com.iquestgroup.dtos.FavoritesDTO;
import com.iquestgroup.dtos.FavoritesItemDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;

import java.util.List;

/**
 * Interface that provides the service functionality of Favorites.
 */
public interface FavoritesService {

    /**
     * Returns a list of FavoritesItemDTOs representing the favorites items from a
     * favorites cart.
     *
     * @param username name of the customer
     * @return list of all items from the targeted favorites cart
     * @throws NotFoundException if the the favorites cart does not exist
     */
    List<FavoritesItemDTO> getFavoritesItems(String username) throws NotFoundException;

    /**
     * Returns an instance of FavoritesDTO representing a representation of an
     * existing favorites cart.
     *
     * @param username name of the customer
     * @return  favorites cart of a customer
     * @throws NotFoundException if the the favorites cart does not exist
     */
    FavoritesDTO getFavoritesCart(String username) throws NotFoundException;

    /**
     * Add a product to a favorites cart.
     *
     * @param productId id of the desired product to add
     * @param username name of the customer where you want to add a product
     * @throws NotFoundException if the product does not exist or if the favorites cart
     *                           does not exist
     * @throws InternalServerErrorException if there is an error in the persistence layer
     */
    FavoritesDTO addProduct(long productId, String username) throws NotFoundException, InternalServerErrorException;

    /**
     * Delete a product from the favorites cart.
     *
     * @param productId id of the desired product to add
     * @param username name of the customer where you want to add a product
     * @throws NotFoundException if the product does not exist or if the favorites cart
     *                           does not exist
     * @throws InternalServerErrorException if there is an error in the persistence layer
     */
    FavoritesDTO deleteProduct(long productId, String username) throws NotFoundException, InternalServerErrorException;
}
