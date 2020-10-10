package com.iquestgroup.exceptions;

import com.iquestgroup.mappers.ServiceExceptionDTOMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for the InternalServerError Service Exception. Will send a 500 Internal Server Error
 * response and the Exception which describes the cause.
 */
@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {
    private static final Logger logger = LogManager.getLogger(InternalServerErrorExceptionMapper.class);

    /**
     * Method that returns the response in case the mapper catches a InternalServerErrorException
     *
     * @param internalServerErrorException The InternalServerErrorException Exception caught by the mapper
     * @return A 500 Internal Server Error response and the Exception which describes the cause
     */
    @Override
    public Response toResponse(InternalServerErrorException internalServerErrorException) {
        logger.error(internalServerErrorException.getMessage(), internalServerErrorException);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ServiceExceptionDTOMapper.mapExceptionToExceptionDTO(internalServerErrorException)).build();
    }
}