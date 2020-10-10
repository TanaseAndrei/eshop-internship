package com.iquestgroup.exceptions;

import com.iquestgroup.mappers.ServiceExceptionDTOMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for the InsufficientStockException Service Exception. Will send a 400 Bad Request response
 * and the exception message.
 */
@Provider
public class InsufficientStockExceptionMapper implements ExceptionMapper<InsufficientStockException> {

    private static final Logger logger = LogManager.getLogger(InsufficientStockExceptionMapper.class);

    /**
     * Method used to give to the customer a HTTP error instead of the exception itself.
     *
     * @param insufficientStockException exception caught by the mapper
     * @return a Response object with the status 400 (Bad Request) and a message
     */
    @Override
    public Response toResponse(InsufficientStockException insufficientStockException) {
        logger.error(insufficientStockException.getMessage(), insufficientStockException);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ServiceExceptionDTOMapper.mapExceptionToExceptionDTO(insufficientStockException))
                .build();
    }
}
