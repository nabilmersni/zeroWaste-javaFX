package gui.commandInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Achats;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.AchatsService;
import services.IAchatsService;

/**
 * FXML Controller class
 *
 * @author ALI
 */

 
public class AdminCommandLivraisonCardController {
    @FXML
    private Text address;

    @FXML
    private Text city;

    @FXML
    private Text commandId;

    @FXML
    private Text email;

    @FXML
    private Text fullname;

    @FXML
    private Text phone;

    @FXML
    private Text total;

    @FXML
    private Text totalPt;

    @FXML
    private Text zipcode;

    public void setCommandLivraison() {
        // Instancier le service de achat
        IAchatsService achatService = new AchatsService();

       // date_achat.setText(achat.getDate_achat());
 
       Achats achat = new Achats();
       try {
         achat= achatService.getOneAchat(Achats.getAchatId());
         fullname.setText(achat.getFull_name());
         address.setText(achat.getAddress());
         city.setText(achat.getCity());
         commandId.setText(""+achat.getCommande_id());
         email.setText(achat.getEmail());
         phone.setText(""+achat.getTel());
         zipcode.setText(""+achat.getZip_code());

         //set the total price command
         List<Produit> produitList = achatService.getAllProducts(achat.getCommande_id());
         float totalPrx = 0;
         int totalPts = 0;
         for(int i =0; i< produitList.size() ; i++){
            totalPrx += produitList.get(i).getPrix_produit() * produitList.get(i).getQuantite() ;
            totalPts += produitList.get(i).getPrix_point_produit() * produitList.get(i).getQuantite() ; 
         }
         total.setText(""+totalPrx);
         totalPt.setText(""+totalPts);



       } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }

    }

}
