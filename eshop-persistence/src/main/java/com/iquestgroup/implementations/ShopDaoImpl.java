package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.ShopNotFoundException;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.interfaces.ShopDao;
import com.iquestgroup.models.Product;
import com.iquestgroup.models.Shop;
import com.iquestgroup.models.Shop_;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ShopDaoImpl extends GenericDaoImpl<Shop> implements ShopDao {

    public ShopDaoImpl() {
        super(CustomEntityManagerFactory.getInstance().getEntityManager());
    }

    @Override
    public List<Product> loadInventoryByAddress(String address) throws ShopNotFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shop> query = criteriaBuilder.createQuery(Shop.class);
        Root<Shop> shops = query.from(Shop.class);
        query.select(shops);
        query.where(criteriaBuilder.equal(shops.get("shopAddress"), address));
        TypedQuery<Shop> typedQuery = entityManager.createQuery(query);
        try {
            Shop shop = typedQuery.getSingleResult();
            return shop.getStockedProducts();
        } catch (NoResultException exception) {
            throw new ShopNotFoundException("There is no shop matching the search input", exception);
        } finally {
            entityManager.close();
        }

    }

    @Override
    public Shop getShopById(long id) throws ShopNotFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shop> criteriaQuery = criteriaBuilder.createQuery(Shop.class);
        Root<Shop> root = criteriaQuery.from(Shop.class);
        root.fetch(Shop_.SELLERS, JoinType.LEFT);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("shopId"), id));
        TypedQuery<Shop> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException exception) {
            throw new ShopNotFoundException("There is no shop matching the search input", exception);
        } finally {
            entityManager.close();
        }
    }

    public List<Product> getAllProductsInStock(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> products = query.from(Product.class);
        query.select(products)
                .where(criteriaBuilder.equal(products.get("shop").get("shopId"), id))
                .where(criteriaBuilder.greaterThan(products.get("stock"), 0));
        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        List<Product> productsInStock = new ArrayList<>(typedQuery.getResultList());
        entityManager.close();
        return productsInStock;
    }
}
