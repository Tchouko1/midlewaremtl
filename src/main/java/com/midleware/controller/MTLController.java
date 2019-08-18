package com.midleware.controller;

import com.midleware.address.AddressStation;
import com.midleware.dao.AddressStationDAOImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("mtl")
public class MTLController {


    @GET
    @Path("/listAddress")
    @Produces(MediaType.APPLICATION_XML)
    public List<AddressStation> getAllAddress() {
        AddressStationDAOImpl stationDAO = new AddressStationDAOImpl();
        List<AddressStation> addressStationList =  stationDAO.getAllAddress();

        return addressStationList;
    }
}
