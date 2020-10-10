package com.iquestgroup.services;

import com.iquestgroup.dtos.FavoritesDTO;
import com.iquestgroup.dtos.FavoritesItemDTO;
import com.iquestgroup.exceptions.*;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.implementations.FavoritesDaoImpl;
import com.iquestgroup.implementations.ProductDaoImpl;
import com.iquestgroup.interfaces.FavoritesDao;
import com.iquestgroup.interfaces.FavoritesService;
import com.iquestgroup.interfaces.ProductDao;
import com.iquestgroup.mappers.FavoritesDTOMapper;
import com.iquestgroup.mappers.FavoritesItemDTOMapper;
import com.iquestgroup.models.Favorites;
import com.iquestgroup.models.FavoritesItem;
import com.iquestgroup.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FavoritesServiceImpl implements FavoritesService {

    private static final Logger logger = LogManager.getLogger(FavoritesServiceImpl.class);

    @Override
    public List<FavoritesItemDTO> getFavoritesItems(String username) throws NotFoundException {
        EntityManager entityManager = CustomEntityManagerFactory.getInstance().getEntityManager();
        FavoritesDao favoritesDao = new FavoritesDaoImpl(entityManager);
        try {
            List<FavoritesItem> favoritesItemList = favoritesDao.getFavoritesItems(username);
            return FavoritesItemDTOMapper.mapFavoritesItemToFavoritesItemDTO(favoritesItemList);
        } catch (NoFavoritesCartFoundException noFavoritesCartFoundException) {
            logger.error(noFavoritesCartFoundException.getMessage(), noFavoritesCartFoundException);
            throw new NotFoundException("The service did not find the searched favorites cart!", noFavoritesCartFoundException);
        }
    }

    @Override
    public FavoritesDTO getFavoritesCart(String username) throws NotFoundException {
        EntityManager entityManager = CustomEntityManagerFactory.getInstance().getEntityManager();
        FavoritesDao favoritesDao = new FavoritesDaoImpl(entityManager);
        try{
            Favorites favorites = favoritesDao.getFavoritesCart(username);
            return FavoritesDTOMapper.mapFavoritesToFavoritesDTO(favorites);
        } catch (NoFavoritesCartFoundException noFavoritesCartFoundException){
            logger.error(noFavoritesCartFoundException.getMessage(), noFavoritesCartFoundException);
            throw new NotFoundException("The service did not find the searched favorites cart!", noFavoritesCartFoundException);
        }
    }

    @Override
    public FavoritesDTO addProduct(long productId, String username) throws NotFoundException, InternalServerErrorException {
        ProductDao productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        FavoritesDao favoritesDao = new FavoritesDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Product product = productDao.getProduct(productId);
            Favorites favorites = favoritesDao.getFavoritesCart(username);
            List<FavoritesItem> favoritesItemList = favorites.getFavoritesItemList();

            Optional<FavoritesItem> returned = favoritesItemList
                    .stream()
                    .filter(favoritesItem -> favoritesItem.getProduct().equals(product))
                    .findFirst();

            if(!returned.isPresent()){
                FavoritesItem favoritesItem = new FavoritesItem();
                favoritesItem.setProduct(product);
                favoritesItemList.add(favoritesItem);
            }

            favoritesDao = new FavoritesDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
            favoritesDao.update(favorites);

            return FavoritesDTOMapper.mapFavoritesToFavoritesDTO(favorites);
        } catch (NoProductFoundException noProductFoundException) {
            logger.error(noProductFoundException.getMessage(), noProductFoundException);
            throw new NotFoundException("The service did not find the searched product!", noProductFoundException);
        } catch (NoFavoritesCartFoundException noFavoritesCartFoundException) {
            logger.error(noFavoritesCartFoundException.getMessage(), noFavoritesCartFoundException);
            throw new NotFoundException("The service did not find the searched favorites cart!", noFavoritesCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("Internal exception in the service layer!", daoException);
        }
    }

    @Override
    public FavoritesDTO deleteProduct(long productId, String username) throws NotFoundException, InternalServerErrorException {
        ProductDao productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        FavoritesDao favoritesDao = new FavoritesDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Product product = productDao.getProduct(productId);
            Favorites favorites = favoritesDao.getFavoritesCart(username);
            List<FavoritesItem> favoritesItemList = favorites.getFavoritesItemList();

            Optional<FavoritesItem> returned = favoritesItemList
                    .stream()
                    .filter(favoritesItem -> favoritesItem.getProduct().equals(product))
                    .findFirst();

            if(returned.isPresent()){
                favoritesItemList.remove(returned.get());
            } else {
                throw new NotFoundException("The product does not exist in the favorites cart!");
            }

            favoritesDao = new FavoritesDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
            favoritesDao.update(favorites);

            return FavoritesDTOMapper.mapFavoritesToFavoritesDTO(favorites);
        } catch (NoProductFoundException noProductFoundException) {
            logger.error(noProductFoundException.getMessage(), noProductFoundException);
            throw new NotFoundException("The service did not find the searched product!", noProductFoundException);
        } catch (NoFavoritesCartFoundException noFavoritesCartFoundException) {
            logger.error(noFavoritesCartFoundException.getMessage(), noFavoritesCartFoundException);
            throw new NotFoundException("The service did not find the searched favorites cart!", noFavoritesCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("Internal exception in the service layer!", daoException);
        }
    }
}
