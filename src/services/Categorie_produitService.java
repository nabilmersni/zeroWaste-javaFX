package services;

import entities.Categorie_Collecte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.MyDB;

public class Categorie_produitService implements ICategorie_produitService {
  public Connection conx;
  public Statement stm;

  public Categorie_produitService() {
    conx = MyDB.getInstance().getConx();
  }

  
  @Override
  public List<Categorie_Collecte> getAllCategories()  {
    List<Categorie_Collecte> categoriesList = new ArrayList<>();
    try {
        String query = "SELECT * FROM categorie_produit ";
        PreparedStatement preparedStatement = conx.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
  
        // Parcours du résultat de la requête
        while (resultSet.next()) {
          Categorie_Collecte category = new Categorie_Collecte();
            category.setId(resultSet.getInt("id"));
            category.setNom_categorie(resultSet.getString("nom_categorie"));
            category.setImage_categorie(resultSet.getString("image_categorie"));
      
            categoriesList.add(category);
        }
        preparedStatement.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return categoriesList;
  
  }

  @Override
  public void supprimer(int idCategory) throws SQLException {
      String sql = "DELETE FROM categorie_produit WHERE id = ?";
      try (PreparedStatement pstmt = conx.prepareStatement(sql)) {
          pstmt.setInt(1, idCategory);
          pstmt.executeUpdate();
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }
  }

  @Override
  public void ajouter(Categorie_Collecte category) {
    try {
      String req = "INSERT INTO `categorie_produit`(`nom_categorie`, `image_categorie`) VALUES (?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
        ps.setString(1, category.getNom_categorie());
        ps.setString(2, category.getImage_categorie());
        ps.executeUpdate();
        System.out.println("Product added successfully");
        ps.close();
    }catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération de la catégorie : " + e.getMessage());
    }
    
  }


  public Categorie_Collecte getOneCategory(int idCategory) throws SQLException {
    String req = "SELECT * FROM `categorie_produit` where id = ?";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, idCategory);

    ResultSet rs = ps.executeQuery();
    Categorie_Collecte category = new Categorie_Collecte() ;
    
    while (rs.next()) {
        category.setId(rs.getInt("id"));
        category.setNom_categorie(rs.getString("nom_categorie"));
        category.setImage_categorie(rs.getString("image_categorie"));
    }
    ps.close();
    return category;
  }


  @Override
  public void updateCategory(Categorie_Collecte category) {
    try {
      String req = "UPDATE `categorie_produit` SET `nom_categorie`=?,`image_categorie`=? WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
        ps.setString(1, category.getNom_categorie());
        ps.setString(2, category.getImage_categorie());
        ps.setInt(3, category.getId());
        ps.executeUpdate();
        System.out.println("Category updated successfully");
        ps.close();
    }catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du categorie : " + e.getMessage());
    }
    
  }

  @Override
  public int categoryExists(String categoryName) {
      int result = 0;
      try {
          String query = "SELECT COUNT(*) FROM categorie_produit WHERE nom_categorie=?";
          PreparedStatement preparedStatement = conx.prepareStatement(query);
          preparedStatement.setString(1, categoryName);
          ResultSet resultSet = preparedStatement.executeQuery();
          
          if(resultSet.next()) {
              result = resultSet.getInt(1);
          }
          
          preparedStatement.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
      return result;
  }


  
}
