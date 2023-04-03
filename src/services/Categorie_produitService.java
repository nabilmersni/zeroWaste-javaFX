package services;

import entities.Categorie_produit;

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
  public List<Categorie_produit> getAllCategories()  {
    List<Categorie_produit> categoriesList = new ArrayList<>();
    try {
        String query = "SELECT * FROM categorie_produit ";
        PreparedStatement preparedStatement = conx.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
  
        // Parcours du résultat de la requête
        while (resultSet.next()) {
          Categorie_produit category = new Categorie_produit();
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

  
}
