package com.iquestgroup.mappers;

import com.iquestgroup.models.Cart;
import com.iquestgroup.dtos.CartDTO;
import com.iquestgroup.dtos.CartItemDTO;

import java.util.List;

/**
 * Class used to map a Cart entity to a CartDTO entity.
 */
public class CartDTOMapper {

    /**
     * Method used to map a Cart to a CartDTO.
     *
     * @param cart cart to map to CartDTO
     * @return a CartDTO instance
     */
    public static CartDTO mapCartToCartDTO(Cart cart) {
        List<CartItemDTO> cartItemDTOList = CartItemDTOMapper.getMap(cart.getListOfCartItems());
        CartDTO cartDTO = new CartDTO();
        cartDTO.setPrice(cart.getPrice());
        cartDTO.setListOfCartItemDTOs(cartItemDTOList);
        return cartDTO;
    }

    public static CartDTO mapCartToOrderInformationCartDTO(Cart cart){
        List<CartItemDTO> cartItemDTOList = CartItemDTOMapper.getMap(cart.getListOfCartItems());
        CartDTO cartDTO = new CartDTO();
        cartDTO.setPrice(cart.getPrice());
        cartDTO.setListOfCartItemDTOs(cartItemDTOList);
        cartDTO.setBillingDetails(cart.getBillingDetails());
        cartDTO.setContactPerson(cart.getContactInfo());
        cartDTO.setPaymentMethod(cart.getPaymentMethod());
        cartDTO.setShippingMethod(cart.getShippingMethod());
        return cartDTO;
    }

}
