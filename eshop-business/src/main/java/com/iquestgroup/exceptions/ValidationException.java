package com.iquestgroup.exceptions;

/**
 * Thrown if data received from the HTTP client failed to pass validation constraints.
 */
public class ValidationException extends ServiceException {

    public ValidationException(String message) {
        super(message);
    }
}
