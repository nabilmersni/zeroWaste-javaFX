package services;

import entities.Achats;
import entities.Coupon;
import entities.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.internal.DownloadAccountResponse.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utils.MyDB;

public class AchatsService implements IAchatsService {

  public Connection conx;
  public Statement stm;

  public AchatsService() {
    conx = MyDB.getInstance().getConx();
  }

  public List<Achats> getAllAchats() {
    List<Achats> achatList = new ArrayList<>();
    try {
      String query = "SELECT * FROM achats WHERE adminDeleted = 0 AND validate = 1 ORDER BY date_achat DESC  ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Achats achat = new Achats();
        achat.setId(resultSet.getInt("id"));
        achat.setFull_name(resultSet.getString("full_name"));
        achat.setAddress(resultSet.getString("address"));
        achat.setEmail(resultSet.getString("email"));
        achat.setCity(resultSet.getString("city"));
        achat.setTel(resultSet.getInt("tel"));
        achat.setCommande_id(resultSet.getInt("commande_id"));

        achat.setDate_achat(resultSet.getString("date_achat"));

        achat.setStatus(resultSet.getInt("status"));
        achatList.add(achat);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return achatList;

  }

  public void adminDeleteAchat(int achat_id) {
    try {
      String req = "UPDATE `achats` SET `adminDeleted`=1 WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, achat_id);
      ps.executeUpdate();
      System.out.println("Achat deleted successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la supression de l'achat : " + e.getMessage());
    }
  }

  public Achats getOneAchat(int idAchat) throws SQLException {
    String req = "SELECT * FROM `achats` where id = ?";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, idAchat);

    ResultSet rs = ps.executeQuery();
    Achats achats = new Achats();

    while (rs.next()) {
      achats.setId(rs.getInt("id"));
      achats.setFull_name(rs.getString("full_name"));
      achats.setEmail(rs.getString("email"));
      achats.setTel(rs.getInt("tel"));
      achats.setAddress(rs.getString("address"));
      achats.setCity(rs.getString("city"));
      achats.setZip_code(rs.getInt("zip_code"));
      achats.setCommande_id(rs.getInt("commande_id"));
      achats.setPayment_method(rs.getString("payment_method"));

    }
    ps.close();
    return achats;
  }

