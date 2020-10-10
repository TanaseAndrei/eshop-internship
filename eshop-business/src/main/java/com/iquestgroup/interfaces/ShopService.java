package com.iquestgroup.interfaces;

import com.iquestgroup.dtos.ShopDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.exceptions.ServiceException;
import com.iquestgroup.models.Seller;
import com.iquestgroup.models.Shop;

/**
 * Interface for the Shop service, exposes three methods, allowing the creation of a new Shop Entity,
 * the querry for a Shop based on a given ID and the updating of a Shop Entity.
 */
public interface ShopService {
    /**
     * Creates a new Shop based on data received from the HTTP client.
     * @param newShop ShopDTO containing information to be persisted in the database.
     * @throws InternalServerErrorException thrown if the registration service failed to persist the Shop entity.
     */
    void createShop(ShopDTO newShop) throws InternalServerErrorException;

    /**
     * Queries the database for a Shop based on a given ID.
     * @param id of the shop searched
     * @return the Entity with the queried ID.
     * @throws NotFoundException if no entity with the queried ID was found
     */
    Shop findShop(long id) throws NotFoundException;

    /**
     * Updates the Entity persisted in the database.
     * @param shop entity to be updated
     * @throws InternalServerErrorException if there's an error persisting the updated entity.
     */
    void updateShop(Shop shop) throws InternalServerErrorException;

    ShopDTO bindShopToSeller(long shopId, long sellerId) throws NotFoundException, InternalServerErrorException;
}
