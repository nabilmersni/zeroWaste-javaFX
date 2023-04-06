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

  public List<Produit> searchProducts(String search);

  public List<Produit> sortProducts(int value, int idCategory); //0: sort by stock *** 1: sort by category *** 2: sort by stack and category

}
