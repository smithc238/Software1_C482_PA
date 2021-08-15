package controller;

import controller.part.AddPartController;
import controller.part.ModifyPartController;
import controller.product.AddProductController;
import controller.product.ModifyProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;

/**
 * Controller for the MainScreen.fxml. Also provides ID for adding parts and products.
 * RUNTIME ERROR, first errors when developing this application occurred from this class.
 * Non-stop processes of learning how to load a new Stage, Scene and FXML Loader.
 * Errors of trying to pass data before loading and communicating data back to this class without showAndWait().
 * FUTURE ENHANCEMENT further developing of the TableViews to include custom data formatting. Such as the price column = $0.00
 */
public class MainScreenController {
    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;
    private int nextIdToUse;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField partSearchField;

    @FXML
    private TextField productSearchField;

    @FXML
    private Label partErrorLabel;

    @FXML
    private Label productErrorLabel;

    @FXML
    private Button addPartButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitMainButton;

    /**
     * Initializes on load and populates Part and Product TableViews.
     */
    public void initialize() {
        // Parts TableView initialize
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Products TableView initialize
        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initializes the lastID variable.
        generateID();
        System.out.println("Next ID to use: " + nextIdToUse);
    }

// Search Fields
    /**
     * Searches Parts List by ID or Name.
     * Displays error message when Part is not found.
     * RUNTIME ERROR have to in place function to filter String or Integer or parseInt gives error.
     * FUTURE ENHANCEMENT can change the ID search to display all alike IDs similar to String search.
     */
    public void partSearchAction() {
        // Get Search Field and reset error for new searches
        String partSearch = partSearchField.getText();
        partErrorLabel.setVisible(false);
        // If search is not blank try ID search first if not number then String.
        if (!partSearch.isBlank()) {
            ObservableList<Part> partFilter = FXCollections.observableArrayList();
            try {
                int searchId = Integer.parseInt(partSearch);
                Part foundPart = Inventory.lookupPart(searchId);
                if (foundPart != null) {partFilter.add(foundPart);}
            } catch (Exception NumberFormatException) {
                partFilter.addAll(Inventory.lookupPart(partSearch));
            }
            // add filter list to view, if empty display error.
            partTableView.setItems(partFilter);
            if (partFilter.isEmpty()) {partErrorLabel.setVisible(true);}
        }
        // If search is blank return to normal view.
        else {
            partTableView.setItems(Inventory.getAllParts());
            partErrorLabel.setVisible(false);
        }
    }

    /**
     * Searches Products List by ID or Name.
     * Displays error message when product is not found.
     */
    public void productSearchAction() {
        // Get Search Field and reset error for new searches
        String productSearch = productSearchField.getText();
        productErrorLabel.setVisible(false);
        // If search is not blank try ID search first if not number then String.
        if (!productSearch.isBlank()) {
            ObservableList<Product> productFilter = FXCollections.observableArrayList();
            try {
                int searchId = Integer.parseInt(productSearch);
                Product foundProduct = Inventory.lookupProduct(searchId);
                if (foundProduct != null) {
                    productFilter.add(foundProduct);
                }
            } catch (Exception NumberFormatException) {
                productFilter.addAll(Inventory.lookupProduct(productSearch));
            }
            // add filter list to view, if empty display error.
            productTableView.setItems(productFilter);
            if (productFilter.isEmpty()) {
                productErrorLabel.setVisible(true);
            }
        }
        // If search is blank return to normal view.
        else {
            productTableView.setItems(Inventory.getAllProducts());
            productErrorLabel.setVisible(false);
        }
    }

// Add forms
    /**
     * Opens the Add Part menu to add parts.
     * @throws IOException if PartMenu.fxml fails to load.
     */
    public void addPartListener() throws IOException {
        AddPartController partAdd = new AddPartController();
        loader = new FXMLLoader(getClass().getResource("/view/PartMenu.fxml"));
        loader.setController(partAdd);
        stage = new Stage();
        stage.setTitle("Add Part");
        scene = new Scene(loader.load());
        partAdd.retrieveID(nextIdToUse);
        stage.setScene(scene);
        stage.showAndWait();
        // Once part was successfully added generate new ID.
        if (partAdd.addComplete)generateID();
    }

