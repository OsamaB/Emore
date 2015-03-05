package se.emore.ecommerce;

import java.util.Date;

public final class Order {
	
	private int orderId;
	private int userId;
	private Date date;
	
	public Order(int orderId, int userId) {
		this.orderId = orderId;
		this.userId = userId;
		this.date = new Date(System.currentTimeMillis());
	}

	public int getUserId() {
		return userId;
	}

	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int sqlOrderId) {
		orderId = sqlOrderId;
	}

	public Date getDate() {
		return date;
	}	
	
}
