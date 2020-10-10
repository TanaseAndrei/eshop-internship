package com.iquestgroup.mappers;

import com.iquestgroup.dtos.ExceptionDTO;
import com.iquestgroup.exceptions.ServiceException;

public class ServiceExceptionDTOMapper {

    public static ExceptionDTO mapExceptionToExceptionDTO(ServiceException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(exception.getMessage());
        return exceptionDTO;
    }

}
