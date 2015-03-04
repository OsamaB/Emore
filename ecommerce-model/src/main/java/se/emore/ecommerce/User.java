package se.emore.ecommerce;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
	private String username;
	private String password;
	private Map<Long, Product> shoppingCart = new HashMap<>();
	private static final AtomicInteger atomicInteger = new AtomicInteger(1000);
	private final int id;
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.id = atomicInteger.incrementAndGet();
	}
	
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
