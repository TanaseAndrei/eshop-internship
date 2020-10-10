package com.iquestgroup.controllers;

import com.iquestgroup.dtos.CategoryDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.services.CategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Provides the full set of resources applicable to a list of entities belonging to the Category class
 */
@Path("/categories")
public class CategoriesController {
    private static final Logger logger = LogManager.getLogger(CategoriesController.class);

    /**
     * Method that returns a List containing all top-level categories
     *
     * @return List of all top-level categories
     * @throws InternalServerErrorException Is thrown if the underlying Service throws an error
     */
    @GET
    @Path("/toplevelcategories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoryDTO> getTopLevelCategories() throws InternalServerErrorException {
        logger.debug("Calling CategoryService to get all top-level categories");

        return new CategoryServiceImpl().getAllTopLevelCategories();
    }

    /**
     * Method that returns a List containing all subcategories of a category
     *
     * @param parentCategoryID The ID of the category whose subcategories are to be returned
     * @return List of all subcategories of the parent category
     * @throws InternalServerErrorException Is thrown if the underlying Service throws an error
     */
    @GET
    @Path("/{parentCategoryID}/subcategories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoryDTO> getSubcategories(@PathParam("parentCategoryID") int parentCategoryID)
            throws InternalServerErrorException {
        logger.debug("Calling CategoryService to get all subcategories belonging to the " +
                "parent category with ID {}", parentCategoryID);

        return new CategoryServiceImpl().getAllSubcategoriesOfParentCategory(parentCategoryID);
    }
}