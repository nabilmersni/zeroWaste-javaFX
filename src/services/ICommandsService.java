package services;

import java.sql.SQLException;
import java.util.List;

import entities.Commands;

public interface ICommandsService {
    
 // public List<Commands> getAllCommands();
  
  public void ajouter(Commands commands) ;

  public void supprimer(int idCommands) throws SQLException;

}
