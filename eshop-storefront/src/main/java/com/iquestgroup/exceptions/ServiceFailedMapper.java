package com.iquestgroup.exceptions;

import com.iquestgroup.mappers.ServiceExceptionDTOMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for the Service Exception. Will send a 500 Internal Server Error response and the
 * exception message.
 */
@Provider
public class ServiceFailedMapper implements ExceptionMapper<ServiceException> {

    private static final Logger logger = LogManager.getLogger(OrderDetailExceptionMapper.class);

    @Override
    public Response toResponse(ServiceException e) {
        logger.error(e.getMessage(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ServiceExceptionDTOMapper.mapExceptionToExceptionDTO(e)).build();
    }
}
