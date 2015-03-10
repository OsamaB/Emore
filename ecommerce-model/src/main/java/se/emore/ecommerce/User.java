package se.emore.ecommerce;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public final class User
{

	@XmlElement()
	private String username;

	@XmlElement()
	private String password;

	@XmlElement
	ArrayList<Product> products = new ArrayList<>();

	@XmlElement()
	private int id;

	@SuppressWarnings("unused")
	private User(){}

	public User(final String username, final String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public int getId()
	{
		return id;
	}

	public void setUserId(int sqlUserId)
	{
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
