package com.iquestgroup.exceptions;

import com.iquestgroup.mappers.ServiceExceptionDTOMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for the NotFoundException Service Exception. Will send a 404 Not Found Error response
 * and the exception message.
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    private static final Logger logger = LogManager.getLogger(NotFoundExceptionMapper.class);

    /**
     * Method that returns the response in case the mapper catches a NotFoundException
     *
     * @param notFoundException The NotFoundException Exception caught by the mapper
     * @return A 404 Not Found Error response and the exception message
     */
    @Override
    public Response toResponse(NotFoundException notFoundException) {
        logger.error(notFoundException.getMessage(), notFoundException);

        return Response.status(Response.Status.NOT_FOUND)
                .entity(ServiceExceptionDTOMapper.mapExceptionToExceptionDTO(notFoundException)).build();
    }
}