package gui.fundraisingInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import entities.DonHistory;
import entities.Fundrising;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.DonHistoryService;
import services.FundrisingService;
import services.IDonHistoryService;
import services.IFundrisingService;
import services.UserService;
import utils.UserSession;


/**
 * FXML Controller class
 *
 */
public class UserFundrisingCardController implements Initializable {

    User currentUser;

    Fundrising fund;

    @FXML
    private ImageView img;

    @FXML
    private Text total;

    @FXML
    private Text date;

    @FXML
    private Text titreDon;

    @FXML
    private Text descriptionDon;

    @FXML
    private Text objectif;

    @FXML
    private Button add_historyBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserService userService = new UserService();
        try {
            currentUser = userService.getOneUser(UserSession.getInstance().getEmail());

            // System.out.println(currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setFundData(Fundrising fundrising){
        // Instancier le service de produit
        IFundrisingService fundrisingService = new FundrisingService();
        
        Image image = new Image(
           getClass().getResource("/assets/FundraisingUploads/" + fundrising.getImage_don()).toExternalForm());
        img.setImage(image);

       titreDon.setText(fundrising.getTitre_don());
        objectif.setText(fundrising.getTotal() + " TND raised of " + fundrising.getObjectif() + " TND");
        date.setText(fundrising.getDate_don().toString() + " - " + fundrising.getDate_don_limite().toString());

        if (fundrising.getEtat().equals("Completed")) {
            add_historyBtn.setVisible(false);
        }
        
       add_historyBtn.setId(String.valueOf(fundrising.getId()));

       add_historyBtn.setOnMouseClicked(event -> {
           System.out.println("ID du fund to donate : " + fundrising.getId());
           Fundrising.setIdFund(fundrising.getId());
           
           FXMLLoader loader = new FXMLLoader(getClass().getResource("AddDonHistoryCard.fxml"));
           try {
               Parent root = loader.load();
               // Accéder à la pane content_area depuis le controller de OneFundListCard.fxml
               Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

               // Vider la pane et afficher le contenu de AddFund.fxml
               contentArea.getChildren().clear();
               contentArea.getChildren().add(root);
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
    }

}