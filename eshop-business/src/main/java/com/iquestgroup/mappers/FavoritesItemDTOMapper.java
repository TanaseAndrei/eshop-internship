package com.iquestgroup.mappers;

import com.iquestgroup.dtos.FavoritesItemDTO;
import com.iquestgroup.models.FavoritesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to map a list of FavoritesItems to a list of FavoritesItemDTOs.
 */
public class FavoritesItemDTOMapper {

    /**
     * Method used to map a list of FavoritesItems to a list of FavoritesItemDTOs.
     * @param favoritesItemList
     * @return list of mapped FavoritesItemDTOs
     */
    public static List<FavoritesItemDTO> mapFavoritesItemToFavoritesItemDTO(List<FavoritesItem> favoritesItemList){
        List<FavoritesItemDTO> favoritesItemDTOList = new ArrayList<>();
        favoritesItemList
                .stream()
                .map(favoritesItem -> {
                    FavoritesItemDTO favoritesItemDTO = new FavoritesItemDTO();
                    favoritesItemDTO.setProductName(favoritesItem.getProduct().getName());
                    favoritesItemDTO.setBrandName(favoritesItem.getProduct().getBrand());
                    favoritesItemDTO.setPrice(favoritesItem.getProduct().getPrice());
                    return favoritesItemDTO;
                })
                .forEach(favoritesItemDTO -> favoritesItemDTOList.add(favoritesItemDTO));
        return favoritesItemDTOList;
    }
}
