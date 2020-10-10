package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.DaoException;
import com.iquestgroup.interfaces.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    protected final EntityManager entityManager;
    private Class<T> type;

    public GenericDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        type = getClassType();
    }

    @Override
    public T create(final T entity) throws DaoException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (RuntimeException runtimeException) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Error saving a new entity, rolling back",runtimeException);
        } finally {
            entityManager.close();
        }
        return entity;
    }

    @Override
    public void delete(final Object id) throws DaoException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.getReference(type, id));
            transaction.commit();
        } catch (RuntimeException runtimeException) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Error deleting entity, rolling back",runtimeException);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T find(final Object id) throws DaoException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T found = entityManager.find(type, id);
            transaction.commit();
            return found;
        } catch (RuntimeException runtimeException) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Error querying for entity, rolling back",runtimeException);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T update(final T entity) throws DaoException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T updated = entityManager.merge(entity);
            transaction.commit();
            return updated;
        } catch (RuntimeException runtimeException) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Error updating entity, rolling back",runtimeException);
        } finally {
            entityManager.close();
        }

    }

    private Class<T> getClassType() {
        Type superclassType = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclassType;
        type = (Class) parameterizedType.getActualTypeArguments()[0];
        return type;
    }
}