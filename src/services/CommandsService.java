package services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.Commands;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.MyDB;

public class CommandsService implements ICommandsService{
    public Connection conx;
    public Statement stm;
  
    public CommandsService() {
      conx = MyDB.getInstance().getConx();
    }
  
    
    public Commands getOneCommand(int userId)  {
        Commands commands = null;

        try {
        String req = "SELECT * FROM `commands` where user_id = ? and status=0";
        PreparedStatement ps = conx.prepareStatement(req);
        ps.setInt(1, userId);
    
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            commands = new Commands();
            commands.setId(rs.getInt("id"));
            commands.setStatus(rs.getInt("status"));
            commands.setUser_id(rs.getInt("user_id"));
    
        }
        ps.close();
       
          }catch (SQLException e) {
              System.out.println("Une erreur s'est produite lors de la récupération du commande : " + e.getMessage());
            }
            return commands;
      }

        public void addNewCommands(int userId) {
            try {
              String req = "INSERT INTO `commands`(`user_id`, `status`) VALUES (?,?)";
              PreparedStatement ps = conx.prepareStatement(req);
                ps.setInt(1, userId);
                ps.setInt(2, 0);
                
                ps.executeUpdate();
                System.out.println("Command added successfully");
                ps.close();
            }catch (SQLException e) {
              System.out.println("Une erreur s'est produite lors de la récupération du commande : " + e.getMessage());
            }
            
          }

          
}
