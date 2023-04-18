package gui.commandInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.Commands;
import entities.Produit;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import services.AchatsService;
import services.CommandsService;
import services.UserService;
import utils.UserSession;


/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserCommandsListController implements Initializable {

    @FXML
    private GridPane commandsListContainer;

    @FXML
    private Pane content_area;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
         // user = userService.getOneUser(UserSession.getInstance().getEmail());
       

        //set one command details Model
         try {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("/gui/commandInterfaces/UsercommandsHeader.fxml"));
   
            HBox commandinfoCard = fxmlLoader1.load();
            //AdminCommandLivraisonCardController commandLivraisonController = fxmlLoader1.getController();
            //commandLivraisonController.setCommandLivraison();
            commandsListContainer.add(commandinfoCard, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User() ;
        
        UserService userService = new UserService();

         if (UserSession.getInstance().getEmail() == null ) {
          
                try {
                    user = userService.getOneUser("nabilkdp0@gmail.com");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(user.getId()); 
       
        } else {
                try {
                    user = userService.getOneUser(UserSession.getInstance().getEmail());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(user.getId()); 

        }
        CommandsService commandsService = new CommandsService();
        Commands command = new Commands();
        command = commandsService.getOneCommand(user.getId()); 
        if ( command != null ){
            AchatsService achatsService = new AchatsService();
            List<Produit> listpProduits = new ArrayList<>();
            listpProduits = achatsService.getAllProducts(command.getId()); 
    
       


        int column = 0;
        int row = 2;
        try {
            for (int i = 0; i < listpProduits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/commandInterfaces/UserCommandsListItem.fxml"));
                HBox oneCommandCard = fxmlLoader.load();
                

                UserCommandsListItemController commandCardController = fxmlLoader.getController();
                commandCardController.setCommandProduit(listpProduits.get(i), command.getId() );

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                commandsListContainer.add(oneCommandCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneCommandCard, new Insets(0, 10, 15, 10));
             //   oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        //END - set one command details Model

    }

   
}