package com.iquestgroup.factories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton implementation of a EntityManagerFactory
 */
public class CustomEntityManagerFactory {

    private static CustomEntityManagerFactory customEntityManagerFactory;
    private final EntityManagerFactory entityManagerFactory;

    private CustomEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("eshop");
    }

    /**
     * Creates a new instance of CustomEntityManagerFactory in case one doesn't already exist by
     * calling the private constructor and returns it. If one instance already exists, returns
     * said instance
     */
    public static CustomEntityManagerFactory getInstance() {
        if (customEntityManagerFactory == null) {
            customEntityManagerFactory = new CustomEntityManagerFactory();
        }
        return customEntityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
