package com.iquestgroup.exceptions;

/**
 * Thrown by the Dao implementation when a single result criteria query returns no results.
 */
public class NoProductFoundException extends DaoException {
    public NoProductFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}