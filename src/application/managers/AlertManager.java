package application.managers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class deals with the management of alert messages.
 * 
 * @author Federico Canali
 **/
public class AlertManager {
	
		/* 
		 * The instance is static so it is shared among all instances of the class. It is also private
	    * so it is accessible only within the class.
	    */
	   private static AlertManager instance = null;

	   /*
	    * The constructor is private so it is accessible only within the class.
	    */
	   private AlertManager() {
		   
	   }

	   /**
	    * The constructor is called only if the static instance is null, so only the first time
	    * that the getInstance() method is invoked.
	    * All the other times the same instance object is returned.
	    * @return the instance object is returned.
	    **/
	   public static AlertManager getInstance() {
	       if (instance == null)
	           instance = new AlertManager();
	       return instance;
	   }
	   
	   /**
	    * This method show error message to user.
	    * 
	    * @param msg is the message that I want to show.
	    **/
	   public void showErrorMessage(String msg) {
		   Alert alert = new Alert(AlertType.ERROR, msg);
			alert.showAndWait();
	   }
	   
	   /**
	    * This method show info message to user.
	    * 
	    * @param msg is the message that I want to show.
	    **/
	   public void showInfoMessage(String msg) {
		   Alert alert = new Alert(AlertType.INFORMATION, msg);
			alert.showAndWait();
	   }

}
