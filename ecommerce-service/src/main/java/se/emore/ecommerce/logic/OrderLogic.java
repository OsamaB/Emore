package se.emore.ecommerce.logic;

import se.emore.ecommerce.Order;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.*;

public interface OrderLogic {
	
	public int createOrder(User user) throws RepositoryException;
	public int removeOrder(int orderId) throws RepositoryException;
	public Order getOrder(int orderId) throws RepositoryException;
}
