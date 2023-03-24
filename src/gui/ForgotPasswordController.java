package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.SendMail;
import utils.TrayNotificationAlert;
import utils.UserInputValidation;
import utils.UserSession;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private AnchorPane left;

    @FXML
    private Hyperlink logInLink;

    @FXML
    private Button sendBTN;

    @FXML
    void sendClicked(ActionEvent event) throws IOException {
        UserService userService = new UserService();

        User user;
        try {
            user = userService.getOneUser(emailField.getText());

            if (!UserInputValidation.forgetPasswordValidator(emailField.getText(), user)) {
                return;
            }

            int code = new Random().nextInt(900000) + 100000;
            user.setVerificationCode(code);

            Map<String, String> data = new HashMap<>();
            data.put("emailSubject", "Reset password request");
            data.put("titlePlaceholder", "Reset password request");
            data.put("msgPlaceholder",
                    "It seems like you forgot your password. If this is true, here's the code to reset your password");

            SendMail.send(user, data);
            System.out.println("sent");

            userService.update(user);

            TrayNotificationAlert.notif("Forgot password",
                    "A verification code was sent to you, please check you email.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));

            UserSession.getInstance().setEmail(user.getEmail());

            Parent root = FXMLLoader.load(getClass().getResource("ForgotPassword_2.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void toLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
