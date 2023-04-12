package services;

import entities.DonHistory;
import entities.Fundrising;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import utils.MyDB;


public class DonHistoryService implements IDonHistoryService  {
  
  
    public Connection conx;
    public Statement stm;
  
    public DonHistoryService() {
      conx = MyDB.getInstance().getConx();
    }


    @Override
    public List<DonHistory> getAllHistories()  {
      List<DonHistory> donHistoryList = new ArrayList<>();
      try {
          String query = "SELECT * FROM don_history ORDER BY id DESC ";
          PreparedStatement preparedStatement = conx.prepareStatement(query);
          ResultSet resultSet = preparedStatement.executeQuery();
    
          // Parcours du résultat de la requête
          while (resultSet.next()) {
            DonHistory donHistory = new DonHistory();
              donHistory.setId(resultSet.getInt("id"));
              donHistory.setUserId(resultSet.getInt("user_id_id"));
              donHistory.setFundId(resultSet.getInt("funds_id_id"));
              donHistory.setComment(resultSet.getString("comment"));
              donHistory.setDonationPrice(resultSet.getFloat("donation_price"));
              donHistory.setDateDonation(resultSet.getDate("date_donation"));
    
              donHistoryList.add(donHistory);
          }
          preparedStatement.close();
  
      } catch (SQLException e) {
          e.printStackTrace();
      }
      
      return donHistoryList;
    
    }

    public DonHistory getOneHistory(int idHistory) throws SQLException {
      String req = "SELECT * FROM `don_history` where id = ?";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, idHistory);
  
      ResultSet rs = ps.executeQuery();
      DonHistory donHistory = new DonHistory();
      
      while (rs.next()) {
        donHistory.setId(rs.getInt("id"));
        donHistory.setUserId(rs.getInt("user_id_id"));
        donHistory.setFundId(rs.getInt("funds_id_id"));
        donHistory.setComment(rs.getString("comment"));
        donHistory.setDonationPrice(rs.getFloat("donation_price"));
        donHistory.setDateDonation(rs.getDate("date_donation"));
      }
      ps.close();
      return donHistory;
    }
  
  
    @Override
    public void deleteHistory(int historyId) {
      try {
          conx = MyDB.getInstance().getConx();
          String query = "DELETE FROM don_history WHERE id=?";
          PreparedStatement preparedStmt = conx.prepareStatement(query);
          preparedStmt.setInt(1, historyId);
          preparedStmt.executeUpdate();
          System.out.println("History with ID " + historyId + " deleted successfully");
      } catch (SQLException e) {
          System.out.println("Error deleting history with ID " + historyId);
          e.printStackTrace();
      }
  }
  
  
    @Override
    public void addHistory(DonHistory donHistory) {
      try {
        String req = "INSERT INTO `don_history`(`user_id_id`, `funds_id_id`, `comment`, `donation_price`, `date_donation`) VALUES (?,?,?,?,?)";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setInt(1, donHistory.getUserId());
          ps.setInt(2, donHistory.getFundId());
          ps.setString(3, donHistory.getComment());
          ps.setFloat(4, donHistory.getDonationPrice());
          ps.setDate(5, donHistory.getDateDonation());     
          ps.executeUpdate();
          System.out.println("History added successfully");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de lajout du history : " + e.getMessage());
      }
      
    }

    @Override
    public void updateHistory(DonHistory donHistory) {
      try {
        String req = "UPDATE `don_history` SET `user_id_id=?,`funds_id_id`=?,`comment`=?,`donation_price`=?,`date_donation`=? WHERE id=?";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setInt(1, donHistory.getUserId());
          ps.setInt(2, donHistory.getFundId());
          ps.setString(3, donHistory.getComment());
          ps.setFloat(4, donHistory.getDonationPrice());
          ps.setDate(5, donHistory.getDateDonation());  
          ps.setInt(6, donHistory.getId());
          ps.executeUpdate();
          System.out.println("history updated successfully");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de la modification du history : " + e.getMessage());
      }
      
    }
    
}