    /**
     * Opens the Add Product Menu to add a product.
     * @throws IOException if the ProductMenu.fxml fails to load.
     */
    public void addProductListener() throws IOException {
        AddProductController productAdd = new AddProductController();
        loader = new FXMLLoader(getClass().getResource("/view/ProductMenu.fxml"));
        loader.setController(productAdd);
        stage = new Stage();
        stage.setTitle("Add Product");
        scene = new Scene(loader.load());
        productAdd.retrieveID(nextIdToUse);
        stage.setScene(scene);
        stage.showAndWait();
        // Once product was added to list generate new ID.
        if (productAdd.addComplete)generateID();
    }

// Modify forms
    /**
     * Opens the Modify Part Menu and populates fields with selected part from TableView.
     * @throws IOException if the PartMenu.fxml fails to load.
     */
    public void modifyPartListener() throws IOException {
        // Stop errors for unselected parts.
        if (partTableView.getSelectionModel().getSelectedItem() != null){
            ModifyPartController partModify = new ModifyPartController();
            loader = new FXMLLoader(getClass().getResource("/view/PartMenu.fxml"));
            loader.setController(partModify);
            stage = new Stage();
            stage.setTitle("Modify Part");
            scene = new Scene(loader.load());
            // Retrieve selected part data.
            partModify.retrievePart(partTableView.getSelectionModel().getSelectedItem());
            partModify.retrievePartIndex(partTableView.getSelectionModel().getSelectedIndex());
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Opens the Modify Product Menu and populates fields with selected product from TableView.
     * @throws IOException if the ProductMenu.fxml fails to load.
     */
    public void modifyProductListener() throws IOException {
        // Stop error for unselected products.
        if (productTableView.getSelectionModel().getSelectedItem() != null) {
            ModifyProductController modifyProduct = new ModifyProductController();
            loader = new FXMLLoader(getClass().getResource("/view/ProductMenu.fxml"));
            loader.setController(modifyProduct);
            stage = new Stage();
            stage.setTitle("Modify Product");
            scene = new Scene(loader.load());
            // Retrieve selected product data.
            modifyProduct.retrieveProduct(productTableView.getSelectionModel().getSelectedItem());
            modifyProduct.retrieveProductIndex(productTableView.getSelectionModel().getSelectedIndex());
            stage.setScene(scene);
            stage.show();
        }
    }

// Delete Button Listeners
    /**
     * Removes selected Part from ObservableList after confirmation.
     */
    public void deletePartListener() {
        if (partTableView.getSelectionModel().getSelectedItem() != null) {
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Confirm Delete");
            confirm.setContentText("Are you sure you want to delete: \n" + selectedPart.getName());
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                }
            });
        }
    }

    /**
     * Removes selected Product from ObservableList after confirmation.
     */
    public void deleteProductListener() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if ( selectedProduct != null && selectedProduct.getAssociatedParts().isEmpty()) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Confirm Delete");
            confirm.setContentText("Are you sure you want to delete: \n" + selectedProduct.getName());
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
                }
            });
        }
        if (selectedProduct != null && !(selectedProduct.getAssociatedParts().isEmpty())) {
            Alert warning = new Alert(Alert.AlertType.INFORMATION);
            warning.setHeaderText("Can Not Delete " + selectedProduct.getName());
            warning.setContentText( selectedProduct.getName() + " has associated parts. Remove them first if you wish to delete.");
            warning.getDialogPane().setPrefHeight(200.00);
            warning.show();
        }
    }

// Exit Application
    /**
     * Closes the application.
     */
    public void exitMainListener() {
        System.exit(0);
    }

// Other Functions
    /**
     * Generates a new ID to be used when adding Part or Product. Also updates the lastID variable.
     * Wanted the ID to be unique to that item and not allow part and product to have same ID.
     * Does not check prior made IDs for uniqueness.
     */
    public void generateID() {
        int newID;
        if (this.nextIdToUse == 0 && Inventory.getAllParts().isEmpty() && Inventory.getAllProducts().isEmpty()) {
            newID = 1;
            System.out.println("generate Id option 1 :" + newID);
        }
        else if (this.nextIdToUse != 0) {
            newID = this.nextIdToUse + 1;
            System.out.println("generate Id option 2 :" + newID);
        }
        else {
            int maxPartId = 0;
            int maxProductId = 0;
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() > maxPartId) {
                    maxPartId = part.getId();
                }
            }
            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() > maxProductId) {
                    maxProductId = product.getId();
                }
            }
            newID = Math.max(maxPartId, maxProductId) + 1;
            System.out.println("generate Id option 3 :" + newID);
        }
        this.nextIdToUse = newID;
    }
}
