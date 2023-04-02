package services;

import java.sql.SQLException;
import java.util.List;

import entities.Produit;

public interface IProduitService {

  public List<Produit> getAllProducts();
  
  public void ajouter(Produit produit) ;

  public void supprimer(int idProduit) throws SQLException;

  public String getCategory(int idCategory);
  
  public Produit getOneProduct(int idProduct) throws SQLException;
  
  public void updateProduct(Produit produit) ;
}
