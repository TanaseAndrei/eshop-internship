package com.iquestgroup.interfaces;

import com.iquestgroup.exceptions.DaoException;

/**
 * Interface for a Data Access Object,can be used for a specified domain model. Provides the four
 * basic CRUD methods, create, read, update and delete.
 *
 * @param <T> the domain object the instance will be used for.
 */
public interface GenericDao<T> {
    /**
     * Adds the entity to the datastore, and assigns it an id.
     *
     * @param t entity to be added.
     */
    T create(T t) throws DaoException;

    /**
     * Gets the entity with the specified id from the datastore.
     *
     * @return the entity that was found, or null if no entity with the given id was found
     */
    T find(Object id) throws DaoException;

    /**
     * Updates the entity already saved in the data store.
     *
     * @return the updated entity.
     */
    T update(T t) throws DaoException;

    /**
     * Deletes the entity with the specified id from the data store.
     *
     * @param id
     */
    void delete(Object id) throws DaoException;

}
