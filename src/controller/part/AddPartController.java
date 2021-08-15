package controller.part;

import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

/**
 * Add Part Controller for PartMenu.fxml, subclass of PartMenu.
 * <p>RUNTIME ERROR, MainScreen controller would generate another ID if I clicked cancel instead of save.Tried to use saveButtonListener as a boolean return type in the MainScreenController, it caused 2 objects to be created as it invoked it to get the return. Solution was the addComplete boolean variable.</p>
 * <p>FUTURE ENHANCEMENT make form fields use inline validation for better user experience.</p>
 */
public class AddPartController extends PartMenu {
    private int nextID;
    public boolean addComplete;

    /**
     * Initializes addComplete boolean for MainScreenController communication.
     */
    public void initialize() {
        this.addComplete = false;
    }

    /**
     * Adds User input created Part to Inventory's Part ObservableList.
     * Uses super getFieldValues to get values and error check.
     */
    public void saveButtonListener() {
        // Clear Errors for resubmit.
        partErrorLabel.setVisible(false);
        partErrorLabel.setText("");
        // Get values and check for errors. Stop if there are errors.
        if (getFieldValues()) {
            // Determine if InHouse or OutSource part.
            if (partSourceListener()) {
                // Add InHouse Part
                Inventory.addPart(new InHouse(this.nextID,this.partName,this.partPrice,this.partInv,this.partMin,this.partMax,this.partMachId));
                System.out.println("Created InHouse object: ID: " + this.nextID + ", Name: " + this.partName + ", Price: " + this.partPrice + ", Inv: " + this.partInv + ", Min: " + this.partMin + ", Max: " + this.partMax + ", MachID: " + this.partMachId);
            }
            else {
                // Add OutSource Part
                Inventory.addPart(new OutSourced(this.nextID,this.partName,partPrice,this.partInv,this.partMin,this.partMax,this.partCoName));
                System.out.println("Created OutSrc object: ID: " + this.nextID + ", Name: " + this.partName + ", Price: " + this.partPrice + ", Inv: " + this.partInv + ", Min: " + this.partMin + ", Max: " + this.partMax + ", CoName: " + this.partCoName);
            }
            // Send MainScreen Controller task complete and close window.
            this.addComplete = true;
            Stage stage = (Stage) partSaveButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Gets from MainScreenController for part to be added.
     * @param newId sets local at nextID to be used in saveButtonListener.
     */
    public void retrieveID(int newId) {
        this.nextID = newId;
    }
}
