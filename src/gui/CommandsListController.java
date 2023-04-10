package gui;

import entities.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import services.CommandsService;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;


public class CommandsListController {
 
    @FXML
    private TextField update1; 
    @FXML
    private TextField update2; 

    private CommandsService CommandsService = new CommandsService();

    CommandsService su=new CommandsService();

    ObservableList<Commands> data=FXCollections.observableArrayList();
   
    

    @FXML
    private Button CommandsAddButton;

    @FXML
    private Pane pane;

    @FXML
    private TableView<Commands> commandsTable;

    @FXML
    private TableColumn<Commands, Integer> idColumn;
    @FXML
    private TableColumn<Commands, Integer> userIdColumn;
    @FXML
    private TableColumn<Commands, Integer> statusColumn;

    private ObservableList<Commands> commandsList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
 //       commandsList.addAll(CommandsService.getAllCommands());

   //     idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
     //   userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
       // statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        // Add other column configurations here
        
     //   commandsTable.setItems(commandsList);

     refreshList();
  
    }
   
    @FXML
    void ajouterCommande(ActionEvent event) {
        Commands newc = new Commands(); // Create a new Commands object
        CommandsService.ajouter(newc); // Call the ajouter commande method to add the new fund
        System.out.println("command added!"); // Print a message to confirm that the fund was added
    }
    
    //  @FXML
     // void supprimeCommande(ActionEvent event) {
     //   CommandsService CommandsService = new CommandsService(); // Create a new FundrisingService object
      //  CommandsService.supprimer(10); // Call the deleteFund method to delete a fund
       // System.out.println("Fund deleted!"); // Print a message to confirm that the fund was deleted
//    }
    
    //supprimer 
    @FXML
    private void supprimeCommande(ActionEvent event) {
        if(commandsTable.getSelectionModel().getSelectedItem()!=null){
            int id=commandsTable.getSelectionModel().getSelectedItem().getId();
            su.supprimer(id);
            refreshList();
           System.out.println("commande deleted!"); // Print a message to confirm that the fund was deleted
        }
        
    }

    public void refreshList(){
        data.clear();
        data=FXCollections.observableArrayList(su.getAllCommands());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        commandsTable.setItems(data);
    }

    @FXML
   private void updatec(ActionEvent event) {
     if(commandsTable.getSelectionModel().getSelectedItem()!=null){
                Commands u=new Commands();
                int id=commandsTable.getSelectionModel().getSelectedItem().getId();
                u.setUser_id(Integer.valueOf(
                    update1.getText()));
                u.setStatus(Integer.valueOf(
                    update2.getText()));
              
                su.modifier(id, u);
                refreshList();
            }

            refreshList();
           System.out.println("commande deleted!"); // Print a message to confirm that the fund was deleted
        } 
        
      /*   @FXML
        private void fillforum(Commands e) {

            if (e != null) {
                update1.setText(String.valueOf(e.getUser_id()));
                update2.setText(String.valueOf(e.getStatus()));
            }
    
        } */

        @FXML
        private void fillforum(ActionEvent event) {
            if(commandsTable.getSelectionModel().getSelectedItem()!=null){
                update1.setText(String.valueOf(commandsTable.getSelectionModel().getSelectedItem().getUser_id()));
                update2.setText(String.valueOf(commandsTable.getSelectionModel().getSelectedItem().getStatus()));
 
            }
            
        }

    }
