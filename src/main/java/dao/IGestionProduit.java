package dao;

import java.util.List;

import entity.Produit;

public interface IGestionProduit {
public void addProduct(Produit p);
public List<Produit> getAllProducts();
public List<Produit> getProductsByMc(String mc);
public Produit getProduct(int id);
public void deleteProduct(int id);
public void updateProduct(Produit p);


}