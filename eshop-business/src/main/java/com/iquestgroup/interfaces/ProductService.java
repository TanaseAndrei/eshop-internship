package com.iquestgroup.interfaces;

import com.iquestgroup.dtos.ProductDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;

import java.util.List;

/**
 * Interface for the Product Service
 */
public interface ProductService {

    /**
     * Method that returns the Product entity whose ID matches the value passed as a parameter, mapped to
     * a DTO
     *
     * @param productID The ID which the entity must match
     * @return The matched Product entity mapped to a DTO
     * @throws NotFoundException Is thrown if there is no entity matching the specified ID
     */
    ProductDTO getProductInformation(long productID) throws NotFoundException;

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
    List<ProductDTO> getProductsBelongingToCategoryTransitive(int categoryID) throws InternalServerErrorException;

    /**
     * @param username is the name of the seller
     * @param shopId is the id of the shop from where to take the products
     * @return List containing all products that user @username sell into shop @shopId
     * @throws NotFoundException Is thrown if there is no entity matching
     */
    List<ProductDTO> getProductListForSellerFromShop(String username, Long shopId) throws NotFoundException;
}