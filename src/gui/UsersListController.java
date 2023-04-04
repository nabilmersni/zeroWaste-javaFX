package gui;

import entities.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import services.UserService;

public class UsersListController implements Initializable {

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

    @FXML
    private VBox userListContainer;

    @FXML
    private Text userListTitle;

    @FXML
    private Pane userPane;

    @FXML
    private HBox userTableHead;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserService userService = new UserService();
        try {
            ArrayList<User> userList = userService.getAllUser();
            for (int i = 0; i < userList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UserItem.fxml"));
                HBox userItem = fxmlLoader.load();
                UserItemController userItemController = fxmlLoader.getController();
                userItemController.setUserData(userList.get(i));
                userListContainer.getChildren().add(userItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
