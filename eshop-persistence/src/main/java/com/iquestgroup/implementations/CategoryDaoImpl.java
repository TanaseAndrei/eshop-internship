package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.QueryException;
import com.iquestgroup.interfaces.CategoryDao;
import com.iquestgroup.models.Category;
import com.iquestgroup.models.Category_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Implementation of the CategoryDao interface
 */
public class CategoryDaoImpl extends GenericDaoImpl<Category> implements CategoryDao {
    private static final Logger logger = LogManager.getLogger(CategoryDaoImpl.class);

    public CategoryDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Method that return all Categories whose parent category matches the Category passed to the method
     * as a parameter
     *
     * @param parentCategory The parent Category for which we want to return all children Categories
     * @return A List containing all Categories whose parent Category is parentCategory
     */
    @Override
    public List<Category> getAllCategoriesWithGivenParentCategory(Category parentCategory) throws QueryException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);

        Root<Category> from = criteriaQuery.from(Category.class);
        Predicate predicate = criteriaBuilder.equal(from.get("parentCategory"), parentCategory);

        try {
            logger.debug("Querying for categories with given parent category: {}", parentCategory);

            return entityManager.createQuery(criteriaQuery.select(from).where(predicate)).getResultList();
        } catch (Exception exception) {
            throw new QueryException(exception);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Method that returns a List containing all Category entities which are top-level categories
     *
     * @return List of all top-level categories
     * @throws QueryException Is thrown if while querying for the entities, another Exception is thrown
     */
    @Override
    public List<Category> getAllTopLevelCategories() throws QueryException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);

        Root<Category> from = criteriaQuery.from(Category.class);
        Predicate predicate = criteriaBuilder.isNull(from.get(Category_.parentCategory));

        try {
            logger.debug("Querying for top-level categories");

            return entityManager.createQuery(criteriaQuery.select(from).where(predicate)).getResultList();
        } catch (Exception exception) {
            throw new QueryException(exception);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Method that returns a List containing all Category entities which are direct subcategories of
     * the category whose ID matches the value passed as a parameter
     *
     * @param parentCategoryID The ID of the parent-category
     * @return List containing all subcategories of said parent-category
     * @throws QueryException Is thrown if while querying for the entities, another Exception is thrown
     */
    @Override
    public List<Category> getAllSubcategoriesOfParentCategory(int parentCategoryID) throws QueryException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);

        Root<Category> subcategories = criteriaQuery.from(Category.class);
        Join<Category, Category> parentCategories = subcategories.join(Category_.parentCategory);

        Predicate predicate = criteriaBuilder.equal(parentCategories.get(Category_.categoryID), parentCategoryID);

        try {
            logger.debug("Querying for categories belonging to parent category with ID {}", parentCategoryID);

            return entityManager.createQuery(criteriaQuery.select(subcategories).where(predicate)).getResultList();
        } catch (Exception exception) {
            throw new QueryException(exception);
        } finally {
            entityManager.close();
        }
    }
}