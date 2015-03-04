package se.emore.ecommerce.logic;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;

public interface UserLogic {
	
	public int addUser(User user) throws RepositoryException;
	public User getUser(int userId) throws RepositoryException;
	public int updateUser(User user) throws RepositoryException;
	public int removeUser(int userId) throws RepositoryException;
	
}
