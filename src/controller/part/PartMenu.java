package controller.part;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * PartMenu abstract class provides link to all fxml features on PartMenu.fxml.
 * <p>Reducing redundancy between the Add and Modify Part controllers.</p>
 * <p>RUNTIME ERROR created the check functions as required to ensure "Max &gt; Inv &gt; Min". When applying them as helpers in the getFieldValues function, took a minute to realize the order mattered when they are called. I kept seeing the "Inv &gt; Max error" since Max check wasn't called yet and was still initialized as 0. Could definitely refine the functions more.</p>
 * <p>FUTURE ENHANCEMENT set the partSourceListener to return another value other than boolean when another Part subclass is added.</p>
 */
public abstract class PartMenu {
    protected String partName;
    protected int partInv;
    protected int partMax;
    protected int partMin;
    protected double partPrice;
    protected int partMachId;
    protected String partCoName;

    @FXML
    protected Label partTitleLabel;

    @FXML
    protected RadioButton partInSrcRadio;

    @FXML
    protected RadioButton partOutSrcRadio;

    @FXML
    protected ToggleGroup partToggleGroup;

    @FXML
    protected Label partIdLabel;

    @FXML
    protected Label partNameLabel;

    @FXML
    protected Label partInvLabel;

    @FXML
    protected Label partPriceLabel;

    @FXML
    protected Label partMaxLabel;

    @FXML
    protected Label partMinLabel;

    @FXML
    protected Label partSourceLabel;

    @FXML
    protected TextField partIdField;

    @FXML
    protected TextField partNameField;

    @FXML
    protected TextField partInvField;

    @FXML
    protected TextField partPriceField;

    @FXML
    protected TextField partMaxField;

    @FXML
    protected TextField partMinField;

    @FXML
    protected TextField partSourceField;

    @FXML
    protected Label partErrorLabel;

    @FXML
    protected Button partSaveButton;

    @FXML
    protected Button partCancelButton;

// Active Listeners
    /**
     * RadioButton ToggleGroup Listener for In-House or Outsourced Parts.
     * FUTURE ENHANCEMENT: will need to change return type if more than 2 choices exist.
     * @return true if In-House Part. Boolean provides functionality to subclasses.
     */
    @FXML
    public boolean partSourceListener() {
        if (partInSrcRadio.isSelected()) {
            partSourceLabel.setText("Machine ID");
            return true;
        }
        else {
            partSourceLabel.setText("Company Name");
            return false;
        }
    }

    /**
     * Closes the Part Menu Instance on Cancel Button press.
     */
    @FXML
    public void cancelButtonListener() {
        Stage stage = (Stage) partCancelButton.getScene().getWindow();
        stage.close();
    }

// Not used Listeners.
    /**
     * Not used in this class.
     */
    @FXML
    public void saveButtonListener() {}

// Added Functions.
    /**
     * Calls helper functions to collect all Part text fields, parse, and check errors.
     * @return false if there are any errors.
     */
    public boolean getFieldValues(){
        this.partName = partNameField.getText();
        // check all integer and double fields for no errors. Order Matters.
        boolean check1 = minInvCheck();
        boolean check2 = maxInvCheck();
        boolean check3 = inventoryCheck();
        boolean check4 = priceCheck();
        if (partSourceListener()) {
            // if InHouse check machineID for integer value.
            boolean check5 = machineIdCheck();
            return (check1 && check2 && check3 && check4 && check5);
        }
        // OutSource set Company Name.
        else if (!(partSourceListener()) && check1 && check2 && check3 && check4){
            this.partCoName = partSourceField.getText();
            return true;
        }
        // if all checks failed then return false.
        return false;
    }

    /**
     * Checks Inventory Text field for errors.
     * @return false if any errors.
     */
    public boolean inventoryCheck() {
        try {
            this.partInv = Integer.parseInt(partInvField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Inventory NumberFormatException");
            System.out.println("Inv = " + this.partInv);
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nInventory is not a number (integer).");
            return false;
        }
        System.out.println("Inv = " + this.partInv);
        if (this.partInv > this.partMax) {
            System.out.println("Inv - Max error");
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nInventory is larger than Max.");
            return false;
        }
        else if (this.partInv < this.partMin) {
            System.out.println("Inv - Min error");
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nInventory is smaller than Min.");
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
            this.partMax = Integer.parseInt(partMaxField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Max NumberFormatException");
            System.out.println("Max = " + this.partMax);
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nMax is not a number (integer).");
            return false;
        }
        if (this.partMax < this.partMin) {
            System.out.println("Max less than min error");
            System.out.println("Max = " + this.partMax);
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nMax is less than Min.");
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
            this.partMin = Integer.parseInt(partMinField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Min NumberFormatException");
            System.out.println("Min = " + this.partMin);
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nMin is not a number (integer).");
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
            this.partPrice = Double.parseDouble(partPriceField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Price NumberFormatException");
            System.out.println("Price = " + this.partPrice);
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nPrice is not correct. (double).");
            return false;
        }
        return true;
    }

    /**
     * Checks Machine ID text field for any errors.
     * @return false if any errors.
     */
    public boolean machineIdCheck() {
        try {
            this.partMachId = Integer.parseInt(partSourceField.getText());
        } catch (Exception NumberFormatException) {
            System.out.println("Machine ID NumberFormatException");
            System.out.println("MachID = " + this.partMachId);
            partErrorLabel.setVisible(true);
            partErrorLabel.setText(partErrorLabel.getText() + "\nMachine ID is not a number (integer).");
            return false;
        }
        return true;
    }
}
