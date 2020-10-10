package com.iquestgroup.exceptions;

/**
 * Thrown by Services in case an operation failed.
 */
public class ServiceException extends Exception {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
