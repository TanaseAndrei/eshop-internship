package com.iquestgroup.exceptions;

public class NoUserFoundException extends DaoException{

    public NoUserFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
