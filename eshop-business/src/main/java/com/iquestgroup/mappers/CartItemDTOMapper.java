package com.iquestgroup.mappers;

import com.iquestgroup.dtos.CartItemDTO;
import com.iquestgroup.models.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to map a CartItem entity to a CartItemDTO entity.
 */
public class CartItemDTOMapper {

    /**
     * Method used to map a list of CartItems to CartItemsDTOs.
     *
     * @param cartItemList list of CartItems to be mapped
     * @return list of mapped CartItems to CartItemDTOs
     */
    public static List<CartItemDTO> getMap(List<CartItem> cartItemList) {
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        cartItemList
                .stream()
                .map(cartItem -> {
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setPrice(cartItem.getPrice());
                    cartItemDTO.setProductName(cartItem.getProduct().getName());
                    cartItemDTO.setQuantity(cartItem.getQuantity());
                    return cartItemDTO;
                })
                .forEach(cartItemDTO -> cartItemDTOList.add(cartItemDTO));
        return cartItemDTOList;
    }

}
