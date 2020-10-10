package com.iquestgroup.controllers;

import com.iquestgroup.dtos.ShopDTO;
import com.iquestgroup.exceptions.ServiceException;
import com.iquestgroup.interfaces.ShopService;
import com.iquestgroup.interfaces.UserService;
import com.iquestgroup.models.Seller;
import com.iquestgroup.models.Shop;
import com.iquestgroup.services.ShopServiceImpl;
import com.iquestgroup.services.UserServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Provides the full set of resources applicable to the Shop class
 */
@Path("/shop")
public class ShopController {

    private final ShopService shopService;
    private final UserService userService;

    public ShopController() {
        shopService = new ShopServiceImpl();
        userService = new UserServiceImpl();
    }

    /**
     * Creates a new shop based on a POST request from the HTTP client.
     *
     * @param shop the new Entity that will be persisted in the database
     * @return the new Entity in JSON format and a 200 OK response code or
     * @throws ServiceException if the service failed to register the user.
     */
    @Path("/createshop")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerShop(ShopDTO shop) throws ServiceException {
        shopService.createShop(shop);
        return Response.ok(shop).build();
    }

    /**
     * Binds one seller entity to a Shop Entity creating a seller selling in a certain shop.
     *
     * @param idShop the id of the shop that will be bind.
     * @param idUser the id of the seller that will be bind.
     * @throws ServiceException
     */
    @Path("/bindshop/{idShop}/{idUser}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShop(@PathParam("idShop") int idShop, @PathParam("idUser") int idUser) throws ServiceException {
        ShopDTO shopDTO = shopService.bindShopToSeller(idShop, idUser);
        return Response.ok(shopDTO).build();
    }
}
