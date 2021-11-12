# Electronic-online-store-javaFX

<h2>Installation</h2>

To install and run the software use an IDE suitable for Java code (Eclipse [https://www.eclipse.org/downloads/, NetBeans] (https://www.eclipse.org/downloads/,%20NetBeans) <https : //netbeans.apache.org/download/index.html>, IntelliJ <https://www.jetbrains.com/idea/download/#section=windows>, etc.). When you have installed the IDE you prefer, you can run the program inside it by opening the java project in this folder and clicking the "Run" button.

In order to use the project it is necessary to configure the environment for JavaFX. To do this, download ** JavaFX 17 ** from the link <https://gluonhq.com/products/javafx/>. Then go to Help> Eclipse marketplace…, search and install ** and (fx) clipse **. Select Properties of your current project> Java Build Path Property> Library> Classpath> Add External JARs and go to the lib directory of the previously downloaded JavaFX and add all the JAR files. Then Run> Run Configurations ...> Arguments and paste in VM arguments 
```
--module-path "C: \ Program Files \ Java \ javafx-sdk-12.0.1 / lib" --add-modules javafx.controls, javafx.fxml
```
replacing the part in quotes with your path.

<h2>Use</h2>

Once the program has been run, a window will appear containing fields for logging in and a button for logging in. There are three accounts by default:

- Administrator account: Federico Canali, username <b>fede</b> and password <b>f</b>
- Account employee: Alice Jones, username <b>ali</b> and password <b>jS</b>
- User account: Bob Smith, username <b>bob</b> and password <b>b</b>

By choosing the registration you will be asked for your name, surname, username and password.

Once a <b>customer</b> accesses the e-commerce, he can:
- filter the search for products by product name, manufacturer name, maximum price and minimum price;
- Buy products;
- Log out of e-commerce.

Once an <b>employee</b> accesses e-commerce he can
- Update the number of products of the products in stock when new products arrive (enter the number of products to add to those already present);
- Execute product orders on behalf of customers;
- Log out of e-commerce.

Once an <b>administrator</b> accesses the e-commerce, he can:
- Add and delete products;
- Add new employees;
- Log out of e-commerce.

<h2>Documentation</h2>

The documentation of the Project (JavaDoc) is found in assignment_2\assignment_2\doc.

<h2>Screenshots</h2>
![administrator_page](/img/administrator_page.png)
