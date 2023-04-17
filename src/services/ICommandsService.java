package services;

import java.sql.SQLException;
import java.util.List;

import entities.Achats;
import entities.Commands;
import entities.Produit;

public interface ICommandsService {

  public Commands getOneCommand(int userId) ;
  public void addNewCommands(int userId) ;
 /*  public List<Achats> getAllAchats();

  public void adminDeleteAchat(int achat_id) ;
  public Achats getOneAchat(int idAchat) throws SQLException;
  public List<Produit> getAllProducts(int commande_id );
  public List<Achats> searchCommands(String search); */
  
}
