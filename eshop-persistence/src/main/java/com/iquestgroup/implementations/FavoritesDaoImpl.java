package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.NoFavoritesCartFoundException;
import com.iquestgroup.interfaces.FavoritesDao;
import com.iquestgroup.models.Favorites;
import com.iquestgroup.models.FavoritesItem;
import com.iquestgroup.models.Favorites_;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class FavoritesDaoImpl extends GenericDaoImpl<Favorites> implements FavoritesDao {
    private static final Logger logger = LogManager.getLogger(FavoritesDaoImpl.class);

    public FavoritesDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<FavoritesItem> getFavoritesItems(String username) throws NoFavoritesCartFoundException {
        Favorites favoritesCart;
        List<FavoritesItem> favoritesList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Favorites> query = criteriaBuilder.createQuery(Favorites.class);
        Root<Favorites> favorites = query.from(Favorites.class);
        favorites.fetch(Favorites_.FAVORITES_ITEM_LIST, JoinType.LEFT);
        ParameterExpression<String> usernameParameter = criteriaBuilder.parameter(String.class);
        query.select(favorites).where(criteriaBuilder.equal((favorites.get("customer")).get("username"), usernameParameter));
        TypedQuery<Favorites> typedQuery = entityManager.createQuery(query);

        try {
            favoritesCart = typedQuery.setParameter(usernameParameter, username).getSingleResult();
            favoritesList = favoritesCart.getFavoritesItemList();
        } catch (NoResultException noResultException) {
            logger.error(noResultException.getMessage(), noResultException);
            throw new NoFavoritesCartFoundException("The searched favorites cart does not exist!", noResultException);
        } finally {
            entityManager.close();
        }

        return favoritesList;
    }

    @Override
    public Favorites getFavoritesCart(String username) throws NoFavoritesCartFoundException {
        Favorites favoritesCart;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Favorites> query = criteriaBuilder.createQuery(Favorites.class);
        Root<Favorites> favorites = query.from(Favorites.class);
        favorites.fetch(Favorites_.FAVORITES_ITEM_LIST, JoinType.LEFT);
        favorites.fetch(Favorites_.CUSTOMER, JoinType.LEFT);
        ParameterExpression<String> usernameParameter = criteriaBuilder.parameter(String.class);
        query.select(favorites).where(criteriaBuilder.equal((favorites.get("customer")).get("username"), usernameParameter));
        TypedQuery<Favorites> typedQuery = entityManager.createQuery(query);

        try {
            favoritesCart = typedQuery.setParameter(usernameParameter, username).getSingleResult();
        } catch (NoResultException noResultException) {
            logger.error(noResultException.getMessage(), noResultException);
            throw new NoFavoritesCartFoundException("The searched favorites cart does not exist!", noResultException);
        } finally {
            entityManager.close();
        }

        return favoritesCart;
    }
}
