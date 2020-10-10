package com.iquestgroup.utilities.crawlers.interfaces;

import com.iquestgroup.exceptions.CrawlerConnectionException;
import com.iquestgroup.exceptions.InvalidProductURLException;

import java.util.List;

/**
 * Interface for an entity crawler
 *
 * @param <T> The entity type which the crawler crawls for
 */
public interface Crawler<T> {

    /**
     * Method which returns a List of entities that correspond to the entities displayed at the URLs
     * provided as argument to the method
     *
     * @param entityURLs A List containing the URLs from where the crawler need to scrap the entities
     *                   that are to be returned
     * @return A List containing all entities that have been scrapped from the URLs supplied as a List
     * to the method
     */
    List<T> getCrawledEntities(List<String> entityURLs) throws CrawlerConnectionException, InvalidProductURLException;
}