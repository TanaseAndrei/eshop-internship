package com.iquestgroup.interfaces;

import com.iquestgroup.exceptions.NoProductFoundException;
import com.iquestgroup.exceptions.QueryException;
import com.iquestgroup.exceptions.ShopNotFoundException;
import com.iquestgroup.models.Product;

import java.util.List;

/**
 * Interface with specific methods for the Product entity.
 */
public interface ProductDao extends GenericDao<Product> {

    /**
     * Method that returns a List containing all products whose stock is less than the threshold
     * specified by the parameter
     *
     * @param stockThreshold An int representing the threshold
     * @return A List containing all products that qualify for the specified condition
     */
    List<Product> getProductsWithStockLessThan(int stockThreshold) throws QueryException;

    /**
     * Method that returns the Product entity whose ID matches the value passed as a parameter
     *
     * @param productID The ID which the entity must match
     * @return The matched Product entity
     * @throws NoProductFoundException Is thrown if there is no entity matching the specified ID
     */
    Product getProduct(long productID) throws NoProductFoundException;

    /**
     * Method that returns a List containing all products that belong strictly (non-transitive) to the
     * Category entity whose ID matched the one passed as a parameter
     *
     * @param categoryID The ID of the Category entity
     * @return List containing all Products belonging to said Category
     * @throws QueryException Is thrown if while querying for the entities, another Exception is thrown
     */
    List<Product> getProductsBelongingToCategoryNontransitive(int categoryID) throws QueryException;

    /**
     * @param username is the name of the logged seller
     * @param shopId is the id of the shop where to take the products
     * @return the products of a seller from a specific shop
     */
    List<Product> getProductListFromSeller(String username, Long shopId) throws NoProductFoundException, ShopNotFoundException;
}