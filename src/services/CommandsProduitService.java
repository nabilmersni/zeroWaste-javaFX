package services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.Commands_produit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import utils.MyDB;

public class CommandsProduitService implements ICommandsProduitService{
    public Connection conx;
    public Statement stm;
  
    public CommandsProduitService() {
      conx = MyDB.getInstance().getConx();
    }
  
    public void addNewCommandsProduit(int commandeID, int produitID) {
      try {
        String req = "INSERT INTO `commands_produit`(`commande_id`, `produit_id`, `quantite_c` ) VALUES (?,?,?)";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setInt(1, commandeID);
          ps.setInt(2, produitID);
          ps.setInt(3, 1);
          
          ps.executeUpdate();
          System.out.println("produit added successfully");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de la récupération du produit : " + e.getMessage());
      }
    }

    public void CommandeDeleteProduct(int produit_id, int command_id){
      try {
        String req = "DELETE FROM `commands_produit` WHERE produit_id=? and commande_id=? ";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setInt(1, produit_id);
          ps.setInt(2, command_id);
          ps.executeUpdate();
          System.out.println("produit deleted successfully from cart");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de la supression de produit de la cart : " + e.getMessage());
      }
    }

    public void Incrimentquantity(int quantite_c, int total , int command_id, int produit_id){
      if (quantite_c < total){
      int quantite = quantite_c+1;      
      try {
        String req = "UPDATE `commands_produit` SET `quantite_c`=? WHERE produit_id=? and commande_id=?";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setInt(1, quantite);
          ps.setInt(2, produit_id);
          ps.setInt(3, command_id);

          ps.executeUpdate();
          System.out.println("la quantite a ete incrimentée");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de lincrimentation : " + e.getMessage());
      }
    }
  }

  public void Dicrimentquantity(int quantite_c , int command_id, int produit_id){
    if (quantite_c >1){
      int quantite = quantite_c-1;      
      try {
        String req = "UPDATE `commands_produit` SET `quantite_c`=? WHERE produit_id=? and commande_id=?";
        PreparedStatement ps = conx.prepareStatement(req);
          ps.setInt(1, quantite);
          ps.setInt(2, produit_id);
          ps.setInt(3, command_id);

          ps.executeUpdate();
          System.out.println("la quantite a ete dicrimentée");
          ps.close();
      }catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de dicrimentation : " + e.getMessage());
      }
    }
  }

  public Commands_produit getOneCommandProduit(int produitId, int commandId)  {
    Commands_produit commandsProduit = null;

    try {
    String req = "SELECT * FROM `commands_produit` where produit_id = ? and commande_id = ? ";
    PreparedStatement ps = conx.prepareStatement(req);
    ps.setInt(1, produitId);
    ps.setInt(2, commandId);

    ResultSet rs = ps.executeQuery();
    
    if (rs.next()) {
        commandsProduit = new Commands_produit();
        commandsProduit.setId(rs.getInt("id"));
        commandsProduit.setProduit_id(rs.getInt("produit_id"));
        commandsProduit.setCommande_id(rs.getInt("commande_id"));
        commandsProduit.setQuantite_c(rs.getInt("quantite_c"));

    }
    ps.close();
   
      }catch (SQLException e) {
          System.out.println("Une erreur s'est produite lors de la récupération du commande : " + e.getMessage());
        }
        return commandsProduit;
  }


  
}
