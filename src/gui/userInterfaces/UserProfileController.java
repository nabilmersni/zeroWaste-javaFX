package gui.userInterfaces;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import utils.UserInputValidation;
import utils.UserSession;
import javafx.scene.Node;
import java.awt.*;
import org.mindrot.jbcrypt.BCrypt;

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
    private HBox updateUserModel;

    @FXML
    private VBox updateUserModelContent;

    @FXML
    private ImageView userItemImg;

    @FXML
    private Label userItemUpdateBtn;

    @FXML
    private ImageView userItemUpdateBtnImg;

    @FXML
    private HBox changePassModel;

    @FXML
    private Button confirmChangePassBtn;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField newRepassField;

    @FXML
    private PasswordField oldPassField;

    User user;
    private static int updateUserModelShow = 0;

    public static int getupdateUserModelShow() {
        return updateUserModelShow;
    }

    public static void setupdateUserModelShow(int updateUserModelShow) {
        UserProfileController.updateUserModelShow = updateUserModelShow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changePassModel.setVisible(false);
        UserService userService = new UserService();
        try {
            // user = userService.getOneUser(UserSession.getInstance().getEmail());
            if (UserSession.getInstance().getEmail() == null) {
                user = userService.getOneUser("nabilkdp0@gmail.com");
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

            if (UserProfileController.getupdateUserModelShow() == 0) {
                updateUserModel.setVisible(false);
            } else if (UserProfileController.getupdateUserModelShow() == 1) {
                updateUserModel.setVisible(true);
                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("updateUserCard.fxml"));
                VBox updateUserform;

                updateUserform = fxmlLoader1.load();
                UpdateUserCardController updateUserCardController = fxmlLoader1.getController();
                UpdateUserCardController.setFxmlToLoad("UserDashboard.fxml");

                updateUserCardController.setUserUpdateData(user);
                updateUserModelContent.getChildren().add(updateUserform);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void changePass(ActionEvent event) {
        changePassModel.setVisible(true);
    }

    @FXML
    void confirmChangePass(MouseEvent event) {

        if (UserInputValidation.changePasswordValidator(user, oldPassField.getText(),
                newPassField.getText(),
                newRepassField.getText())) {
            UserService userService = new UserService();
            try {
                user.setPassword(BCrypt.hashpw(newPassField.getText(), BCrypt.gensalt()));
                userService.update(user);
                changePassModel.setVisible(false);
                TrayNotificationAlert.notif("change password", "password changed successfully.",
                        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void close_ChangePassModel(MouseEvent event) {
        changePassModel.setVisible(false);
    }

    @FXML
    void updateProfile(MouseEvent event) {
        updateUserModel.setVisible(true);
        updateUserModelShow = 1;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfile.fxml"));
        try {
            Parent root = loader.load();
            Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

            // Vider la pane et afficher le contenu de ProductsList.fxml
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close_updateUserModel(MouseEvent event) {
        updateUserModel.setVisible(false);
        updateUserModelShow = 0;
    }

    @FXML
    void fbClicked(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.facebook.com/" + user.getFbLink()));
    }

    @FXML
    void instaClicked(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.instagram.com/" + user.getInstaLink()));

    }

    @FXML
    void twitterClicked(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://twitter.com/" + user.getTwitterLink()));

    }
}
