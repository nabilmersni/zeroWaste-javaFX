package gui.fundraisingInterfaces;

import java.sql.SQLException;

import entities.DonHistory;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import services.DonHistoryService;
import services.UserService;

public class ParticipantItemController {

    @FXML
    private Text userItemEmail;

    @FXML
    private ImageView userItemImg;

    @FXML
    private Text userItemName;

    @FXML
    private Text userItemRole;

    @FXML
    private Label userItemStateBtn;

    @FXML
    private ImageView userItemStateBtnImg;

    @FXML
    private Label userItemStateLabel;

    @FXML
    private Text userItemStateText;

    @FXML
    private Text userItemTel;

    @FXML
    private Label userItemUpdateBtn;

    @FXML
    private ImageView userItemUpdateBtnImg;

    User user;

    public void setParticipantData(DonHistory donHistory) {
        // DonHistoryService donHistoryService = new DonHistoryService();
        UserService userService = new UserService();
        try {
            user = userService.getUserById(donHistory.getUserId());
            
        } catch (SQLException e) {
            // handle the exception
            e.printStackTrace();
        }
        
        /*Image image = new Image(
                getClass().getResource("/assets/fundraisingUploads/" + user.getImgUrl()).toExternalForm());
        userItemImg.setImage(image);*/

        Rectangle clip = new Rectangle(
                userItemImg.getFitWidth(), userItemImg.getFitHeight());
        clip.setArcWidth(100);
        clip.setArcHeight(100);

        userItemImg.setClip(clip);

        userItemName.setText(user.getFullname());
        userItemEmail.setText(Float.toString(donHistory.getDonationPrice()));

    }

}
