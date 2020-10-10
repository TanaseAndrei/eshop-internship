package com.iquestgroup.dtos;


import com.iquestgroup.models.Seller;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates Shop Entity fields and is used to transfer data between the application levels.
 */

public class ShopDTO {

    private List<Long> sellerIds = new ArrayList<>();

    private String shopAddress;

    public List<Long> getSellerIds() {
        return sellerIds;
    }

    public void setSellerIds(List<Long> sellerIds) {
        this.sellerIds = sellerIds;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
