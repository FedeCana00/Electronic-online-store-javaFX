/**
 * 
 */
package application.models;

/**
 * This class represents a person.
 * 
 * @author Federico Canali
 */
public class Person {
	String name, lastName;
	Credentials credentials;

	/**
	 * Constructor class.
	 * @param name person's name
	 * @param lastName person's lastname
	 * @param credentials is the person's credentials.
	 */
	public Person(String name, String lastName, Credentials credentials) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.credentials = credentials;
	}

	/**
	 * Name getter.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name setter.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Lastname getter.
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Lastname setter.
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Credentials getter.
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * Credentials setter.
	 * @param credentials the credentials to set
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public String toString() {
		return "Person [name=" + name + ", lastName=" + lastName + ", credentials=" + credentials + "]";
	}
	
	
	

}
