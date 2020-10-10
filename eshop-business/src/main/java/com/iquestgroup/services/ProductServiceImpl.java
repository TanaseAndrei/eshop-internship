package com.iquestgroup.services;

import com.iquestgroup.dtos.ProductDTO;
import com.iquestgroup.exceptions.*;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.implementations.CategoryDaoImpl;
import com.iquestgroup.implementations.ProductDaoImpl;
import com.iquestgroup.interfaces.CategoryDao;
import com.iquestgroup.interfaces.ProductDao;
import com.iquestgroup.interfaces.ProductService;
import com.iquestgroup.mappers.ProductMapper;
import com.iquestgroup.models.Category;
import com.iquestgroup.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Implementation for the Product Service interface
 */
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    /**
     * Method that returns the Product entity whose ID matches the value passed as a parameter, mapped to
     * a DTO
     *
     * @param productID The ID which the entity must match
     * @return The matched Product entity mapped to a DTO
     * @throws NotFoundException Is thrown if there is no entity matching the specified ID
     */
    @Override
    public ProductDTO getProductInformation(long productID) throws NotFoundException {
        ProductDao productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance()
                .getEntityManager());
        try {
            logger.debug("Calling ProductDAO to get the product with ID {}", productID);
            Product product = productDao.getProduct(productID);
            ProductMapper productMapper = new ProductMapper();

            logger.debug("Returning a List containing the product with ID {} mapped to a DTO", productID);
            return productMapper.convertToDto(product);
        } catch (NoProductFoundException noProductFoundException) {
            throw new NotFoundException(noProductFoundException.getMessage(), noProductFoundException);
        }
    }

    /**
     * Method that returns a List containing all products that belong to the Category entity whose ID
     * matched the one passed as a parameter, or to a subcategory of the said Category entity, mapped to
     * DTOs
     *
     * @param categoryID The ID of the Category entity
     * @return List containing all Products belonging to said Category or subcategories of said Category
     * mapped to DTOs
     * @throws InternalServerErrorException Is thrown if while querying for the entities, another Exception
     *                                      is thrown
     */
    @Override
    public List<ProductDTO> getProductsBelongingToCategoryTransitive(int categoryID) throws InternalServerErrorException {
        Stack<Category> transitiveCategoriesList = new Stack<>();
        List<Category> subcategories;
        ProductDao productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance()
                .getEntityManager());
        CategoryDao categoryDao = new CategoryDaoImpl(CustomEntityManagerFactory.getInstance()
                .getEntityManager());

        try {
            logger.debug("Calling ProductDAO to get a List of products belonging to category " +
                    "with ID {}", categoryID);
            List<Product> productsBelongingToCategoryTransitive = new ArrayList<>
                    (productDao.getProductsBelongingToCategoryNontransitive(categoryID));

            logger.debug("Calling CategoryDAO to get a List of subcategories of category " +
                    "with ID {}", categoryID);
            subcategories = categoryDao.getAllSubcategoriesOfParentCategory(categoryID);

            for (Category subcategory : subcategories) {
                logger.debug("Pushing {} to the stack", subcategory);
                transitiveCategoriesList.push(subcategory);
            }

            while (!transitiveCategoriesList.empty()) {
                Category category = transitiveCategoriesList.pop();
                logger.debug("Popped {} from the stack", category);

                categoryDao = new CategoryDaoImpl(CustomEntityManagerFactory.getInstance()
                        .getEntityManager());
                logger.debug("Calling CategoryDAO to get a List of subcategories of category " +
                        "with ID {}", categoryID);
                subcategories = categoryDao.getAllSubcategoriesOfParentCategory(category.getCategoryID());
                for (Category subcategory : subcategories) {
                    logger.debug("Pushing {} to the stack", subcategory);
                    transitiveCategoriesList.push(subcategory);
                }

                productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance()
                        .getEntityManager());
                logger.debug("Calling ProductDAO to get a List of products belonging to category " +
                        "with ID {}", categoryID);
                productsBelongingToCategoryTransitive.addAll(productDao.getProductsBelongingToCategoryNontransitive(category.getCategoryID()));
            }

            ProductMapper productMapper = new ProductMapper();

            logger.debug("Returning a List containing all prodacts belonging to category with ID {}" +
                    " treansitively, mapped to DTOs", categoryID);
            return new ArrayList<>(productMapper.convertToDto(productsBelongingToCategoryTransitive));
        } catch (QueryException queryException) {
            throw new InternalServerErrorException("An error has occurred", queryException);
        }
    }

    /**
     * @param username is the name of the seller
     * @param shopId   is the id of the shop from where to take the products
     * @return a list of products belonging to the logged in user from specific shop
     * @throws NotFoundException Is thrown if there is no entity matching
     */
    @Override
    public List<ProductDTO> getProductListForSellerFromShop(String username, Long shopId) throws NotFoundException {
        ProductDaoImpl productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        List<Product> productList;
        try {
            productList = productDao.getProductListFromSeller(username, shopId);
            ProductMapper productMapper = new ProductMapper();

            return productMapper.convertToDto(productList);
        } catch (NoProductFoundException | ShopNotFoundException noProductFoundException) {
            throw new NotFoundException(noProductFoundException.getMessage(), noProductFoundException);
        }
    }
}