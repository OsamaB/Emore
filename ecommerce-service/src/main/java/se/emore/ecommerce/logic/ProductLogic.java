package se.emore.ecommerce.logic;

import se.emore.ecommerce.Product;

public interface ProductLogic {
	
	public void addProduct(Product product);
	public void getProduct(Long productId);
	public void updateProduct(Product product);
	public void removeProduct(Long productId);
}
