package services;

import java.sql.SQLException;
import java.util.List;

import entities.Notification;
import entities.Collecte;
import entities.Reviews;

public interface IProduitService {

  public List<Collecte> getAllProducts();

  public void ajouter(Collecte produit);

  public void supprimer(int idProduit) throws SQLException;

  public String getCategory(int idCategory);

  public Collecte getOneProduct(int idProduct) throws SQLException;

  public void updateProduct(Collecte produit);

 public List<Collecte> searchProducts(String search);

  public List<Collecte> sortProducts(int value, int idCategory); // 0: sort by stock *** 1: sort by category *** 2: sort
                                                                // by stock and category

  public void AddProductOffer(Collecte produit);

  public List<Collecte> sortProductsUser(String sortBy, String comboBoxData);

  public List<Collecte> getPromotionalProducts();

  public List<Collecte> searchProductByImage(String etiquette, double score);

  public void addProductToFavoriteList(int produitId, int userId);

  public int getTotalProductReviews(int productId);

  public int getTotalProductReviewsByStar(int productId, int value);

  public List<Reviews> getAllComments(int produitId);

  public List<Collecte> getProductFavList(int userId);

  public int productInFavList(int productID, int userId) throws SQLException;

  public void removeProductFromFavoriteList(int idProduit, int userID) throws SQLException;

  public List<Collecte> getProductAchete(int userId);

  public void addReview(Reviews review);

  public void supprimerReview(int idReview) throws SQLException;

  public Reviews getOneUserReview(int userId, int productId);

  public List<Notification> getUserNotifications(int userId);

  public void MakeAsReadNotif(int userId, int notifId);

  public void AddNewNotif(Notification notif);

  public int getTotalNotif(int userId);

}
