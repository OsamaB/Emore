package se.emore.ecommerce.logic;

import java.util.Map;

import se.emore.ecommerce.Product;

public interface OrderLogic {
	
	public void addOrder(Map<Integer, Product> map);
	public void removeOrder(int orderId);
	public Map<Integer, Product> getOrder(int orderId);
}
