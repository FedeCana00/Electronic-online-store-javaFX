package application.models;

/**
 * This class is a subclass of the {@code Person} class. 
 * Represents the employee.
 * 
 * @see Person
 * @author Federico Canali
 **/
public class Employee extends Person{

	/**
	 * {@inheritDoc}
	 **/
	public Employee(String name, String lastName, Credentials credentials) {
		super(name, lastName, credentials);
	}
}
