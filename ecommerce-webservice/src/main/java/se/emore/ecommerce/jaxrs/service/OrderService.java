package se.emore.ecommerce.jaxrs.service;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import se.emore.ecommerce.Order;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlOrderRepository;
import se.emore.ecommerce.repository.SqlUserRepository;

@Path("order")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public final class OrderService
{

	SqlOrderRepository rep = new SqlOrderRepository();

	@Context
	public UriInfo uriInfo;

	@POST
	@Path("{userId}")
	public Response createOrder(@PathParam("userId") final int userId, int[] productId) throws RepositoryException
	{

		String id = "" + rep.createOrder(new SqlUserRepository().getUser(userId), productId);
		final URI location = uriInfo.getBaseUriBuilder().path("/order/" + id).build();
		return Response.status(Status.CREATED).location(location).build();

	}

	@GET
	@Path("user/{userId}")
	public Response getUsersOrders(@PathParam("userId") final int userId) throws RepositoryException
	{
		final Order[] order = rep.getUserOrders(userId);
		return Response.ok(order).build();
	}

	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("orderId") final int orderId) throws RepositoryException
	{
		final Order order = rep.getOrder(orderId);
		return Response.ok().entity(order).build();
	}

	@PUT
	@Path("{userId}/{orderId}")
	public Response updateOrder(@PathParam("userId") final int userId,
								@PathParam("orderId") final int orderId,
								final int[] productIds) throws RepositoryException
	{
		rep.removeOrder(orderId);
		rep.createOrder(new SqlUserRepository().getUser(userId), productIds);
		return Response.ok().build();
	}

	@DELETE
	@Path("{orderId}")
	public Response removeOrder(@PathParam("orderId") final int orderId) throws RepositoryException
	{
		rep.removeOrder(orderId);
		return Response.ok().build();

	}

}