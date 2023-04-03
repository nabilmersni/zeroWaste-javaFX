package services;

import java.sql.SQLException;
import java.util.List;

import entities.Categorie_produit;



public interface ICategorie_produitService {

  public List<Categorie_produit> getAllCategories();
  
}
