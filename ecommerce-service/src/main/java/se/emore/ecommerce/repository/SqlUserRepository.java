package se.emore.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.emore.ecommerce.User;
import se.emore.ecommerce.exception.RepositoryException;
import se.emore.ecommerce.logic.UserLogic;

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
			try (PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO ecommerceUser VALUES(null, ?, ?)",
					Statement.RETURN_GENERATED_KEYS))
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
	public ArrayList<User> getAllUsers() throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM ecommerceUser"))
			{

				ResultSet rs = stmt.executeQuery();
				ArrayList<User> users = new ArrayList<>();

				while (rs.next())
				{

					int id = rs.getInt(1);
					String username = rs.getString(2);
					String password = rs.getString(3);

					User user = new User(username, password);
					user.setUserId(id);
					users.add(user);
				}
				return users;
			}
			catch (SQLException e)
			{
				throw new RepositoryException("could not get User");
			}
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
					.prepareStatement("SELECT * FROM ecommerceUser WHERE id = ?"))
			{
				stmt.setInt(1, userId);
				ResultSet rs = stmt.executeQuery();

				while (rs.next())
				{

					int id = rs.getInt(1);
					String username = rs.getString(2);
					String password = rs.getString(3);

					User user = new User(username, password);
					user.setUserId(id);
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
	public int updateUser(int userId, User user) throws RepositoryException
	{
		try (final Connection connection = getConnection())
		{
			connection.setAutoCommit(false);
			try (PreparedStatement stmt = connection
					.prepareStatement("UPDATE ecommerceUser SET username = ?, password = ? WHERE id = ?"))
			{
				stmt.setInt(3, userId);
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
					.prepareStatement("DELETE FROM ecommerceUser WHERE id = ?"))
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
					.prepareStatement("DELETE FROM ecommerceUser WHERE username = ?", Statement.RETURN_GENERATED_KEYS))
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
