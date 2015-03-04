package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.emore.ecommerce.*;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.logic.*;

public final class SqlUserRepository implements UserLogic {
	
	private final String URL = "jdbc:mysql://127.0.0.1:3306/emore";
	private final String username = "root";
	private final String password = "";	
	
	@Override
	public int addUser(User user) throws RepositoryException {
		
		try (final Connection connection = DriverManager.getConnection(URL, username, password))
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO user VALUES(null, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				
				int affectedRows = stmt.executeUpdate();
				
				if(affectedRows == 1) {
					ResultSet rs = stmt.getGeneratedKeys();
					if(rs.next()) {
						int id = rs.getInt(1);
						connection.commit();
						
						return id;
					}
				}
				

				if (stmt.executeUpdate() == 1)
				{
					connection.commit();
				}
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
	public void getUser(Long userId) {
		
	}

	@Override
	public void updateUser(User user) {
		
	}

	@Override
	public void removeUser(Long userId) {
		
	}

	@Override
	public void addToShoppingCart(Product product) {
		
	}

	@Override
	public void removeFromShoppingCart(Long productId) {
		
	}
	
}
