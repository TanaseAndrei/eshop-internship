package com.iquestgroup.utilities.crawlers.implementations;

import com.iquestgroup.exceptions.CrawlerConnectionException;
import com.iquestgroup.exceptions.InvalidProductURLException;
import com.iquestgroup.models.Category;
import com.iquestgroup.models.Product;
import com.iquestgroup.models.Shop;
import com.iquestgroup.utilities.crawlers.interfaces.Crawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Crawler implementation that crawls and scraps products from eMAG
 */
public class EmagProductCrawler implements Crawler<Product> {
    private static final Logger logger = LogManager.getLogger(EmagProductCrawler.class);

    /**
     * Method which returns a List of entities that correspond to the entities displayed at the URLs
     * provided as argument to the method
     *
     * @param entityURLs A List containing the URLs from where the crawler need to scrap the entities
     *                   that are to be returned
     * @return A List containing all entities that have been scrapped from the URLs supplied as a List
     * to the method
     */
    @Override
    public List<Product> getCrawledEntities(List<String> entityURLs) throws
            CrawlerConnectionException, InvalidProductURLException {
        logger.info("Crawling started");

        List<Product> productList = new ArrayList<>();

        for (String entityURL : entityURLs) {
            logger.debug("Adding to the list of crawled products the product from {}", entityURL);
            productList.add(scrapForProduct(entityURL));
        }

        randomizeStock(productList);

        return productList;
    }

    /**
     * Methods that scraps a Product from the provided URL
     *
     * @param productURL The URL of the product page
     * @return The Product entity scraped from the web page
     * @throws CrawlerConnectionException Is thrown if the connection to the URL fails
     * @throws InvalidProductURLException Is thrown if the web page doesn't belong to a product page
     */
    private Product scrapForProduct(String productURL) throws CrawlerConnectionException,
            InvalidProductURLException {
        Document productPage;

        try {
            logger.debug("Trying to connect to {}", productURL);
            productPage = Jsoup.connect(productURL).get();
        } catch (IOException ioException) {
            throw new CrawlerConnectionException("Connection to the provided URL failed", ioException);
        }

        if (!isProductPage(productPage) || !productURL.contains("www.emag.ro")) {
            logger.info("The given URL: {}, was not a valid product page", productURL);
            throw new InvalidProductURLException("The provided product page is not valid");
        }

        return buildProduct(productPage);
    }

    /**
     * Method that parses the product page and return the product's name
     *
     * @param productPage The page of the product
     * @return The product's name
     */
    private String getProductName(Document productPage) {
        logger.debug("Getting product name");
        return productPage.select("h1.page-title").first().text();
    }

    /**
     * Method that parses the product page and return the product's brand name
     *
     * @param productPage The page of the product
     * @return The product's brand name
     */
    private String getProductBrandName(Document productPage) {
        logger.debug("Getting product brand name");
        return productPage.select("div.disclaimer-section > p:first-of-type > a").first()
                .text();
    }

    /**
     * Method that parses the product page and return the product's description
     *
     * @param productPage The page of the product
     * @return The product's description
     */
    private String getProductDescription(Document productPage) {
        logger.debug("Getting product description");
        Elements descriptionElements = productPage.select("#description-body > *");

        StringBuilder description = new StringBuilder();

        for (Element descriptionElement : descriptionElements) {
            description.append(descriptionElement.text());
        }

        return description.toString();
    }

    /**
     * Method that parses the product page and return the product's price
     *
     * @param productPage The page of the product
     * @return The price of the product as a double
     */
    private double getProductPrice(Document productPage) {
        logger.debug("Getting product price");
        BigDecimal firstPricePart = new BigDecimal(productPage.select("p.product-new-price")
                .first().ownText().replace(".", ""));
        BigDecimal secondPricePart = new BigDecimal("0." + productPage.select("p.product-new-price > sup")
                .first().text());

        return firstPricePart.add(secondPricePart).doubleValue();
    }

    /**
     * Method that parses the product page and return the product's Category
     *
     * @param productPage The page of the product
     * @return The Category associated to the product
     */
    private Category getProductCategory(Document productPage) {
        logger.debug("Getting product category");
        Elements categoriesList = productPage.select("ol.breadcrumb").first().select("li");

        Category parentCategory = new Category();
        parentCategory.setCategoryName(categoriesList.get(categoriesList.size() - 2).text());

        Category productCategory = new Category();
        productCategory.setCategoryName(categoriesList.get(categoriesList.size() - 1).text());
        productCategory.setParentCategory(parentCategory);

        return productCategory;
    }

    /**
     * Method that parses the product page and return the product's Shop
     *
     * @param productPage The page of the product
     * @return The Shop associated to the product
     */
    private Shop getProductShop(Document productPage) {
        logger.debug("Getting product shop");
        String shopName = productPage.select("div.col-sm-12.col-md-6.col-lg-6.col-lg-offset-1")
                .first().select("div:nth-child(2)").first().select("div:last-child")
                .first().ownText();
        Shop productShop = new Shop();

        if (shopName.contains("eMAG")) {
            productShop.setShopAddress("eMAG");

            return productShop;
        }
        productShop.setShopAddress(shopName);

        return productShop;
    }

    /**
     * Method that parses the product page and return the product's stock
     *
     * @param productPage The page of the product
     * @return 1 if the product is in stock, 0 otherwise
     */
    private int getProductStock(Document productPage) {
        logger.debug("Getting product stock");
        Element stockLabel = productPage.select("span.label.label-in_stock").first();

        if (stockLabel != null) {
            return 1;
        }

        return 0;
    }

    /**
     * Utility method that randomizes the stock of products that are in stock, as eMAG does not specify
     * the stock number of products
     *
     * @param productList List of Products on which the method performs the randomisation
     */
    private void randomizeStock(List<Product> productList) {
        logger.debug("Randomizing stock");
        for (Product product : productList) {
            if (product.getStock() > 0) {
                product.setStock(new Random().nextInt(51 - 1) + 1);
            }
        }
    }

    /**
     * Method that checks if a page is valid
     *
     * @param productPage Product page that is to be checked
     * @return True if the page is valid, false otherwise
     */
    private boolean isProductPage(Document productPage) {
        logger.info("Checking if the URL is a valid product page");
        return productPage.select("div.disclaimer-section > p:first-of-type > a").first() != null;
    }

    /**
     * Method that builds the Product object scrapped from the supplied product page
     *
     * @param productPage The product page from where the product needs to be scrapped
     * @return The Product object
     */
    private Product buildProduct(Document productPage) {
        logger.debug("Building product belonging to the supplied product page");
        Product product = new Product();
        product.setName(getProductName(productPage));
        product.setBrand(getProductBrandName(productPage));
        product.setDescription(getProductDescription(productPage));
        product.setPrice(getProductPrice(productPage));
        product.setCategory(getProductCategory(productPage));
        product.setShop(getProductShop(productPage));
        product.setStock(getProductStock(productPage));

        logger.info("Product object successfully built");
        return product;
    }
}