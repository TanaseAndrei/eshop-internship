package com.iquestgroup.exceptions;

/**
 * Exception that is thrown by the service layer when placing an order, it doesn't contain all the
 * necessary data like billing details, contact info etc.
 */
public class OrderDetailException extends ServiceException {
    public OrderDetailException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDetailException(String message) {
        super(message);
    }
}
