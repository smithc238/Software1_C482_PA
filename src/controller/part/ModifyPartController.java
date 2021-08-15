package controller.part;

import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

/**
 * Modify Part Controller for the PartMenu.fxml, subclass of PartMenu.
 * <p>RUNTIME ERROR tried to use the retrievePart function prior to the fxmlloader.load() in the MainController. Placing after solved the issue.</p>
 * <p>FUTURE ENHANCEMENT make parts similar to products with options of having associated parts for assemblies or components.</p>
 */
public class ModifyPartController extends PartMenu {
    private int partIndex;

    /**
     * Initializes the shared PartMenu to display Modify Part title.
     */
    public void initialize() {
        partTitleLabel.setText("Modify Part");
    }

    /**
     * Updates part based on User input in text fields.
     * Uses super getFieldValues to get values and error check.
     */
    public void saveButtonListener() {
        // Clear Errors for resubmit.
        partErrorLabel.setVisible(false);
        partErrorLabel.setText("");
        // Get values and check for errors. Stop if there are errors.
        if (getFieldValues()) {
            int id = Integer.parseInt(partIdField.getPromptText());
            // Determine if InHouse or OutSource part.
            if (partSourceListener()) {
                // Update InHouse Part
                Inventory.updatePart(partIndex, new InHouse(id, this.partName, this.partPrice, this.partInv, this.partMin, this.partMax, this.partMachId));
                System.out.println("Modified InHouse object @ " + partIndex + ", ID: " + id + ", Name: " + this.partName + ", Price: " + this.partPrice + ", Inv: " + this.partInv + ", Min: " + this.partMin + ", Max: " + this.partMax + ", MachID: " + this.partMachId);
            }
            else {
                // Update OutSource Part
                Inventory.updatePart(partIndex, new OutSourced(id, this.partName, this.partPrice, this.partInv, this.partMin, this.partMax, this.partCoName));
                System.out.println("Modified OutSrc object @ " + partIndex + ", ID: " + id + ", Name: " + this.partName + ", Price: " + this.partPrice + ", Inv: " + this.partInv + ", Min: " + this.partMin + ", Max: " + this.partMax + ", CoName: " + this.partCoName);
            }
            // Send MainScreen Controller task complete and close window.
            Stage stage = (Stage) partSaveButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Populates text fields with selected Part data.
     * Helper function to send Selected Part data between controllers.
     * @param part selected part to modify.
     */
    public void retrievePart(Part part) {
        partIdField.setPromptText(String.valueOf(part.getId()));
        partNameField.setText(part.getName());
        partInvField.setText(String.valueOf(part.getStock()));
        partPriceField.setText(String.valueOf(part.getPrice()));
        partMaxField.setText(String.valueOf(part.getMax()));
        partMinField.setText(String.valueOf(part.getMin()));
        // Determines if Part Object is InHouse or OutSource.
        if (part instanceof InHouse) {
            partInSrcRadio.setSelected(true);
            partSourceField.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else {
            partSourceLabel.setText("Company Name"); //Default Text is MachineID and Listener does not catch setSelected.
            partOutSrcRadio.setSelected(true);
            partSourceField.setText(((OutSourced)part).getCompanyName());
        }
    }

    /**
     * Retrieves Index of selected part from MainScreenController.
     * Required for updatePart method.
     * @param partIndex index of selected part.
     */
    public void retrievePartIndex(int partIndex) {
        this.partIndex = partIndex;
    }
}
