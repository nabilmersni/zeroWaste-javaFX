package gui;

import java.io.IOException;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import utils.UserInputValidation;
import utils.UserSession;

public class ForgotPasswordController_3 {

    @FXML
    private Button confirmBTN;

    @FXML
    private AnchorPane left;

    @FXML
    private Hyperlink logInLink;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField rePasswordField;

    @FXML
    void confirmBTNClicked(ActionEvent event) throws IOException {
        UserService userService = new UserService();
        User user;

        try {
            if (!UserInputValidation.forgetPasswordValidator2(passwordField.getText(), rePasswordField.getText())) {
                return;
            }
            user = userService.getOneUser(UserSession.getInstance().getEmail());
            user.setPassword(BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()));
            userService.update(user);

            TrayNotificationAlert.notif("Forgot password", "Password updated successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

            UserSession.getInstance().cleanUserSession();
            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toLogin(ActionEvent event) throws IOException {
        UserSession.getInstance().cleanUserSession();
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
