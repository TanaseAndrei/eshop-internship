package com.iquestgroup.dtos;

import java.util.List;

/**
 * Model used to encapsulate data of a Favorites model entity.
 */
public class FavoritesDTO {

    private long customerId;

    private String customerName;

    private List<FavoritesItemDTO> favoritesItemDTOList;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<FavoritesItemDTO> getFavoritesItemDTOList() {
        return favoritesItemDTOList;
    }

    public void setFavoritesItemDTOList(List<FavoritesItemDTO> favoritesItemDTOList) {
        this.favoritesItemDTOList = favoritesItemDTOList;
    }
}
