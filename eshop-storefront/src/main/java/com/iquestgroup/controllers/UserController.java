package com.iquestgroup.controllers;

import com.iquestgroup.dtos.UserDTO;
import com.iquestgroup.exceptions.ServiceException;
import com.iquestgroup.exceptions.ValidationException;
import com.iquestgroup.interfaces.UserService;
import com.iquestgroup.services.UserServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Provides the full set of resources applicable to the User class.
 */
@Path("/user")
public class UserController {

    private final UserService userService;

    public UserController() {
        userService = new UserServiceImpl();
    }

    /**
     * @param newUser the new User Entity that will be registered and persisted in the database.
     * @return the new Entity in JSON format and a 200 OK response code or
     * @throws ValidationException if the input data failed to pass the implemented registration constraints.
     * @throws ServiceException    if the service failed to register the user.
     */
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDTO newUser) throws ValidationException, ServiceException {
        UserDTO register = userService.register(newUser);
        return Response.ok(register).build();
    }
}
