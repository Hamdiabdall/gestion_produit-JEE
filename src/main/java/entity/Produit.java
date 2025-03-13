package entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity

public class Produit {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nom;
    private int id, quantite;
    private double prix;
    
   /* public Produit(int id, String nom, double prix, int quantite) {
        this.nom = nom;
        this.id = id;
        this.quantite = quantite;
        this.prix = prix;
    }*/
    
    
    public Produit(String nom, int quantite, double prix) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
    }
    
    public Produit() {}
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public double getPrix() {
        return prix;
    }
    
    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    @Override
    public String toString() {
        return "Produit [nom=" + nom + ", id=" + id + ", quantite=" + quantite + ", prix=" + prix + "]";
    }
}
