package se.emore.ecommerce;

import java.util.ArrayList;
import java.util.Date;

public final class Order
{
	private int orderId;
	private Date date;
	private String user;

	private ArrayList<Product> products = new ArrayList<>();

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
