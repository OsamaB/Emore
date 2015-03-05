package se.emore.ecommerce;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;

	private int id;
	
	ArrayList<Product> products = new ArrayList<>();
	
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
	
	public ArrayList<Product> getProducts()
	{
		return products;
	}
	
	public void addProduct(Product product)
	{
		products.add(product);
	}
	
	public void removeProduct(Product product)
	{
		products.remove(product);
	}
}
