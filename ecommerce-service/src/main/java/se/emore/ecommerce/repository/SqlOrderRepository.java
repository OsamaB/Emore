package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import se.emore.ecommerce.Order;
import se.emore.ecommerce.Product;
import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.logic.OrderLogic;

public class SqlOrderRepository implements OrderLogic {
	
	private final String URL = "jdbc:mysql://127.0.0.1:3306/emore";
	private final String username = "root";
	private final String password = "";	
	
	private Map<Integer, Map<Integer,Product>> orders = new HashMap<>();
	private AtomicInteger atomicInteger = new AtomicInteger(1000);
	
	@Override
	public void addOrder(User user, ArrayList<Product> products) throws RepositoryException {
		Order order = new Order(atomicInteger.incrementAndGet(), user.getId());
		
		
		try (final Connection connection = DriverManager.getConnection(URL, username, password))
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO order VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				for(Product product : user.getProducts())
				{
					stmt.setInt(1, order.getOrderId());
					stmt.setInt(2, user.getId());
					stmt.setInt(3, product.getProductId());
					stmt.setDate(4, (Date) order.getDate());
										
				}
//				int affectedRows = stmt.executeUpdate();
//
//				if (affectedRows == 1) {
//					ResultSet rs = stmt.getGeneratedKeys();
//					connection.commit();
//					
//					if(rs.next()) {
//						user.setUserId(rs.getInt(1));
//						return rs.getInt(1);
//					}				
//				}
			}
			catch (SQLException e)
			{
				connection.rollback();
			}

			throw new RepositoryException("Could not add user");

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public void removeOrder(int orderId) throws RepositoryException{
		orders.remove(orderId);
	}

	@Override
	public Map<Integer, Product> getOrder(int orderId) throws RepositoryException{
		return orders.get(orderId);
	}

}
