package com.iquestgroup.controllers;

import com.iquestgroup.decoder.Base64Decoder;
import com.iquestgroup.dtos.ProductDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.services.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Provides the full set of resources applicable to a list of entities belonging to the Product class
 */
@Path("/products")
public class ProductsController {
    private static final Logger logger = LogManager.getLogger(ProductsController.class);

    /**
     * Method that returns a List containing all Products belonging to a given parent category or one of
     * the parent's category subcategories
     *
     * @param categoryID The ID of the parent category
     * @return List containing all Products belonging to a given parent category or one of
     * the parent's category subcategories
     * @throws InternalServerErrorException Is thrown if the underlying Service throws an error
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDTO> getProductsBelongingToCategory(@QueryParam("categoryID") int categoryID)
            throws InternalServerErrorException {
        logger.debug("Calling ProductService to return all products belonging to category with " +
                "ID {}", categoryID);

        return new ProductServiceImpl().getProductsBelongingToCategoryTransitive(categoryID);
    }

    /**
     * This method return a list of products belonging to a seller
     *
     * @param httpServletRequest is the request from user
     * @param shopId             is the id of the shop from where to get products
     * @return a list of products from a specific shop belonging to the authenticated user
     * @throws NotFoundException it is thrown if there is no product to return
     */
    @GET
    @Path("/seller/{shopId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDTO> getProductsForLoggedInSellerFromShop(@Context HttpServletRequest httpServletRequest, @PathParam("shopId") Long shopId)
            throws NotFoundException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        return new ProductServiceImpl().getProductListForSellerFromShop(username, shopId);
    }
}