package gui.commandInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Achats;
import entities.Commands;
import entities.Produit;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.AchatsService;
import services.CommandsProduitService;
import services.CommandsService;
import services.IAchatsService;
import services.ProduitService;
import services.UserService;
import utils.TrayNotificationAlert;
import utils.UserSession;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import javafx.util.Duration;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author ALI
 */

 
public class UserCommandsListItemController {
    @FXML
    private Text nomp;

    @FXML
    private Text pointp;

    @FXML
    private Text prixp;

    @FXML
    private Text quantitep;
    @FXML
    private HBox deletep;
    @FXML
    private HBox plus;
    @FXML
    private HBox moin;


    public void setCommandProduit(Produit produit, int command_id) {
 
    nomp.setText(produit.getNom_produit());
    pointp.setText(""+produit.getPrix_point_produit());
    prixp.setText(""+produit.getPrix_produit());
    quantitep.setText(""+produit.getQuantite());     

    CommandsProduitService commandsProduitService = new CommandsProduitService();
        // deleteCommand btn click
        //deleteCommand.setId(String.valueOf(achat.getId()));

        deletep.setOnMouseClicked(event -> {
       //     System.out.println("ID du produit à supprimer : " + produit.getId());

            commandsProduitService.CommandeDeleteProduct(produit.getId(), command_id);
            TrayNotificationAlert.notif("Command", "produit incrimented successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

            // supprimer le contenu de la liste et afficher la nouvelle liste
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis ce controller
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de ProductsList.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // end
        });
        //END deleteCommand btn click


        //update plus 
        plus.setOnMouseClicked(event -> {
            //     System.out.println("ID du produit a ete incrimenté : " + produit.getId());
     ProduitService produitService = new ProduitService();
     Produit p1= new Produit();
     try {
         p1 = produitService.getOneProduct(produit.getId());
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
                 commandsProduitService.Incrimentquantity(produit.getQuantite(), p1.getQuantite(), command_id, produit.getId());
                 TrayNotificationAlert.notif("Command", "produit deleted successfully.",
                         NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
     
                 // supprimer le contenu de la liste et afficher la nouvelle liste
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));
                 try {
                     Parent root = loader.load();
                     // Accéder à la pane content_area depuis ce controller
                     Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");
     
                     // Vider la pane et afficher le contenu de ProductsList.fxml
                     contentArea.getChildren().clear();
                     contentArea.getChildren().add(root);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 // end
             });
             //END plusCommand btn click
            
             //update moin 
             moin.setOnMouseClicked(event -> {
            //     System.out.println("ID du produit a ete incrimenté : " + produit.getId());
     ProduitService produitService = new ProduitService();
     Produit p1= new Produit();
     try {
         p1 = produitService.getOneProduct(produit.getId());
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
                 commandsProduitService.Dicrimentquantity(produit.getQuantite(), command_id, produit.getId());
                if(produit.getQuantite() > 1){
                 TrayNotificationAlert.notif("Command", "produit dicrimented successfully.",
                         NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                }else {
                    TrayNotificationAlert.notif("Command", "impossible de dicrimentes vous pouver supprimer.",
                         NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));
                }
                 // supprimer le contenu de la liste et afficher la nouvelle liste
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));
                 try {
                     Parent root = loader.load();
                     // Accéder à la pane content_area depuis ce controller
                     Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");
     
                     // Vider la pane et afficher le contenu de ProductsList.fxml
                     contentArea.getChildren().clear();
                     contentArea.getChildren().add(root);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 // end
             });
             //END plusCommand btn click
    }
    }



    

