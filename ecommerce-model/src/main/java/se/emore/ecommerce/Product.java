package se.emore.ecommerce;

public final class Product {
	private String productName;
	private int productId;
	private double price;
	
	public Product(String productName, double price) {
		this.productName = productName;
		this.price = price;
	}
	
	public Product(String productName, int productId, double price) {
		this.productName = productName;
		this.productId = productId;
		this.price = price;
	}
	

	public String getProductName() {
		return productName;
	}
	

	public int getProductId() {
		return productId;
	}
	
	public double getProductPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + productId;
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (productId != other.productId)
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		return true;
	}

	
	
}
