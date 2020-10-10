package com.iquestgroup.mappers;

import com.iquestgroup.dtos.FavoritesDTO;
import com.iquestgroup.dtos.FavoritesItemDTO;
import com.iquestgroup.models.Favorites;

import java.util.List;

/**
 * Class used to map a Favorites entity to a FavoritesDTO entity.
 */
public class FavoritesDTOMapper {

    /**
     * Method used to map a Favorites cart to a FavoritesDTO.
     *
     * @param favorites favorites cart to be mapped
     * @return mapped FavoritesDTO object
     */
    public static FavoritesDTO mapFavoritesToFavoritesDTO(Favorites favorites) {
        List<FavoritesItemDTO> favoritesItemDTOList = FavoritesItemDTOMapper.mapFavoritesItemToFavoritesItemDTO(favorites.getFavoritesItemList());
        FavoritesDTO favoritesDTO = new FavoritesDTO();
        favoritesDTO.setCustomerId(favorites.getCustomer().getId());
        favoritesDTO.setCustomerName(favorites.getCustomer().getUsername());
        favoritesDTO.setFavoritesItemDTOList(favoritesItemDTOList);
        return favoritesDTO;
    }
}
