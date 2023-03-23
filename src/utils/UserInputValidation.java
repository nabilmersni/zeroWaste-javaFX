package utils;

import java.sql.SQLException;
import java.util.regex.Pattern;

import entities.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class UserInputValidation {

    public static boolean signUpValidator(User user, String repassword, UserService userService) {
        if (user.getFullname().equals("") || user.getEmail().equals("") || user.getTel().equals("")
                || user.getRoles().equals("")
                || user.getPassword().equals("")) {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("Please fill out all required fields.");

            // alert.showAndWait();
            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("Please fill out all required fields.");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        Pattern fullnamePattern = Pattern.compile(".*\\d.*");
        if (fullnamePattern.matcher(user.getFullname()).matches()) {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("Your name cannot contain a number.");

            // alert.showAndWait();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("Your name cannot contain a number.");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        if (user.getFullname().length() < 3) {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("not a valid fullname");

            // alert.showAndWait();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("not a valid fullname.");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        if (!emailPattern.matcher(user.getEmail()).matches()) {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("The email " + user.getEmail() + " is not a valid
            // email.");

            // alert.showAndWait();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("The email " + user.getEmail() + " is not a valid email.");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        Pattern telPattern = Pattern.compile("[0-9]+");
        if (!(telPattern.matcher(user.getTel()).matches() && user.getTel().length() >= 8)) {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("not a valid phone number.");

            // alert.showAndWait();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("not a valid phone number.");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        if (user.getRoles() != "[\"ROLE_USER\"]" && user.getRoles() != "[\"ROLE_ASSOCIATION\"]") {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("Hello little hacker 🐱‍💻👩‍💻");

            // alert.showAndWait();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("Hello little hacker 🐱‍💻👩‍💻");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        if (user.getPassword().length() < 8) {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("Your password must be at least 8 characters long.");

            // alert.showAndWait();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("Your password must be at least 8 characters long.");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        if (!user.getPassword().equals(repassword)) {
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("signUp error");
            // alert.setHeaderText(null);
            // alert.setContentText("Passwords do NOT match.");

            // alert.showAndWait();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("sign Up");
            tray.setMessage("Passwords do NOT match.");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(2500));

            return false;
        }

        try {
            if (userService.getOneUser(user.getEmail()).getId() != -999) {
                // Alert alert = new Alert(AlertType.WARNING);
                // alert.setTitle("signUp error");
                // alert.setHeaderText(null);
                // alert.setContentText("email already in use.");
                // alert.showAndWait();

                TrayNotification tray = new TrayNotification();
                tray.setTitle("sign Up");
                tray.setMessage("email already in use.");
                tray.setNotificationType(NotificationType.WARNING);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.millis(2500));

                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
