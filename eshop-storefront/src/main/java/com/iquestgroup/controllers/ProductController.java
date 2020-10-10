package com.iquestgroup.controllers;

import com.iquestgroup.dtos.ProductDTO;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.services.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Provides the full set of resources applicable to a single entity belonging to the Product class
 */
@Path("/product")
public class ProductController {
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    /**
     * Method that returns a single Product entity whose ID matches productID
     *
     * @param productID The ID after which the Product entity is searched
     * @return The Product entity
     * @throws NotFoundException Is thrown in case no matching Product is found
     */
    @GET
    @Path("/{productID}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDTO getProduct(@PathParam("productID") long productID) throws NotFoundException {
        logger.debug("Calling ProductService to return the product with ID {}", productID);

        return new ProductServiceImpl().getProductInformation(productID);
    }
}