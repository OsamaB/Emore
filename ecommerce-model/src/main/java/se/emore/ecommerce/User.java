package se.emore.ecommerce;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	
	@XmlElement(required=true)
	private String username;
	
	@XmlElement(required=true)
	private String password;
	
	ArrayList<Product> products = new ArrayList<>();
	
	@XmlElement(required=true)
	private int id;
	
//	@SuppressWarnings("unused")
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
	
	public void addProduct(Product product)
	{
		products.add(product);
	}
	
	public ArrayList<Product> getProducts()
	{
		return products;
	}
	
	public void removeProduct(Product product)
	{
		products.remove(product);
	}
	
}
