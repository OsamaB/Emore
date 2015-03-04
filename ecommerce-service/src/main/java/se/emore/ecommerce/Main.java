package se.emore.ecommerce;

import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.repository.SqlUserRepository;

public class Main {
	public static void main(String[] args) throws RepositoryException {
		
		final User user = new User("Hej", "Osama", 1001);
		int id = new SqlUserRepository().addUser(user);
	}
}
