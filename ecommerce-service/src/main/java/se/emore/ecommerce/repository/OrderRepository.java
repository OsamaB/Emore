package se.emore.ecommerce.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.logic.OrderLogic;

public class OrderRepository implements OrderLogic {
	
	private Map<Integer, Map<Integer,Product>> orders = new HashMap<>();
	private AtomicInteger atomicInteger = new AtomicInteger(1000);
	private int orderId;
	
	@Override
	public void addOrder(Map<Integer, Product> map) {
		orders.put(orderId = atomicInteger.incrementAndGet(), map);
	}

	@Override
	public void removeOrder(int orderId) {
		orders.remove(orderId);
	}

	@Override
	public Map<Integer, Product> getOrder(int orderId) {
		return orders.get(orderId);
	}

}
