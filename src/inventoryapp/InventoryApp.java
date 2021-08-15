package inventoryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Product;

import java.util.Objects;

/**
 * Main Class for the Inventory Application.
 * Contains Sample parts and products.
 * RUNTIME ERROR forget to add sample associated parts to products.
 * FUTURE ENHANCEMENT depending on user needs and requirements would develop best persistent storage solution,
 * sorting and search algorithms to make data more efficient. Used only simple for loops for this small dataset.
 */
public class InventoryApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
//      Sample Parts and Products.
        InHouse part1 = new InHouse(1, "Washer", 0.10, 30,5,150,12);
        InHouse part2 = new InHouse(5,"bolt", 1.95, 20,5,50,1002);
        OutSourced part3 = new OutSourced(13,"Tires", 199.99,8,4,16,"Goodyear");
        OutSourced part4 = new OutSourced(2,"Windshield Wipers",20.00,8,5,20,"Rain-X");
        Product product1 = new Product(4, "Used Sedan", 1499.99, 2, 1,5);
        Product product2 = new Product(3, "Toyota Hilux", 13999.99, 5,1,10);
        Product product3 = new Product(6,"Toyota Prius", 8999.99,2,1,8);
        Product product4 = new Product(8,"SWAG T-Shirt", 00.00,10,1,20);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        product1.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);
        product4.addAssociatedPart(part1);
        product4.addAssociatedPart(part4);

        launch(args);
    }
}

