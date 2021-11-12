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
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * The class represents the fxml screen controller
 * of EmployeeMainPage. 
 * Handles events and configures the screen.
 * 
 * @author Federico Canali
 */
public class EmployeeMainPageController {

	private static final String FILE_PATH_LOGOUT = "img/exit.png";
	private static final String FILE_PATH_USER = "img/user.png";
	
	/**
	 * Used to show the screen. 
	 **/
	public static void show() {
		try {
			FXMLLoader loaderEmployee = new FXMLLoader();
			loaderEmployee.setLocation(Main.class.getResource("views/employeeMainPage.fxml"));
	        AnchorPane rootPane = (AnchorPane) loaderEmployee.load();
	        
	        // Show the scene containing the root layout.
	        Scene scene = new Scene(rootPane);
	        
	        // table set up
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

	        ViewController.getInstance().setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* used to add button to table view of employee programmatically */
	private static void addButtonsToTable(TableView<Product> tableViewProduct) {
		TableColumn<Product, Void> colBtnUpdate = new TableColumn("Update quantity");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactoryUpdate = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("Update");

                    {
                    	btn.setOnAction(new EventHandler<>() {

                			@Override
                			public void handle(javafx.event.ActionEvent arg0) {
                				Product p =  getTableView().getItems().get(getIndex());
                				// create a text input dialog
                		        TextInputDialog td = new TextInputDialog("Insert quantity...");
                		  
                		        // setHeaderText
                		        td.setHeaderText("Enter the quantity to add to the product [code: "
                		        		+ p.getProductCode() + "]");
                		        td.show();
                		        
                		        //implement callback and its button
                		        Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
                		        okButton.setOnAction(new EventHandler<>() {

    	                			@Override
    	                			public void handle(javafx.event.ActionEvent arg0) {
    	                				try {
    	                					
    	                					String quantity = td.getEditor().getText();
    	                					if(Integer.parseInt(quantity) > 0)
    	                						if(ProductsManager.getInstance().updateProduct(p.getProductCode(), Integer.parseInt(quantity))) {
    	                							tableViewProduct.setItems(FXCollections.observableList(ProductsManager.getInstance().getProducts()));
    	                							tableViewProduct.refresh();
    	                							AlertManager.getInstance().showInfoMessage("The quantity of this product has been updated successfully!"
    	                									+ p.toString());
    	                						}
    	                						else
    	                							AlertManager.getInstance().showErrorMessage("This product's code was not found... Retry!");
    	                					
    	                				} catch (Exception e) {
    	                					AlertManager.getInstance().showErrorMessage("Quantity inserted is not valid! Please retry...");
    	                				}
    	                			}
                		        });
                			}
                		});
                    	
                    	//setting button BUY style
                    	btn.setMaxWidth(Double.MAX_VALUE);
                    	btn.setStyle("-fx-background-color: ORANGE;"
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

        colBtnUpdate.setCellFactory(cellFactoryUpdate);

        tableViewProduct.getColumns().add(colBtnUpdate);
        
        TableColumn<Product, Void> colBtnShip = new TableColumn("Ship");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactoryShip = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("Ship");

                    {
                    	btn.setOnAction(new EventHandler<>() {

                			@Override
                			public void handle(javafx.event.ActionEvent arg0) {
                				Product p =  getTableView().getItems().get(getIndex());
                				// create a text input dialog
                		        TextInputDialog td = new TextInputDialog("Insert quantity...");
                		  
                		        // setHeaderText
                		        td.setHeaderText("Enter the quantity of the product you want to ship [code: "
                		        		+ p.getProductCode() + "]");
                		        td.show();
                		        
                		        //implement callback and its button
                		        Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
                		        okButton.setOnAction(new EventHandler<>() {

    	                			@Override
    	                			public void handle(javafx.event.ActionEvent arg0) {
    	                				try {
    	                					
    	                					String quantity = td.getEditor().getText();
    	                					if(Integer.parseInt(quantity) > 0)
    	                						if(ProductsManager.getInstance().buySomeProducts(p.getProductCode(), Integer.parseInt(quantity))) {
    	                							tableViewProduct.setItems(FXCollections.observableList(ProductsManager.getInstance().getProducts()));
    	                							tableViewProduct.refresh();
    	                							AlertManager.getInstance().showInfoMessage("Product shipped successfully!"
    	                									+ p.toString());
    	                						}
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

        colBtnShip.setCellFactory(cellFactoryShip);

        tableViewProduct.getColumns().add(colBtnShip);
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
