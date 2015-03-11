package se.emore.ecommerce.logic;

import se.emore.ecommerce.Order;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.*;

public interface OrderLogic {
	
	int createOrder(User user, int[] productIds) throws RepositoryException;
	Order getOrder(int orderId) throws RepositoryException;
	Order[] getUserOrders(int userId) throws RepositoryException;
	int removeOrder(int orderId) throws RepositoryException;
}
