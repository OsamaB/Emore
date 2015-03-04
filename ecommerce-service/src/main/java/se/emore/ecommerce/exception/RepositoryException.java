package se.emore.ecommerce.exception;

public class RepositoryException extends Exception {

	private static final long serialVersionUID = 4279657140091980652L;
	
	public RepositoryException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public RepositoryException(String message)
	{
		super(message);
	}
}
