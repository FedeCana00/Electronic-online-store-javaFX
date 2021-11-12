package application.models;

/**
 * This class represents the login credentials used to 
 * connect to the electronic product sales site.
 * 
 * @author Federico Canali
 **/
public class Credentials {
	String username, password;

	/**
	 * Constructor class
	 * @param username user's username
	 * @param password user's password
	 */
	public Credentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * Username getter.
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Username setter.
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Password getter.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Password setter.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 **/
	@Override
	public String toString() {
		return "Credentials [username=" + username + ", password=" + password + "]";
	}

	/**
	 * {@inheritDoc} 
	 **/
	@Override
	public boolean equals(Object obj) {
		super.equals(obj);
		if(obj == null)
			return false;
		Credentials o = (Credentials) obj;
		return this.username.equals(o.getUsername()) && this.password.equals(o.getPassword());
	}
	
	
	

}
