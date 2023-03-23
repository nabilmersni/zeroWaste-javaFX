package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import utils.UserSession;

public class ConfirmEmailController implements Initializable {
    User currentUser;

    @FXML
    private TextField codeField;

    @FXML
    private Button confirmBTN;

    @FXML
    private AnchorPane left;

    @FXML
    private AnchorPane logoutBTN;

    @FXML
    void confirmEmail(ActionEvent event) {
        String code = codeField.getText();

        if (code.equals(String.valueOf(currentUser.getVerificationCode()))) {
            currentUser.setIsVerified(true);
            UserService userService = new UserService();
            try {
                userService.update(currentUser);

                TrayNotificationAlert.notif("sign Up", "email verified successfully.",
                        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            TrayNotificationAlert.notif("confirm email", "Please verify your verification code",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));
        }
    }

    @FXML
    void logOut(MouseEvent event) {
        System.out.println("yohohoho");
        UserSession.getInstance().cleanUserSession();
    }

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
}
