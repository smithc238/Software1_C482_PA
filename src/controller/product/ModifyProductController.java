package controller.product;

import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * Controller for ProductMenu to Modify Products.
 * <p>RUNTIME ERROR needed to make a deeper copy of the Product object to access associated parts. Simply making the Product instance reference the selected product made unwanted permanent changes.</p>
 * <p>FUTURE ENHANCEMENT might need to make a better copy machine for complete deep copy if Product gets more complex. Only made the function for the associated parts.</p>
 */
public class ModifyProductController extends ProductMenu {
    private int productIndex;

    /**
     * Initializes the shared ProductMenu to display Modify Product title.
     */
    @Override
    public void initialize() {
        this.product = new Product(this.productId,this.productName,this.productPrice,this.productInv,this.productMin,this.productMax);
        productTitleLabel.setText("Modify Product");
    }

    /**
     * Saves any changes made to the Product by replacing the product with current instance.
     */
    @Override
    public void saveButtonListener() {
        // Resets Error Label for resubmit.
        productErrorLabel.setVisible(false);
        productErrorLabel.setText("");
        // If super's error checks are true for any changes to text fields.
        // Then set new values and update product.
        if (getFieldValues()) {
            this.product.setName(this.productName);
            this.product.setPrice(this.productPrice);
            this.product.setStock(this.productInv);
            this.product.setMax(this.productMax);
            this.product.setMin(this.productMin);
            Inventory.updateProduct(productIndex, this.product);
            // Close window
            Stage stage = (Stage) productSaveButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Retrieves Product values from MainScreenController to populate ProductMenu.
     * @param product the product user selected in MainScreen.
     */
    public void retrieveProduct(Product product) {
        // Place string values in TextFields.
        productIdField.setPromptText(String.valueOf(product.getId()));
        productNameField.setText(product.getName());
        productInvField.setText(String.valueOf(product.getStock()));
        productPriceField.setText(String.valueOf(product.getPrice()));
        productMaxField.setText(String.valueOf(product.getMax()));
        productMinField.setText(String.valueOf(product.getMin()));
        // load the TableViews after copying necessary values to instance.
        associatedPartsCopyMachine(product);
        this.product.setId(product.getId());
        super.initialize();
    }

    /**
     * Retrieves Product Index from MainScreenController.
     * @param productIndex required for updating Product.
     */
    public void retrieveProductIndex(int productIndex) {
        this.productIndex = productIndex;
    }
}
