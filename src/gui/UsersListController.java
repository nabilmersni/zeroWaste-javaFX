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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.UserService;

public class UsersListController implements Initializable {

    @FXML
    private HBox updateUserModel;

    @FXML
    private VBox updateUserModelContent;

    @FXML
    private VBox userListContainer;

    @FXML
    private Text userListTitle;

    @FXML
    private Pane userPane;

    @FXML
    private HBox userTableHead;

    private static int updateUserModelShow = 0;
    private static String userEmailToUpdate = "";

    public static int getupdateUserModelShow() {
        return updateUserModelShow;
    }

    public static void setupdateUserModelShow(int updateUserModelShow) {
        UsersListController.updateUserModelShow = updateUserModelShow;
    }

    public static String getuserToUpdate() {
        return userEmailToUpdate;
    }

    public static void setuserEmailToUpdate(String userEmailToUpdate) {
        UsersListController.userEmailToUpdate = userEmailToUpdate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserService userService = new UserService();
        User userToUpdate;

        // System.out.println(userEmailToUpdate);
        if (UsersListController.getupdateUserModelShow() == 0) {
            updateUserModel.setVisible(false);
        } else if (UsersListController.getupdateUserModelShow() == 1) {
            updateUserModel.setVisible(true);
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("updateUserCard.fxml"));
            VBox updateUserform;
            try {
                updateUserform = fxmlLoader1.load();
                UpdateUserCardController updateUserCardController = fxmlLoader1.getController();
                userToUpdate = userService.getOneUser(userEmailToUpdate);

                updateUserCardController.setUserUpdateData(userToUpdate);
                updateUserModelContent.getChildren().add(updateUserform);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

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

    @FXML
    void close_updateUserModel(MouseEvent event) {
        updateUserModel.setVisible(false);
        updateUserModelShow = 0;
    }

}
