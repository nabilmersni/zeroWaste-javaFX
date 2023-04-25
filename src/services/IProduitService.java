package services;

import java.sql.SQLException;
import java.util.List;

import entities.Produit;
import entities.Reviews;

public interface IProduitService {

  public List<Produit> getAllProducts();

  public void ajouter(Produit produit);

  public void supprimer(int idProduit) throws SQLException;

  public String getCategory(int idCategory);

  public Produit getOneProduct(int idProduct) throws SQLException;

  public void updateProduct(Produit produit);

  public List<Produit> searchProducts(String search);

  public List<Produit> sortProducts(int value, int idCategory); // 0: sort by stock *** 1: sort by category *** 2: sort
                                                                // by stock and category

  public void AddProductOffer(Produit produit);

  public List<Produit> sortProductsUser(String sortBy, String comboBoxData);

  public List<Produit> getPromotionalProducts();

  public List<Produit> searchProductByImage(String etiquette, double score);

  public void addProductToFavoriteList(int produitId, int userId);

  public int getTotalProductReviews(int productId);

  public int getTotalProductReviewsByStar(int productId, int value);

  public List<Reviews> getAllComments(int produitId);
}
