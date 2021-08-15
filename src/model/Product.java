package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class. Contains associated parts list.
 * <p>RUNTIME ERROR forgot to add = FXCollections.observableArrayList() to associated Parts.</p>
 * <p>FUTURE ENHANCEMENT add a clone method for deep copy.</p>
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product Constructor.
     * @param id unique Product ID.
     * @param name Product Name.
     * @param price Product Price.
     * @param stock Product Inventory level.
     * @param min Product Minimum Inventory level.
     * @param max Product Maximum Inventory level.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Set Product ID.
     * @param id unique ID for Product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Product ID.
     * @return Product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Set Product Name.
     * @param name Name of product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Product Name.
     * @return Product Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set Product Price.
     * @param price Price of product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get Product Price.
     * @return Price of product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set Product Inventory level.
     * @param stock Inventory level.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get Product Inventory level.
     * @return Inventory level of product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set Minimum Inventory level.
     * @param min Minimum inventory level.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get Minimum Inventory level.
     * @return Minimum inventory level.
     */
    public int getMin() {
        return min;
    }

    /**
     * Set Maximum Inventory level.
     * @param max Maximum Inventory level.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get Maximum Inventory level.
     * @return Maximum Inventory level.
     */
    public int getMax() {
        return max;
    }

    /**
     * Add Associated parts to Product.
     * @param part Part to add.
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Delete Associated parts from Product.
     * @param selectedAssociatedPart Part to delete.
     * @return true if deleted, else false.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

    /**
     * Get all Associated parts from Product
     * @return associated parts ObservableList.
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
}
