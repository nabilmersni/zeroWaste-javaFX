package services;

import entities.Commands;

public interface ICommandsService {

  public Commands getOneCommand(int userId) ;
  public void addNewCommands(int userId) ;

  
}
