package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Map<Integer, CartItem> items;
    
    public Cart() {
        this.items = new HashMap<>();
    }
    
    public void addItem(Produit produit, int quantity) {
        int productId = produit.getId();
        
        if (items.containsKey(productId)) {
            // If product already in cart, increment quantity
            CartItem existingItem = items.get(productId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Otherwise add new item
            items.put(productId, CartItem.fromProduit(produit, quantity));
        }
    }
    
    public void updateItemQuantity(int productId, int quantity) {
        if (items.containsKey(productId)) {
            if (quantity <= 0) {
                // Remove item if quantity is zero or negative
                items.remove(productId);
            } else {
                // Update quantity
                items.get(productId).setQuantity(quantity);
            }
        }
    }
    
    public void removeItem(int productId) {
        items.remove(productId);
    }
    
    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getTotal();
        }
        return total;
    }
    
    public void clear() {
        items.clear();
    }
} 