package com.iquestgroup.services;

import com.iquestgroup.dtos.ShopDTO;
import com.iquestgroup.exceptions.DaoException;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.exceptions.ServiceException;
import com.iquestgroup.exceptions.ShopNotFoundException;
import com.iquestgroup.implementations.ShopDaoImpl;
import com.iquestgroup.interfaces.ShopDao;
import com.iquestgroup.interfaces.ShopService;
import com.iquestgroup.mappers.ShopMapper;
import com.iquestgroup.models.Seller;
import com.iquestgroup.models.Shop;
import com.iquestgroup.models.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShopServiceImpl implements ShopService {

    @Override
    public void createShop(ShopDTO newShop) throws InternalServerErrorException {
        Shop shop = new ShopMapper().convertShopDTOToShop(newShop);
        ShopDao shopDao = new ShopDaoImpl();
        try {
            shopDao.create(shop);
        } catch (DaoException daoException) {
            throw new InternalServerErrorException("Error persisting shop", daoException);
        }
    }

    @Override
    public Shop findShop(long id) throws NotFoundException {
        ShopDao shopDao = new ShopDaoImpl();
        try {
            return shopDao.getShopById(id);
        } catch (ShopNotFoundException shopNotFoundException) {
            throw new NotFoundException("No shop with the given id exists",shopNotFoundException);
        }
    }

    @Override
    public void updateShop(Shop shop) throws InternalServerErrorException {
        ShopDao shopDao = new ShopDaoImpl();
        try {
            shopDao.update(shop);
        } catch (DaoException daoException) {
            throw new InternalServerErrorException("Error updating entity", daoException);
        }
    }

    @Override
    public ShopDTO bindShopToSeller(long shopId, long sellerId) throws NotFoundException, InternalServerErrorException {
        Shop shop = findShop(shopId);
        Seller seller = new UserServiceImpl().findSeller(sellerId);

        if(seller.getShops() != null){
            Optional<Shop> shopOptional = seller.getShops().stream().filter(shopAux -> shopAux.getShopId() == shopId).findFirst();
            if(shopOptional.isPresent()){
                return new ShopMapper().convertShopToShopDTO(shop);
            }
        }

        seller.addShop(shop);
        User updatedSeller = new UserServiceImpl().updateSeller(seller);
        shop.addSeller((Seller)updatedSeller);
        updateShop(shop);
        ShopDTO shopDTO = new ShopMapper().convertShopToShopDTO(shop);

        return shopDTO;
    }
}
