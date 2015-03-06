package se.emore.ecommerce.jaxrs.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlUserRepository;

@Path("user")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public final class UserService {

	SqlUserRepository rep = new SqlUserRepository();

	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final int userId)
			throws RepositoryException {
		
		final User user = rep.getUser(userId);
				
//		String username = user.getUsername();
		
		return Response.ok().entity(user.getUsername()).build();
	
		
		
	}
}
