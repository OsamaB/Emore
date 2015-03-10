package se.emore.ecommerce;

import se.emore.ecommerce.exception.RepositoryException;

public class Main
{
	public static void main(String[] args) throws RepositoryException
	{
		///////////////
		// PRODUKTER //
		///////////////
		
//		// Skapa produkt:
//		Product product = new Product("Leksak", 25);
//		new SqlProductRepository().addProduct(product);
//		
//		// Hämta/Läsa produkt:
//		Product product = new SqlProductRepository().getProduct(2);
//		System.out.println(product.getProductName());
//		
//		// Hämta/Läsa alla produkter:
//		for(Product product : new SqlProductRepository().getAllProducts())
//		{
//			System.out.println(product);
//		}
//		
//		// Uppdatera produkt:
//		Product newProduct = new Product("Kaka", 100);
//		Product product = new SqlProductRepository().updateProduct(2, newProduct);
//		System.out.println(product.getProductName());
//		
//		// Radera produkt:
//		System.out.println(new SqlProductRepository().removeProduct(2));
		
		///////////////
		// ANVÄNDARE //
		///////////////
		
//		// Skapa användare:
//		User user = new User("Superman", "secret");
//		new SqlUserRepository().addUser(user);
//		
//		// Hämta/Läsa produkt:
//		User user = new SqlUserRepository().getUser(5);
//		System.out.println(user.getUsername());
//		
//		// Uppdatera användare:
//		User newUser = new User("Batman", "cave");
//		User user = new SqlUserRepository().updateUser(5, newUser);
//		System.out.println(user.getUsername());
//		
//		// Radera produkt:
//		System.out.println(new SqlUserRepository().removeUser(5));
		
		////////////
		// ORDRAR //
		////////////
		
//		// Skapa order:
//		User user = new User("Superman", "secret");
//		Order order = new Order(100, user);
//		int[] orders = {1, 2, 3, 4, 5};
//		new SqlOrderRepository().createOrder(user, orders);
//		
//		// Hämta/Läsa order för en specifik användare:
//		Order[] orders = new SqlOrderRepository().getUserOrders(1001);
//		System.out.println(orders);
//		
//		// Hämta/Läsa order:
//		Order order = new SqlOrderRepository().getOrder(1002);
//		System.out.println(order);
//		
//		// Uppdatera order:
//		
//		// Radera order:
	}
}
