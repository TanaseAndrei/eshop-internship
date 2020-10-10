package com.iquestgroup.interfaces;

import com.iquestgroup.dtos.UserDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.exceptions.ServiceException;
import com.iquestgroup.exceptions.ValidationException;
import com.iquestgroup.models.Seller;
import com.iquestgroup.models.User;

/**
 * Interface for the User Service. Exposes two methods, allowing the registration of a new User Entity or
 * the finding of a persisted entity
 */
public interface UserService {

    /**
     * Persists a new user in the database, based on data received from the HTTP client.
     *
     * @param newUser UserDTO containing information to be persisted in the database.
     * @throws ValidationException thrown if User data failed to pass validation constraints.
     * @throws ServiceException    thrown if the registration service failed to persist the User entity.
     */
    UserDTO register(UserDTO newUser) throws ValidationException, ServiceException;

    /**
     * Queries the database for a Seller based on a given ID.
     * @param id of the shop searched
     * @return the Entity with the queried ID.
     * @throws NotFoundException if no entity with the queried ID was found
     */
    Seller findSeller(long id) throws NotFoundException;

    /**
     * Updates the Entity persisted in the database.
     * @param seller entity to be updated
     * @throws InternalServerErrorException if there's an error persisting the updated entity.
     * @return
     */
    User updateSeller(Seller seller) throws InternalServerErrorException;
}
