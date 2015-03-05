package se.emore.ecommerce.logic;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.exception.RepositoryException;

public interface ProductLogic {
	
	public int addProduct(Product product) throws RepositoryException;
	public Product getProduct(int productId) throws RepositoryException;
	public void updateProduct(Product product) throws RepositoryException;
	public void removeProduct(int productId) throws RepositoryException;
}
