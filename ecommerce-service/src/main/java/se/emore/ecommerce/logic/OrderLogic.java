package se.emore.ecommerce.logic;

import java.util.ArrayList;
import java.util.Map;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.*;

public interface OrderLogic {
	
	public void addOrder(User user, ArrayList<Product> products) throws RepositoryException;
	public void removeOrder(int orderId) throws RepositoryException;
	public Map<Integer, Product> getOrder(int orderId) throws RepositoryException;
}
