package services;

import entities.Collecte;
import entities.Reviews;
import entities.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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
  public List<Collecte> getAllProducts() {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "SELECT * FROM produit ORDER BY id DESC ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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
  public void ajouter(Collecte produit) {
    try {
      String req = "INSERT INTO `produit`(`nom_produit`, `description`, `quantite`, `prix_produit`, `image`, `categorie_produit_id`, `prix_point_produit`, `etiquette`, `score` ) VALUES (?,?,?,?,?,?,?,?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setString(1, produit.getNom_produit());
      ps.setString(2, produit.getDescription());
      ps.setInt(3, produit.getQuantite());
      ps.setFloat(4, produit.getPrix_produit());
      ps.setString(5, produit.getImage());
      ps.setInt(6, produit.getCategorie_produit_id());
      ps.setInt(7, produit.getPrix_point_produit());
      ps.setString(8, produit.getEtiquette());
      ps.setDouble(9, produit.getScore());
      ps.executeUpdate();
      System.out.println("Product added successfully");
      ps.close();
    } catch (SQLException e) {
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

  public Collecte getOneProduct(int idProduct) throws SQLException {
    String req = "SELECT * FROM `produit` where id = ?";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, idProduct);

    ResultSet rs = ps.executeQuery();
    Collecte produit = new Collecte();

    while (rs.next()) {
      produit.setId(rs.getInt("id"));
      produit.setNom_produit(rs.getString("nom_produit"));
      produit.setDescription(rs.getString("description"));
      produit.setQuantite(rs.getInt("quantite"));
      produit.setPrix_produit(rs.getFloat("prix_produit"));
      produit.setImage(rs.getString("image"));
      produit.setCategorie_produit_id(rs.getInt("categorie_produit_id"));
      produit.setPrix_point_produit(rs.getInt("prix_point_produit"));
      produit.setRemise(rs.getInt("remise"));
    }
    ps.close();
    return produit;
  }

  @Override
  public void updateProduct(Collecte produit) {
    try {
      String req = "UPDATE `produit` SET `nom_produit`=?,`description`=?,`quantite`=?,`prix_produit`=?,`image`=?,`categorie_produit_id`=?,`prix_point_produit`=? , `etiquette`=?, `score`=? WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setString(1, produit.getNom_produit());
      ps.setString(2, produit.getDescription());
      ps.setInt(3, produit.getQuantite());
      ps.setFloat(4, produit.getPrix_produit());
      ps.setString(5, produit.getImage());
      ps.setInt(6, produit.getCategorie_produit_id());
      ps.setInt(7, produit.getPrix_point_produit());
      ps.setString(8, produit.getEtiquette());
      ps.setDouble(9, produit.getScore());
      ps.setInt(10, produit.getId());
      ps.executeUpdate();
      System.out.println("Product updated successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du produit : " + e.getMessage());
    }

  }

  public List<Collecte> searchProducts(String search) {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "SELECT * FROM produit WHERE nom_produit LIKE ? OR description LIKE ? OR prix_produit LIKE ?";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setString(1, "%" + search + "%");
      preparedStatement.setString(2, "%" + search + "%");
      preparedStatement.setString(3, "%" + search + "%");
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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

  public List<Collecte> sortProducts(int value, int idCategory) {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "";
      PreparedStatement preparedStatement;

      if (value == 1 && idCategory == -1) { // sort by stock
        query = "SELECT * FROM produit ORDER BY quantite asc";
        preparedStatement = conx.prepareStatement(query);
      } else if (value == 0 && idCategory != 0) { // filter by category
        query = "SELECT * FROM produit where categorie_produit_id = ? ";
        preparedStatement = conx.prepareStatement(query);
        preparedStatement.setInt(1, idCategory);
      } else { // sort by stock and category
        query = "SELECT * FROM produit where categorie_produit_id = ? ORDER BY quantite asc";
        preparedStatement = conx.prepareStatement(query);
        preparedStatement.setInt(1, idCategory);
      }
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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

  public void AddProductOffer(Collecte produit) {
    try {
      String req = "UPDATE `produit` SET `remise`=? WHERE id=? ";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setFloat(1, produit.getRemise());
      ps.setInt(2, produit.getId());
      ps.executeUpdate();
      System.out.println("Offer added successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la creation d'une offre : " + e.getMessage());
    }
  }

  public List<Collecte> sortProductsUser(String sortBy, String comboBoxData) {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "";
      PreparedStatement preparedStatement;

      if (sortBy.equals("price")) {
        if (comboBoxData.equals("Price - Low To High")) {
          query = "SELECT * FROM produit ORDER BY prix_produit asc";
        } else if (comboBoxData.equals("Price - High To Low")) {
          query = "SELECT * FROM produit ORDER BY prix_produit desc";
        }
      } else if (sortBy.equals("points")) {
        if (comboBoxData.equals("Points - Low To High")) {
          query = "SELECT * FROM produit ORDER BY prix_point_produit asc";
        } else if (comboBoxData.equals("Points - High To Low")) {
          query = "SELECT * FROM produit ORDER BY prix_point_produit desc";
        }
      }

      preparedStatement = conx.prepareStatement(query);

      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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

  public List<Collecte> getPromotionalProducts() {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "SELECT * FROM produit WHERE remise != 0 ORDER BY id DESC  ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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

  public List<Collecte> searchProductByImage(String etiquette, double score) {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "SELECT * FROM produit WHERE etiquette=? and score=? ORDER BY id DESC ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setString(1, etiquette);
      preparedStatement.setDouble(2, score);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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

  public void addProductToFavoriteList(int produitId, int userId) {
    try {
      String req = "INSERT INTO `product_favoris`(`user_id`, `product_id` ) VALUES (?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, userId);
      ps.setInt(2, produitId);
      ps.executeUpdate();
      System.out.println("Product added to fav list successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de l'ajout' du produit au fav list : " + e.getMessage());
    }

  }

  public int getTotalProductReviews(int productId) {
    int total = 0;
    try {
      String query = "SELECT count(*) FROM reviews WHERE product_id = ? ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, productId);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        total = resultSet.getInt(1);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return total;
  }

  public int getTotalProductReviewsByStar(int productId, int value) {
    int total = 0;
    try {
      String query = "SELECT count(*) FROM reviews WHERE product_id = ? and value = ? ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, productId);
      preparedStatement.setInt(2, value);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        total = resultSet.getInt(1);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return total;
  }

  public List<Reviews> getAllComments(int productId) {
    List<Reviews> reviewList = new ArrayList<>();
    try {
      String query = "SELECT * FROM reviews WHERE product_id = ? ORDER BY id DESC ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, productId);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Reviews review = new Reviews();
        review.setId(resultSet.getInt("id"));
        review.setUser_id(resultSet.getInt("user_id"));
        review.setProduct_id(resultSet.getInt("product_id"));
        review.setTitle(resultSet.getString("title"));
        review.setComment(resultSet.getString("comment"));
        review.setValue(resultSet.getInt("value"));
        review.setDate_ajout(resultSet.getDate("date_ajout"));

        reviewList.add(review);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return reviewList;

  }

  public List<Collecte> getProductFavList(int userId) {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "SELECT * FROM produit p JOIN product_favoris f ON p.id = f.product_id WHERE f.user_id=? ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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

  public int productInFavList(int productID, int userId) throws SQLException {
    String req = "SELECT * FROM `product_favoris` where product_id = ? and user_id=? ";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, productID);
    ps.setInt(2, userId);

    ResultSet rs = ps.executeQuery();
    int found = 0;
    if (rs.next()) {
      found = 1;
    }
    ps.close();
    return found;
  }

  public void removeProductFromFavoriteList(int idProduit, int userID) throws SQLException {
    String sql = "DELETE FROM product_favoris WHERE product_id = ? and user_id=?";
    try (PreparedStatement pstmt = conx.prepareStatement(sql)) {
      pstmt.setInt(1, idProduit);
      pstmt.setInt(2, userID);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public List<Collecte> getProductAchete(int userId) {
    List<Collecte> productList = new ArrayList<>();
    try {
      String query = "SELECT DISTINCT p.* FROM produit p JOIN commands_produit cp ON p.id = cp.produit_id JOIN commands c On cp.commande_id = c.id JOIN achats a ON c.id = a.commande_id  WHERE c.user_id=? ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Collecte produit = new Collecte();
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

  public void addReview(Reviews review) {
    try {
      String req = "INSERT INTO `reviews`(`title`, `comment`, `user_id`, `product_id`, `value` ) VALUES (?,?,?,?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setString(1, review.getTitle());
      ps.setString(2, review.getComment());
      ps.setInt(3, review.getUser_id());
      ps.setInt(4, review.getProduct_id());
      ps.setInt(5, review.getValue());

      ps.executeUpdate();
      System.out.println("reviews added successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du review : " + e.getMessage());
    }

  }

  public void supprimerReview(int idReview) throws SQLException {
    String sql = "DELETE FROM reviews WHERE id = ?";
    try (PreparedStatement pstmt = conx.prepareStatement(sql)) {
      pstmt.setInt(1, idReview);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public Reviews getOneUserReview(int userId, int productId) {
    Reviews review = null;

    try {
      String req = "SELECT * FROM `reviews` where user_id = ? and product_id =? ";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, userId);
      ps.setInt(2, productId);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        review = new Reviews();
        review.setId(rs.getInt("id"));
        review.setTitle(rs.getString("title"));
        review.setComment(rs.getString("comment"));
        review.setValue(rs.getInt("value"));
      }
      ps.close();

    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du review : " + e.getMessage());
    }
    return review;
  }

  public void updateReview(Reviews review) {
    try {
      String req = "UPDATE `reviews` SET `title`=?,`comment`=?,`value`=? WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setString(1, review.getTitle());
      ps.setString(2, review.getComment());
      ps.setInt(3, review.getValue());
      ps.setInt(4, review.getId());

      ps.executeUpdate();
      System.out.println("Review updated successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du review : " + e.getMessage());
    }

  }

  public List<Notification> getUserNotifications(int userId) {
    List<Notification> notifList = new ArrayList<>();
    try {
      String query = "SELECT * FROM notification n WHERE NOT EXISTS " +
          "(SELECT * FROM user_notification un " +
          "WHERE un.notification_id = n.id AND un.user_id = ?)";

      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Notification notif = new Notification();
        notif.setId(resultSet.getInt("id"));
        notif.setProduct_id(resultSet.getInt("product_id"));
        notif.setContent(resultSet.getString("content"));
        notif.setDate(resultSet.getString("date"));

        notifList.add(notif);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return notifList;

  }

  public void MakeAsReadNotif(int userId, int notifId) {
    try {
      String req = "INSERT INTO `user_notification`(`user_id`, `notification_id` ) VALUES (?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, userId);
      ps.setInt(2, notifId);

      ps.executeUpdate();
      System.out.println("notif marked as read successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du notif : " + e.getMessage());
    }

  }

  public void AddNewNotif(Notification notif) {
    try {
      String req = "INSERT INTO `notification`(`product_id`, `content`, `date` ) VALUES (?,?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, notif.getProduct_id());
      ps.setString(2, notif.getContent());

      LocalDateTime currentDateTime = LocalDateTime.now();
      java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(currentDateTime);
      ps.setTimestamp(3, sqlTimestamp);

      ps.executeUpdate();
      System.out.println("notif added successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du notif : " + e.getMessage());
    }

  }

  public int getTotalNotif(int userId) {
    int total = 0;
    try {
      String query = "SELECT count(*) FROM notification n WHERE NOT EXISTS " +
          "(SELECT * FROM user_notification un " +
          "WHERE un.notification_id = n.id AND un.user_id = ?)";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        total = resultSet.getInt(1);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return total;
  }

}
