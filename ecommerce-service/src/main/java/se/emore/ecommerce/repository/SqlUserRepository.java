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

public class SqlUserRepository implements UserLogic
{

	private final String URL = "jdbc:mysql://127.0.0.1:3306/emore";
	private final String username = "root";
	private final String password = "";

	@Override
	public int addUser(User user) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO user VALUES(null, ?, ?)", Statement.RETURN_GENERATED_KEYS))
			{
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());

				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 1)
				{
					ResultSet rs = stmt.getGeneratedKeys();
					connection.commit();

					if (rs.next())
					{
						user.setUserId(rs.getInt(1));
						return rs.getInt(1);
					}
				}
			}
			catch (SQLException e)
			{
				connection.rollback();
			}
			throw new RepositoryException("Could not add user! That username might already been taken");
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not connect to DB", e);
		}
	}

	@Override
	public User getUser(int userId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM user WHERE id = ?"))
			{
				stmt.setInt(1, userId);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
				{
					System.out.println("User: " + rs.getString("username") + " Pass: " + rs.getString("password"));
					System.out.println("ID: " + rs.getInt("id"));
					User user = new User(rs.getString(2), rs.getString(3));
					user.setUserId(rs.getInt("id"));
					return user;
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
	public int updateUser(User user) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("UPDATE user SET username = ?, password = ? WHERE id = ?"))
			{
				stmt.setInt(3, user.getId());
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());

				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 1)
				{
					connection.commit();
					return user.getId();
				}
				else
				{
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
	public int removeUser(int userId) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			try (PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM user WHERE id = ?"))
			{
				stmt.setInt(1, userId);

				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 1)
				{
					return userId;
				}
				else
				{
					throw new RepositoryException("No user with that Id");
				}
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
	
	public String removeUser(String userName) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			try (PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM user WHERE username = ?", Statement.RETURN_GENERATED_KEYS))
			{
			
				stmt.setString(1, userName);
				int affectedRows = stmt.executeUpdate();
				
				if (affectedRows == 1)
				{
					return userName + " removed!";
				}
				else
				{
					throw new RepositoryException("No user with username " + userName);
				}
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
