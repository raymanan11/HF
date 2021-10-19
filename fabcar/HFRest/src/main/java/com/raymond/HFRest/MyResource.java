package com.raymond.HFRest;

import jakarta.ws.rs.GET;
import org.example.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("cars")
public class MyResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
		ClientApp clientApp = new ClientApp();
		return clientApp.getCars();
    }
}
