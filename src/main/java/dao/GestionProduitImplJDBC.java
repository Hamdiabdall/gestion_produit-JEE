package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import entity.Produit;

public class GestionProduitImplJDBC implements IGestionProduit {
private Connection cnx=SingletonConnection.getInstance();
  @Override
  public void addProduct(Produit p) {
     
      try {
          PreparedStatement ps=cnx.prepareStatement("insert into produit(nom,prix,quantite) values(?,?,?)");
       ps.setString(1, p.getNom());
       ps.setDouble(2, p.getPrix());
       ps.setInt(3, p.getQuantite());
       ps.executeUpdate();
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
     
     
  }

  @Override
  public List<Produit> getAllProducts() {
      List<Produit> liste=new ArrayList<Produit>();
      try {
          PreparedStatement ps=cnx.prepareStatement("select * from produit");
          ResultSet rs=ps.executeQuery();
          while(rs.next())
              liste.add(new Produit(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4)));
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
     
      return liste;
  }

  @Override
  public List<Produit> getProductsByMc(String mc) {
      List<Produit> liste=new ArrayList<Produit>();
      try {
          PreparedStatement ps=cnx.prepareStatement("select * from produit where nom like ?");
          ps.setString(1, "%"+mc+"%");
          ResultSet rs=ps.executeQuery();
          while(rs.next())
              liste.add(new Produit(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4)));
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
     
      return liste;
  }

  @Override
  public Produit getProduct(int id) {
      Produit p=null;
      try {
          PreparedStatement ps=cnx.prepareStatement("select * from produit where id=?");
          ps.setInt(1, id);
          ResultSet rs=ps.executeQuery();
          if(rs.next())
              p=new Produit(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4));
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
     
      return p;
  }

  @Override
  public void deleteProduct(int id) {
      try {
          PreparedStatement ps=cnx.prepareStatement("delete from produit where id=?");
       ps.setInt(1, id);
     
       ps.executeUpdate();
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
     
  }

  @Override
  public void updateProduct(Produit p) {
      try {
          PreparedStatement ps=cnx.prepareStatement("update produit set nom=?,prix=?,quantite=? where id=?");
       ps.setString(1, p.getNom());
       ps.setDouble(2, p.getPrix());
       ps.setInt(3, p.getQuantite());
       ps.setInt(4, p.getId());
       ps.executeUpdate();
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
     
  }



}
