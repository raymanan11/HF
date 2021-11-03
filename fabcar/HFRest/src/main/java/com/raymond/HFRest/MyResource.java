package com.raymond.HFRest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;

import org.example.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("parts")
public class MyResource {
	ClientApp clientApp = new ClientApp();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getParts() {
		return clientApp.getAllAirlineParts();
    }
    
    @GET
    @Path("{partName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentPart(@PathParam("partName") String partName) {
		return clientApp.queryAirlinePart(partName);
    }
    
    @GET
    @Path("/{airlinePart}/name/{newOwner}")
    public String changePartOwner(@PathParam("airlinePart") String airlinePart, @PathParam("newOwner") String newOwner) {
		return clientApp.changePartOwner(airlinePart, newOwner);
    }
    
}
