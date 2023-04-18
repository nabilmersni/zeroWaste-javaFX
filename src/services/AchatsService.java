package services;

import entities.Achats;
import entities.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utils.MyDB;

public class AchatsService implements IAchatsService {
  
  public Connection conx;
  public Statement stm;

  public AchatsService() {
    conx = MyDB.getInstance().getConx();
  }

  
  
  public List<Achats> getAllAchats()  {
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
        
            achatList.add(achat);
        }
        preparedStatement.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return achatList;
  
  }


  public void adminDeleteAchat(int achat_id){
    try {
      String req = "UPDATE `achats` SET `adminDeleted`=1 WHERE id=?";
      PreparedStatement ps = conx.prepareStatement(req);
        ps.setInt(1, achat_id);
        ps.executeUpdate();
        System.out.println("Achat deleted successfully");
        ps.close();
    }catch (SQLException e) {
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

    }
    ps.close();
    return achats;
  }

  public List<Produit> getAllProducts( int commande_id ){
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
            produitList.add(produit);
        }
        preparedStatement.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return produitList;
  
  }

  public List<Achats> searchCommands(String search){
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
System.out.println("sqlTimestamp"+sqlTimestamp);


      ps.executeUpdate();
      System.out.println("checkout approved");
      ps.close();
  }catch (SQLException e) {
    System.out.println("Une erreur s'est produite lors de la récupération du achat : " + e.getMessage());
  }
  

}

public void supprimerAddress(int achat_Id) throws SQLException {
  String sql = "DELETE FROM achats WHERE commande_id = ?";
  try (PreparedStatement pstmt = conx.prepareStatement(sql)) {
      pstmt.setInt(1, 24);
      pstmt.executeUpdate();
  } catch (SQLException e) {
      System.out.println(e.getMessage());
  }

}
  


}


