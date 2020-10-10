package com.iquestgroup.exceptions;

/**
 * Exception that is thrown by services when they cannot find the resource requested by the client
 */
public class NotFoundException extends ServiceException {

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message){
        super(message);
    }
}
