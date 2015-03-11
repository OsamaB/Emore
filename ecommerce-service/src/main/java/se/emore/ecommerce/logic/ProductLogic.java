package se.emore.ecommerce.logic;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.exception.RepositoryException;

public interface ProductLogic {
	
	int addProduct(Product product) throws RepositoryException;
	Product getProduct(int productId) throws RepositoryException;
	void updateProduct(int productId, Product product) throws RepositoryException;
	void removeProduct(int productId) throws RepositoryException;
}
