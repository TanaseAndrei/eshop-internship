package com.iquestgroup.controllers;

import com.iquestgroup.decoder.Base64Decoder;
import com.iquestgroup.dtos.FavoritesDTO;
import com.iquestgroup.dtos.FavoritesItemDTO;
import com.iquestgroup.exceptions.InternalServerErrorException;
import com.iquestgroup.exceptions.NotFoundException;
import com.iquestgroup.interfaces.FavoritesService;
import com.iquestgroup.services.FavoritesServiceImpl;
import com.iquestgroup.wrappers.ProductIdWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controller that provides the functionality for the favorites cart through HTTP requests.
 */
@Path("/favorites")
public class FavoritesController {

    /**
     * Method that returns a Response object with a FavoritesDTO inside.
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
    public Response getFavoritesCart(@Context HttpServletRequest httpServletRequest) throws NotFoundException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        FavoritesService favoritesService = new FavoritesServiceImpl();
        FavoritesDTO favoritesDTO = favoritesService.getFavoritesCart(username);
        return Response
                .status(Response.Status.OK)
                .entity(favoritesDTO)
                .build();
    }

    /**
     * Method that retrieves all the favorites items from a favorites cart.
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
    public Response getFavoritesItems(@Context HttpServletRequest httpServletRequest) throws NotFoundException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        FavoritesService favoritesService = new FavoritesServiceImpl();
        List<FavoritesItemDTO> favoritesItemDTOList = favoritesService.getFavoritesItems(username);
        return Response
                .status(Response.Status.OK)
                .entity(favoritesItemDTOList)
                .build();
    }

    /**
     * Method that adds a product to a customer's favorites cart.
     *
     * @param httpServletRequest the request that contains the customer's name
     * @param productId          the id of the product you want to add
     * @return a Response object
     * @throws NotFoundException            if the searched favorites cart does not exist. The
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
    public Response addProduct(ProductIdWrapper productId, @Context HttpServletRequest httpServletRequest) throws NotFoundException, InternalServerErrorException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        FavoritesService favoritesService = new FavoritesServiceImpl();
        FavoritesDTO favoritesDTO = favoritesService.addProduct(productId.getProductId(), username);
        return Response
                .status(Response.Status.OK)
                .entity(favoritesDTO)
                .build();
    }

    /**
     * Method that deletes a product from a customer's favorites cart.
     *
     * @param httpServletRequest the request that contains the customer's name
     * @param productId          the id of the product you want to delete
     * @return a Response object
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
    public Response deleteProduct(ProductIdWrapper productId, @Context HttpServletRequest httpServletRequest) throws NotFoundException, InternalServerErrorException {
        Base64Decoder base64Decoder = new Base64Decoder();
        String username = base64Decoder.getUsernameDecodedFromUrl(httpServletRequest);
        FavoritesService favoritesService = new FavoritesServiceImpl();
        FavoritesDTO favoritesDTO = favoritesService.deleteProduct(productId.getProductId(), username);
        return Response
                .status(Response.Status.OK)
                .entity(favoritesDTO)
                .build();
    }
}
