package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("myresource")
public class APIEndpoints {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public static String getCars() {
//		ClientApp clientApp = new ClientApp();
//		return clientApp.getCars();
		return "Getting cars!";
	}
}
