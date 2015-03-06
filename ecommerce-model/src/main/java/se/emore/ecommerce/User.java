package se.emore.ecommerce;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	@XmlElement
	private String username;
	@XmlElement
	private String password;
	private Map<Integer, Product> shoppingCart = new HashMap<>();
	private static final AtomicInteger productIdAtomicInteger = new AtomicInteger();
	@XmlElement
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
	
	public Map<Integer, Product> getShoppingCart() {
		return shoppingCart;
	}
	
	public void addToShoppingCart(Product product) {
		shoppingCart.put(productIdAtomicInteger.incrementAndGet(), product);
	}
	
}
