/**
 * 
 */
package application.models;

/**
 * This class is a subclass of the {@code Person} class. 
 * Represents the administrator.
 * 
 * @see Person
 * @author Federico Canali
 **/
public class Amministrator extends Person{
	/**
	 * {@inheritDoc}
	 **/
	public Amministrator(String name, String lastName, Credentials credentials) {
		super(name, lastName, credentials);
	}

}
