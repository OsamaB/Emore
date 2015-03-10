package se.emore.ecommerce.logic;

import java.util.ArrayList;

import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;

public interface UserLogic {
	
	int addUser(User user) throws RepositoryException;
	User getUser(int userId) throws RepositoryException;
	int updateUser(int userId, User user) throws RepositoryException;
	int removeUser(int userId) throws RepositoryException;
	ArrayList<User> getAllUsers() throws RepositoryException;
	
}
