package model;

/**
 * InHouse part class that is subclass of Part.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * InHouse Constructor
     * @param id unique Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Inventory level
     * @param min Part Minimum Inventory level
     * @param max Part Maximum Inventory level
     * @param machineId Part Machine ID, not unique to part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Get the Part Machine ID.
     * @return the part's machine ID.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Set the Part Machine ID.
     * @param machineId value to set for part machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
