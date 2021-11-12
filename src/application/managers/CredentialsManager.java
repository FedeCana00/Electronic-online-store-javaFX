package application.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.classes.Operations;
import application.models.Amministrator;
import application.models.Credentials;
import application.models.Employee;
import application.models.Person;
import application.models.User;

/**
 * This class deals with the management of accesses and registrations.
 * 
 * @see Person
 * @see Credentials
 * @author Federico Canali
 **/

public class CredentialsManager {
	/* CONSTANSTS */
	/**
	 * this variable indicates that no user has logged in yet.
	 * */
	public static final int NOT_DEFINED = -1;
	/**
	 * this variable indicates that the person logged in is a customer.
	 * */
	public static final int USER = 0;
	/**
	 * this variable indicates that the person logged in is an employee.
	 * */
	public static final int EMPLOYEE = 1;
	/**
	 * this variable indicates that the person logged in is an administrator.
	 * */
	public static final int ADMINISTRATOR = 2;
	/* VARIABLES */
	List<Person> people = null;
	Person loggedIn = null;
	int typeOfPersonLoggedIn = -1;
	
	/*
    * The instance is static so it is shared among all instances of the class. It is also private
    * so it is accessible only within the class.
    */
   private static CredentialsManager instance = null;

   /*
    * The constructor is private so it is accessible only within the class.
    */
   private CredentialsManager() {
       this.people = new ArrayList<Person>();
   }

   /**
    * The constructor is called only if the static instance is null, so only the first time
    * that the getInstance() method is invoked.
    * All the other times the same instance object is returned.
    * @return the instance object is returned.
    **/
   public static CredentialsManager getInstance() {
       if (instance == null)
           instance = new CredentialsManager();
       return instance;
   }
   
   /**
    * Returns the type of user who is currently logged on.
    * @return the type of user who is logged in is returned.
    * */
   public int getTypeOfPersonLoggedIn() {
	   return this.typeOfPersonLoggedIn;
   }
   
   /**
    * Returns the user name who is currently logged on.
    * @return Returns the user name who is currently logged on.
    * */
   public String getUsernameLoggedIn() {
	   return this.loggedIn.getCredentials().getUsername();
   }
   
   /**
    *  Used to sign up new user.
    *  @param person  person to register.
    *  @return This method returns true if registration is successful, otherwise false.
    **/
   public boolean signUp(Person person){
	   //check if all fields are filled right
	   if(!areAllFieldsFilledRight(person)) {
		   System.out.println("Please fill all fields before add new user!");
		   return false;
	   }
	   
	   //check before insert new user
	   if(hasSomeUserSameUserName(person.getCredentials().getUsername())) {
		   System.out.println("This username already exists.. please choose another one!");
		   return false;
	   }
	   
	   //add new user
	   this.people.add(person);
	   return true;
   }
   
   /* used to check if there is already someone with same username */
   private boolean hasSomeUserSameUserName(String userName) {
	   for(Person p: this.people)
		   if(p.getCredentials().getUsername().equals(userName))
			   return true;
	   return false;
   }
   
   /* used to check if all properties of person are filled right */
   private boolean areAllFieldsFilledRight(Person person) {
	   return !person.getName().isEmpty() && !person.getLastName().isEmpty()
			   && !person.getCredentials().getUsername().isEmpty()
			   && !person.getCredentials().getPassword().isEmpty();
   }
   
   /** 
    * Used to log in with credentials.
    * @param username is the username of the user trying to connect.
    * @param password is the password of the user trying to connect. 
    * @return This method returns true if access is successful, otherwise false.
    * */
   public boolean logIn(String username, String password) {
	   try {
		   
		   if(isThisCredentialsExist(new Credentials(username, password))) {
			   Person person = getPersonFromUsername(username);
			   if(person == null) {
				  throw new Exception("Something went wrong...");
			   }
			   
			   //insert person logged in 
			   this.loggedIn = person;
			   changeTypeOfPersonLoggedIn();
			   Operations.printOperationThatUserCanDo(this.typeOfPersonLoggedIn);
			   System.out.println("Welcome " + username + " [type of user : " + getTypeOfUser() + "]");
			   return true;
		   }
		   
		   //else show message if credentials not found
		   throw new Exception("Credentials not found.. Please retry!");
	   
	   } catch(Exception e){
		   System.out.println(e.getMessage());
	   }
	   return false;
   }
   
   /* check if this credentials exists */
   private boolean isThisCredentialsExist(Credentials credentials) {
	   for(Person p: this.people)
		   if(p.getCredentials().equals(credentials))
			   return true;
	   return false;
   }
   
   /* used to get user with this specific username */
   private Person getPersonFromUsername(String username) {
	   for(Person p: this.people)
		   if(p.getCredentials().getUsername().equals(username))
			   return p;
	   return null;
   }
   
   /* used to insert type of person logged in */
   private void changeTypeOfPersonLoggedIn() {
	   if(this.loggedIn == null) 
		   this.typeOfPersonLoggedIn = NOT_DEFINED;
	   
	   if(this.loggedIn instanceof User)
		   this.typeOfPersonLoggedIn = USER;
	   
	   if(this.loggedIn instanceof Employee)
		   this.typeOfPersonLoggedIn = EMPLOYEE;
	   
	   if(this.loggedIn instanceof Amministrator)
		   this.typeOfPersonLoggedIn = ADMINISTRATOR;
   }
   
   /**
    *  Used to get the type of user logged on.
    *  @return This method returns the type of connected user in string. 
    *  If none linked, it returns <code>null</code>.
    **/
   public String getTypeOfUser() {
	   switch(this.typeOfPersonLoggedIn) {
	   		case USER:
	   			return "Customer";
	   		case EMPLOYEE:
	   			return "Employee";
	   		case ADMINISTRATOR:
	   			return "Administrator";
	   		default:
	   			return null;
	   }
   }
   
   /**
    *  Used to print to console all people registered 
    **/
   public void printAllPeople() {
	   for(Person p: this.people)
		   System.out.println(p.toString());
   }
   
   /** 
    * This method is used to add a new employee 
    * by entering all data from the console.
    * @param name represents the name of new employee.
    * @param lastName represents the surname of new employee.
    * @param username represents the username of new employee.
    * @param password represents the password of new employee.
    * @return This method returns true if access is successful, otherwise false.
    * */
   public boolean addNewEmployee(String name, String lastName, String username, String password) {
	   
	 //check before insert new user
	   if(hasSomeUserSameUserName(username)) {
		   System.out.println("This username already exists.. please choose another one!");
		   return false;
	   }
	   
	   //add new employee
	   this.people.add(new Employee(name, lastName, new Credentials(username, password)));
	   System.out.println("New employee added correctly!");
	   
	   return true;
		   
   }
   
   /** This method is used to get all registered 
    * employees and print them to console.
    * @return Returns the list of all employee
    */
   public List<Employee> getAllEmployee(){
	   System.out.println("----- ALL EMPLOYEES ----");
	   List<Employee> employees = new ArrayList<Employee>();
	   
	   for(Person p: this.people)
		   if(p instanceof Employee) {
			   employees.add((Employee) p);
			   System.out.println(p.toString());
		   }
	   
	   System.out.println("-----------------------");
	   return employees;
   }
   
   /**
    *  This method is used to log out.
    **/
   public void logout() {
	   this.loggedIn = null;
	   changeTypeOfPersonLoggedIn();
   }
   
}
