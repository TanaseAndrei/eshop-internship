package com.iquestgroup.mappers;

import com.iquestgroup.dtos.UserDTO;
import com.iquestgroup.models.Admin;
import com.iquestgroup.models.Cart;
import com.iquestgroup.models.Customer;
import com.iquestgroup.models.Favorites;
import com.iquestgroup.models.Seller;
import com.iquestgroup.models.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps the data from a userDTO to a entity inheriting User.
 */
public class UserMapper {

    /**
     * Binds the data from a userDTO to a newly created customer entity. Creates the two carts for the user entity
     * for ordering products and adding favorites.
     * @param user
     * @return
     */
    public Customer convertUserDTOToCustomer(UserDTO user) {
        Customer customer=new Customer();
        customer.setConsentGiven(true);
        customer.setAddress(user.getAddress());
        customer.setUsername(user.getUsername());
        customer.setPassword(user.getPassword());
        customer.setUserRole(user.getUserRole());
        Cart cart = new Cart();
        Favorites favorites = new Favorites();
        customer.setCart(cart);
        customer.setFavorites(favorites);
        cart.setCustomer(customer);
        favorites.setCustomer(customer);
        return customer;
    }

    /**
     * Binds the data from a userDTO to a newly created seller entity. Creates an empty list of shops where
     * the seller sells products and binds it to the newly created entity.
     * @param userDTO
     * @return
     */
    public Seller convertUserDTOToSeller(UserDTO userDTO) {
        List<Shop> shops=new ArrayList<>();
        Seller seller=new Seller();
        seller.setUsername(userDTO.getUsername());
        seller.setPassword(userDTO.getPassword());
        seller.setUserRole(userDTO.getUserRole());
        seller.setAddress(userDTO.getAddress());
        seller.setShops(shops);
        return seller;
    }

    public Admin convertUserDTOToAdmin(UserDTO userDTO){
        Admin admin=new Admin();
        admin.setUserRole(userDTO.getUserRole());
        admin.setUsername(userDTO.getUsername());
        admin.setPassword(userDTO.getPassword());
        admin.setAddress(userDTO.getAddress());
        return admin;
    }
}
