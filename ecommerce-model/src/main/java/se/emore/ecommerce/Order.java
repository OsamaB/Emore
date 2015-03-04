package se.emore.ecommerce;

import java.util.Date;

public final class Order {
	
	private Long orderId;
	private Long userId;
	private Date date = new Date(System.currentTimeMillis());
	
	public Order(Long orderId, Long userId, Date date) {
		this.orderId = orderId;
		this.userId = userId;
		this.date = date;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public Date getDate() {
		return date;
	}	
	
}
