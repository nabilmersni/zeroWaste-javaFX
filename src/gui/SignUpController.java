package gui;

import java.sql.SQLException;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import services.UserService;
import utils.UserInputValidation;

public class SignUpController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullnameField;

    @FXML
    private AnchorPane left;

    @FXML
    private Hyperlink logInLink;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField repassField;

    @FXML
    private RadioButton role_assoc;

    @FXML
    private RadioButton role_indiv;

    @FXML
    private Button signUpBTN;

    @FXML
    private ToggleGroup roles;

    @FXML
    void signUp(ActionEvent event) {
        String token = UUID.randomUUID().toString();
        String role = ((RadioButton) roles.getSelectedToggle()).getText();
        if (role == "Individual") {
            role = "[\"ROLE_USER\"]";
        } else {
            role = "[\"ROLE_ASSOCIATION\"]";
        }
        User user = new User(fullnameField.getText(), emailField.getText(),
                phoneField.getText(), token,
                "defaultPic.jpg", role, passField.getText(), 0);

        UserService userService = new UserService();
        if (UserInputValidation.signUpValidator(user, repassField.getText(), userService)) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            try {
                userService.ajouter(user);
            } catch (SQLException e) {
                e.getMessage();
                System.out.println("error: " + e.getMessage());
            }
        } else {

        }

        // try {
        // userService.ajouter(user);
        // } catch (SQLException e) {
        // e.getMessage();
        // System.out.println("error: " + e.getMessage());
        // }
        // to check password
        // if (BCrypt.checkpw(candidate, hashed))
        // System.out.println("It matches");
        // else
        // System.out.println("It does not match");
    }

    @FXML
    void toLogin(ActionEvent event) {

    }

}
