package com.iquestgroup.exceptions;

/**
 * Signals that a customer has been been searched by a wrong name, wrong meaning
 * that the targeted entity does not exist in the database.
 */
public class NoCustomerFoundException extends DaoException {

    /**
     * Constructs a new exception with the specified message and
     * cause.
     * @param message the detail message (that can be accessed by
     *                the {@link Throwable#getMessage()} method)
     * @param cause the cause of the exception (can be accessed
     *              by the {@link Throwable#getCause()} method)
     */
    public NoCustomerFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
