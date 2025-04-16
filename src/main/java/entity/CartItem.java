package entity;

import java.io.Serializable;

// This class is not an entity as it will be stored in the session
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    
    public CartItem() {}
    
    public CartItem(int productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Create a CartItem from a Produit
    public static CartItem fromProduit(Produit produit, int quantity) {
        return new CartItem(
            produit.getId(),
            produit.getNom(),
            produit.getPrix(),
            quantity
        );
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotal() {
        return price * quantity;
    }
} 