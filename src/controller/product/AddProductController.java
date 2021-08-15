package controller.product;

import javafx.stage.Stage;
import model.Inventory;
import model.Product;

/**
 * Controller for ProductMenu to Add new Products.
 * <p>RUNTIME ERROR after making the ProductMenu.initialize() to include the associatedPart TableViews, I realized that it didn't help that I Override the function here. Quick solution was to call the function in this instance.</p>
 * <p>FUTURE ENHANCEMENT add icons to the GUI instead of text on buttons. I.E. plus symbol for add.</p>
 */
public class AddProductController extends ProductMenu {
    private int nextID;
    public boolean addComplete;

    /**
     * Initializes the Add ProductMenu with a new product and TableViews.
     */
    @Override
    public void initialize() {
        // Created new product with new ID, the rest are uninitialized.
        this.product = new Product(this.nextID,this.productName,this.productPrice,this.productInv,this.productMin,this.productMax);
        // Populate Tableviews.
        super.initialize();
        // Set default message to MainScreenController.
        this.addComplete = false;
    }

    /**
     * Gets user input from the fields, checks errors, and adds to Inventory List.
     */
    @Override
    public void saveButtonListener() {
        // Resets Error Label for resubmit.
        productErrorLabel.setVisible(false);
        productErrorLabel.setText("");
        // If super's error checks are true, set new values and add product.
        if (getFieldValues()) {
            // new values are coming from super's protected variables
            this.product.setId(this.nextID);
            this.product.setName(this.productName);
            this.product.setPrice(this.productPrice);
            this.product.setStock(this.productInv);
            this.product.setMax(this.productMax);
            this.product.setMin(this.productMin);
            Inventory.addProduct(this.product);
            // Let MainScreenController know to generate new ID and close window.
            this.addComplete = true;
            Stage stage = (Stage) productSaveButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Gets next ID from MainScreenController for product to be added.
     * @param newId sets local at nextID to be used in saveButtonListener.
     */
    public void retrieveID(int newId) {
        this.nextID = newId;
    }
}
