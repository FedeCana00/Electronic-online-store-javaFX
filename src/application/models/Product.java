/**
 * 
 */
package application.models;

/**
 * This class represents an electronic product for sale.
 * 
 *  @author Federico Canali
 */
public class Product {
	
	String productName;
	int productCode;
	String producerName;
	int price;
	int quantity;

	/** Class constructor.
	 * @param productName Represents the name of the product.
	 * @param productCode Represents the code of the product.
	 * @param producerName Represents the name of the producer.
	 * @param price Represents the price of the product.
	 * @param quantity Represents the quantity available of the product.
	 */
	public Product(String productName, int productCode, String producerName, int price, int quantity) {
		super();
		this.productName = productName;
		this.productCode = productCode;
		this.producerName = producerName;
		this.price = price;
		this.quantity = quantity;
	}
	
	/**
	 *  Class constructor.
	 * @param productName Represents the name of the product.
	 * @param producerName Represents the name of the producer.
	 * @param price Represents the price of the product.
	 * @param quantity Represents the quantity available of the product.
	 *
	 */
	public Product(String productName, String producerName, int price, int quantity) {
		super();
		this.productName = productName;
		this.producerName = producerName;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * Product's name getter.
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Product's name setter.
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Product's code getter.
	 * @return the productCode
	 */
	public int getProductCode() {
		return productCode;
	}

	/**
	 * Product's code setter.
	 * @param productCode the productCode to set
	 */
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	/**
	 * Producer's name getter.
	 * @return the producerName
	 */
	public String getProducerName() {
		return producerName;
	}

	/**
	 * Producer's name setter.
	 * @param producerName the producerName to set
	 */
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	/**
	 * Price getter.
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Print the price with its currency in String.
	 * @return the price
	 */
	public String getPriceString() {
		return String.valueOf((double) price/100) + "€";
	}

	/**
	 * Price setter.
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Quantity getter.
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Quantity setter.
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public String toString() {
		return "Products [productName=" + productName + ", productCode=" + productCode + ", producerName="
				+ producerName + ", price=" + (float)price/100 + "€, quantity=" + quantity + "]";
	}
	
	

}