  public List<Produit> getAllProducts(int commande_id) {
    List<Produit> produitList = new ArrayList<>();
    try {
      String query = "SELECT * FROM produit p JOIN commands_produit c ON p.id = c.produit_id WHERE c.commande_id=? ";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setInt(1, commande_id);
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Produit produit = new Produit();

        produit.setId(resultSet.getInt("id"));
        produit.setNom_produit(resultSet.getString("nom_produit"));
        produit.setQuantite(resultSet.getInt("quantite_c"));
        produit.setPrix_produit(resultSet.getFloat("prix_produit"));
        produit.setImage(resultSet.getString("image"));
        produit.setCategorie_produit_id(resultSet.getInt("categorie_produit_id"));
        produit.setPrix_point_produit(resultSet.getInt("prix_point_produit"));
        produit.setRemise(resultSet.getInt("remise"));
        produitList.add(produit);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return produitList;

  }

  public List<Achats> searchCommands(String search) {
    List<Achats> achatList = new ArrayList<>();
    try {
      String query = "SELECT * FROM achats WHERE (adminDeleted = 0 AND validate = 1) AND (full_name LIKE ? OR city LIKE ? OR email LIKE ? OR address LIKE ? OR tel LIKE ?)";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      preparedStatement.setString(1, "%" + search + "%");
      preparedStatement.setString(2, "%" + search + "%");
      preparedStatement.setString(3, "%" + search + "%");
      preparedStatement.setString(4, "%" + search + "%");
      preparedStatement.setString(5, "%" + search + "%");
      ResultSet resultSet = preparedStatement.executeQuery();

      // Parcours du résultat de la requête
      while (resultSet.next()) {
        Achats achats = new Achats();
        achats.setId(resultSet.getInt("id"));
        achats.setFull_name(resultSet.getString("full_name"));
        achats.setEmail(resultSet.getString("email"));
        achats.setTel(resultSet.getInt("tel"));
        achats.setAddress(resultSet.getString("address"));
        achats.setCity(resultSet.getString("city"));
        achats.setZip_code(resultSet.getInt("zip_code"));
        achats.setCommande_id(resultSet.getInt("commande_id"));
        achats.setDate_achat(resultSet.getString("date_achat"));
        achats.setStatus(resultSet.getInt("status"));

        achatList.add(achats);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return achatList;
  }

  public List<Achats> getAchatsToday() {
    List<Achats> achatsList = new ArrayList<>();
    try {
      String query = "SELECT * FROM achats WHERE adminDeleted = 0 AND validate = 1 AND date_achat >= ? ORDER BY date_achat DESC";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      java.sql.Date today = new java.sql.Date(new java.util.Date().getTime()); // Utiliser la date actuelle
      preparedStatement.setDate(1, today);
      System.out.println("Today date: " + today);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Achats achat = new Achats();
        achat.setId(resultSet.getInt("id"));
        achat.setFull_name(resultSet.getString("full_name"));
        achat.setAddress(resultSet.getString("address"));
        achat.setEmail(resultSet.getString("email"));
        achat.setCity(resultSet.getString("city"));
        achat.setTel(resultSet.getInt("tel"));
        achat.setCommande_id(resultSet.getInt("commande_id"));
        achat.setDate_achat(resultSet.getString("date_achat"));
        achat.setStatus(resultSet.getInt("status"));
        achatsList.add(achat);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return achatsList;
  }

  public List<Achats> getAchatsLastWeek() {
    List<Achats> achatsList = new ArrayList<>();
    try {
      String query = "SELECT * FROM achats WHERE adminDeleted = 0 AND validate = 1 AND date_achat BETWEEN ? AND ? ORDER BY date_achat DESC";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      LocalDate now = LocalDate.now();
      preparedStatement.setString(1, now.minusDays(7).toString()); // Utiliser la date il y a 7 jours
      preparedStatement.setString(2, now.toString()); // Utiliser la date actuelle
      System.out.println("last week deb: " + now.minusDays(7).toString());
      System.out.println("last week end: " + now.toString());
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Achats achat = new Achats();
        achat.setId(resultSet.getInt("id"));
        achat.setFull_name(resultSet.getString("full_name"));
        achat.setAddress(resultSet.getString("address"));
        achat.setEmail(resultSet.getString("email"));
        achat.setCity(resultSet.getString("city"));
        achat.setTel(resultSet.getInt("tel"));
        achat.setCommande_id(resultSet.getInt("commande_id"));
        achat.setDate_achat(resultSet.getString("date_achat"));
        achat.setStatus(resultSet.getInt("status"));
        achatsList.add(achat);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return achatsList;
  }

  public List<Achats> getAchatsLastMonth() {
    List<Achats> achatsList = new ArrayList<>();
    try {
      String query = "SELECT * FROM achats WHERE adminDeleted = 0 AND validate = 1 AND date_achat BETWEEN ? AND ? ORDER BY date_achat DESC";
      PreparedStatement preparedStatement = conx.prepareStatement(query);
      LocalDate now = LocalDate.now();
      LocalDate startOfMonth = now.withDayOfMonth(1); // Le début du mois en cours
      LocalDate startOfLastMonth = startOfMonth.minusMonths(1); // Le début du mois précédent
      LocalDate endOfLastMonth = startOfMonth.minusDays(1); // La fin du mois précédent
      preparedStatement.setString(1, startOfLastMonth.toString()); // Utiliser la date du début du mois précédent
      preparedStatement.setString(2, endOfLastMonth.toString()); // Utiliser la date de la fin du mois précédent
      System.out.println("last month deb: " + startOfLastMonth.toString());
      System.out.println("last month end: " + endOfLastMonth.toString());
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Achats achat = new Achats();
        achat.setId(resultSet.getInt("id"));
        achat.setFull_name(resultSet.getString("full_name"));
        achat.setAddress(resultSet.getString("address"));
        achat.setEmail(resultSet.getString("email"));
        achat.setCity(resultSet.getString("city"));
        achat.setTel(resultSet.getInt("tel"));
        achat.setCommande_id(resultSet.getInt("commande_id"));
        achat.setDate_achat(resultSet.getString("date_achat"));
        achat.setStatus(resultSet.getInt("status"));
        achatsList.add(achat);
      }
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return achatsList;
  }

  //
  public void Checkout(Achats achat) {
    try {
      String req = "INSERT INTO `achats`(`commande_id`, `date_achat`, `full_name`, `email`, `address`, `tel`, `city`, `zip_code`, `validate`) VALUES (?,?,?,?,?,?,?,?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, achat.getCommande_id());

      ps.setString(3, achat.getFull_name());
      ps.setString(4, achat.getEmail());
      ps.setString(5, achat.getAddress());
      ps.setInt(6, achat.getTel());
      ps.setString(7, achat.getCity());
      ps.setInt(8, achat.getZip_code());
      ps.setInt(9, 0);

      LocalDateTime currentDateTime = LocalDateTime.now();
      java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(currentDateTime);
      ps.setTimestamp(2, sqlTimestamp);
      System.out.println("sqlTimestamp" + sqlTimestamp);

      ps.executeUpdate();
      System.out.println("checkout approved");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du achat : " + e.getMessage());
    }

  }

  public void supprimerAddress(int achat_id) throws SQLException {
    String sql = "DELETE FROM achats WHERE id = ?";
    try (PreparedStatement pstmt = conx.prepareStatement(sql)) {
      pstmt.setInt(1, achat_id);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  public Achats getAddressDetails(int commande_id) throws SQLException {
    String req = "SELECT * FROM `achats` where commande_id = ?";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, commande_id);

    ResultSet rs = ps.executeQuery();
    Achats achats = null;

    while (rs.next()) {
      achats = new Achats();
      achats.setId(rs.getInt("id"));
      achats.setFull_name(rs.getString("full_name"));
      achats.setEmail(rs.getString("email"));
      achats.setTel(rs.getInt("tel"));
      achats.setAddress(rs.getString("address"));
      achats.setCity(rs.getString("city"));
      achats.setZip_code(rs.getInt("zip_code"));
      achats.setCommande_id(rs.getInt("commande_id"));
      achats.setDate_achat(rs.getString("date_achat"));
      achats.setPayment_method(rs.getString("payment_method"));

    }
    ps.close();
    return achats;
  }

  public void updateCheckout(Achats achat) {
    try {
      String req = "UPDATE `achats` SET `full_name`=?,`email`=?,`address`=?,`tel`=?,`city`=?,`zip_code`=?  WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setString(1, achat.getFull_name());
      ps.setString(2, achat.getEmail());
      ps.setString(3, achat.getAddress());
      ps.setInt(4, achat.getTel());
      ps.setString(5, achat.getCity());
      ps.setInt(6, achat.getZip_code());
      ps.setInt(7, achat.getId());

      ps.executeUpdate();
      System.out.println("checkout address updated successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération de l'achat : " + e.getMessage());
    }
  }

  public void updatePaymentMethod(int test, int achatId, String PaymentMethod) {// 1: set method name (update)*** 2: set
                                                                                // null (delete)
    try {
      String req = "UPDATE `achats` SET `payment_method`=?  WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
      if (test == 1) {
        ps.setString(1, PaymentMethod);
      } else {
        ps.setString(1, null);
      }

      ps.setInt(2, achatId);

      ps.executeUpdate();
      System.out.println("payment method updated successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération de l'achat : " + e.getMessage());
    }

  }

  // validate by points
  public void ValidateCheckoutPoints(int command_id, int achatId, int user_id, int point) {
    try {
      String req = "UPDATE `commands` SET `status`=1  WHERE id=?";
      String req2 = "UPDATE `achats` SET `validate`=1, `date_achat`=?  WHERE id=?";
      String req3 = "UPDATE `user` SET `point`=?  WHERE id=?";

      PreparedStatement ps = conx.prepareStatement(req);
      PreparedStatement ps2 = conx.prepareStatement(req2);
      PreparedStatement ps3 = conx.prepareStatement(req3);

      ps.setInt(1, command_id);

      ps2.setInt(2, achatId);
      LocalDateTime currentDateTime = LocalDateTime.now();
      java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(currentDateTime);
      ps2.setTimestamp(1, sqlTimestamp);
      System.out.println("sqlTimestamp" + sqlTimestamp);

      ps3.setInt(1, point);
      ps3.setInt(2, user_id);

      ps.executeUpdate();
      ps2.executeUpdate();
      ps3.executeUpdate();

      System.out.println("checkout method with points done");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération de l'achat : " + e.getMessage());
    }
  }

  // validate by Livraison
  public void ValidateCheckoutLivraison(int command_id, int achatId) {
    try {
      String req = "UPDATE `commands` SET `status`=1  WHERE id=?";
      String req2 = "UPDATE `achats` SET `validate`=1  WHERE id=?";

      PreparedStatement ps = conx.prepareStatement(req);
      PreparedStatement ps2 = conx.prepareStatement(req2);

      ps.setInt(1, command_id);

      ps2.setInt(1, achatId);

      ps.executeUpdate();
      ps2.executeUpdate();

      System.out.println("checkout method with Livraison done");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération de l'achat : " + e.getMessage());
    }
  }

  // coupon

  public void addCoupon(int coupon_code, int produit_id, String email) {
    try {
      String req = "INSERT INTO `coupon`(`coupon_code`,`produit_id`,`email`) VALUES (?,?,?)";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, coupon_code);
      ps.setInt(2, produit_id);
      ps.setString(3, email);

      ps.executeUpdate();
      System.out.println("Coupon added successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du coupon : " + e.getMessage());
    }

  }

  // verif Coupon
  public int VerifCoupon(int coupon_code) throws SQLException {
    String req = "SELECT * FROM `coupon` where coupon_code = ?";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, coupon_code);

    ResultSet rs = ps.executeQuery();
    int found = 0;
    if (rs.next()) {
      found = 1;
    }
    ps.close();
    return found;
  }

  public int VerifUserCoupon(int coupon_code, String email) throws SQLException {
    String req = "SELECT * FROM `coupon` where coupon_code = ? AND email = ? AND status=0 ";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, coupon_code);
    ps.setString(2, email);

    ResultSet rs = ps.executeQuery();
    int found = 0;
    if (rs.next()) {
      found = 1;
    }
    ps.close();
    return found;
  }

  public void updateStatusCoupon(int coupon_code, String email) {
    try {
      String req = "UPDATE `coupon` SET `status`=1  where coupon_code = ? AND email = ? ";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, coupon_code);
      ps.setString(2, email);

      ps.executeUpdate();
      System.out.println("status updated successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération du coupon : " + e.getMessage());
    }

  }

  public Coupon getOneCoupon(int coupon_code) throws SQLException {
    String req = "SELECT * FROM `coupon` where coupon_code = ?";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, coupon_code);

    ResultSet rs = ps.executeQuery();
    Coupon coupon = new Coupon();

    while (rs.next()) {
      coupon.setId(rs.getInt("id"));
      coupon.setProduit_id(rs.getInt("produit_id"));
      coupon.setStatus(rs.getInt("status"));
      coupon.setEmail(rs.getString("email"));

    }
    ps.close();
    return coupon;
  }

  public void updateCommandStatus(int achatId, int etat) {
    try {
      String req = "UPDATE `achats` SET `status`=?  where id = ?  ";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, etat);
      ps.setInt(2, achatId);

      ps.executeUpdate();
      System.out.println("status updated successfully");
      ps.close();
    } catch (SQLException e) {
      System.out.println("Une erreur s'est produite lors de la récupération de l'achats : " + e.getMessage());
    }

  }

}
