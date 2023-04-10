package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import services.UserService;
import utils.UserSession;

public class UserProfileController implements Initializable {

    @FXML
    private Button changePassBTN;

    @FXML
    private Text descriptionText;

    @FXML
    private Text emailText;

    @FXML
    private Text fullnameText;

    @FXML
    private Pane profilePane;

    @FXML
    private Text telText;

    @FXML
    private ImageView userItemImg;

    @FXML
    private Label userItemUpdateBtn;

    @FXML
    private ImageView userItemUpdateBtnImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user;
        UserService userService = new UserService();
        try {
            // user = userService.getOneUser(UserSession.getInstance().getEmail());
            if (UserSession.getInstance().getEmail() == null) {
                user = userService.getOneUser("1");
            } else {
                user = userService.getOneUser(UserSession.getInstance().getEmail());
            }

            Image image = new Image(
                    getClass().getResource("/assets/userUploads/" + user.getImgUrl()).toExternalForm());
            userItemImg.setImage(image);

            Rectangle clip = new Rectangle(
                    userItemImg.getFitWidth(), userItemImg.getFitHeight());
            clip.setArcWidth(100);
            clip.setArcHeight(100);

            userItemImg.setClip(clip);

            fullnameText.setText(user.getFullname());
            descriptionText.setText(user.getDescription());
            emailText.setText(user.getEmail());
            telText.setText(user.getTel());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void changePass(ActionEvent event) {

    }

    @FXML
    void updateProfile(MouseEvent event) {

    }

}
