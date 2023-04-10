package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.UserService;
import utils.UserSession;
import zerowaste.ZeroWaste;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserDashboardController implements Initializable {

    @FXML
    private Circle circle;

    @FXML
    private Pane content_area;

    @FXML
    private HBox dashboardBtn;

    @FXML
    private Label dashboardText;

    @FXML
    private ImageView dashboardIcon;

    @FXML
    private HBox fundrisingBtn;

    @FXML
    private Label fundrisingText;

    @FXML
    private ImageView fundrisingIcon;

    @FXML
    private HBox productsBtn;

    @FXML
    private Label productsText;

    @FXML
    private ImageView productsIcon;

    @FXML
    private HBox collectBtn;

    @FXML
    private Label collectText;

    @FXML
    private ImageView collectIcon;

    @FXML
    private HBox commandsBtn;

    @FXML
    private Label commandsText;

    @FXML
    private ImageView commandsIcon;

    @FXML
    private HBox sideBarLogout;

    @FXML
    private HBox navBarLogout;

    @FXML
    private Text navFullname;

    @FXML
    private HBox profileBtn;

    @FXML
    private ImageView profileIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User user;
        UserService userService = new UserService();

        try {
            // user = userService.getOneUser(UserSession.getInstance().getEmail());
            if (UserSession.getInstance().getEmail() == null) {
                user = userService.getOneUser("1");
            } else {
                user = userService.getOneUser(UserSession.getInstance().getEmail());
            }
            Image img = new Image("assets/userUploads/" + user.getImgUrl());
            circle.setFill(new ImagePattern(img));

            navFullname.setText(user.getFullname());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void open_dashboard(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserDashboard.fxml"));
        ZeroWaste.stage.getScene().setRoot(root);
    }

    @FXML
    void open_profile(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        // set active class
        if (!profileBtn.getStyleClass().contains("activeLink")) {
            profileBtn.getStyleClass().add("activeLink");

            // Load the image
            Image image = new Image("assets/img/user-active.png");
            profileIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");

                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            } else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");

                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            }

            /*
             * else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
             * fundrisingBtn.getStyleClass().remove("activeLink");
             * 
             * Image fundrisingImg = new Image("assets/img/heart.png");
             * fundrisingIcon.setImage(fundrisingImg);
             * } else if (collectBtn.getStyleClass().contains("activeLink")) {
             * collectBtn.getStyleClass().remove("activeLink");
             * 
             * Image collectImg = new Image("assets/img/recycle.png");
             * collectIcon.setImage(collectImg);
             * } else if (commandsBtn.getStyleClass().contains("activeLink")) {
             * commandsBtn.getStyleClass().remove("activeLink");
             * 
             * Image commandsImg = new Image("assets/img/shopping-cart.png");
             * commandsIcon.setImage(commandsImg);
             * }
             */

        }
    }

    @FXML
    private void open_fundrisingList(MouseEvent event) throws IOException {
        /*
         * Parent fxml = FXMLLoader.load(getClass().getResource("FundrisingList.fxml"));
         * content_area.getChildren().removeAll();
         * content_area.getChildren().setAll(fxml);
         * 
         * // set active class
         * if (!fundrisingBtn.getStyleClass().contains("activeLink")) {
         * fundrisingBtn.getStyleClass().add("activeLink");
         * fundrisingText.getStyleClass().add("activeText");
         * 
         * // Load the image
         * Image image = new Image("assets/img/heart-active.png");
         * fundrisingIcon.setImage(image);
         * 
         * if (dashboardBtn.getStyleClass().contains("activeLink")) {
         * dashboardBtn.getStyleClass().remove("activeLink");
         * dashboardText.getStyleClass().remove("activeText");
         * 
         * Image dashIcon = new Image("assets/img/menu.png");
         * dashboardIcon.setImage(dashIcon);
         * } else if (usersBtn.getStyleClass().contains("activeLink")) {
         * usersBtn.getStyleClass().remove("activeLink");
         * usersText.getStyleClass().remove("activeText");
         * 
         * Image usersImg = new Image("assets/img/user.png");
         * usersIcon.setImage(usersImg);
         * } else if (productsBtn.getStyleClass().contains("activeLink")) {
         * productsBtn.getStyleClass().remove("activeLink");
         * productsText.getStyleClass().remove("activeText");
         * 
         * Image productsImg = new Image("assets/img/store.png");
         * productsIcon.setImage(productsImg);
         * } else if (collectBtn.getStyleClass().contains("activeLink")) {
         * collectBtn.getStyleClass().remove("activeLink");
         * collectText.getStyleClass().remove("activeText");
         * 
         * Image collectImg = new Image("assets/img/recycle.png");
         * collectIcon.setImage(collectImg);
         * } else if (commandsBtn.getStyleClass().contains("activeLink")) {
         * commandsBtn.getStyleClass().remove("activeLink");
         * commandsText.getStyleClass().remove("activeText");
         * 
         * Image commandsImg = new Image("assets/img/shopping-cart.png");
         * commandsIcon.setImage(commandsImg);
         * }
         * 
         * }
         */
    }

    @FXML
    private void open_productsList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("UserProductsList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        // set active class
        if (!productsBtn.getStyleClass().contains("activeLink")) {
            productsBtn.getStyleClass().add("activeLink");

            // Load the image
            Image image = new Image("assets/img/store-active.png");
            productsIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");

                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            } else if (profileBtn.getStyleClass().contains("activeLink")) {
                profileBtn.getStyleClass().remove("activeLink");

                Image usersImg = new Image("assets/img/user.png");
                profileIcon.setImage(usersImg);
            }

            /*
             * else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
             * fundrisingBtn.getStyleClass().remove("activeLink");
             * 
             * Image fundrisingImg = new Image("assets/img/heart.png");
             * fundrisingIcon.setImage(fundrisingImg);
             * } else if (collectBtn.getStyleClass().contains("activeLink")) {
             * collectBtn.getStyleClass().remove("activeLink");
             * 
             * Image collectImg = new Image("assets/img/recycle.png");
             * collectIcon.setImage(collectImg);
             * } else if (commandsBtn.getStyleClass().contains("activeLink")) {
             * commandsBtn.getStyleClass().remove("activeLink");
             * 
             * Image commandsImg = new Image("assets/img/shopping-cart.png");
             * commandsIcon.setImage(commandsImg);
             * }
             */

        }
    }

    @FXML
    private void open_collectList(MouseEvent event) throws IOException {
        /*
         * Parent fxml = FXMLLoader.load(getClass().getResource("CollectList.fxml"));
         * content_area.getChildren().removeAll();
         * content_area.getChildren().setAll(fxml);
         * 
         * // set active class
         * if (!collectBtn.getStyleClass().contains("activeLink")) {
         * collectBtn.getStyleClass().add("activeLink");
         * 
         * // Load the image
         * Image image = new Image("assets/img/recycle-active.png");
         * collectIcon.setImage(image);
         * 
         * if (dashboardBtn.getStyleClass().contains("activeLink")) {
         * dashboardBtn.getStyleClass().remove("activeLink");
         * 
         * Image dashIcon = new Image("assets/img/menu.png");
         * dashboardIcon.setImage(dashIcon);
         * } else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
         * fundrisingBtn.getStyleClass().remove("activeLink");
         * 
         * Image fundrisingImg = new Image("assets/img/heart.png");
         * fundrisingIcon.setImage(fundrisingImg);
         * } else if (productsBtn.getStyleClass().contains("activeLink")) {
         * productsBtn.getStyleClass().remove("activeLink");
         * 
         * Image productsImg = new Image("assets/img/store.png");
         * productsIcon.setImage(productsImg);
         * } else if (commandsBtn.getStyleClass().contains("activeLink")) {
         * commandsBtn.getStyleClass().remove("activeLink");
         * 
         * Image commandsImg = new Image("assets/img/shopping-cart.png");
         * commandsIcon.setImage(commandsImg);
         * }
         * 
         * }
         */
    }

    @FXML
    private void open_commandsList(MouseEvent event) throws IOException {
        /*
         * Parent fxml = FXMLLoader.load(getClass().getResource("CommandsList.fxml"));
         * content_area.getChildren().removeAll();
         * content_area.getChildren().setAll(fxml);
         * 
         * // set active class
         * if (!commandsBtn.getStyleClass().contains("activeLink")) {
         * commandsBtn.getStyleClass().add("activeLink");
         * 
         * // Load the image
         * Image image = new Image("assets/img/shopping-cart-active.png");
         * commandsIcon.setImage(image);
         * 
         * if (dashboardBtn.getStyleClass().contains("activeLink")) {
         * dashboardBtn.getStyleClass().remove("activeLink");
         * 
         * Image dashIcon = new Image("assets/img/menu.png");
         * dashboardIcon.setImage(dashIcon);
         * } else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
         * fundrisingBtn.getStyleClass().remove("activeLink");
         * 
         * Image fundrisingImg = new Image("assets/img/heart.png");
         * fundrisingIcon.setImage(fundrisingImg);
         * } else if (productsBtn.getStyleClass().contains("activeLink")) {
         * productsBtn.getStyleClass().remove("activeLink");
         * 
         * Image productsImg = new Image("assets/img/store.png");
         * productsIcon.setImage(productsImg);
         * } else if (collectBtn.getStyleClass().contains("activeLink")) {
         * collectBtn.getStyleClass().remove("activeLink");
         * collectText.getStyleClass().remove("activeText");
         * 
         * Image collectImg = new Image("assets/img/recycle.png");
         * collectIcon.setImage(collectImg);
         * }
         * 
         * }
         */
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        UserSession.getInstance().cleanUserSession();
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
