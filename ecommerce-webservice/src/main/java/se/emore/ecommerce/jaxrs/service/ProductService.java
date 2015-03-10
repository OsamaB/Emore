package se.emore.ecommerce.jaxrs.service;

import java.util.ArrayList;

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

import se.emore.ecommerce.Product;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlProductRepository;

@Path("product")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public final class ProductService {

	SqlProductRepository rep = new SqlProductRepository();

	@POST
	public Response addProduct(Product product) throws RepositoryException {
		
		rep.addProduct(product);
		return Response.ok().entity(product).build();
	}
	
	@GET
	public Response getAllProducts() throws RepositoryException {
		ArrayList<Product> products = new ArrayList<>();
		products = rep.getAllProducts();
		return Response.ok().entity(products).build();
	}
	
	@GET
	@Path("{productId}")
	public Response getProduct(@PathParam("productId") final int productId)
			throws RepositoryException {
		
		final Product product = rep.getProduct(productId);
						
		return Response.ok(product).build();		
	}
	
	@PUT
	@Path("{productId}")
	public Response updateProduct(@PathParam("productId") final int productId, Product product) throws RepositoryException {
		rep.updateProduct(productId, product);
		product.setProductId(productId);
		
		return Response.ok().entity(product).build();
	}
	
	@DELETE
	@Path("{productId}")
	public Response removeProduct(@PathParam("productId") final int productId) throws RepositoryException {
		rep.removeProduct(productId);
		
		return Response.ok().build();
	}
}
