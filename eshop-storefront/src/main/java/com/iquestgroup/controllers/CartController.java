package com.iquestgroup.controllers;

import com.iquestgroup.decoder.Base64Decoder;
import com.iquestgroup.dtos.CartDTO;
import com.iquestgroup.dtos.CartItemDTO;
import com.iquestgroup.exceptions.InsufficientStockException;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.interfaces.CartService;
import com.iquestgroup.services.CartServiceImpl;
import com.iquestgroup.wrappers.ProductIdWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controller that provides the functionality for the cart through HTTP requests.
 */
@Path("/cart")
public class CartController {

    /**
     * Method that returns a Response object with a CartDTO inside.
     *
     * @param httpServletRequest the request object that contains the customer's data
     * @return a Response object
     * @throws NotFoundException if the searched cart does not exist. The
     *                           exception returns a HTTP status code 404
     *                           and a message
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@Context HttpServletRequest httpServletRequest) throws NotFoundException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartService cartService = new CartServiceImpl();
        CartDTO cartDTO = cartService.getCart(username);
        return Response
                .status(Response.Status.OK)
                .entity(cartDTO)
                .build();
    }

    /**
     * Method that retrieves all the items from a cart.
     *
     * @param httpServletRequest the request object that contains the customer's data
     * @return a Response object
     * @throws NotFoundException if the searched cart does not exist. The
     *                           exception returns a HTTP status code 404
     *                           and a message
     */
    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems(@Context HttpServletRequest httpServletRequest) throws NotFoundException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartService cartService = new CartServiceImpl();
        List<CartItemDTO> listOfItems = cartService.getItems(username);
        return Response
                .status(Response.Status.OK)
                .entity(listOfItems)
                .build();
    }

    /**
     * Method that adds a product to a customer's cart.
     *
     * @param httpServletRequest the request that contains the customer's name
     * @param productId          the id of the product you want to add
     * @return status code 200 if the operation was successfully, 404 if
     * the cart does not exist, 404 if the product does not exist
     * or 400 if there is an internal service exception.
     * @throws NotFoundException            if the searched cart does not exist. The
     *                                      exception returns a HTTP status code 404
     *                                      and a message
     * @throws InternalServerErrorException if there is an internal exception in the service layer.
     *                                      The exception returns a HTTP status code 500 and a
     *                                      message
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@Context HttpServletRequest httpServletRequest, ProductIdWrapper productId) throws NotFoundException, InternalServerErrorException, InsufficientStockException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartService cartService = new CartServiceImpl();
        CartDTO cartDTO = cartService.addProduct(productId.getProductId(), username);
        return Response
                .status(Response.Status.OK)
                .entity(cartDTO)
                .build();
    }

    /**
     * Method that deletes a product from a customer's cart.
     *
     * @param httpServletRequest the request that contains the customer's name
     * @param productId          the id of the product you want to delete
     * @return status code 200 if the operation was successfully, 404 if
     * the cart does not exist, 404 if the product does not exist
     * or 400 if there is an internal service exception.
     * @throws NotFoundException            if the searched cart does not exist. The
     *                                      exception returns a HTTP status code 404
     *                                      and a message
     * @throws InternalServerErrorException if there is an internal exception in the service layer.
     *                                      The exception returns a HTTP status code 500 and a
     *                                      message
     */
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@Context HttpServletRequest httpServletRequest, ProductIdWrapper productId) throws NotFoundException, InternalServerErrorException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        CartService cartService = new CartServiceImpl();
        CartDTO cartDTO = cartService.deleteProduct(productId.getProductId(), username);
        return Response
                .status(Response.Status.OK)
                .entity(cartDTO)
                .build();
    }

}
