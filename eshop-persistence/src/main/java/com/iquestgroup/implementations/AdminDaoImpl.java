package com.iquestgroup.implementations;

import com.iquestgroup.interfaces.AdminDao;
import com.iquestgroup.models.Admin;

import javax.persistence.EntityManager;

public class AdminDaoImpl extends UserDaoImpl<Admin> implements AdminDao {
    public AdminDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
