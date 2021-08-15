package model;

/**
 * Out Source Part class, subclass of Part.
 * <p>No Errors to declare.</p>
 * <p>FUTURE ENHANCEMENT establish business rules for company name.</p>
 */
public class OutSourced extends Part {
    private String companyName;

    /**
     * OutSource class constructor.
     * @param id unique Part ID.
     * @param name Part Name.
     * @param price Part Price.
     * @param stock Part Inventory level.
     * @param min Part Maximum Inventory level.
     * @param max Part Minimum Inventory level.
     * @param companyName Part Manufacturer Company Name.
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Get the Company Name.
     * @return Company name string value.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set the Company Name.
     * @param companyName string to set as the Company Name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
