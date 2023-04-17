package services;

import java.sql.SQLException;
import java.util.List;

import entities.Achats;
import entities.Produit;

public interface IAchatsService {

  public List<Achats> getAllAchats();

  public void adminDeleteAchat(int achat_id) ;
  public Achats getOneAchat(int idAchat) throws SQLException;
  public List<Produit> getAllProducts(int commande_id );
  public List<Achats> searchCommands(String search);

  public List<Achats> getAchatsToday();
  public List<Achats> getAchatsLastWeek();
  public List<Achats> getAchatsLastMonth();
  
}
