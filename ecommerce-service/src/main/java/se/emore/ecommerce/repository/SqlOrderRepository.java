package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

	private Map<Integer, Map<Integer, Product>> orders = new HashMap<>();
	private AtomicInteger atomicInteger = new AtomicInteger(1000);

	@Override
	public int createOrder(User user) throws RepositoryException
	{
		Order order = new Order(atomicInteger.incrementAndGet(), user);

		java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO `order` VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
			{

				for (Product product : user.getProducts())
				{
					stmt.setInt(1, order.getOrderId());
					stmt.setInt(2, user.getId());
					stmt.setInt(3, product.getProductId());
					stmt.setDate(4, sqlDate);
					stmt.addBatch();
				}
				stmt.executeBatch();

				int affectedRows = stmt.executeUpdate();

				if (affectedRows == 1)
				{
					ResultSet rs = stmt.getGeneratedKeys();
					connection.commit();
					System.out.println("RS next: " + rs.next());
					if (rs.next())
					{
						System.out.println(rs.getInt(1));
						return rs.getInt(1);
					}
					else
					{
						return 0;
					}
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
	public Order getOrder(int orderId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM `order` WHERE orderId = ?"))
			{
				stmt.setInt(1, orderId);
				ResultSet rs = stmt.executeQuery();

				if (rs.next())
				{
					int userId = rs.getInt("userId");
					User user = new SqlUserRepository().getUser(userId);

					Date date = rs.getDate(4);

					int productId = rs.getInt(3);
					Product product = new SqlProductRepository().getProduct(productId);

					while (rs.next())
					{
						user.addProduct(product);
					}

					Order order = new Order(orderId, user);
					order.setDate(date);

					return order;
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
	public void removeOrder(int orderId) throws RepositoryException
	{
		orders.remove(orderId);
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
