package se.emore.ecommerce.logic;

import se.emore.ecommerce.Order;

public interface OrderLogic {
	
	public void addOrder(Order order);
	public void removeOrder(Long orderId);
	public void getOrder(Long orderId);
}
