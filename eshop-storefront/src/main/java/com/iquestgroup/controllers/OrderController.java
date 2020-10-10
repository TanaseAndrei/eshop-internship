package com.iquestgroup.controllers;

import com.iquestgroup.decoder.Base64Decoder;
import com.iquestgroup.dtos.CartDTO;
import com.iquestgroup.dtos.OrderDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.exceptions.OrderDetailException;
import com.iquestgroup.exceptions.ServiceException;
import com.iquestgroup.interfaces.OrderService;
import com.iquestgroup.services.OrderServiceImpl;
import com.iquestgroup.wrappers.BillingDetailsWrapper;
import com.iquestgroup.wrappers.ContactPersonWrapper;
import com.iquestgroup.wrappers.PaymentMethodWrapper;
import com.iquestgroup.wrappers.ShippingMethodWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controller that provides the functionality for placing orders through HTTP requests.
 */
@Path("/order")
public class OrderController {


    /**
     * Method that adds a shipping method to the customer's cart.
     *
     * @param httpServletRequest    the request object that contains the customer's data
     * @param shippingMethodWrapper wrapper for POST body that contains the shipping method
     * @return a Response object
     * @throws NotFoundException thrown if the customer's cart does not exist. The exception returns
     *                           a HTTP status code 404 and a message
     */
    @POST
    @Path("/shipping-method")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShippingMethod(@Context HttpServletRequest httpServletRequest,
                                      ShippingMethodWrapper shippingMethodWrapper) throws NotFoundException, InternalServerErrorException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartDTO cartDTO = new OrderServiceImpl().addShippingMethod(shippingMethodWrapper.getShippingMethod(), username);
        return Response.status(HttpServletResponse.SC_OK)
                .entity(cartDTO)
                .build();
    }

    /**
     * Method that adds a payment method to the customer's cart.
     *
     * @param paymentMethodWrapper wrapper for POST body that contains the payment method
     * @param httpServletRequest   the request object that contains the customer's data
     * @return a Response object
     * @throws NotFoundException thrown if the customer's cart does not exist. The exception returns
     *                           a HTTP status code 404 and a message
     */
    @POST
    @Path("/payment-method")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPaymentMethod(PaymentMethodWrapper paymentMethodWrapper,
                                     @Context HttpServletRequest httpServletRequest) throws NotFoundException, InternalServerErrorException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartDTO cartDTO = new OrderServiceImpl().addPaymentMethod(paymentMethodWrapper.getPaymentMethod(), username);
        return Response.status(HttpServletResponse.SC_OK)
                .entity(cartDTO)
                .build();
    }


    /**
     * Method that adds a contact person to the customer's cart.
     *
     * @param contactPersonWrapper wrapper for POST body that contains the contact person
     * @param httpServletRequest   the request object that contains the customer's data
     * @return a Response object
     * @throws NotFoundException thrown if the customer's cart does not exist. The exception returns
     *                           a HTTP status code 404 and a message
     */
    @POST
    @Path("/contact-person")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addContactPerson(ContactPersonWrapper contactPersonWrapper,
                                     @Context HttpServletRequest httpServletRequest) throws NotFoundException, InternalServerErrorException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartDTO cartDTO = new OrderServiceImpl().addContactPerson(contactPersonWrapper.getContactPerson(), username);
        return Response.status(HttpServletResponse.SC_OK)
                .entity(cartDTO)
                .build();
    }


    /**
     * Method that adds the billing details to the customer's cart.
     *
     * @param billingDetailsWrapper wrapper for POST body that contains the billing details
     * @param httpServletRequest    the request object that contains the customer's data
     * @return a Response object
     * @throws NotFoundException thrown if the customer's cart does not exist. The exception returns
     *                           a HTTP status code 404 and a message
     */
    @POST
    @Path("/billing-details")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBillingDetails(BillingDetailsWrapper billingDetailsWrapper,
                                      @Context HttpServletRequest httpServletRequest) throws NotFoundException, InternalServerErrorException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartDTO cartDTO = new OrderServiceImpl().addBillingDetails(billingDetailsWrapper.getBillingDetails(), username);
        return Response.status(HttpServletResponse.SC_OK)
                .entity(cartDTO)
                .build();
    }


    /**
     * Method that places the order to the customer's cart.
     *
     * @param httpServletRequest the request object that contains the customer's data
     * @return a Response object
     * @throws OrderDetailException         thrown if, when checking the cart's details, needed for placing the order,
     *                                      are missing
     * @throws InternalServerErrorException thrown if there is an error in the persistence layer
     * @throws NotFoundException            thrown if the cart does not exist
     */
    @POST
    @Path("/place-order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(@Context HttpServletRequest httpServletRequest) throws OrderDetailException, InternalServerErrorException, NotFoundException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        OrderService orderService = new OrderServiceImpl();
        List<OrderDTO> orderDTOList = orderService.placeOrder(username);
        return Response.status(HttpServletResponse.SC_OK)
                .entity(orderDTOList)
                .build();
    }
}
