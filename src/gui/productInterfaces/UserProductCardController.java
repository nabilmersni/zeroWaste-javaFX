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
     
        //add product to cart btn
        AddToCart.setOnMouseClicked(event -> {
            System.out.println("product added to command : " + produit.getId());
            User user= new User();
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
            System.out.println("Command is :" + command);
            if ( command == null ){
    
                commandsService.addNewCommands(user.getId());
           }

           command = commandsService.getOneCommand(user.getId()); 
           
        //    System.out.println("Command not null :" + command);
        //    System.out.println("Command id :" + command.getId());
        //    System.out.println("produit id :" + produit.getId());
        //    System.out.println("user id :" + user.getId());
    
           CommandsProduitService commandeProduitService = new CommandsProduitService();
           // si le produit n'existe pas dans la commande on ajoute ce produit
           if(commandeProduitService.getOneCommandProduit(produit.getId(), command.getId()) == null){
                commandeProduitService.addNewCommandsProduit(command.getId(), produit.getId());

                Text addedCartModelText = (Text) ((Node) event.getSource()).getScene().lookup("#addedCartModelText");
                addedCartModelText.setText("Product Added To Cart Successfully");

                HBox addedCartModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addedCartModel");
                addedCartModel.setVisible(true);
               
           }
           else{ // produit existe deja dans la commande => msg affiché
                Text addedCartModelText = (Text) ((Node) event.getSource()).getScene().lookup("#addedCartModelText");
                addedCartModelText.setText("Product is ALready Added To Cart");

                HBox addedCartModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addedCartModel");
                addedCartModel.setVisible(true);


           }
           //System.out.println(commandsService.getOneCommand(user.getId())); 
        
    
        });
        //END add product to cart cart
    }

    
    
    
}
