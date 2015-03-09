package se.emore.ecommerce.logic;

import se.emore.ecommerce.Order;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;

public interface OrderLogic {
	
	public int createOrder(User user) throws RepositoryException;
	public void removeOrder(int orderId) throws RepositoryException;
	public Order getOrder(int orderId) throws RepositoryException;
}
