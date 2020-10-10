package com.iquestgroup.exceptions;

/**
 * Thrown by the Dao implementation if while querying for entities, another Exception is thrown
 */
public class QueryException extends DaoException {

    public QueryException(Throwable cause) {
        super(cause);
    }
}