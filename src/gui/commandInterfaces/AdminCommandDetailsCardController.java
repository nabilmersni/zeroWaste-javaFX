package gui.commandInterfaces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;

import entities.Achats;
import entities.Collecte;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.AchatsService;
import services.IAchatsService;
import services.IProduitService;
import services.ProduitService;
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
public class AdminCommandDetailsCardController  {
    @FXML
    private Text cat;

    @FXML
    private Text name;

    @FXML
    private Text price;

    @FXML
    private Text quantite;

    @FXML
    private Text total;

    @FXML
    private ImageView img;
    
    public void setCommandDetails(Collecte produit) {
        IProduitService produitService = new ProduitService();

        name.setText(produit.getNom_produit());
        price.setText(""+produit.getPrix_produit());
        Image image = new Image(
                getClass().getResource("/assets/ProductUploads/" + produit.getImage()).toExternalForm());
                img.setImage(image);
                  // get category Name
        String categoryName = produitService.getCategory(produit.getCategorie_produit_id());
        cat.setText(categoryName);

        quantite.setText("" + produit.getQuantite());
        //total
        float totalc = produit.getPrix_produit()* produit.getQuantite();
        String prix= String.format("%.1f", totalc);
            total.setText(prix);

        

       // date_achat.setText(achat.getDate_achat());
 


    }
    
   
    

}
