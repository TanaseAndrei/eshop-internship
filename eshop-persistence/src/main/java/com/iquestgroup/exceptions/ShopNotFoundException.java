package com.iquestgroup.exceptions;

/**
 * Thrown by the Dao implementation when a single result criteria query returns no results.
 */
public class ShopNotFoundException extends DaoException {

    public ShopNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
