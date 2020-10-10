package com.iquestgroup.implementations;

import com.iquestgroup.interfaces.OrderDao;
import com.iquestgroup.models.Order;

import javax.persistence.EntityManager;

public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao {

    public OrderDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

}
