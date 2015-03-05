package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.logic.ProductLogic;

public class SqlProductRepository implements ProductLogic{
	
	private final String URL = "jdbc:mysql://127.0.0.1:3306/emore";
	private final String username = "root";
	private final String password = "";	

	@Override
	public int addProduct(Product product) throws RepositoryException {
		try (final Connection connection = DriverManager.getConnection(URL, username, password))
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO product VALUES(null, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, product.getProductName());
				stmt.setDouble(2, product.getProductPrice());
			
				int affectedRows = stmt.executeUpdate();
				
				if(affectedRows == 1) {
					ResultSet rs = stmt.getGeneratedKeys();
					if(rs.next()) {
						connection.commit();
						return rs.getInt(1);
					}
				}
			}
			catch (SQLException e)
			{
				connection.rollback();
			}

			throw new RepositoryException("Could not add product");

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public Product getProduct(int productId) throws RepositoryException {
		try (final Connection connection = DriverManager.getConnection(URL, username, password))
		{
			try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM product WHERE id = ?")) {
				stmt.setInt(1, productId);
			
				ResultSet rs = stmt.executeQuery();
				
				if(rs.next()) {
					return new Product(rs.getString(2), rs.getInt(1), rs.getDouble(3));
				}
				
			}
			
			throw new RepositoryException("Could not add product");

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public void updateProduct(Product product) throws RepositoryException {
		try (final Connection connection = DriverManager.getConnection(URL, username, password))
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("UPDATE product SET productName = ?, price = ? WHERE id = ?")) {
				stmt.setInt(3, product.getProductId());
				stmt.setString(1, product.getProductName());
				stmt.setDouble(2, product.getProductPrice());
				
				int affectedRows = stmt.executeUpdate();
				
				if(affectedRows == 1){
					connection.commit();
					return;
				} else {
					throw new RepositoryException("No user with that Id");
				}
				
			}
			catch (SQLException e)
			{
				connection.rollback();
				throw new RepositoryException("Invalid sql statement");
			}

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public void removeProduct(int productId) throws RepositoryException {
		try (final Connection connection = DriverManager.getConnection(URL, username, password))
		{
			try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM product WHERE id = ?")) {
				stmt.setInt(1, productId);
				int affectedRows = stmt.executeUpdate();
				
				if(affectedRows == 1){
					return;
				} else {
					throw new RepositoryException("No user with that Id");
				}
				
			}
			catch (SQLException e)
			{
				connection.rollback();
				throw new RepositoryException("Invalid sql statement");
			}

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}
	}


