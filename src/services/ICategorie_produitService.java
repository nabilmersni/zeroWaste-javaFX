package services;

import java.sql.SQLException;
import java.util.List;

import entities.Categorie_produit;



public interface ICategorie_produitService {

  public List<Categorie_produit> getAllCategories();

  public void supprimer(int idCategory) throws SQLException;

  public void ajouter(Categorie_produit Category) ;

  public Categorie_produit getOneCategory(int idCategory) throws SQLException;

  public void updateCategory(Categorie_produit category) ;

  public int categoryExists(String categoryName); 
  
}
