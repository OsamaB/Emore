package se.emore.ecommerce.jaxrs.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlUserRepository;

@Path("user")
public final class UserServlet {

	SqlUserRepository rep = new SqlUserRepository();

	// String hej = "hej";

	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final int userId)
			throws RepositoryException {
		
		User user = rep.getUser(userId);
		
		System.out.println(user.getId());
		
		System.out.println(user.getUsername() + " är deras username");
		
		String string = user.getUsername();
		
		return Response.ok().entity(string).build();

		// user = SqlUserRepository

	}
}
