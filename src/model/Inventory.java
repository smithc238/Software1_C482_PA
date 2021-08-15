package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class. Container for all parts and products.
 * RUNTIME ERROR forgot to make ObservableList = FXCollections.observableArrayList();
 * FUTURE ENHANCEMENT make partID also return a List to allow more than 1 return for searches with .contains().
 * The same as I did for String search.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add part to Parts ObservableList.
     * @param newPart Part to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add product to Product ObservableList.
     * @param newProduct Product to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Search for Part in the List by ID.
     * @param partId Part ID for search.
     * @return Part if match, null if no match.
     */
    public static Part lookupPart(int partId) {
        for (Part partObject : allParts) {
            if (partObject.getId() == partId) {
                return partObject;
            }
        }
        return null;
    }

    /**
     * Search for Product in the List by ID.
     * @param productId Product ID for search.
     * @return Product if match, null if not match
     */
    public static Product lookupProduct(int productId) {
        for (Product productObject : allProducts) {
            if (productObject.getId() == productId) {
                return productObject;
            }
        }
        return null;
    }

    /**
     * Search for Part in the List by Name.
     * @param partName Part Name to search.
     * @return List with any names that contains the parameter.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchList = FXCollections.observableArrayList();
        for (Part part : allParts) {
            String lowerCasePartName = part.getName().toLowerCase();
            if (lowerCasePartName.contains(partName.toLowerCase())) {
                searchList.add(part);
            }
        }
        return searchList;
    }

    /**
     * Search for Product in List by Name.
     * @param productName Name for search.
     * @return List with any names that contains the parameter.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchList = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            String lowerCaseProduct = product.getName().toLowerCase();
            if (lowerCaseProduct.contains(productName.toLowerCase())) {
                searchList.add(product);
            }
        }
        return searchList;
    }

    /**
     * Updates the Selected Part in the List.
     * @param index index of selected part.
     * @param selectedPart updated part to set in that index.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates the Selected Product in the List.
     * @param index index of selected product.
     * @param newProduct updated product to set in that index.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes selected part from List.
     * @param selectedPart selected part to be deleted.
     * @return true if deleted, else false.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else return false;
    }

    /**
     * Deletes selected product from List.
     * @param selectedProduct selected product to be deleted.
     * @return true if deleted, else false.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else return false;
    }

    /**
     * Get all parts from List.
     * @return ObservableList of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Get all products from List.
     * @return ObservableList of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
