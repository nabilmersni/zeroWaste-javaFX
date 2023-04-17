package gui.productInterfaces;

import java.io.IOException;
import java.sql.SQLException;

import entities.Commands;
import entities.Produit;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.CommandsProduitService;
import services.CommandsService;
import services.IProduitService;
import services.ProduitService;
import services.UserService;
import javafx.scene.Node;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import utils.UserSession;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserProductCardController {

    @FXML
    private ImageView img;

    @FXML
    private Text productDescription;

    @FXML
    private Text productName;

    @FXML
    private Text productPoints;

    @FXML
    private Text productPrice;
    @FXML
    private HBox AddToCart;
    

    public void setProductData(Produit produit) {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();

        Image image = new Image(
                getClass().getResource("/assets/ProductUploads/" + produit.getImage()).toExternalForm());
        img.setImage(image);

        productName.setText(produit.getNom_produit());

        productDescription.setText("" + produit.getDescription());
        productPrice.setText("" + produit.getPrix_produit());
        productPoints.setText("" + produit.getPrix_point_produit());
     
        AddToCart.setOnMouseClicked(event -> {
            System.out.println("product added to command : " + produit.getId());
            User user= new User();
            UserService userService = new UserService();
             // user = userService.getOneUser(UserSession.getInstance().getEmail());

             if (UserSession.getInstance().getEmail() == null ) {
              
                    try {
                        user = userService.getOneUser("nabilkdp0@gmail.com");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(user.getId()); 
           
            } else {
                    try {
                        user = userService.getOneUser(UserSession.getInstance().getEmail());
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(user.getId()); 
                    CommandsService commandsService = new CommandsService();
    
            }
            CommandsService commandsService = new CommandsService();
            Commands command = new Commands();
            command = commandsService.getOneCommand(user.getId()); 
            if ( command == null ){
    
                commandsService.addNewCommands(user.getId());
           }
           command = commandsService.getOneCommand(user.getId()); 
           CommandsProduitService commandeProduitService = new CommandsProduitService();
           commandeProduitService.addNewCommandsProduit(command.getId(), produit.getId());
           System.out.println(commandsService.getOneCommand(user.getId())); 
        
    
        });
    }

    
    
    
}
