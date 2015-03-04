package se.emore.ecommerce.logic;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;

public interface UserLogic {
	
	public int addUser(User user) throws RepositoryException;
	public void getUser(Long userId) throws RepositoryException;
	public void updateUser(User user) throws RepositoryException;
	public void removeUser(Long userId) throws RepositoryException;
	
	public void addToShoppingCart(Product product);
	public void removeFromShoppingCart(Long productId);
}
