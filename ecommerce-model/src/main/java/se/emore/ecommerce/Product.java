package se.emore.ecommerce;

import javax.xml.bind.annotation.XmlElement;

public final class Product
{
	@XmlElement
	private String productName;
	@XmlElement
	private int productId;
	@XmlElement
	private double productPrice;

	@SuppressWarnings("unused")
	private Product(){}

	public Product(String productName, double productPrice)
	{
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public String getProductName()
	{
		return productName;
	}

	public int getProductId()
	{
		return productId;
	}

	public double getProductPrice()
	{
		return productPrice;
	}

	public void setProductId(int sqlProductId)
	{
		productId = sqlProductId;
	}

}
