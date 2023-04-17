package gui;

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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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


    public void setFundData(Fundrising fundrising) {
        // Instancier le service de fund
        IFundrisingService fundrisingService = new FundrisingService();

        Image image = new Image(getClass().getResource("/assets/FundraisingUploads/" + fundrising.getImage_don()).toExternalForm());
        img.setImage(image);

        try {
            fund = fundrisingService.getOneFund(fundrising.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        titreDon.setText(fundrising.getTitre_don());
        total.setText("" + fundrising.getTotal());
        descriptionDon.setText("" + fundrising.getDescription_don());
        objectif.setText("" + fundrising.getObjectif());
    }

    @FXML
    void addHistory(MouseEvent event) throws SQLException {

        DonHistory donHistory = new DonHistory();
        donHistory.setUserId(currentUser.getId());
        donHistory.setFundId(fund.getId());
        donHistory.setComment("Testing");
        donHistory.setDonationPrice(100);
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        donHistory.setDateDonation(currentDate);

        // Instancier le service de produit
        IDonHistoryService donHistoryService = new DonHistoryService();

        donHistoryService.addHistory(donHistory);

    }

}