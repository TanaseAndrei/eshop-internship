package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.NoProductFoundException;
import com.iquestgroup.exceptions.QueryException;
import com.iquestgroup.exceptions.ShopNotFoundException;
import com.iquestgroup.interfaces.ProductDao;
import com.iquestgroup.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of the ProductDao interface
 */
public class ProductDaoImpl extends GenericDaoImpl<Product> implements ProductDao {
    private static final Logger logger = LogManager.getLogger(ProductDaoImpl.class);

    public ProductDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Method that returns a List containing all products whose stock is less than the threshold
     * specified by the parameter
     *
     * @param stockThreshold An int representing the threshold
     * @return A List containing all products that qualify for the specified condition
     */
    @Override
    public List<Product> getProductsWithStockLessThan(int stockThreshold) throws QueryException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> from = criteriaQuery.from(Product.class);
        Predicate predicate = criteriaBuilder.lessThan(from.get(Product_.stock), stockThreshold);

        try {
            logger.debug("Querying for products with stock less than {}", stockThreshold);

            return entityManager.createQuery(criteriaQuery.select(from).where(predicate)).getResultList();
        } catch (Exception exception) {
            throw new QueryException(exception);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Method that returns the Product entity whose ID matches the value passed as a parameter
     *
     * @param productID The ID which the entity must match
     * @return The matched Product entity
     * @throws NoProductFoundException Is thrown if there is no entity matching the specified ID
     */
    @Override
    public Product getProduct(long productID) throws NoProductFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> from = criteriaQuery.from(Product.class);
        Predicate predicate = criteriaBuilder.equal(from.get(Product_.productID), productID);

        try {
            logger.debug("Querying for product with ID {}", productID);

            return entityManager.createQuery(criteriaQuery.select(from)
                    .where(predicate)).getSingleResult();
        } catch (NoResultException noResultException) {
            throw new NoProductFoundException("There is no product matching the search ID",
                    noResultException);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Method that returns a List containing all products that belong strictly (non-transitive) to the
     * Category entity whose ID matched the one passed as a parameter
     *
     * @param categoryID The ID of the Category entity
     * @return List containing all Products belonging to said Category
     * @throws QueryException Is thrown if while querying for the entities, another Exception is thrown
     */
    @Override
    public List<Product> getProductsBelongingToCategoryNontransitive(int categoryID) throws QueryException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> products = criteriaQuery.from(Product.class);
        Join<Product, Category> categories = products.join(Product_.category);

        Predicate predicate = criteriaBuilder.equal(categories.get(Category_.categoryID), categoryID);

        try {
            logger.debug("Querying for products belonging to category with ID {}", categoryID);

            return entityManager.createQuery(criteriaQuery.select(products).where(predicate)).getResultList();
        } catch (Exception exception) {
            throw new QueryException(exception);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Product> getProductListFromSeller(String username, Long shopId) throws NoProductFoundException, ShopNotFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shop> criteriaQuery = criteriaBuilder.createQuery(Shop.class);

        Root<Shop> shopRoot = criteriaQuery.from(Shop.class);
        Join<Shop, Seller> seller = shopRoot.join("sellers");
        ParameterExpression<String> usernameParameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(shopRoot).where(criteriaBuilder.equal(seller.get("username"), usernameParameter));
        TypedQuery<Shop> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(usernameParameter, username);

        Shop shop;
        try {
            shop = typedQuery.getResultList()
                    .stream()
                    .filter(s -> s.getShopId() == shopId)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            throw new ShopNotFoundException("There is no shop matching the searching.", e);
        }

        CriteriaQuery<Product> cq = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        ParameterExpression<Shop> parameterExpression = criteriaBuilder.parameter(Shop.class);
        cq.select(productRoot).where(criteriaBuilder.equal(productRoot.get("shop"), parameterExpression));

        TypedQuery<Product> productTypedQuery = entityManager.createQuery(cq);
        productTypedQuery.setParameter(parameterExpression, shop);

        try {
            return productTypedQuery.getResultList();
        } catch (NoResultException noResultException) {
            throw new NoProductFoundException("There is no product matching the searching.", noResultException);
        } finally {
            entityManager.close();
        }
    }
}