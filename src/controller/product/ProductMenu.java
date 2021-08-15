package controller.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * ProductMenu abstract class provides link to ProductMenu.fxml features.
 * RUNTIME ERROR most issues came with the associatedParts List in the product.
 * Needed a new Product Object instance with both the Add and Modify product controllers.
 * A solution which I used was make the new instance in the sub controllers with the reference in this class.
 * This allowed this class to keep the Add and Remove associatedPartListeners'.
 * FUTURE ENHANCEMENT add more data formatting to the TableViews to convey more information. I.E. inventory level = 5/10 Max
 */
public abstract class ProductMenu {
    protected Product product;
    protected int productId;
    protected String productName;
    protected int productInv;
    protected int productMax;
    protected int productMin;
    protected double productPrice;

    @FXML
    protected Label productTitleLabel;

    @FXML
    protected TextField productIdField;

    @FXML
    protected Label productMinLabel;

    @FXML
    protected TextField productNameField;

    @FXML
    protected TextField productInvField;

    @FXML
    protected TextField productPriceField;

    @FXML
    protected TextField productMaxField;

    @FXML
    protected TextField productMinField;

    @FXML
    protected Label productIdLabel;

    @FXML
    protected Label productNameLabel;

    @FXML
    protected Label productInvLabel;

    @FXML
    protected Label productPriceLabel;

    @FXML
    protected Label productMaxLabel;

    @FXML
    protected Label productErrorLabel;

    @FXML
    protected TextField partSearchField;

    @FXML
    protected Label partSearchError;

    @FXML
    protected TableView<Part> partTableView;

    @FXML
    protected TableColumn<Part, Integer> partIdCol;

    @FXML
    protected TableColumn<Part, String> partNameCol;

    @FXML
    protected TableColumn<Part, Integer> partInvCol;

    @FXML
    protected TableColumn<Part, Double> partPriceCol;

    @FXML
    protected Button addPartButton;

    @FXML
    protected TableView<Part> associatedPartTable;

    @FXML
    protected TableColumn<Part, Integer> associatedIdCol;

    @FXML
    protected TableColumn<Part, String> associatedNameCol;

    @FXML
    protected TableColumn<Part, Integer> associatedInvCol;

    @FXML
    protected TableColumn<Part, Double> associatedPriceCol;

    @FXML
    protected Button removeSelectedPartButton;

    @FXML
    protected Button productSaveButton;

    @FXML
    protected Button productCancelButton;

    /**
     * Initializes the Tableviews in Product Menu.
     */
    public void initialize() {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(this.product.getAssociatedParts());
        associatedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

// Active Listeners
    /**
     * Searches Parts List by ID or Name. Displays Error if not found.
     */
    @FXML
    public void partSearchListener() {
        // Get Search Field and reset error for new searches
        String partSearch = partSearchField.getText();
        partSearchError.setVisible(false);
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
            if (partFilter.isEmpty()) {
                partSearchError.setVisible(true);
            }
        }
        // If search is blank return to normal view.
        else {
            partTableView.setItems(Inventory.getAllParts());
            partSearchError.setVisible(false);
        }
    }

    /**
     * Adds selected part to products associated parts.
     */
    @FXML
    public void addPartListener() {
        // Restricted to selecting one part at a time to add. Not specified to require more.
        if (partTableView.getSelectionModel().getSelectedItem() != null) {
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            this.product.addAssociatedPart(selectedPart);
        }
    }

    /**
     * Removes associated part from Product with confirmation dialogue.
     */
    @FXML
    public void removePartListener() {
        if (associatedPartTable.getSelectionModel().getSelectedItem() != null) {
            Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Confirm Remove");
            confirm.setContentText("Are you sure you want to remove: \n" + selectedPart.getName());
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    this.product.deleteAssociatedPart(selectedPart);
                }
            });
        }
    }

    /**
     * Closes the Product Menu Instance on Cancel Button press.
     */
    @FXML
    public void cancelButtonListener() {
        Stage stage = (Stage) productCancelButton.getScene().getWindow();
        stage.close();
    }

// Not used listeners in this class.
    /**
     * Not used in this class.
     */
    @FXML
    public void saveButtonListener() {
    }

// Added Functions.
    /**
     * Calls helper functions to collect all Product text fields, parse, and check errors.
     * @return false if there are any errors.
     */
    public boolean getFieldValues(){
        this.productName = productNameField.getText();
        // check all integer and double fields for no errors. Order Matters.
        boolean check1 = minInvCheck();
        boolean check2 = maxInvCheck();
        boolean check3 = inventoryCheck();
        boolean check4 = priceCheck();
        return (check1 && check2 && check3 && check4);
    }

    /**
     * Checks Inventory Text field for errors.
     * @return false if any errors.
     */
    public boolean inventoryCheck() {
        try {
            this.productInv = Integer.parseInt(productInvField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Inventory NumberFormatException");
            System.out.println("Inv = " + this.productInv);
            productErrorLabel.setVisible(true);
            productErrorLabel.setText(productErrorLabel.getText() + "\nInventory is not a number (integer).");
            return false;
        }
        System.out.println("Inv = " + this.productInv);
        if (this.productInv > this.productMax) {
            System.out.println("Inv - Max error");
            productErrorLabel.setVisible(true);
            productErrorLabel.setText(productErrorLabel.getText() + "\nInventory is larger than Max.");
            return false;
        }
        else if (this.productInv < this.productMin) {
            System.out.println("Inv - Min error");
            productErrorLabel.setVisible(true);
            productErrorLabel.setText(productErrorLabel.getText() + "\nInventory is smaller than Min.");
            return false;
        }
        return true;
    }

    /**
     * Check Max text field for errors.
     * @return false if any errors.
     */
    public boolean maxInvCheck() {
        try {
            this.productMax = Integer.parseInt(productMaxField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Max NumberFormatException");
            System.out.println("Max = " + this.productMax);
            productErrorLabel.setVisible(true);
            productErrorLabel.setText(productErrorLabel.getText() + "\nMax is not a number (integer).");
            return false;
        }
        if (this.productMax < this.productMin) {
            System.out.println("Max less than min error");
            System.out.println("Max = " + this.productMax);
            productErrorLabel.setVisible(true);
            productErrorLabel.setText(productErrorLabel.getText() + "\nMax is less than Min.");
            return false;
        }
        return true;
    }

    /**
     * Checks Min text field for errors.
     * @return false if any errors.
     */
    public boolean minInvCheck() {
        try {
            this.productMin = Integer.parseInt(productMinField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Min NumberFormatException");
            System.out.println("Min = " + this.productMin);
            productErrorLabel.setVisible(true);
            productErrorLabel.setText(productErrorLabel.getText() + "\nMin is not a number (integer).");
            return false;
        }
        return true;
    }

    /**
     * Checks Price text field for errors.
     * @return false if any errors.
     */
    public boolean priceCheck() {
        try {
            this.productPrice = Double.parseDouble(productPriceField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Price NumberFormatException");
            System.out.println("Price = " + this.productPrice);
            productErrorLabel.setVisible(true);
            productErrorLabel.setText(productErrorLabel.getText() + "\nPrice is not correct. (double).");
            return false;
        }
        return true;
    }

    /**
     * Copies associated parts if there are more than one.
     * @param product product to make copies from.
     */
    public void associatedPartsCopyMachine(Product product){
       for (Part part : product.getAssociatedParts()) {
           this.product.addAssociatedPart(part);
       }
    }
}
