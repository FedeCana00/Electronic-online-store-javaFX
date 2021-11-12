package application.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.models.Product;

/**
 *  This class deals with the management of products for sale.
 *  
 *  @see Product
 *  @author Federico Canali
 **/
public class ProductsManager {
	/* VARIABLES */
	List<Product> products = null;
	int nextUniqueProductCode = 0;
	
	/*
    * The instance is static so it is shared among all instances of the class. It is also private
    * so it is accessible only within the class.
    */
   private static ProductsManager instance = null;

   /*
    * The constructor is private so it is accessible only within the class.
    */
   private ProductsManager() {
       this.products = new ArrayList<Product>();
   }

   /**
    * The constructor is called only if the static instance is null, so only the first time
    * that the getInstance() method is invoked.
    * All the other times the same instance object is returned.
    * @return the instance object is returned.
    **/
   public static ProductsManager getInstance() {
       if (instance == null)
           instance = new ProductsManager();
       return instance;
   }
   
   
   
	 /**
	  * Used to get all products.
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}


	/**
    *  This method put a new product on sale.
    *  @param product Product that needs to be added.
    **/
   public void addProduct(Product product) {
	   if(product == null) {
		   System.out.println("Sorry but you've inserted a empty product...");
		   return;
	   }
	   
	   product.setProductCode(nextUniqueProductCode);
	   
	   this.products.add(product);
	   //increment unique int code for products
	   this.nextUniqueProductCode++;
   }
   
   /**
    *  This method prints all products for sale on the console. 
    **/
   public void printAllProducts() {
	   System.out.println("----- ALL PRODUCTS -----");
	   
	   for(Product p: this.products)
		   System.out.println(p.toString());
	   
	   System.out.println("------------------------");
   }
   
   /**
    * Used to filter the product search.
    * @param maxPrice is the maximum price to search.
    * @param minPrice is the minimum price to search.
    * @param productName is the product's name to search.
    * @param producerName is the producer's name to search.
    * @return returns the list of products filtered.
    */
   public List<Product> filterSearch(String maxPrice, String minPrice, String productName, String producerName) {
	   try {
		   
	   List<Product> mProductsFilter = new ArrayList<Product>();
	   
	   for(Product p: this.products) {
		   boolean isToInsert = true;
		   float price = p.getPrice()/100;
		   
		   if(!maxPrice.isBlank())
			   if( price > Float.parseFloat(maxPrice))
				   isToInsert = false;
		   
		   if(!minPrice.isBlank())
			   if(price < Float.parseFloat(minPrice))
				   isToInsert = false;
		   
		   String sProductName = p.getProductName().toLowerCase();
		   if(!productName.isBlank() && !sProductName.contains(productName.toLowerCase()))
			   isToInsert = false;
		   
		   /*
		   String sProducerName = p.getProducerName().toLowerCase();
		   if(!producerName.isBlank() && !sProducerName.contains(producerName.toLowerCase()));
			   isToInsert = false;*/
		   
		   if(isToInsert)
			   mProductsFilter.add(p);
	   }
	   
	   System.out.println("------- Result of the search -------");
	   for(Product pr: mProductsFilter)
		   System.out.println(pr.toString());
	   
	   return mProductsFilter;
	   
	   } catch(Exception e) {
		   e.printStackTrace();
		   return null;
	   }
   }
   
   /**
    *  This method is used to buy and ship products.
    *  @param productCode is the product's code to buy.
    *  @param productQuantity is the product's quantity to buy.
    *  @return If true then the operation was successful, false otherwise.
    **/
   public boolean buySomeProducts(int productCode, int productQuantity) {
	   try {
		   
		   for(Product p: this.products) {
			   if(p.getProductCode() == productCode) {
				   if(p.getQuantity() >= productQuantity) {
					   int index = this.products.indexOf(p);
					   this.products.remove(p);
					   p.setQuantity(p.getQuantity() - productQuantity);
					   this.products.add(index,p);
					   System.out.println("Congratulation! You've buy/ship this product => "
							   + p.getProductName() + " qt = " + productQuantity);
					   checkIfThisProductIsOutOfStock(p);
					   return true;
				   } else {
					   System.out.println("The quantity that you want to is not avaiable...");
					   return false;
				   }
			   }
		   }
		   
		   System.out.println("Product not found... Retry!");
		   
	   } catch(Exception e) {
		   System.out.println("You have entered something invalid .. Try again!");
	   }
	   return false;
   }
   
   /* used to check if this product is out of stock and print it */
   private void checkIfThisProductIsOutOfStock(Product product) {
	   if(product.getQuantity() == 0)
		   System.out.println("This product is out of stock... Please reorder it ." + product.toString());
   }
   
   /**
    *  This method is used to update a product.
    *  @param productCode is the product's code to update.
    *  @param quantity is the product's quantity to update.
    *  @return If true then the operation was successful, false otherwise.
    **/
   public boolean updateProduct(int productCode, int quantity) {
	   for(Product p: this.products) {
		   if(p.getProductCode() == productCode) {
			   int index = this.products.indexOf(p);
			   this.products.remove(p);
			   p.setQuantity(p.getQuantity() + quantity);
			   this.products.add(index, p);
			   
			   System.out.println("The quantity of this product has been updated successfully! "
			   		+ p.toString());
			   return true;
		   }
	   }
	   
	   System.out.println("This product's code was not found... Retry!");
	   return false;
   }
   
   /**
    *  This method is used to add a new product.
    *  @param productName is the product's name of new product.
    *  @param producerName is the producer's name of new product.
    *  @param price is the product's price of new product.
    *  @param quantity is the product's quantity of new product.
    *  @return If true then the operation was successful, false otherwise.
    **/
   public boolean newProduct(String productName, String producerName, float price, int quantity) {
	   
	   if(productName == null || producerName == null || price <= 0 || quantity < 0) {
		   System.out.println("One ore more fields files were not compiled correctly!");
		   return false;
	   }
	   
	   //add new product
	   int iPrice = (int) (price*100); //used to convert float into integer
	   addProduct(new Product(productName, producerName, iPrice , quantity));
	   System.out.println("New product added successfully!");
	   return true;
   }
   
   /**
    *  This method is used to remove a product.
    *  @param code is the product's code to remove.
    *  @return If true then the operation was successful, false otherwise.
    **/
   public boolean removeProduct(int code) {
	   try {
		   
		   for(Product p: this.products)
			   if(p.getProductCode() == code) {
				   this.products.remove(p);
				   System.out.println("This product has been removed! " + p.toString());
				   return true;
			   }
		   
		   System.out.println("Product was not not found... Retry!");
	   
	   } catch(Exception e) {
		   System.out.println("You have entered something invalid .. Try again!");
	   }
	   
	   return false;
   }
   
}
