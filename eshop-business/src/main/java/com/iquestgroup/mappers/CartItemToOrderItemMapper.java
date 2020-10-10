package com.iquestgroup.mappers;

import com.iquestgroup.models.CartItem;
import com.iquestgroup.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to map a CartItem entity to a OrderItem entity.
 */
public class CartItemToOrderItemMapper {

    /**
     * Method used to map a list of CartItems to a list of OrderItems.
     *
     * @param cartItemList list of CartItems to be mapped
     * @return list of mapped OrderItems
     */
    public static List<OrderItem> getOrderItems(List<CartItem> cartItemList){
        List<OrderItem> orderItemList = new ArrayList<>();
        cartItemList
                .stream()
                .map(cartItem ->{
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setQuantity(cartItem.getQuantity());
                    return orderItem;
                })
                .forEach(orderItemList::add);
        return orderItemList;
    }
}
