package se.emore.ecommerce;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public final class Order
{
	@XmlElement
	private int orderId;
	@XmlElement
	private Date date;
	@XmlElement
	private String user;
	@XmlElement
	private ArrayList<Product> products = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private Order(){}

	public Order(int orderId, User user)
	{
		this.orderId = orderId;
		this.date = new Date(System.currentTimeMillis());

		setProducts(user.getProducts());
	}

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int sqlOrderId)
	{
		orderId = sqlOrderId;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}
	
	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public ArrayList<Product> getProducts()
	{
		return products;
	}

	public void setProducts(ArrayList<Product> products)
	{
		this.products = products;
	}
}
