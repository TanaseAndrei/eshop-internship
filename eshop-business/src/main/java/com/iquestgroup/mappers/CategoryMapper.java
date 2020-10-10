package com.iquestgroup.mappers;

import com.iquestgroup.dtos.CategoryDTO;
import com.iquestgroup.models.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that helps to map a Category entity to a CategoryDTO
 */
public class CategoryMapper {
    private static final Logger logger = LogManager.getLogger(CategoryMapper.class);

    /**
     * Method that converts the Category entity passed as a parameter to a CategoryDTO and returns the DTO
     *
     * @param category Category entity to be converted
     * @return CategoryDTO to which the Category entity was converted to
     */
    public CategoryDTO convertToDto(Category category) {
        logger.debug("Mapping {} to a CategoryDTO", category);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryID(category.getCategoryID());
        categoryDTO.setCategoryName(category.getCategoryName());

        logger.info("Successfully mapped {} to {}", category, categoryDTO);
        return categoryDTO;
    }

    /**
     * Method that converts all Category entities from a List passed as a parameter to CategoryDTOs and
     * returns a new List containing said DTOs
     *
     * @param categoryList List containing Category entities that are to be converted
     * @return List containing CategoryDTOs to which the Category entities were converted to
     */
    public List<CategoryDTO> convertToDto(List<Category> categoryList) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            logger.debug("Trying to convert and then add {} to the CategoryDTO List", category);
            categoryDTOList.add(convertToDto(category));
        }

        logger.info("Successfully converted the Categories List to a CategoryDTO List");
        return categoryDTOList;
    }
}