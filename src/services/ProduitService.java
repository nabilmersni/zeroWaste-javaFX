package services;

import entities.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.MyDB;

public class ProduitService implements IProduitService {
  
  public Connection conx;
  public Statement stm;

  public ProduitService() {
    conx = MyDB.getInstance().getConx();
  }

  
  @Override
  public List<Produit> getAllProducts()  {
    List<Produit> productList = new ArrayList<>();
    try {
        String query = "SELECT * FROM produit ORDER BY id DESC ";
        PreparedStatement preparedStatement = conx.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
  
        // Parcours du résultat de la requête
        while (resultSet.next()) {
            Produit produit = new Produit();
            produit.setId(resultSet.getInt("id"));
            produit.setNom_produit(resultSet.getString("nom_produit"));
            produit.setDescription(resultSet.getString("description"));
            produit.setQuantite(resultSet.getInt("quantite"));
            produit.setPrix_produit(resultSet.getFloat("prix_produit"));
            produit.setImage(resultSet.getString("image"));
            produit.setCategorie_produit_id(resultSet.getInt("categorie_produit_id"));
            produit.setPrix_point_produit(resultSet.getInt("prix_point_produit"));
            produit.setRemise(resultSet.getFloat("remise"));
  
            productList.add(produit);
        }
        preparedStatement.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return productList;
  
  }

  @Override
  public void supprimer(int idProduit) throws SQLException {
      String sql = "DELETE FROM produit WHERE id = ?";
      try (PreparedStatement pstmt = conx.prepareStatement(sql)) {
          pstmt.setInt(1, idProduit);
          pstmt.executeUpdate();
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }
  }

  @Override
  public void ajouter(Produit produit) {
    try {
      String req = "INSERT INTO `produit`(`nom_produit`, `description`, `quantite`, `prix_produit`, `image`, `categorie_produit_id`, `prix_point_produit`) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
        ps.setString(1, produit.getNom_produit());
        ps.setString(2, produit.getDescription());
        ps.setInt(3, produit.getQuantite());
        ps.setFloat(4, produit.getPrix_produit());
        ps.setString(5, produit.getImage());
        ps.setInt(6, produit.getCategorie_produit_id());
        ps.setInt(7, produit.getPrix_point_produit());
        ps.executeUpdate();
        System.out.println("Product added successfully");
        ps.close();
    }catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du produit : " + e.getMessage());
    }
    
  }

  public String getCategory(int idCategory) {
    String categoryName = "";

    try {
        String req = "SELECT nom_categorie FROM `categorie_produit` where id = ?";
        PreparedStatement ps = conx.prepareStatement(req);
        ps.setInt(1, idCategory);
        ResultSet rs = ps.executeQuery();
        rs.next(); 
        categoryName = rs.getString("nom_categorie"); 
        ps.close();
    } catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de la récupération du produit : " + e.getMessage());
    }

