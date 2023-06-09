package services;


import java.sql.SQLException;
import java.util.List;

import entities.Fundrising;

public interface IFundrisingService {
    

    public List<Fundrising> getAllFunds();

    public List<Fundrising> getCompletedFunds();

    public List<Fundrising> getInProgressFunds();

    public void ajouterFunds(Fundrising funds);
  
    public void supprimerFunds(int id) throws SQLException;
  
    //public String getCategory(int idCategory);
    
    public Fundrising getOneFund(int id) throws SQLException;
    
    public void updateFunds(Fundrising funds);

    public void updateTotal(Fundrising funds);

    public void updateEtat(Fundrising funds);
}
