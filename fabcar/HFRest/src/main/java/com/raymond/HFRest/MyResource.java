package com.raymond.HFRest;

import jakarta.ws.rs.GET;
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getParts() {
		ClientApp clientApp = new ClientApp();
		return clientApp.getAllAirlineParts();
    }
    
    @GET
    @Path("{partName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentPart(@PathParam("partName") String partName) {
		ClientApp clientApp = new ClientApp();
		return clientApp.queryAirlinePart(partName);
    }
    
//    @GET
//    @Path("{newOwner}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String changePartOwner(@PathParam("airlinePart") String airlinePart, @PathParam("newOwner") String newOwner) {
//		ClientApp clientApp = new ClientApp();
//		return clientApp.changePartOwner(airlinePart, newOwner);
//    }
    
}
