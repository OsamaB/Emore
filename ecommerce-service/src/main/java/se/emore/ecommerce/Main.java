package se.emore.ecommerce;

import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlProductRepository;
import se.emore.ecommerce.repository.SqlUserRepository;

public class Main {
	public static void main(String[] args) throws RepositoryException {
				
		
//		User user1 = new SqlUserRepository().getUser(1001);
//		System.out.println(user1.getId() + " " + user1.getUsername() + " " + user1.getPassword());
		
		User user = new User("jkdsadw", "hhjkhkleh");
		
		int id = new SqlUserRepository().addUser(user);
		
		System.out.println(user.getId());
		
		
//		int id = new SqlUserRepository().updateUser(user);
//		System.out.println(id);
		
//		int id = new SqlUserRepository().removeUser(1001);
		
//		Product product = new Product("Katter", 3250);
//		Product product2 = new Product("Hund", 3025.5);
//		int id = new SqlProductRepository().addProduct(product2);
//		System.out.println(id);
		
//		Product product;
//		product = new SqlProductRepository().getProduct(5);
//		System.out.println(product.getProductId() + " " + product.getProductName() + " " + product.getProductPrice());
		
//		Product product = new Product("Hunden2", 5, 10000);
//		new SqlProductRepository().updateProduct(product);
		
//		new SqlProductRepository().removeProduct(5);
		
		
		
		
		
		
	}
}
