package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import se.emore.ecommerce.Order;
import se.emore.ecommerce.Product;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.logic.OrderLogic;

public class SqlOrderRepository implements OrderLogic
{

	private final String URL = "jdbc:mysql://127.0.0.1:3306/emore";
	private final String username = "root";
	private final String password = "";

	@Override
	public int createOrder(User user, int[] productIds) throws RepositoryException
	{
		for (int productId : productIds)
		{
			user.addProduct(new SqlProductRepository().getProduct(productId));
		}

		java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

		int orderId = 0;

		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM ecommerceOrder"))
			{
				ResultSet rs = stmt.executeQuery();

				while (rs.next())
				{
					orderId = rs.getInt(1);
					if (orderId > 0)
					{
						orderId++;
					}
					else
					{
						orderId = 1;
					}
				}
			}

			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO ecommerceOrder VALUES(?, ?, ?, ?)"))
			{
				for (Product product : user.getProducts())
				{
					stmt.setInt(1, orderId);
					stmt.setInt(2, user.getId());
					stmt.setInt(3, product.getProductId());
					stmt.setDate(4, sqlDate);
					stmt.addBatch();
				}
				int affectedRows = stmt.executeBatch().length;
				if (affectedRows > 0)
				{

					connection.commit();
					return orderId;

				}
			}
			catch (SQLException e)
			{
				connection.rollback();
			}
			throw new RepositoryException("Could not create order");
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public Order[] getUserOrders(int userId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM ecommerceOrder WHERE userId = ?"))
			{
				stmt.setInt(1, userId);
				ResultSet rs = stmt.executeQuery();

				if (rs.next())
				{

					User user = new SqlUserRepository().getUser(userId);

					ArrayList<Order> orderList = new ArrayList<>();

					Order order;
					ArrayList<Product> products = new ArrayList<>();

					int thisOrderId = rs.getInt("orderId");
					int productId = rs.getInt(3);
					Product product = new SqlProductRepository().getProduct(productId);

					products.add(product);

					Date date = rs.getDate(4);

					while (rs.next())
					{
						date = rs.getDate(4);

						int orderId = rs.getInt("orderId");

						if (thisOrderId == orderId)
						{
							productId = rs.getInt(3);
							product = new SqlProductRepository().getProduct(productId);

							products.add(product);
							thisOrderId = orderId;
						}
						else
						{
							order = new Order(orderId, user);
							order.setProducts(products);
							order.setUser(user.getUsername());
							order.setDate(date);
							orderList.add(order);

							products.clear();

							productId = rs.getInt(3);
							product = new SqlProductRepository().getProduct(productId);

							products.add(product);

							thisOrderId = orderId;
						}
					}
					order = new Order(thisOrderId, user);
					order.setProducts(products);
					order.setUser(user.getUsername());
					order.setDate(date);
					orderList.add(order);

					Order[] orders = new Order[orderList.size()];
					orderList.toArray(orders);

					return orders;
				}
			}
			throw new RepositoryException("Could not get user");
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public Order getOrder(int orderId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM ecommerceOrder WHERE orderId = ?"))
			{
				stmt.setInt(1, orderId);
				ResultSet rs = stmt.executeQuery();

				if (rs.next())
				{
					int userId = rs.getInt("userId");
					User user = new SqlUserRepository().getUser(userId);
					int productId = rs.getInt("productId");
					Product product = new SqlProductRepository().getProduct(productId);
					Date date = rs.getDate(4);
					user.addProduct(product);

					while (rs.next())
					{
						productId = rs.getInt("productId");
						product = new SqlProductRepository().getProduct(productId);

						user.addProduct(product);
					}

					Order order = new Order(orderId, user);
					order.setUser(user.getUsername());
					order.setDate(date);

					return order;
				}
			}
			throw new RepositoryException("Could not get order");
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public int removeOrder(int orderId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			try (PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM ecommerceOrder WHERE orderId = ?"))
			{

				stmt.setInt(1, orderId);
				stmt.executeUpdate();
				return orderId;

			}
			catch (SQLException e)
			{
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
			throw new RepositoryException("Problem connecting to Database", e);
		}
	}
}
