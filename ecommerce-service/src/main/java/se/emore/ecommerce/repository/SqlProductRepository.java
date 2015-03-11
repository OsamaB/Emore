package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.emore.ecommerce.Product;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.logic.ProductLogic;

public class SqlProductRepository implements ProductLogic
{

	private final String URL = "jdbc:mysql://127.0.0.1:3306/emore";
	private final String username = "root";
	private final String password = "";

	@Override
	public int addProduct(Product product) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO ecommerceProduct VALUES(null, ?, ?)", Statement.RETURN_GENERATED_KEYS))
			{
				stmt.setString(1, product.getProductName());
				stmt.setDouble(2, product.getProductPrice());

				int affectedRows = stmt.executeUpdate();

				if (affectedRows == 1)
				{
					ResultSet rs = stmt.getGeneratedKeys();
					if (rs.next())
					{
						connection.commit();
						product.setProductId(rs.getInt(1));
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
	public Product getProduct(int productId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ecommerceProduct WHERE id = ?"))
			{
				stmt.setInt(1, productId);

				ResultSet rs = stmt.executeQuery();

				if (rs.next())
				{
					Product product = new Product(rs.getString(2), rs.getDouble(3));
					product.setProductId(rs.getInt(1));
					return product;
				}

			}

			throw new RepositoryException("Could not get product");

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	public ArrayList<Product> getAllProducts() throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM ecommerceProduct"))
			{
				ArrayList<Product> products = new ArrayList<>();
				ResultSet rs = stmt.executeQuery();

				while (rs.next())
				{
					Product product = new Product(rs.getString(2), rs.getDouble(3));
					product.setProductId(rs.getInt(1));
					products.add(product);
				}

				return products;

			}
			catch (SQLException e)
			{
				throw new RepositoryException("could not get all products");
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public void updateProduct(int productId, Product product) throws
			RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt =
					connection.prepareStatement("UPDATE ecommerceProduct SET productName = ?, productPrice = ? WHERE id = ?"))
			{
				stmt.setInt(3, productId);
				stmt.setString(1, product.getProductName());
				stmt.setDouble(2, product.getProductPrice());

				int affectedRows = stmt.executeUpdate();

				if (affectedRows == 1)
				{
					connection.commit();
					return;
				}
				else
				{
					throw new RepositoryException("No product with that Id");
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
	public void removeProduct(int productId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM ecommerceProduct WHERE id = ?"))
			{
				stmt.setInt(1, productId);
				int affectedRows = stmt.executeUpdate();

				if (affectedRows == 1)
				{
					return;
				}
				else
				{
					throw new RepositoryException("No product with that Id");
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

	public Connection getConnection() throws SQLException, RepositoryException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(URL, username, password);

		}
		catch (SQLException | ClassNotFoundException e)
		{
			throw new RepositoryException("", e);
		}
	}
}
