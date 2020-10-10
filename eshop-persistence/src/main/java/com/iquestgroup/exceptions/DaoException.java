package com.iquestgroup.exceptions;

/**
 * Thrown by the Generic Dao implementation, when a Create, Read, Update, Delete operation fails.
 */
public class DaoException extends Exception {

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}