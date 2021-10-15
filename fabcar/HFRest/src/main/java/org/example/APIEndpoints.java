package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("cars")
public class APIEndpoints {

	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	public String getCars() {
		ClientApp clientApp = new ClientApp();
		return clientApp.getCars();
	}
}
