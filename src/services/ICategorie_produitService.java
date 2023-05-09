package services;

import java.sql.SQLException;
import java.util.List;

import entities.Categorie_Collecte;



public interface ICategorie_produitService {

  public List<Categorie_Collecte> getAllCategories();

  public void supprimer(int idCategory) throws SQLException;

  public void ajouter(Categorie_Collecte Category) ;

  public Categorie_Collecte getOneCategory(int idCategory) throws SQLException;

  public void updateCategory(Categorie_Collecte category) ;

  public int categoryExists(String categoryName); 
  
}
