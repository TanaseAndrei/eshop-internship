package com.iquestgroup.services;

import com.iquestgroup.constants.RoleRequired;
import com.iquestgroup.exceptions.DaoException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.implementations.UserDaoImpl;
import com.iquestgroup.interfaces.LogInService;
import com.iquestgroup.interfaces.UserDao;
import com.iquestgroup.models.User;
import org.mindrot.jbcrypt.BCrypt;

public class LogInServiceImpl implements LogInService {

    @Override
    public boolean areCredentialsValid(String username, String password, RoleRequired roleRequired) throws NotFoundException {
        UserDao userDao = new UserDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        User user;
        try {
            user = userDao.getUserByUsername(username);
        } catch (DaoException e) {
            throw new NotFoundException(e.getMessage(), e);
        }

        return checkPassword(password, user.getPassword()) && user.getUserRole().name().equals(roleRequired.name());
    }

    private boolean checkPassword(String decryptedPassword, String encryptedPassword) {
        return BCrypt.checkpw(decryptedPassword, encryptedPassword);
    }
}
