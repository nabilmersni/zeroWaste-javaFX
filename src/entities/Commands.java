package entities;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    private Integer id;
    private Integer user_id;
    private Integer status;

private List<CommandsProduit> commandsProduits = new ArrayList<>();

       public Commands() {
    }
   
    public Commands(Integer id, Integer user_id, Integer status) {
        this.id = id;
        this.user_id = user_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CommandsProduit> getCommandsProduits() {
        return commandsProduits;
    }

    public void setCommandsProduits(List<CommandsProduit> commandsProduits) {
        this.commandsProduits.clear();
        if (commandsProduits != null) {
            this.commandsProduits.addAll(commandsProduits);
        }
    }

    public void addCommandsProduit(CommandsProduit commandsProduit) {
        commandsProduits.add(commandsProduit);
        commandsProduit.setCommande(this);
    }

    public void removeCommandsProduit(CommandsProduit commandsProduit) {
        commandsProduits.remove(commandsProduit);
        commandsProduit.setCommande(null);
    }

}
