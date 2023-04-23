package gui.fundraisingInterfaces;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ResourceBundle;
import entities.DonHistory;
import entities.Fundrising;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.IFundrisingService;
import services.UserService;
import utils.UserSession;
import services.DonHistoryService;
import services.FundrisingService;
import services.IDonHistoryService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * FXML Controller class
 *
 */
public class AddDonHistoryCardController implements Initializable {

    @FXML
    private TextField amount;

    @FXML
    private TextArea comment;

    @FXML
    private Button donateBtn;

    @FXML
    private Text amountInputError;

    @FXML
    private Text commentInputError;

    @FXML
    private HBox amountInputErrorBox;

    @FXML
    private HBox commentInputErrorBox;

    User currentUser;
    Fundrising fund;
    private int amountTest = 0;
    private int commentTest = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        amountInputErrorBox.setVisible(false);
        commentInputErrorBox.setVisible(false);
        UserService userService = new UserService();

        try {
            currentUser = userService.getOneUser(UserSession.getInstance().getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setFundData(Fundrising fundrising) {
        // Instancier le service de fund
        IFundrisingService fundrisingService = new FundrisingService();

        try {
            fund = fundrisingService.getOneFund(fundrising.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addHistory(MouseEvent event) throws SQLException {
        String ACCOUNT_SID = "AC97c73f447d8b48e0816226228f7951f4";
        String AUTH_TOKEN = "325e69ad7acebb574b036513954a16d2";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        FundrisingService fundrisingService = new FundrisingService();
        Fundrising fundrising = fundrisingService.getOneFund(Fundrising.getIdFund());
        IDonHistoryService donHistoryService = new DonHistoryService();
        DonHistory donHistory = donHistoryService.hasDonated(currentUser.getId(), Fundrising.getIdFund());

        if (donHistory != null) {
            donHistory.setComment(comment.getText());
            Float newDonation = donHistory.getDonationPrice();
            newDonation += Float.parseFloat(amount.getText());
            donHistory.setDonationPrice(newDonation);
            java.util.Date utilDate = new java.util.Date();
            Date currentDate = new Date(utilDate.getTime());
            donHistory.setDateDonation(currentDate);
            donHistoryService.updateHistoryAmount(donHistory);
        } else {
            donHistory = new DonHistory();
            donHistory.setUserId(currentUser.getId());
            donHistory.setFundId(Fundrising.getIdFund());
            donHistory.setComment(comment.getText());
            donHistory.setDonationPrice(Float.parseFloat(amount.getText()));
            java.util.Date utilDate = new java.util.Date();
            Date currentDate = new Date(utilDate.getTime());
            donHistory.setDateDonation(currentDate);
            donHistoryService.addHistory(donHistory);
        }
        

        float amountRaised = fundrising.getTotal();
        float newDonationAmount = Float.parseFloat(amount.getText());
        amountRaised +=  newDonationAmount;
        fundrising.setTotal(amountRaised);
        fundrisingService.updateTotal(fundrising);

        if (amountRaised > fundrising.getObjectif()) {
            fundrisingService.updateEtat(fundrising);
        }

        String recipientNumber = "+21698428606";
        String message = "Hello " + currentUser.getFullname() + "\n, You have successfully donated " + amount.getText() + " TND";

        Message twilioMessage = Message.creator(
                    new PhoneNumber(recipientNumber),
                    new PhoneNumber("+12762624016"),
                    message)
                    .create();
        System.out.println("SMS envoyé : " + twilioMessage.getSid());


    }


}
