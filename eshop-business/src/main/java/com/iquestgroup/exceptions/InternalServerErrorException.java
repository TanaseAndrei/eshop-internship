package com.iquestgroup.exceptions;

/**
 * Exception that is thrown by services in case they cannot deliver a response due to an error that has
 * occurred internally
 */
public class InternalServerErrorException extends ServiceException {

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}