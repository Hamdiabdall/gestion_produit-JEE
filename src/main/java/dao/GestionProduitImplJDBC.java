package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Produit;

public class GestionProduitImplJDBC implements IGestionProduit {
    private Connection cnx = SingletonConnection.getInstance();

    @Override
    public void addProduct(Produit p) {
        String sql = "INSERT INTO produit(nom, prix, quantite) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getQuantite());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du produit : " + e.getMessage());
        }
    }

    @Override
    public List<Produit> getAllProducts() {
        List<Produit> liste = new ArrayList<>();
        String sql = "SELECT * FROM produit";
        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(new Produit(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite")));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits : " + e.getMessage());
        }
        return liste;
    }

    @Override
    public List<Produit> getProductsByMc(String mc) {
        List<Produit> liste = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE nom LIKE ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, "%" + mc + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Produit(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des produits : " + e.getMessage());
        }
        return liste;
    }

    @Override
    public Produit getProduct(int id) {
        Produit p = null;
        String sql = "SELECT * FROM produit WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Produit(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du produit : " + e.getMessage());
        }
        return p;
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM produit WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du produit : " + e.getMessage());
        }
    }

    @Override
    public void updateProduct(Produit p) {
        String sql = "UPDATE produit SET nom=?, prix=?, quantite=? WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getQuantite());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du produit : " + e.getMessage());
        }
    }
}
