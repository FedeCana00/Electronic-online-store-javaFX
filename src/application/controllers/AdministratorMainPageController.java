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
import application.models.Employee;
import application.models.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * The class represents the fxml screen controller
 * of AdministratorMainPage. 
 * Handles events and configures the screen.
 * 
 * @author Federico Canali
 */
public class AdministratorMainPageController {
	
	private static final String FILE_PATH_LOGOUT = "img/exit.png";
	private static final String FILE_PATH_USER = "img/user.png";

	
	/**
	 * Used to show the screen. 
	 **/
	public static void show() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/administratorMainPage.fxml"));
	        AnchorPane rootPane = (AnchorPane) loader.load();
	        
	        // Show the scene containing the root layout.
	        Scene scene = new Scene(rootPane);
	        
	        // table set up of products
	        TableView<Product> tableViewProduct = (TableView<Product>) scene.lookup("#tableViewProduct");
	        
	        TableColumn<Product, String> column0 = new TableColumn<>("Product code");
	        column0.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getProductCode())));
	        column0.setStyle("-fx-alignment: CENTER");
	        
	        TableColumn<Product, String> column1 = new TableColumn<>("Product name");
	        column1.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getProductName().toString()));
	        column1.setStyle("-fx-alignment: CENTER");


	        TableColumn<Product, String> column2 = new TableColumn<>("Producer Name");
	        column2.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getProducerName()));
	        column2.setStyle("-fx-alignment: CENTER");

	        TableColumn<Product, String> column3 = new TableColumn<>("Quantity");
	        column3.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getQuantity())));
	        column3.setStyle("-fx-alignment: CENTER");
	        
	        TableColumn<Product, String> column4 = new TableColumn<>("Price");
	        column4.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getPriceString()));
	        column4.setStyle("-fx-alignment: CENTER");

	        tableViewProduct.getColumns().add(column0);
	        tableViewProduct.getColumns().add(column1);
	        tableViewProduct.getColumns().add(column2);
	        tableViewProduct.getColumns().add(column3);
	        tableViewProduct.getColumns().add(column4);
	        
	        addButtonsToTable(tableViewProduct);
	        
	    	// table set up of employees
	        TableView<Employee> tableViewEmployee = (TableView<Employee>) scene.lookup("#tableViewEmployee");
	        
	        TableColumn<Employee, String> columnE0 = new TableColumn<>("Name");
	        columnE0.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
	        columnE0.setStyle("-fx-alignment: CENTER");
	        
	        TableColumn<Employee, String> columnE1 = new TableColumn<>("Surname");
	        columnE1.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getLastName()));
	        columnE1.setStyle("-fx-alignment: CENTER");


	        TableColumn<Employee, String> columnE2 = new TableColumn<>("Username");
	        columnE2.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getCredentials().getUsername()));
	        columnE2.setStyle("-fx-alignment: CENTER");

	        TableColumn<Employee, String> columnE3 = new TableColumn<>("Password");
	        columnE3.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getCredentials().getPassword())));
	        columnE3.setStyle("-fx-alignment: CENTER");
	        
	        tableViewEmployee.getColumns().add(columnE0);
	        tableViewEmployee.getColumns().add(columnE1);
	        tableViewEmployee.getColumns().add(columnE2);
	        tableViewEmployee.getColumns().add(columnE3);
	        
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
	       
	        tableViewProduct.setItems(FXCollections.observableList(ProductsManager.getInstance().getProducts()));
	        tableViewEmployee.setItems(FXCollections.observableList(CredentialsManager.getInstance().getAllEmployee()));
	        
	        Button bntAddProduct = (Button) scene.lookup("#bntAddNewProduct");
	        Button bntAddEmployee = (Button) scene.lookup("#bntAddNewEmployee");
	        
	        bntAddProduct.setOnAction(new EventHandler<>() {

    			@Override
    			public void handle(javafx.event.ActionEvent arg0) {
    				ViewController.getInstance().showLayout(ViewController.ADD_PRODUCT_PAGE);
    			}
	        });
	        
	        bntAddEmployee.setOnAction(new EventHandler<>() {

    			@Override
    			public void handle(javafx.event.ActionEvent arg0) {
    				ViewController.getInstance().showLayout(ViewController.ADD_EMPLOYEE_PAGE);
    			}
	        });

	        ViewController.getInstance().setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* used to show administrator main page */
	private static void addButtonsToTable(TableView<Product> tableViewProduct) {
		TableColumn<Product, Void> colBtnDelete = new TableColumn("Delete");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactoryDelete = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                    	btn.setOnAction(new EventHandler<>() {

                			@Override
                			public void handle(javafx.event.ActionEvent arg0) {
                				Product p =  getTableView().getItems().get(getIndex());
                				
                				if(ProductsManager.getInstance().removeProduct(p.getProductCode())) {
        							tableViewProduct.setItems(FXCollections.observableList(ProductsManager.getInstance().getProducts()));
        							tableViewProduct.refresh();
        							AlertManager.getInstance().showInfoMessage("Product deleted successfully!"
        									+ p.toString());
        						}
        						else
        							AlertManager.getInstance().showErrorMessage("This product's code was not found... Retry!");
                			}
                		});
                    	
                    	//setting button BUY style
                    	btn.setMaxWidth(Double.MAX_VALUE);
                    	btn.setStyle("-fx-background-color: RED;"
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

        colBtnDelete.setCellFactory(cellFactoryDelete);

        tableViewProduct.getColumns().add(colBtnDelete);
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
