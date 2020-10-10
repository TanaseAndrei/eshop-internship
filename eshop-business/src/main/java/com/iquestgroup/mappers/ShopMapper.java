package com.iquestgroup.mappers;

import com.iquestgroup.dtos.ShopDTO;
import com.iquestgroup.models.Shop;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps the data from a shopDTO to a Shop entity.
 */
public class ShopMapper {
    /**
     * Binds the data from a shopDTO to a newly created shop entity.
     * @param shopDTO
     * @return
     */
    public Shop convertShopDTOToShop(ShopDTO shopDTO){
        Shop shop=new Shop();
        shop.setShopAddress(shopDTO.getShopAddress());
        return shop;
    }

    public ShopDTO convertShopToShopDTO(Shop shop){
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopAddress(shop.getShopAddress());
        if(shop.getSellers() != null){
            List<Long> listOfSellersIds = shop.getSellers().stream().map(sellerAux -> sellerAux.getId()).collect(Collectors.toList());
            shopDTO.setSellerIds(listOfSellersIds);
        }
        return shopDTO;
    }
}
