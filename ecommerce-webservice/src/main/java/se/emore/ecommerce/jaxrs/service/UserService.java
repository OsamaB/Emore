package se.emore.ecommerce.jaxrs.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlUserRepository;

@Path("user")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public final class UserService {

	SqlUserRepository rep = new SqlUserRepository();

	@POST
	public Response createUser(User user) throws RepositoryException {
		rep.addUser(user);
		
		return Response.ok().entity(user).build();
	}
	
	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") final int userId)
			throws RepositoryException {

		final User user = rep.getUser(userId);

		return Response.ok(user).build();

	}

	@PUT
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") final int userId, User user)
			throws RepositoryException {
		rep.updateUser(userId, user);

		return Response.ok().build();
	}

	@DELETE
	@Path("{userId}")
	public Response removeUser(@PathParam("userId") final int userId)
			throws RepositoryException {
		rep.removeUser(userId);

		return Response.ok("Deleted").build();
	}
}
