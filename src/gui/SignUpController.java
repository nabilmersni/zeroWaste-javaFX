package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

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

    }

    @FXML
    void toLogin(ActionEvent event) {

    }

}
