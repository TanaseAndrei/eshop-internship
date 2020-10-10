package com.iquestgroup.mappers;

import com.iquestgroup.dtos.OrderItemDTO;
import com.iquestgroup.models.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemDTOMapper {

    public static List<OrderItemDTO> mapOrderItemToOrderItemDTO(List<OrderItem> orderItemList){
        List<OrderItemDTO> orderItemDTOList =
                orderItemList
                .stream()
                .map(orderItem -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setPrice(orderItem.getPrice());
                    orderItemDTO.setProductName(orderItem.getProduct().getName());
                    orderItemDTO.setQuantity(orderItem.getQuantity());
                    return orderItemDTO;
                })
                .collect(Collectors.toList());
        return orderItemDTOList;
    }
}
