package com.iquestgroup.exceptions;

import com.iquestgroup.mappers.ServiceExceptionDTOMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for the OrderDetailException Service Exception. Will send a 400 Bad Request response
 * and the exception message.
 */
@Provider
public class OrderDetailExceptionMapper implements ExceptionMapper<OrderDetailException> {

    private static final Logger logger = LogManager.getLogger(OrderDetailExceptionMapper.class);

    /**
     * Method that returns the response in case the mapper catches a NotFoundException
     *
     * @param orderDetailException exception caught by the mapper
     * @return a Response object with the status 400 (Bad Request) and a message
     */
    @Override
    public Response toResponse(OrderDetailException orderDetailException) {
        logger.error(orderDetailException.getMessage(), orderDetailException);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ServiceExceptionDTOMapper.mapExceptionToExceptionDTO(orderDetailException))
                .build();
    }
}
