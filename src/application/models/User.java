package application.models;

/**
 * This class is a subclass of the {@code Person} class. 
 * Represents the user.
 * 
 * @see Person
 * @author Federico Canali
 **/
public class User extends Person{
	/**
	 * {@inheritDoc} 
	 **/
	public User(String name, String lastName, Credentials credentials) {
		super(name, lastName, credentials);
	}

}
