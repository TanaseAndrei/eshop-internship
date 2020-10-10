package com.iquestgroup.implementations;

import com.iquestgroup.interfaces.CustomerDao;
import com.iquestgroup.models.Customer;

import javax.persistence.EntityManager;

public class CustomerDaoImpl extends UserDaoImpl<Customer> implements CustomerDao {
    public CustomerDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