    return categoryName;
  }

  public Produit getOneProduct(int idProduct) throws SQLException {
    String req = "SELECT * FROM `produit` where id = ?";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, idProduct);

    ResultSet rs = ps.executeQuery();
    Produit produit = new Produit();
    
    while (rs.next()) {
        produit.setId(rs.getInt("id"));
        produit.setNom_produit(rs.getString("nom_produit"));
        produit.setDescription(rs.getString("description"));
        produit.setQuantite(rs.getInt("quantite"));
        produit.setPrix_produit(rs.getFloat("prix_produit"));
        produit.setImage(rs.getString("image"));
        produit.setCategorie_produit_id(rs.getInt("categorie_produit_id"));
        produit.setPrix_point_produit(rs.getInt("prix_point_produit"));
    }
    ps.close();
    return produit;
  }

  @Override
  public void updateProduct(Produit produit) {
    try {
      String req = "UPDATE `produit` SET `nom_produit`=?,`description`=?,`quantite`=?,`prix_produit`=?,`image`=?,`categorie_produit_id`=?,`prix_point_produit`=? WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
        ps.setString(1, produit.getNom_produit());
        ps.setString(2, produit.getDescription());
        ps.setInt(3, produit.getQuantite());
        ps.setFloat(4, produit.getPrix_produit());
        ps.setString(5, produit.getImage());
        ps.setInt(6, produit.getCategorie_produit_id());
        ps.setInt(7, produit.getPrix_point_produit());
        ps.setInt(8, produit.getId());
        ps.executeUpdate();
        System.out.println("Product updated successfully");
        ps.close();
    }catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du produit : " + e.getMessage());
    }
    
  }

  public List<Produit> searchProducts(String search) {
    List<Produit> productList = new ArrayList<>();
    try {
        String query = "SELECT * FROM produit WHERE nom_produit LIKE ? OR description LIKE ? OR prix_produit LIKE ?";
        PreparedStatement preparedStatement = conx.prepareStatement(query);
        preparedStatement.setString(1, "%" + search + "%");
        preparedStatement.setString(2, "%" + search + "%");
        preparedStatement.setString(3, "%" + search + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        // Parcours du résultat de la requête
        while (resultSet.next()) {
            Produit produit = new Produit();
            produit.setId(resultSet.getInt("id"));
            produit.setNom_produit(resultSet.getString("nom_produit"));
            produit.setDescription(resultSet.getString("description"));
            produit.setQuantite(resultSet.getInt("quantite"));
            produit.setPrix_produit(resultSet.getFloat("prix_produit"));
            produit.setImage(resultSet.getString("image"));
            produit.setCategorie_produit_id(resultSet.getInt("categorie_produit_id"));
            produit.setPrix_point_produit(resultSet.getInt("prix_point_produit"));

            productList.add(produit);
        }
        preparedStatement.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return productList;
  }


  public List<Produit> sortProducts(int value, int idCategory) {
    List<Produit> productList = new ArrayList<>();
    try {
        String query = "";
        PreparedStatement preparedStatement;
        
        if(value == 1 && idCategory == -1){ //sort by stock
          query = "SELECT * FROM produit ORDER BY quantite asc";
          preparedStatement = conx.prepareStatement(query);
        }else if(value == 0 && idCategory !=0 ){ //filter by category
          query = "SELECT * FROM produit where categorie_produit_id = ? ";
          preparedStatement = conx.prepareStatement(query);
          preparedStatement.setInt(1, idCategory);
        }else{ // sort by stock and category
          query = "SELECT * FROM produit where categorie_produit_id = ? ORDER BY quantite asc";
          preparedStatement = conx.prepareStatement(query);
          preparedStatement.setInt(1, idCategory );
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        // Parcours du résultat de la requête
        while (resultSet.next()) {
            Produit produit = new Produit();
            produit.setId(resultSet.getInt("id"));
            produit.setNom_produit(resultSet.getString("nom_produit"));
            produit.setDescription(resultSet.getString("description"));
            produit.setQuantite(resultSet.getInt("quantite"));
            produit.setPrix_produit(resultSet.getFloat("prix_produit"));
            produit.setImage(resultSet.getString("image"));
            produit.setCategorie_produit_id(resultSet.getInt("categorie_produit_id"));
            produit.setPrix_point_produit(resultSet.getInt("prix_point_produit"));

            productList.add(produit);
        }
        preparedStatement.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return productList;
  }


  public void AddProductOffer(Produit produit){
    try {
      String req = "UPDATE `produit` SET `remise`=? WHERE id=? ";
      PreparedStatement ps = conx.prepareStatement(req);
        ps.setFloat(1, produit.getRemise());
        ps.setInt(2, produit.getId());
        ps.executeUpdate();
        System.out.println("Offer added successfully");
        ps.close();
    }catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la creation d'une offre : " + e.getMessage());
    }      
  }

  
}


