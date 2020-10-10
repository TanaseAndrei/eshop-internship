package com.iquestgroup.interfaces;

import com.iquestgroup.exceptions.QueryException;
import com.iquestgroup.models.Category;

import java.util.List;

/**
 * Interface with specific methods for the Category entity.
 */
public interface CategoryDao extends GenericDao<Category> {

    /**
     * Method that returns all Categories whose parent category matches the Category passed to the method
     * as a parameter
     *
     * @param parentCategory The parent Category for which we want to return all children Categories
     * @return A List containing all Categories whose parent Category is parentCategory
     */
    List<Category> getAllCategoriesWithGivenParentCategory(Category parentCategory) throws QueryException;

    /**
     * Method that returns a List containing all Category entities which are top-level categories
     *
     * @return List of all top-level categories
     * @throws QueryException Is thrown if while querying for the entities, another Exception is thrown
     */
    List<Category> getAllTopLevelCategories() throws QueryException;

    /**
     * Method that returns a List containing all Category entities which are direct subcategories of
     * the category whose ID matches the value passed as a parameter
     *
     * @param parentCategoryID The ID of the parent-category
     * @return List containing all subcategories of said parent-category
     * @throws QueryException Is thrown if while querying for the entities, another Exception is thrown
     */
    List<Category> getAllSubcategoriesOfParentCategory(int parentCategoryID) throws QueryException;
}