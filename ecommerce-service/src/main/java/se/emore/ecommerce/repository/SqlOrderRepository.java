package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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

	private AtomicInteger atomicInteger = new AtomicInteger(1000);

	@Override
	public int createOrder(User user, int[] productIds) throws RepositoryException
	{
		for(int productId : productIds)
		{
			user.addProduct(new SqlProductRepository().getProduct(productId));
		}
		
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
				
				int affectedRows = stmt.executeBatch().length;

				if (affectedRows > 0)
				{
					ResultSet rs = stmt.getGeneratedKeys();
					connection.commit();
					if (rs.next())
					{
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
	public Order[] getUserOrders(int userId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM `order` WHERE userId = ?"))
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
						
						if(thisOrderId == orderId)
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
					for(Order order2 : orders)
					{
						System.out.println("I ordersArray: " + order2 + ". Med storlek: " + orders.length + ".. Men OrderList: " + orderList.size());
						
					}
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
					.prepareStatement("SELECT * FROM `order` WHERE orderId = ?"))
			{
				stmt.setInt(1, orderId);
				ResultSet rs = stmt.executeQuery();

				if (rs.next())
				{
					int userId = rs.getInt("userId");
					User user = new SqlUserRepository().getUser(userId);

					Date date = rs.getDate(4);


					while (rs.next())
					{
						int productId = rs.getInt(3);
						Product product = new SqlProductRepository().getProduct(productId);

						user.addProduct(product);
					}

					Order order = new Order(orderId, user);
					order.setUser(user.getUsername());
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
	public int removeOrder(int orderId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			try (PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM `order` WHERE orderId = ?"))
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
