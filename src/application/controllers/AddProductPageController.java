/**
 * 
 */
package application.controllers;

import java.io.File;

import application.Main;
import application.managers.AlertManager;
import application.managers.ProductsManager;
import application.managers.ViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * The class represents the fxml screen controller
 * of AddProductPage. 
 * Handles events and configures the screen.
 * 
 * @author Federico Canali
 */
public class AddProductPageController {
	
	private static final String FILE_PATH_BACK = "img/back.png";
	
	/**
	 * Used to show the screen. 
	 **/
	public static void show() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/addProductPage.fxml"));
	        AnchorPane rootPane = (AnchorPane) loader.load();
	        
	        // Show the scene containing the root layout.
	        Scene scene = new Scene(rootPane);
	       
	        TextField textFieldProductName =(TextField) scene.lookup("#textFieldProductName");
	        TextField textFieldProducerName =(TextField) scene.lookup("#textFieldProducerName");
	        TextField textFieldQuantity =(TextField) scene.lookup("#textFieldQuantity");
	        TextField textFieldPrice =(TextField) scene.lookup("#textFieldProductPrice");
	        Button bntAdd = (Button) scene.lookup("#bntAdd");
	        ImageView bntBack = (ImageView) scene.lookup("#bntBack");
	        setImageToImageView(bntBack);
	        
	        bntBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	            @Override
	            public void handle(MouseEvent event) {
	            	ViewController.getInstance().showLayout(ViewController.ADMINISTRATOR_MAIN_PAGE);
	            }
	       });
	        
	        bntAdd.setOnAction(new EventHandler<>() {

    			@Override
    			public void handle(javafx.event.ActionEvent arg0) {
    				try {
    					
    					int quantity = Integer.parseInt(textFieldQuantity.getText());
    					float price = Float.parseFloat(textFieldPrice.getText());
    					
    					if(ProductsManager.getInstance().newProduct(textFieldProductName.getText(), textFieldProducerName.getText(),
    							price, quantity)) {
    						AlertManager.getInstance().showInfoMessage("Product added successfully!");
    						ViewController.getInstance().showLayout(ViewController.ADMINISTRATOR_MAIN_PAGE);
    					} else
    						AlertManager.getInstance().showErrorMessage("One ore more fields files were not compiled correctly!");
    					
    					} catch(Exception e) {
    						AlertManager.getInstance().showErrorMessage("Some numeric field is not filled correctly! Retry...");
    					}
    				}
	        	});

	        ViewController.getInstance().setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void setImageToImageView(ImageView bntBack){
		File file = new File(FILE_PATH_BACK);
        Image image = new Image(file.toURI().toString());
        bntBack.setImage(image);
	}

}
