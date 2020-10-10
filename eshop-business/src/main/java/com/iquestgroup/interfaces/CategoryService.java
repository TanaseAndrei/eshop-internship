package com.iquestgroup.interfaces;

import com.iquestgroup.dtos.CategoryDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;

import java.util.List;

/**
 * Interface for the Category Service
 */
public interface CategoryService {

    /**
     * Method that returns a List containing all Category entities which are top-level categories, mapped
     * to DTOs
     *
     * @return List of all top-level categories mapped to DTOs
     * @throws InternalServerErrorException Is thrown if while querying for the entities, another
     *                                      Exception is thrown
     */
    List<CategoryDTO> getAllTopLevelCategories() throws InternalServerErrorException;

    /**
     * Method that returns a List containing all Category entities which are direct subcategories of
     * the category whose ID matches the value passed as a parameter, mapped to DTOs
     *
     * @param parentCategoryID The ID of the parent-category
     * @return List containing all subcategories of said parent-category mapped to DTOs
     * @throws InternalServerErrorException Is thrown if while querying for the entities, another Exception
     *                                      is thrown
     */
    List<CategoryDTO> getAllSubcategoriesOfParentCategory(int parentCategoryID) throws InternalServerErrorException;
}