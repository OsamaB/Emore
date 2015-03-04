package se.emore.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class User {
	private String username;
	private String password;
	private int id;
	private Map<Long, Product> shoppingCart = new HashMap<>();
	
	public User(String username, String password, int id) {
		this.username = username;
		this.password = password;
		this.id = id;
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
	
	public Map<Long, Product> getShoppingCart() {
		return shoppingCart;
	}
	
}
