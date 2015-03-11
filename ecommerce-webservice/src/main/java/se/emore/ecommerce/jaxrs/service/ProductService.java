package se.emore.ecommerce.jaxrs.service;

import java.net.URI;
import java.util.ArrayList;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlProductRepository;

@Path("product")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public final class ProductService
{

	SqlProductRepository rep = new SqlProductRepository();

	@Context
	public UriInfo uriInfo;

	@POST
	public Response addProduct(Product product) throws RepositoryException
	{

		rep.addProduct(product);
		String id = "" + product.getProductId();
		final URI location = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.status(Status.CREATED).location(location).build();

	}

	@GET
	public Response getAllProducts() throws RepositoryException
	{
		ArrayList<Product> products = new ArrayList<>();
		products = rep.getAllProducts();
		return Response.ok().entity(products).build();
	}

	@GET
	@Path("{productId}")
	public Response getProduct(@PathParam("productId") final int productId)	throws RepositoryException
	{
		final Product product = rep.getProduct(productId);
		return Response.ok(product).build();
	}

	@PUT
	@Path("{productId}")
	public Response updateProduct(@PathParam("productId") final int productId, Product product) throws RepositoryException
	{
		rep.updateProduct(productId, product);
		return Response.ok().build();
	}

	@DELETE
	@Path("{productId}")
	public Response removeProduct(@PathParam("productId") final int productId) throws RepositoryException
	{
		rep.removeProduct(productId);
		return Response.ok().build();
	}
}
