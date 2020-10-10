package com.iquestgroup.exceptions;

/**
 * Exception that is thrown by the services when a product is out of stock and a
 * customer is trying to add it to his cart.
 */
public class InsufficientStockException extends ServiceException {
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}
