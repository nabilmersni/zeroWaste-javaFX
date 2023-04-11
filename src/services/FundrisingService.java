package services;

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


public class FundrisingService implements IFundrisingService  {
  
  
    public Connection conx;
    public Statement stm;
  
    public FundrisingService() {
      conx = MyDB.getInstance().getConx();
    }


    @Override
    public List<Fundrising> getAllFunds()  {
      List<Fundrising> fundstList = new ArrayList<>();
      try {
          String query = "SELECT * FROM fundrising ORDER BY id DESC ";
          PreparedStatement preparedStatement = conx.prepareStatement(query);
          ResultSet resultSet = preparedStatement.executeQuery();
    
          // Parcours du résultat de la requête
          while (resultSet.next()) {
              Fundrising funds = new Fundrising();
              funds.setId(resultSet.getInt("id"));
              funds.setTitre_don(resultSet.getString("titre_don"));
              funds.setDescription_don(resultSet.getString("description_don"));
              funds.setImage(resultSet.getString("image_don"));
              funds.setDate_don(resultSet.getDate("date_don"));
              funds.setDate_don_limite(resultSet.getDate("date_don_limite"));
              funds.setEtat(resultSet.getString("etat"));
           
              funds.setObjectif(resultSet.getInt("objectif"));
            //  funds.setTotal(resultSet.getInt("total"));
    
              fundstList.add(funds);
          }
          preparedStatement.close();
  
      } catch (SQLException e) {
          e.printStackTrace();
      }
      
      return fundstList;
    
    }

    public Fundrising getOneFund(int idFund) throws SQLException {
      String req = "SELECT * FROM `fundrising` where id = ?";
      PreparedStatement ps = conx.prepareStatement(req);
      ps.setInt(1, idFund);
  
      ResultSet rs = ps.executeQuery();
      Fundrising fundrising = new Fundrising();
      
      while (rs.next()) {
        fundrising.setId(rs.getInt("id"));
        fundrising.setTitre_don(rs.getString("titre_don"));
        fundrising.setDescription_don(rs.getString("description_don"));
        fundrising.setObjectif(rs.getFloat("objectif"));
        fundrising.setDate_don(rs.getDate("date_don"));
        fundrising.setDate_don_limite(rs.getDate("date_don_limite"));
      }
      ps.close();
      return fundrising;
    }
  
  
    @Override
    public void supprimerFunds(int fundId) {
      try {
          conx = MyDB.getInstance().getConx();
          String query = "DELETE FROM fundrising WHERE id=?";
          PreparedStatement preparedStmt = conx.prepareStatement(query);
          preparedStmt.setInt(1, fundId);
          preparedStmt.executeUpdate();
          System.out.println("Fund with ID " + fundId + " deleted successfully");
      } catch (SQLException e) {
          System.out.println("Error deleting fund with ID " + fundId);
          e.printStackTrace();
      }
  }
  
  
    @Override
    public void ajouterFunds(Fundrising funds) {
      try {
        String req = "INSERT INTO `fundrising`(`titre_don`, `description_don`, `image_don`, `date_don`, `date_don_limite`, `etat`, `objectif` , `total`) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setString(1, funds.getTitre_don());
          ps.setString(2, funds.getDescription_don());
          ps.setString(3, "zzz");
          ps.setDate(4, funds.getDate_don());
          ps.setDate(5, funds.getDate_don_limite());     
          ps.setString(6, funds.getEtat());
          ps.setFloat(7, funds.getObjectif());
          ps.setFloat(8,  funds.getTotal());
          ps.executeUpdate();
          System.out.println("fundraising added successfully");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de lajout de la dons : " + e.getMessage());
      }
      
    }

    @Override
    public void updateFunds(Fundrising funds) {
      try {
        String req = "UPDATE `fundrising` SET `titre_don`=?,`description_don`=?,`image_don`=?,`date_don`=?,`date_don_limite`=?,`etat`=?,`objectif`=? WHERE id=?";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setString(1, funds.getTitre_don());
          ps.setString(2, funds.getDescription_don());
          ps.setString(3, "zzz");
          ps.setDate(4, funds.getDate_don());
          ps.setDate(5, funds.getDate_don_limite());
          ps.setString(6, funds.getEtat());
          ps.setFloat(7, funds.getObjectif());
          ps.setInt(8, funds.getId());
          ps.executeUpdate();
          System.out.println("funds updated successfully");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de la modification du fund : " + e.getMessage());
      }
      
    }
    
}
