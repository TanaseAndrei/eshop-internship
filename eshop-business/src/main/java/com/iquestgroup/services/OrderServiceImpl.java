package com.iquestgroup.services;

import com.iquestgroup.constants.PaymentMethod;
import com.iquestgroup.constants.ShippingMethod;
import com.iquestgroup.dtos.CartDTO;
import com.iquestgroup.dtos.OrderDTO;
import com.iquestgroup.exceptions.*;
import com.iquestgroup.factories.CustomEntityManagerFactory;
import com.iquestgroup.implementations.CartDaoImpl;
import com.iquestgroup.implementations.CustomerDaoImpl;
import com.iquestgroup.implementations.OrderDaoImpl;
import com.iquestgroup.implementations.ShopDaoImpl;
import com.iquestgroup.interfaces.CartDao;
import com.iquestgroup.interfaces.OrderService;
import com.iquestgroup.mappers.CartDTOMapper;
import com.iquestgroup.mappers.CartItemToOrderItemMapper;
import com.iquestgroup.mappers.OrderDTOMapper;
import com.iquestgroup.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public CartDTO addShippingMethod(ShippingMethod shippingMethod, String username)
            throws NotFoundException, InternalServerErrorException {
        CartDao cartDao =
                new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Cart cart = cartDao.getCart(username);
            cart.setShippingMethod(shippingMethod);

            cartDao = new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
            cartDao.update(cart);

            return CartDTOMapper.mapCartToOrderInformationCartDTO(cart);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException(
                    "The service did not find the searched cart!", noCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("Error in persistence layer!", daoException);
        }
    }

    @Override
    public CartDTO addPaymentMethod(PaymentMethod paymentMethod, String username)
            throws NotFoundException, InternalServerErrorException {
        CartDao cartDao =
                new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Cart cart = cartDao.getCart(username);
            cart.setPaymentMethod(paymentMethod);
            new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).update(cart);

            return CartDTOMapper.mapCartToOrderInformationCartDTO(cart);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException(
                    "The service did not find the searched cart!", noCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("Error in the persistence layer!", daoException);
        }
    }

    @Override
    public CartDTO addContactPerson(String contactInfo, String username) throws NotFoundException, InternalServerErrorException {
        CartDao cartDao =
                new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Cart cart = cartDao.getCart(username);
            cart.setContactInfo(contactInfo);
            new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).update(cart);

            return CartDTOMapper.mapCartToOrderInformationCartDTO(cart);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException(
                    "The service did not find the searched cart!", noCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("Error in the persistence layer!", daoException);
        }
    }

    @Override
    public CartDTO addBillingDetails(String billingDetails, String username) throws NotFoundException, InternalServerErrorException {
        CartDao cartDao =
                new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        try {
            Cart cart = cartDao.getCart(username);
            cart.setBillingDetails(billingDetails);
            cartDao = new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
            cartDao.update(cart);

            return CartDTOMapper.mapCartToOrderInformationCartDTO(cart);
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException(
                    "The service did not find the searched cart!", noCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("Error in the persistence layer!", daoException);
        }
    }

    @Override
    public List<OrderDTO> placeOrder(String username)
            throws NotFoundException, OrderDetailException, InternalServerErrorException {
        CartDao cartDao =
                new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager());
        List<OrderDTO> orderDTOList = new ArrayList<>();
        try {
            Customer customer = new CustomerDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).getUserByUsername(username);
            Cart cart = cartDao.getCart(username);
            List<CartItem> cartItemList = cart.getListOfCartItems();
            checkOrderDetails(cart);

            int numberOfShops = checkNumberOfShops(cartItemList);
            if (numberOfShops == 1) {
                orderDTOList = placeSingleShopOrder(cart, customer);
            } else {
                orderDTOList = placeMultipleShopsOrders(cart, customer);
            }

            updateCustomer(cart, customer);
            return orderDTOList;
        } catch (NoCartFoundException noCartFoundException) {
            logger.error(noCartFoundException.getMessage(), noCartFoundException);
            throw new NotFoundException(
                    "The service did not find the searched cart!", noCartFoundException);
        } catch (DaoException daoException) {
            logger.error(daoException.getMessage(), daoException);
            throw new InternalServerErrorException("There is an error in the persistence layer!", daoException);
        }
    }

    private int checkNumberOfShops(List<CartItem> cartItemList) {
        Set<Integer> numberOfUniqueShops = cartItemList
                .stream()
                .map(cartItem -> cartItem.getProduct().getShop().getShopId())
                .collect(Collectors.toSet());
        return numberOfUniqueShops.size();
    }

    private List<OrderDTO> placeSingleShopOrder(Cart cart, Customer customer) throws DaoException {
        List<CartItem> cartItemList = cart.getListOfCartItems();
        Order order = new Order();
        order.setShippingMethod(cart.getShippingMethod());
        order.setPaymentMethod(cart.getPaymentMethod());
        order.setPrice(cart.getPrice());
        order.setContactInfo((cart.getContactInfo()));
        order.setBillingDetails(cart.getBillingDetails());
        order.setDate(LocalDateTime.now());
        order.setCustomer(customer);
        Shop shop = new ShopDaoImpl().find(cart.getListOfCartItems().get(0).getProduct().getShop().getShopId());
        order.setShop(shop);
        order.setOrderItemList(CartItemToOrderItemMapper.getOrderItems(cartItemList));
        new OrderDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).create(order);
        return List.of(OrderDTOMapper.mapOrderToOrderDTO(order));
    }

    private List<OrderDTO> placeMultipleShopsOrders(Cart cart, Customer customer) throws DaoException {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<CartItem> cartItemList = cart.getListOfCartItems();
        Set<Integer> shops = cartItemList
                .stream()
                .map(cartItem -> cartItem.getProduct().getShop().getShopId())
                .collect(Collectors.toSet());

        for (Integer integer : shops) {
            List<CartItem> listOfCartItemsOfCurrentShop = cartItemList
                    .stream()
                    .filter(cartItem -> cartItem.getProduct().getShop().getShopId() == integer)
                    .collect(Collectors.toList());
            double totalPrice = listOfCartItemsOfCurrentShop
                    .stream()
                    .mapToDouble(cartItem -> cartItem.getPrice())
                    .sum();
            List<OrderItem> listOfCartItemsMappedToOrderItems = CartItemToOrderItemMapper.getOrderItems(listOfCartItemsOfCurrentShop);
            Order order = new Order();
            order.setShippingMethod(cart.getShippingMethod());
            order.setPaymentMethod(cart.getPaymentMethod());
            order.setPrice(totalPrice);
            order.setContactInfo(cart.getContactInfo());
            order.setBillingDetails(cart.getBillingDetails());
            order.setDate(LocalDateTime.now());
            order.setCustomer(customer);
            Shop shop = new ShopDaoImpl().find(integer);
            order.setShop(shop);
            order.setOrderItemList(listOfCartItemsMappedToOrderItems);
            new OrderDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).create(order);
            orderDTOList.add(OrderDTOMapper.mapOrderToOrderDTO(order));
        }

        return orderDTOList;
    }

    private void updateCustomer(Cart cart, Customer customer) throws DaoException {
        Cart newCart = new Cart();
        newCart.setPrice(0);
        newCart.setCustomer(customer);
        customer.setCart(newCart);
        new CartDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).delete(cart.getId());
        new CustomerDaoImpl(CustomEntityManagerFactory.getInstance().getEntityManager()).update(customer);
    }

    private void checkOrderDetails(Cart cart) throws OrderDetailException {
        checkShippingMethod(cart);
        checkPaymentMethod(cart);
        checkContactInfo(cart);
        checkBillingDetails(cart);
    }

    private void checkShippingMethod(Cart cart) throws OrderDetailException {
        ShippingMethod shippingMethod = cart.getShippingMethod();
        if (shippingMethod == null) {
            throw new OrderDetailException("The cart does not have a shipping method!");
        }
    }

    private void checkPaymentMethod(Cart cart) throws OrderDetailException {
        PaymentMethod paymentMethod = cart.getPaymentMethod();
        if (paymentMethod == null) {
            throw new OrderDetailException("The cart does not have a payment method!");
        }
    }

    private void checkContactInfo(Cart cart) throws OrderDetailException {
        String contactInfo = cart.getContactInfo();
        if (contactInfo == null) {
            throw new OrderDetailException("The cart does not have contact informations!");
        }
    }

    private void checkBillingDetails(Cart cart) throws OrderDetailException {
        String billingDetails = cart.getBillingDetails();
        if (billingDetails == null) {
            throw new OrderDetailException("The cart does not have the billing details!");
        }
    }
}
