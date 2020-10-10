package com.iquestgroup.services;

import com.iquestgroup.constants.UserRole;
import com.iquestgroup.dtos.UserDTO;
import com.iquestgroup.exceptions.DaoException;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.exceptions.ServiceException;
import com.iquestgroup.exceptions.ValidationException;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.implementations.AdminDaoImpl;
import com.iquestgroup.implementations.CustomerDaoImpl;
import com.iquestgroup.implementations.SellerDaoImpl;
import com.iquestgroup.interfaces.AdminDao;
import com.iquestgroup.interfaces.CustomerDao;
import com.iquestgroup.interfaces.UserService;
import com.iquestgroup.interfaces.SellerDao;
import com.iquestgroup.mappers.UserMapper;
import com.iquestgroup.models.Admin;
import com.iquestgroup.models.Customer;
import com.iquestgroup.models.Seller;
import com.iquestgroup.models.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Implementation for the Register Service interface
 */
public class UserServiceImpl implements UserService {

    public UserDTO register(UserDTO newUser) throws ServiceException {
        validateUser(newUser);
        hashPassword(newUser);
        User user = null;
        switch (newUser.getUserRole()) {
            case SELLER:
                user = registerSeller(newUser);
                break;
            case ADMIN:
                user = registerAdmin(newUser);
                break;
            case CUSTOMER:
                user = registerCustomer(newUser);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setUserRole(user.getUserRole());
        return userDTO;
    }

    @Override
    public Seller findSeller(long id) throws NotFoundException {
        SellerDao sellerDao = new SellerDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Seller seller = sellerDao.getUserById(id);
            if (seller.getUserRole().equals(UserRole.CUSTOMER)) {
                throw new NotFoundException("No seller with this ID exist");
            }
            return seller;
        } catch (DaoException daoException) {
            throw new NotFoundException("No seller with this ID exists", daoException);
        }
    }

    @Override
    public User updateSeller(Seller seller) throws InternalServerErrorException {
        SellerDao sellerDao = new SellerDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            return sellerDao.update(seller);
        } catch (DaoException daoException) {
            throw new InternalServerErrorException("Error updating seller", daoException);
        }
    }


    private User registerSeller(UserDTO newSeller) throws InternalServerErrorException {
        Seller seller = new UserMapper().convertUserDTOToSeller(newSeller);
        SellerDao sellerDaoDao = new SellerDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            return sellerDaoDao.create(seller);
        } catch (DaoException daoException) {
            throw new InternalServerErrorException("Error registering seller", daoException);
        }
    }

    private User registerCustomer(UserDTO newCustomer) throws InternalServerErrorException {
        Customer customer = new UserMapper().convertUserDTOToCustomer(newCustomer);
        CustomerDao customerDao = new CustomerDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            return customerDao.create(customer);
        } catch (DaoException daoException) {
            throw new InternalServerErrorException("Error registering customer", daoException);
        }
    }

    private User registerAdmin(UserDTO newCustomer) throws InternalServerErrorException {
        Admin admin = new UserMapper().convertUserDTOToAdmin(newCustomer);
        AdminDao adminDao = new AdminDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            return adminDao.create(admin);
        } catch (DaoException daoException) {
            throw new InternalServerErrorException("Error registering admin", daoException);
        }
    }

    private void validateUser(UserDTO newUser) throws ValidationException {

        if (newUser.getUsername().isBlank()) {
            throw new ValidationException("Username can't be blank");
        }

        if (!newUser.getPassword().matches("^.{10,}")) {
            throw new ValidationException("Password should have at least 10 characters");
        }

        if (!newUser.getPassword().matches(".*[A-Z].*")) {
            throw new ValidationException("Password should have at least one uppercase character");
        }

        if (!newUser.getPassword().matches(".*[!\"\\['()*,./:;<>?^_`{|}~@#$%&+=].*")) {
            throw new ValidationException("Password contain at least one special character");
        }
    }

    private void hashPassword(UserDTO newUser) {
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
    }
}
