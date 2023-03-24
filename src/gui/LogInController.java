package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.SendMail;
import utils.TrayNotificationAlert;
import utils.UserSession;

public class LogInController {

    @FXML
    private TextField emailField;

    @FXML
    private AnchorPane left;

    @FXML
    private Button loginBTN;

    @FXML
    private PasswordField passField;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    void logIn(ActionEvent event) throws IOException {

        String email = emailField.getText();
        String password = passField.getText();

        UserService userService = new UserService();
        User user;

        try {
            user = userService.getOneUser(email);

            if (user.getId() == -999) {
                TrayNotificationAlert.notif("Login", "Invalid credentials.",
                        NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            } else {
                // System.out.println(user);
                if (BCrypt.checkpw(password, user.getPassword())) {

                    if (!user.getState()) {
                        TrayNotificationAlert.notif("Login", "Your account is blocked.",
                                NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
                    } else if (user.getIsVerified()) {
                        TrayNotificationAlert.notif("Login", "logged in successfully.",
                                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                        UserSession.getInstance().setEmail(user.getEmail());
                        System.out.println("to the DASHBOARD");
                    } else {

                        Map<String, String> data = new HashMap<>();
                        data.put("emailSubject", "Confirm your email address for zeroWaste");
                        data.put("titlePlaceholder", "Confirm Your Email Address");
                        data.put("msgPlaceholder", "Here's the code to confirm your email address:");

                        SendMail.send(user, data);

                        TrayNotificationAlert.notif("Login", "Please verify your email.",
                                NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));

                        UserSession.getInstance().setEmail(user.getEmail());
                        Parent root = FXMLLoader.load(getClass().getResource("ConfirmEmail.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }
                } else {
                    TrayNotificationAlert.notif("Login", "Invalid credentials.",
                            NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // UserSession.getInstance().setEmail("java@gmail.com");
        // Parent root = FXMLLoader.load(getClass().getResource("ConfirmEmail.fxml"));
        // Scene scene = new Scene(root);
        // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // stage.setScene(scene);
        // stage.show();
    }

    @FXML
    void toSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void forgotPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
