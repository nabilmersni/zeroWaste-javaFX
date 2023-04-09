package gui;

import java.io.IOException;
import java.sql.SQLException;

import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.IProduitService;
import services.ProduitService;
import javafx.scene.Node;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
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


    public void setProductData(Produit produit) {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();

        Image image = new Image(getClass().getResource("/assets/ProductUploads/" + produit.getImage()).toExternalForm());
        img.setImage(image);

        productName.setText(produit.getNom_produit());
        
        productDescription.setText("" + produit.getDescription());
        productPrice.setText("" + produit.getPrix_produit());
        productPoints.setText("" + produit.getPrix_point_produit());

      
    }

}
