/**
 * 
 */
package application.controllers;

import java.io.File;

import application.Main;
import application.managers.AlertManager;
import application.managers.CredentialsManager;
import application.managers.ProductsManager;
import application.managers.ViewController;
import application.models.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * The class represents the fxml screen controller
 * of UserMainPage. 
 * Handles events and configures the screen.
 * 
 * @author Federico Canali
 */
public class UserMainPageController {
	
	private static final String FILE_PATH_LOGOUT = "img/exit.png";
	private static final String FILE_PATH_USER = "img/user.png";
	
	/**
	 * Used to show the screen. 
	 **/
	public static void show() {
		try {
			FXMLLoader loaderMain = new FXMLLoader();
	        loaderMain.setLocation(Main.class.getResource("views/userMainPage.fxml"));
	        AnchorPane rootPane = (AnchorPane) loaderMain.load();
	        
	        // Show the scene containing the root layout.
	        Scene scene = new Scene(rootPane);
	        
	        /* table set up*/
	        TableView<Product> tableViewProduct = (TableView<Product>) scene.lookup("#tableViewProduct");
	        TableColumn<Product, String> column1 = new TableColumn<>("Product name");
	        column1.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getProductName().toString()));
	        column1.setStyle("-fx-alignment: CENTER");


	        TableColumn<Product, String> column2 = new TableColumn<>("Producer Name");
	        column2.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getProducerName()));
	        column2.setStyle("-fx-alignment: CENTER");

	        TableColumn<Product, String> column3 = new TableColumn<>("Price");
	        column3.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getPriceString()));
	        column3.setStyle("-fx-alignment: CENTER");

	        tableViewProduct.getColumns().add(column1);
	        tableViewProduct.getColumns().add(column2);
	        tableViewProduct.getColumns().add(column3);
	        addButtonToTable(tableViewProduct);
	        
	        tableViewProduct.setItems(FXCollections.observableList(ProductsManager.getInstance().getProducts()));
	        
	        /* elements set up */
	        TextField textFieldMinSearch = (TextField) scene.lookup("#textFieldMinPrice");
	        TextField textFieldMaxSearch = (TextField) scene.lookup("#textFieldMaxPrice");
	        TextField textFieldProductName = (TextField) scene.lookup("#textFieldProductName");
	        TextField textFieldProducerName = (TextField) scene.lookup("#textFieldProducerName");
	        Button  bntSearch = (Button) scene.lookup("#bntSearch");
	        AnchorPane searchPane = (AnchorPane) scene.lookup("#anchorPaneSearch");
	        CheckBox searchCheckBox = (CheckBox) scene.lookup("#checkBoxSearch");
	        Label userDescription = (Label) scene.lookup("#username");
	        userDescription.setText(CredentialsManager.getInstance().getUsernameLoggedIn()
	        		+ " [" + CredentialsManager.getInstance().getTypeOfUser() + "]");
	        ImageView logout = (ImageView) scene.lookup("#logout");
	        ImageView userImage = (ImageView) scene.lookup("#userImage");
	        setImageToImageView(logout, userImage);
	        
	        logout.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	            @Override
	            public void handle(MouseEvent event) {
	                CredentialsManager.getInstance().logout();
	                ViewController.getInstance().showLayout(ViewController.START_PAGE);
	            }
	       });
	        
	        bntSearch.setOnAction(new EventHandler<>() {

    			@Override
    			public void handle(javafx.event.ActionEvent arg0) {
    				 tableViewProduct.setItems(FXCollections.observableList(ProductsManager.getInstance()
    						 .filterSearch(textFieldMaxSearch.getText(), textFieldMinSearch.getText(),
    								 textFieldProductName.getText(), textFieldProducerName.getText())));
    			}
    		});
	        
	        searchCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					if(arg2)
	   					 searchPane.setVisible(true);
	   				 else {
	   					 searchPane.setVisible(false);
	   					 tableViewProduct.setItems(FXCollections.observableList(ProductsManager.getInstance().getProducts()));
	   				 }
				}
	        });
	        
	        ViewController.getInstance().setScene(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* used to add button to table view of user programmatically */
	private static void addButtonToTable(TableView<Product> tableViewProduct) {
	    TableColumn<Product, Void> colBtn = new TableColumn("Action");
	
	    Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
	        @Override
	        public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
	            final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
	
	                private final Button btn = new Button("Buy");
	
	                {
	                	btn.setOnAction(new EventHandler<>() {
	
	            			@Override
	            			public void handle(javafx.event.ActionEvent arg0) {
	            				Product p =  getTableView().getItems().get(getIndex());
	            				// create a text input dialog
	            		        TextInputDialog td = new TextInputDialog("Insert quantity...");
	            		  
	            		        // setHeaderText
	            		        td.setHeaderText("Enter quantity of product");
	            		        td.show();
	            		        
	            		        //implement callback and its button
	            		        Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
	            		        okButton.setOnAction(new EventHandler<>() {
	
		                			@Override
		                			public void handle(javafx.event.ActionEvent arg0) {
		                				try {
		                					
		                					String quantity = td.getEditor().getText();
		                					if(Integer.parseInt(quantity) > 0)
		                						if(ProductsManager.getInstance().buySomeProducts(p.getProductCode(), Integer.parseInt(quantity)))
		                							AlertManager.getInstance().showInfoMessage("Product purchased!");
		                						else
		                							AlertManager.getInstance().showErrorMessage("Product quantity not available!");
		                					
		                				} catch (Exception e) {
		                					AlertManager.getInstance().showErrorMessage("Quantity inserted is not valid! Please retry...");
		                				}
		                			}
	            		        });
	            			}
	            		});
	                	
	                	//setting button BUY style
	                	btn.setMaxWidth(Double.MAX_VALUE);
	                	btn.setStyle("-fx-background-color: GREEN;"
	                			+ "-fx-text-fill: WHITE;");
	                	
	                }
	
	                @Override
	                public void updateItem(Void item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        setGraphic(btn);
	                    }
	                }
	            };
	            return cell;
	        }
	    };
	
	    colBtn.setCellFactory(cellFactory);
	
	    tableViewProduct.getColumns().add(colBtn);
	
	}
	
	private static void setImageToImageView(ImageView logout, ImageView userImage){
		//logout
		File filelogout = new File(FILE_PATH_LOGOUT);
        Image imageLogout = new Image(filelogout.toURI().toString());
        logout.setImage(imageLogout);
        //user image
        File fileUser = new File(FILE_PATH_USER);
        Image imageUser = new Image(fileUser.toURI().toString());
        userImage.setImage(imageUser);
	}

}
