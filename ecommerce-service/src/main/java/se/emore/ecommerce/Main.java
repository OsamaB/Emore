package se.emore.ecommerce;

import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlOrderRepository;
import se.emore.ecommerce.repository.SqlProductRepository;
import se.emore.ecommerce.repository.SqlUserRepository;

public class Main
{
	public static void main(String[] args) throws RepositoryException
	{

//		 System.out.println(new SqlUserRepository().removeUser("Osama"));
//		
//		 User user = new User("Osama", "PW");
//		 new SqlUserRepository().addUser(user);
//		 System.out.println(user.getId() + " " + user.getUsername() + " " +
//		 user.getPassword());
//		
//		 user.addProduct(new SqlProductRepository().getProduct(1));
//		 user.addProduct(new SqlProductRepository().getProduct(3));
//		 user.addProduct(new SqlProductRepository().getProduct(5));
//		 user.addProduct(new SqlProductRepository().getProduct(4));
//		
//		 for(Product products : user.getProducts()) {
//		 System.out.println(products);
//		 }
//		
//		 new SqlOrderRepository().createOrder(user);

		new SqlOrderRepository().removeOrder(1001);

		// user.setUserId(sqlUserId);
		// int id = new SqlUserRepository().updateUser(user);
		// System.out.println(id);

		
//		 Product product;
//		 product = new SqlProductRepository().getProduct(5);
//		 System.out.println(product.getProductId() + " " +
//		 product.getProductName() + " " + product.getProductPrice());
//		
//		 product = new Product("Hunden2", 10000);
//		 new SqlProductRepository().updateProduct(product.getProductId());
		//
		// new SqlProductRepository().removeProduct(5);
	}
}
