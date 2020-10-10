package com.iquestgroup.interfaces;

import com.iquestgroup.exceptions.DaoException;
import com.iquestgroup.models.User;

import java.util.List;

/**
 * Interface with specific methods for user.
 *
 * @param <M> represent the type of user
 */
public interface UserDao<M extends User> extends GenericDao<User> {
    /**
     * @return a list, based on the generic type, with all entries
     * of that type.
     */
    List<M> getUserList() throws DaoException;

    /**
     * @param id represents the id of user to be found
     * @return the user that has id equal with @param id
     */
    M getUserById(long id) throws DaoException;

    /**
     * @param username represents the username of the user to be found
     * @return the user that has username equal with @param username
     */
    M getUserByUsername(String username) throws DaoException;
}
