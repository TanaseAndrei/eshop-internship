package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.NoUserFoundException;
import com.iquestgroup.exceptions.ShopNotFoundException;
import com.iquestgroup.interfaces.SellerDao;
import com.iquestgroup.models.Seller;
import com.iquestgroup.models.Seller_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public class SellerDaoImpl extends UserDaoImpl<Seller> implements SellerDao {
    public SellerDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public Seller getUserById(long id) throws NoUserFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Seller> criteriaQuery = criteriaBuilder.createQuery(Seller.class);
        Root<Seller> root = criteriaQuery.from(Seller.class);
        root.fetch(Seller_.SHOPS, JoinType.LEFT);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Seller> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException exception) {
            throw new NoUserFoundException("There is no seller matching the search input", exception);
        } finally {
            entityManager.close();
        }
    }
}
