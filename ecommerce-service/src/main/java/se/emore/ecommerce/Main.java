package se.emore.ecommerce;

import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlOrderRepository;

public class Main
{
	public static void main(String[] args) throws RepositoryException
	{

		// System.out.println(new SqlUserRepository().removeUser("Osama"));
		//
		// User user = new User("Osama", "PW");
		// new SqlUserRepository().addUser(user);
		// System.out.println(user.getId() + " " + user.getUsername() + " " +
		// user.getPassword());
		//
		// user.addProduct(new SqlProductRepository().getProduct(1));
		// user.addProduct(new SqlProductRepository().getProduct(3));
		// user.addProduct(new SqlProductRepository().getProduct(5));
		// user.addProduct(new SqlProductRepository().getProduct(4));
		//
		// System.out.println();
		//
		// for(Product products : user.getProducts()) {
		// System.out.println(products);
		// }
		//
		// new SqlOrderRepository().createOrder(user);

		Order order = new SqlOrderRepository().getOrder(1001);

		for (Product product : order.getProducts())
		{
			System.out.println(product.getProductName());
		}

		// user.setUserId(sqlUserId);
		// int id = new SqlUserRepository().updateUser(user);
		// System.out.println(id);

		//
		// Product product;
		// product = new SqlProductRepository().getProduct(5);
		// System.out.println(product.getProductId() + " " +
		// product.getProductName() + " " + product.getProductPrice());
		//
		// Product product = new Product("Hunden2", 5, 10000);
		// new SqlProductRepository().updateProduct(product);
		//
		// new SqlProductRepository().removeProduct(5);
	}
}
