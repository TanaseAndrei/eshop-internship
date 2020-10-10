package com.iquestgroup.interfaces;

import com.iquestgroup.constants.PaymentMethod;
import com.iquestgroup.constants.ShippingMethod;
import com.iquestgroup.dtos.CartDTO;
import com.iquestgroup.dtos.OrderDTO;
import com.iquestgroup.exceptions.*;

import java.util.List;

/**
 * Interface that provides the service functionality of an Order.
 */
public interface OrderService {

    /**
     * Method that adds a shipping method to an order.
     *
     * @param shippingMethod the shipping method of the order
     * @param username       the username of the customer that owns the cart
     * @throws NotFoundException            thrown if there is no cart found for the
     *                                      targeted username
     * @throws InternalServerErrorException thrown if there is an error in the
     *                                      persistence layer while trying to
     *                                      update the cart
     */
    CartDTO addShippingMethod(ShippingMethod shippingMethod, String username) throws NotFoundException, InternalServerErrorException;

    /**
     * Method that adds a payment method to an order.
     *
     * @param paymentMethod the payment method of the order
     * @param username      the username of the customer that owns the cart
     * @throws NotFoundException            is thrown if there is no cart found for the
     *                                      targeted username
     * @throws InternalServerErrorException thrown if there is an error in the
     *                                      persistence layer while trying to
     *                                      update the cart
     */
    CartDTO addPaymentMethod(PaymentMethod paymentMethod, String username) throws NotFoundException, InternalServerErrorException;

    /**
     * Method that adds information about the contact person of an order.
     *
     * @param contactInfo information about the contact person
     * @param username    the name of the customer that owns the cart
     * @throws NotFoundException            is thrown if there is no cart found for the
     *                                      targeted username
     * @throws InternalServerErrorException thrown if there is an error in the
     *                                      persistence layer while trying to
     *                                      update the cart
     */
    CartDTO addContactPerson(String contactInfo, String username) throws NotFoundException, InternalServerErrorException;

    /**
     * Method that adds the billing details of an order.
     *
     * @param billingDetails information about the billing process
     * @param username       the name of the customer that owns the cart
     * @throws NotFoundException            is thrown if there is no cart found for the
     *                                      targeted username
     * @throws InternalServerErrorException thrown if there is an error in the
     *                                      persistence layer while trying to
     *                                      update the cart
     */
    CartDTO addBillingDetails(String billingDetails, String username) throws NotFoundException, InternalServerErrorException;

    /**
     * Method used to place an order.
     *
     * @param username the name of the customer that owns the cart
     * @throws NotFoundException            thrown if there is no cart found for the
     *                                      targeted username
     * @throws InternalServerErrorException thrown if there is an error in the
     *                                      persistence layer while trying to
     *                                      update the cart
     * @throws OrderDetailException         thrown if an order detail is missing, like
     *                                      contact person or shipping method, etc.
     */
    List<OrderDTO> placeOrder(String username) throws NotFoundException, InternalServerErrorException, OrderDetailException;
}
