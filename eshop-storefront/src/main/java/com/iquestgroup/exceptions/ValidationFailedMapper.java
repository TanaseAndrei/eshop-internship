package com.iquestgroup.exceptions;

import com.iquestgroup.mappers.ServiceExceptionDTOMapper;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for the 3 potential validation exceptions. Will send a 422 Unprocessable Entity response
 * code and the exception message.
 */
@Provider
public class ValidationFailedMapper implements ExceptionMapper<ValidationException> {

    private static final Logger logger = LogManager.getLogger(OrderDetailExceptionMapper.class);

    @Override
    public Response toResponse(ValidationException e) {
        logger.error(e.getMessage(), e);
        return Response.status(Response.Status.BAD_REQUEST).entity(ServiceExceptionDTOMapper.mapExceptionToExceptionDTO(e)).build();
    }
}
