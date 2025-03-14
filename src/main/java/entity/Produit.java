package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private int quantite;
    private double prix;

    // Default constructor (needed by JPA)
    public Produit() {}

    // Constructor for new products (without ID)
    public Produit(String nom, int quantite, double prix) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
    }

    // **Constructor with ID (for database retrieval)**
    public Produit(int id, String nom, double prix, int quantite) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    @Override
    public String toString() {
        return "Produit [id=" + id + ", nom=" + nom + ", quantite=" + quantite + ", prix=" + prix + "]";
    }
}
