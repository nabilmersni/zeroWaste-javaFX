package services;


public interface ICommandsProduitService {

  public void addNewCommandsProduit(int commandeID, int produitID) ;
  public void CommandeDeleteProduct(int produit_id, int command_id);

  
}
