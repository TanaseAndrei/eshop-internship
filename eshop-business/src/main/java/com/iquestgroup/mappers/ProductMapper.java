package com.iquestgroup.mappers;

import com.iquestgroup.dtos.ProductDTO;
import com.iquestgroup.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that helps to map a Product entity to a ProductDTO
 */
public class ProductMapper {
    private static final Logger logger = LogManager.getLogger(ProductMapper.class);

    /**
     * Method that converts the Product entity passed as a parameter to a ProductDTO and returns the DTO
     *
     * @param product Product entity to be converted
     * @return ProductDTO to which the Product entity was converted to
     */
    public ProductDTO convertToDto(Product product) {
        logger.debug("Mapping {} to a ProductDTO", product);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(product.getProductID());
        productDTO.setName(product.getName());
        productDTO.setBrand(product.getBrand());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory().getCategoryName());
        productDTO.setStock(product.getStock());

        logger.info("Successfully mapped {} to {}", product, productDTO);
        return productDTO;
    }

    /**
     * Method that converts all Product entities from a List passed as a parameter to ProductDTOs and
     * returns a new List containing said DTOs
     *
     * @param productList List containing Product entities that are to be converted
     * @return List containing ProductDTOs to which the Product entities were converted to
     */
    public List<ProductDTO> convertToDto(List<Product> productList) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            logger.debug("Trying to convert and then add {} to the ProductDTO List", product);
            productDTOList.add(convertToDto(product));
        }

        logger.info("Successfully converted the Products List to a ProductDTO List");
        return productDTOList;
    }
}