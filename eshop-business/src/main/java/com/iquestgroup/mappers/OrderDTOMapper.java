package com.iquestgroup.mappers;

import com.iquestgroup.dtos.OrderDTO;
import com.iquestgroup.models.Order;

public class OrderDTOMapper {

    public static OrderDTO mapOrderToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBillingDetails(order.getBillingDetails());
        orderDTO.setContactInfo(order.getContactInfo());
        orderDTO.setOrderDate(order.getDate());
        orderDTO.setShopId(order.getShop().getShopId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setOrderItemDTOS(OrderItemDTOMapper.mapOrderItemToOrderItemDTO(order.getOrderItemList()));
        return orderDTO;
    }
}
