package services;


import java.sql.SQLException;
import java.util.List;

import entities.DonHistory;

public interface IDonHistoryService {
    

    public List<DonHistory> getAllHistories();

    public void addHistory(DonHistory funds);
  
    public void deleteHistory(int id) throws SQLException;
  
    //public String getCategory(int idCategory);
    
    public DonHistory getOneHistory(int id) throws SQLException;
    
    public void updateHistory(DonHistory funds) ;

    public List<DonHistory> getFundrisingHistory(int id);

    public void updateHistoryAmount(DonHistory donHistory);

    public DonHistory hasDonated(int userId, int fundId);

    
}