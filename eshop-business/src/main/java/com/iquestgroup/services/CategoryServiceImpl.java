package com.iquestgroup.services;

import com.iquestgroup.dtos.CategoryDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.QueryException;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.implementations.CategoryDaoImpl;
import com.iquestgroup.interfaces.CategoryDao;
import com.iquestgroup.interfaces.CategoryService;
import com.iquestgroup.mappers.CategoryMapper;
import com.iquestgroup.models.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for the Category Service interface
 */
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);

    /**
     * Method that returns a List containing all Category entities which are top-level categories, mapped
     * to DTOs
     *
     * @return List of all top-level categories mapped to DTOs
     * @throws InternalServerErrorException Is thrown if while querying for the entities, another
     *                                      Exception is thrown
     */
    @Override
    public List<CategoryDTO> getAllTopLevelCategories() throws InternalServerErrorException {
        CategoryDao categoryDao = new CategoryDaoImpl(CustomEntityManagerFactory.getInstance()
                .getEntityManager());
        try {
            logger.debug("Calling CategoryDao to get all top-level categories");
            List<Category> topLevelCategories = categoryDao.getAllTopLevelCategories();
            CategoryMapper categoryMapper = new CategoryMapper();

            logger.debug("Returning a List containing all top-level categories mapped to DTOs");
            return new ArrayList<>(categoryMapper.convertToDto(topLevelCategories));
        } catch (QueryException queryException) {
            throw new InternalServerErrorException("An error has occurred", queryException);
        }
    }

    /**
     * Method that returns a List containing all Category entities which are direct subcategories of
     * the category whose ID matches the value passed as a parameter, mapped to DTOs
     *
     * @param parentCategoryID The ID of the parent-category
     * @return List containing all subcategories of said parent-category mapped to DTOs
     * @throws InternalServerErrorException Is thrown if while querying for the entities, another Exception
     *                                      is thrown
     */
    @Override
    public List<CategoryDTO> getAllSubcategoriesOfParentCategory(int parentCategoryID) throws InternalServerErrorException {
        CategoryDao categoryDao = new CategoryDaoImpl(CustomEntityManagerFactory.getInstance()
                .getEntityManager());
        try {
            logger.debug("Calling CategoryDao to get all subcategories of parent-category with ID {}",
                    parentCategoryID);
            List<Category> subcategories = categoryDao.getAllSubcategoriesOfParentCategory(parentCategoryID);
            CategoryMapper categoryMapper = new CategoryMapper();

            logger.debug("Returning a List containing all subcategories of parent-category with ID {}, " +
                    "mapped to DTOs", parentCategoryID);
            return new ArrayList<>(categoryMapper.convertToDto(subcategories));
        } catch (QueryException queryException) {
            throw new InternalServerErrorException("An error has occurred", queryException);
        }
    }
}