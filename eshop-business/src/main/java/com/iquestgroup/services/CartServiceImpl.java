package com.iquestgroup.services;

import com.iquestgroup.dtos.CartDTO;
import com.iquestgroup.dtos.CartItemDTO;
import com.iquestgroup.exceptions.*;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.implementations.CartDaoImpl;
import com.iquestgroup.implementations.ProductDaoImpl;
import com.iquestgroup.interfaces.CartDao;
import com.iquestgroup.interfaces.CartService;
import com.iquestgroup.interfaces.ProductDao;
import com.iquestgroup.mappers.CartDTOMapper;
import com.iquestgroup.mappers.CartItemDTOMapper;
import com.iquestgroup.models.Cart;
import com.iquestgroup.models.CartItem;
import com.iquestgroup.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private static final Logger logger = LogManager.getLogger(CartServiceImpl.class);

    @Override
    public CartDTO getCart(String name) throws NotFoundException {
        EntityManager entityManager = CustomEntityManagerFactory.getInstance().getEntityManager();
        CartDao cartDao = new CartDaoImpl(entityManager);
        try {
            Cart cart = cartDao.getCart(name);
            return CartDTOMapper.mapCartToCartDTO(cart);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException("The service did not find the searched cart!", noCartFoundException);
        }
    }

    @Override
    public List<CartItemDTO> getItems(String username) throws NotFoundException {
        EntityManager entityManager = CustomEntityManagerFactory.getInstance().getEntityManager();
        CartDao cartDao = new CartDaoImpl(entityManager);
        try {
            List<CartItem> resultedList = cartDao.getItems(username);
            return CartItemDTOMapper.getMap(resultedList);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException("The service did not find the searched cart!", noCartFoundException);
        }
    }

    @Override
    public CartDTO addProduct(long productId, String username) throws InternalServerErrorException, NotFoundException, InsufficientStockException {
        CartDao cartDao = new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        ProductDao productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Product product = productDao.getProduct(productId);
            checkProductStock(product);

            Cart cart = cartDao.getCart(username);
            List<CartItem> cartItemList = cart.getListOfCartItems();

            Optional<CartItem> returned = cartItemList
                    .stream()
                    .filter(cartItem -> cartItem.getProduct().equals(product))
                    .findFirst();

            if (returned.isPresent()) {
                returned.get().setQuantity(returned.get().getQuantity() + 1);
                returned.get().setPrice(returned.get().getQuantity() * product.getPrice());
                returned.get().getProduct().setStock(returned.get().getProduct().getStock() - 1);
                product.setStock(product.getStock() - 1);
            } else {
                cartItemList.add(getCartItem(product));
                product.setStock(product.getStock() - 1);
            }

            updateEntities(cart, product);
            return CartDTOMapper.mapCartToCartDTO(cart);
        } catch (NoProductFoundException noProductFoundException) {
            logger.error(noProductFoundException.getMessage(), noProductFoundException);
            throw new NotFoundException("The service did not find the desired product!", noProductFoundException);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException("The service did not find the searched cart!", noCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("The service couldn't add a product to the specified card!", daoException);
        }
    }

    private CartItem getCartItem(Product product){
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setQuantity(1);
        newCartItem.setPrice(product.getPrice());
        return newCartItem;
    }

    private void checkProductStock(Product product) throws InsufficientStockException {
        if (product.getStock() <= 0) {
            throw new InsufficientStockException("The product " + product.getName() + " has insufficient stock!");
        }
    }

    private void updateEntities(Cart cart, Product product) throws DaoException {
        Optional<Double> totalCartPrice = cart
                .getListOfCartItems()
                .stream()
                .map(cartItem -> cartItem.getPrice())
                .reduce(Double::sum);
        cart.setPrice(totalCartPrice.get());
        new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).update(cart);
        new ProductDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).update(product);
    }

    @Override
    public CartDTO deleteProduct(long productId, String username) throws InternalServerErrorException, NotFoundException {
        CartDao cartDao = new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        ProductDao productDao = new ProductDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Cart cart = cartDao.getCart(username);
            Product product = productDao.getProduct(productId);
            List<CartItem> itemsOfExtractedCart = cart.getListOfCartItems();

            Optional<CartItem> returned = itemsOfExtractedCart
                    .stream()
                    .filter(cartItem -> cartItem.getProduct().equals(product))
                    .findFirst();

            if (returned.isPresent()) {
                if (returned.get().getQuantity() > 1) {
                    returned.get().setQuantity(returned.get().getQuantity() - 1);
                    returned.get().setPrice(returned.get().getPrice() - product.getPrice());
                    cart.setPrice(cart.getPrice() - product.getPrice());
                    product.setStock(product.getStock() + 1);
                } else {
                    itemsOfExtractedCart.remove(returned.get());
                    cart.setPrice(cart.getPrice() - returned.get().getPrice());
                    product.setStock(product.getStock() + 1);
                }
            } else {
                throw new NotFoundException("The product does not exist in the current cart!");
            }

            cartDao = new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
            cartDao.update(cart);
            new ProductDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).update(product);

            return CartDTOMapper.mapCartToCartDTO(cart);
        } catch (NoProductFoundException noProductFoundException) {
            logger.error(noProductFoundException.getMessage(), noProductFoundException);
            throw new NotFoundException("The service did not find the desired product!", noProductFoundException);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException("The service did not find the searched cart!", noCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("Error in Service layer while trying to delete an user.", daoException);
        }
    }

}
