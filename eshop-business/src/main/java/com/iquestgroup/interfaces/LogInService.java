package com.iquestgroup.interfaces;

import com.iquestgroup.constants.RoleRequired;
import com.iquestgroup.exceptions.NotFoundException;

/**
 * Verify if the credentials of user are valid, and if they are, the user get the response for their request
 */
public interface LogInService {
    /**
     * @param username is the username with which the user registered
     * @param password is the password with which the user registered
     * @param roleRequired the role required for the request
     * @return true if the user is authorized, false otherwise
     * @throws NotFoundException it is thrown if there is no such user
     */
    boolean areCredentialsValid(String username, String password, RoleRequired roleRequired) throws NotFoundException;
}
