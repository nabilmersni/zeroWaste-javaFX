
package services;

import entities.Commands;
import entities.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.MyDB;


public class CommandsService implements ICommandsService  {
    public Connection conx;
    public Statement stm;
  
    public CommandsService() {
      conx = MyDB.getInstance().getConx();
    }  

     public List<Commands> getAllCommands() {
      List<Commands> commandsList = new ArrayList<>();
      try{
        conx = MyDB.getInstance().getConx();
        String sql = "SELECT * FROM commands";
        Statement querry = conx.createStatement();
        ResultSet res = querry.executeQuery(sql);
        while(res.next()){
          Commands c = new Commands(res.getInt("id"),res.getInt("user_id"),res.getInt("status"));
          commandsList.add(c);
        }
      } catch(SQLException e){
        System.out.println(e.getMessage());
      }
      return commandsList;
  }

     @Override
    public void ajouter(Commands commands) {
       
      try {
        String req = "INSERT INTO commands`(id`, user_id, status) VALUES (?,?,?)";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setInt(1, 13);
          ps.setInt(2, 13);
         ps.setInt(3, 13);
         
          ps.executeUpdate();
          System.out.println("Command added successfully");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de la récupération de la commande : " + e.getMessage());
      }
    }

    @Override
    public void supprimer(int Id) {
      try {
          conx = MyDB.getInstance().getConx();
          String query = "DELETE FROM commands WHERE id=?";
          PreparedStatement preparedStmt = conx.prepareStatement(query);
          preparedStmt.setInt(1, Id);
          preparedStmt.executeUpdate();
          System.out.println("command with ID " + Id + " deleted successfully");
      } catch (SQLException e) {
          System.out.println("Error deleting command with ID " + Id);
          e.printStackTrace();
      }
  }

    public void modifier(int Id, Commands t) {
        try {
          conx = MyDB.getInstance().getConx();
            String query="UPDATE commands SET "
                    + "`user_id`='"+t.getUser_id()+"',"
                    + "`status`='"+t.getStatus()+"',";
            Statement st=conx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(CommandsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
}
