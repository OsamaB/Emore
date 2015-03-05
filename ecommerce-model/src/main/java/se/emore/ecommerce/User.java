package se.emore.ecommerce;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
	private String username;
	private String password;
	private Map<Integer, Product> shoppingCart = new HashMap<>();
	private static final AtomicInteger productIdAtomicInteger = new AtomicInteger();
	private int id;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}	
	
	public int getId() {
		return id;
	}
	
	public void setUserId(int sqlUserId) {
		id = sqlUserId;
	}
	
	public Map<Integer, Product> getShoppingCart() {
		return shoppingCart;
	}
	
	public void addToShoppingCart(Product product) {
		shoppingCart.put(productIdAtomicInteger.incrementAndGet(), product);
	}
	
}
