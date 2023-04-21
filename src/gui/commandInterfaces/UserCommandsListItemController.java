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
    private Text dollar;

    @FXML
    private Text priceAfterOffer;

    @FXML
    private Text priceBeforeOffer;

    @FXML
    private HBox priceAfterOfferHbox;

    @FXML
    private HBox deletep;
    @FXML
    private HBox plus;
    @FXML
    private HBox moin;


    public void setCommandProduit(Produit produit, int command_id) {
 
    nomp.setText(produit.getNom_produit());
    pointp.setText(""+produit.getPrix_point_produit());

    float prixApresOffre = 0;

       
        if (produit.getRemise() == 0) {
            priceAfterOfferHbox.setVisible(false);
            prixp.setVisible(true);
            dollar.setVisible(true);
            prixp.setText(""+produit.getPrix_produit());
        } else {
            prixp.setVisible(false);
            dollar.setVisible(false);
            priceAfterOfferHbox.setVisible(true);

            priceBeforeOffer.setText("" + produit.getPrix_produit());

            prixApresOffre = (float) (produit.getPrix_produit()
                    - (produit.getPrix_produit() * produit.getRemise() / 100.0));
            
            String prixApresOffreStr = String.format("%.1f", prixApresOffre);
            priceAfterOffer.setText(prixApresOffreStr);
        }
    
    quantitep.setText(""+produit.getQuantite());     

    CommandsProduitService commandsProduitService = new CommandsProduitService();
        
        // deleteCommand btn click
        deletep.setOnMouseClicked(event -> {
            //System.out.println("ID du produit à supprimer : " + produit.getId());

            commandsProduitService.CommandeDeleteProduct(produit.getId(), command_id);
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
        //END deleteCommand btn click

        
        //incrementerProduit Btn click
        plus.setOnMouseClicked(event -> {
            // plus.setVisible(false);
            //HBox clickedButton = (HBox) event.getSource();
            //clickedButton.setStyle("-fx-background-color: red;");
            //     System.out.println("ID du produit a ete incrimenté : " + produit.getId());
            //tester si la quantite_c est inférieure à la quantite totale disponible pour le produit            
            ProduitService produitService = new ProduitService();
            Produit p1= new Produit();
            try {
                p1 = produitService.getOneProduct(produit.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(produit.getQuantite() < p1.getQuantite()){
            commandsProduitService.Incrimentquantity(produit.getQuantite(), p1.getQuantite(), command_id, produit.getId());
            TrayNotificationAlert.notif("Command", "produit incrimented successfully.",
                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
            }else{
                TrayNotificationAlert.notif("Command", "Stock Insuffisante.",
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
        //END incrementerProduit btn click
            
            //update moin 
            moin.setOnMouseClicked(event -> {
                //System.out.println("ID du produit a ete incrimenté : " + produit.getId());
                ProduitService produitService = new ProduitService();
                Produit p1= new Produit();
                try {
                    p1 = produitService.getOneProduct(produit.getId());
                } catch (SQLException e) {
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



    

