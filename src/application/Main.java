package application;

import application.managers.CredentialsManager;
import application.managers.ProductsManager;
import application.managers.ViewController;
import application.models.Amministrator;
import application.models.Credentials;
import application.models.Employee;
import application.models.Product;
import application.models.User;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class represents the entry point of application execution. 
 * It has the main method inside {@link #main(String[])}.
 * 
 * @author Federico Canali
 **/
public class Main<ActionEvent> extends Application {

    @Override
    public void start(Stage primaryStage) {
        
    	ViewController.getInstance().initAndStart(primaryStage);
        
        initDefaultConfiguration();

    }

    /**
     * Used to launch the main page activity.
     * @param args is the argument of String array.
     **/
	public static void main(String[] args) {
		launch(args);
	}
	
	/* used to initialize some users and some products */
	private static void initDefaultConfiguration() {
		Amministrator myself = new Amministrator("Federico", "Canali", new Credentials("fede","f"));
		User user = new User("Bob", "Smith", new Credentials("bob","b"));
		Employee employee = new Employee("Alice", "Jones", new Credentials("ali","jS"));
		
		CredentialsManager.getInstance().signUp(myself);
		CredentialsManager.getInstance().signUp(user);
		CredentialsManager.getInstance().signUp(employee);
		
		Product computer = new Product("Computer HP", "HP", 55600, 10);
		Product earphones = new Product("Earphones", "AKG", 3550, 2);
		Product tvXiaomi = new Product("Mi TV LED", "Xiaomi", 27900, 40);
		Product iPad = new Product("iPad", "Apple", 99999, 5);
		Product iPhone = new Product("iPhone X", "Apple", 129999, 2);
		
		ProductsManager.getInstance().addProduct(computer);
		ProductsManager.getInstance().addProduct(earphones);
		ProductsManager.getInstance().addProduct(tvXiaomi);
		ProductsManager.getInstance().addProduct(iPad);
		ProductsManager.getInstance().addProduct(iPhone);
	}
}
