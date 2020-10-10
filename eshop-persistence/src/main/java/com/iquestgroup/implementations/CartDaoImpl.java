package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.NoCartFoundException;
import com.iquestgroup.interfaces.CartDao;
import com.iquestgroup.models.Cart;
import com.iquestgroup.models.CartItem;
import com.iquestgroup.models.Cart_;
import com.iquestgroup.models.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class CartDaoImpl extends GenericDaoImpl<Cart> implements CartDao {
    private static final Logger logger = LogManager.getLogger(CartDaoImpl.class);

    public CartDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public Cart getCart(String name) throws NoCartFoundException {
        Cart resultedCart;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);
        Root<Cart> cart = criteriaQuery.from(Cart.class);
        cart.fetch(Cart_.listOfCartItems, JoinType.LEFT);
        Join<Cart, Customer> customer = cart.join("customer");
        ParameterExpression<String> nameParameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.select(cart).where(criteriaBuilder.equal(customer.get("username"), nameParameter));
        TypedQuery<Cart> typedQuery = entityManager.createQuery(criteriaQuery);

        try {
            resultedCart = typedQuery.setParameter(nameParameter, name).getSingleResult();
        } catch (NoResultException noResultException) {
            logger.error(noResultException.getMessage(), noResultException);
            throw new NoCartFoundException("The searched cart does no exist!", noResultException);
        } finally {
            entityManager.close();
        }

        return resultedCart;
    }

    @Override
    public List<CartItem> getItems(String username) throws NoCartFoundException {
        Cart resultedCart;
        List<CartItem> listOfCartItems;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);
        Root<Cart> cart = criteriaQuery.from(Cart.class);
        cart.fetch(Cart_.listOfCartItems, JoinType.LEFT);
        Join<Cart, Customer> customer = cart.join("customer");
        ParameterExpression<String> usernameParameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.select(cart).where(criteriaBuilder.equal(customer.get("username"), usernameParameter));
        TypedQuery<Cart> typedQuery = entityManager.createQuery(criteriaQuery);

        try {
            resultedCart = typedQuery.setParameter(usernameParameter, username).getSingleResult();
            listOfCartItems = resultedCart.getListOfCartItems();
        } catch (NoResultException noResultException) {
            logger.error(noResultException.getMessage(), noResultException);
            throw new NoCartFoundException("The searched cart does not exist!", noResultException);
        } finally {
            entityManager.close();
        }

        return listOfCartItems;
    }

}
