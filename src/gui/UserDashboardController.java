package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.Notification;
import entities.Produit;
import entities.User;
import gui.productInterfaces.NotifItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ProduitService;
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
    private HBox favBtn;

    @FXML
    private HBox achatBtn;

    @FXML
    private ImageView profileIcon;

    @FXML
    private ImageView achatIcon;

    @FXML
    private ImageView favIcon;

    @FXML
    private Circle circle1;

    @FXML
    private Text navFullname1;

    @FXML
    private Text totalNotif;

    @FXML
    private Circle donHisImg;

    @FXML
    private Rectangle fundImg;

    @FXML
    private Text userPointText;

    @FXML
    private GridPane notifContainer;

    @FXML
    private VBox notifModel;

    private int notifModel_isOpen = 0;

    User user = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        notifModel.setVisible(false);

        UserService userService = new UserService();

        try {
            // user = userService.getOneUser(UserSession.getInstance().getEmail());
            if (UserSession.getInstance().getEmail() == null) {
                user = userService.getOneUser("nabilkdp0@gmail.com");
            } else {
                user = userService.getOneUser(UserSession.getInstance().getEmail());
            }
            Image img = new Image("assets/userUploads/" + user.getImgUrl());
            circle.setFill(new ImagePattern(img));
            circle1.setFill(new ImagePattern(img));

            userPointText.setText(user.getPoint() + " Point");

            Image fundImage = new Image("assets/img/cancer.jpg");
            fundImg.setFill(new ImagePattern(fundImage));

            Image donHisImage = new Image("assets/img/darna-logo.jpg");
            donHisImg.setFill(new ImagePattern(donHisImage));

            navFullname.setText(user.getFullname());
            navFullname1.setText(user.getFullname());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ProduitService ps = new ProduitService();
        totalNotif.setText("" + ps.getTotalNotif(user.getId()));
        List<Notification> notifList = ps.getUserNotifications(user.getId());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < notifList.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/productInterfaces/NotifItem.fxml"));
                HBox notifItem = fxmlLoader.load();
                NotifItemController notifController = fxmlLoader.getController();
                notifController.setNotifData(notifList.get(i));

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                notifContainer.add(notifItem, column++, row);
                // GridPane.setMargin(notifItem, new Insets(10));
                GridPane.setMargin(notifItem, new Insets(0, 20, 20, 10));

            }
        } catch (IOException e) {
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
        Parent fxml = FXMLLoader.load(getClass().getResource("/gui/userInterfaces/UserProfile.fxml"));
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
            } else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");

                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            } // else if (collectBtn.getStyleClass().contains("activeLink")) {
              // collectBtn.getStyleClass().remove("activeLink");

            // Image collectImg = new Image("assets/img/recycle.png");
            // collectIcon.setImage(collectImg);
            // }
            else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            } else if (achatBtn.getStyleClass().contains("activeLink")) {
                achatBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-bag.png");
                achatIcon.setImage(commandsImg);
            } else if (favBtn.getStyleClass().contains("activeLink")) {
                favBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/like.png");
                favIcon.setImage(commandsImg);
            }

        }
    }

    @FXML
    private void open_fundrisingList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/gui/fundraisingInterfaces/UserFundrisingsList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        // set active class
        if (!fundrisingBtn.getStyleClass().contains("activeLink")) {
            fundrisingBtn.getStyleClass().add("activeLink");

            // Load the image
            Image image = new Image("assets/img/heart-active.png");
            fundrisingIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");

                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            } else if (profileBtn.getStyleClass().contains("activeLink")) {
                profileBtn.getStyleClass().remove("activeLink");

                Image usersImg = new Image("assets/img/user.png");
                profileIcon.setImage(usersImg);
            } else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");

                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            } // else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
              // fundrisingBtn.getStyleClass().remove("activeLink");

            // Image fundrisingImg = new Image("assets/img/heart.png");
            // fundrisingIcon.setImage(fundrisingImg);
            // } else if (collectBtn.getStyleClass().contains("activeLink")) {
            // collectBtn.getStyleClass().remove("activeLink");

            // Image collectImg = new Image("assets/img/recycle.png");
            // collectIcon.setImage(collectImg);
            // }
            else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            } else if (achatBtn.getStyleClass().contains("activeLink")) {
                achatBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-bag.png");
                achatIcon.setImage(commandsImg);
            } else if (favBtn.getStyleClass().contains("activeLink")) {
                favBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/like.png");
                favIcon.setImage(commandsImg);
            }

        }
    }

    @FXML
    private void open_productsList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        Produit.setSortByCateg(null);
        Produit.setSortByCategId(-1);

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
            } else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");

                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            } // else if (collectBtn.getStyleClass().contains("activeLink")) {
              // collectBtn.getStyleClass().remove("activeLink");

            // Image collectImg = new Image("assets/img/recycle.png");
            // collectIcon.setImage(collectImg);
            // }
            else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            } else if (achatBtn.getStyleClass().contains("activeLink")) {
                achatBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-bag.png");
                achatIcon.setImage(commandsImg);
            } else if (favBtn.getStyleClass().contains("activeLink")) {
                favBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/like.png");
                favIcon.setImage(commandsImg);
            }

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

        Parent fxml = FXMLLoader.load(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        // set active class
        if (!commandsBtn.getStyleClass().contains("activeLink")) {
            commandsBtn.getStyleClass().add("activeLink");

            // Load the image
            Image image = new Image("assets/img/shopping-cart-active.png");
            commandsIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");

                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            } else if (profileBtn.getStyleClass().contains("activeLink")) {
                profileBtn.getStyleClass().remove("activeLink");

                Image usersImg = new Image("assets/img/user.png");
                profileIcon.setImage(usersImg);
            } else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");

                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            } else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");

                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            } // else if (collectBtn.getStyleClass().contains("activeLink")) {
              // collectBtn.getStyleClass().remove("activeLink");
              // collectText.getStyleClass().remove("activeText");

            // Image collectImg = new Image("assets/img/recycle.png");
            // collectIcon.setImage(collectImg);
            // }
            else if (achatBtn.getStyleClass().contains("activeLink")) {
                achatBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-bag.png");
                achatIcon.setImage(commandsImg);
            } else if (favBtn.getStyleClass().contains("activeLink")) {
                favBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/like.png");
                favIcon.setImage(commandsImg);
            }

        }

    }

    @FXML
    void open_favList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductFavoris.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        // set active class
        if (!favBtn.getStyleClass().contains("activeLink")) {
            favBtn.getStyleClass().add("activeLink");

            // Load the image
            Image image = new Image("assets/img/like-active.png");
            favIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");

                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            } else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");

                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            } else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");

                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            } // else if (collectBtn.getStyleClass().contains("activeLink")) {
              // collectBtn.getStyleClass().remove("activeLink");

            // Image collectImg = new Image("assets/img/recycle.png");
            // collectIcon.setImage(collectImg);
            // }
            else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            } else if (achatBtn.getStyleClass().contains("activeLink")) {
                achatBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-bag.png");
                achatIcon.setImage(commandsImg);
            } else if (profileBtn.getStyleClass().contains("activeLink")) {
                profileBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/user.png");
                profileIcon.setImage(commandsImg);
            }

        }
    }

    @FXML
    void open_achatList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductAchats.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        // set active class
        if (!achatBtn.getStyleClass().contains("activeLink")) {
            achatBtn.getStyleClass().add("activeLink");

            // Load the image
            Image image = new Image("assets/img/shopping-bag-active.png");
            achatIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");

                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            } else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");

                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            } else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");

                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            } // else if (collectBtn.getStyleClass().contains("activeLink")) {
              // collectBtn.getStyleClass().remove("activeLink");

            // Image collectImg = new Image("assets/img/recycle.png");
            // collectIcon.setImage(collectImg);
            // }
            else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            } else if (favBtn.getStyleClass().contains("activeLink")) {
                favBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/like.png");
                favIcon.setImage(commandsImg);
            } else if (profileBtn.getStyleClass().contains("activeLink")) {
                profileBtn.getStyleClass().remove("activeLink");

                Image commandsImg = new Image("assets/img/user.png");
                profileIcon.setImage(commandsImg);
            }

        }
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        UserSession.getInstance().cleanUserSession();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/userInterfaces/LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void open_notifModel(MouseEvent event) {
        // notifModel.setVisible(true);
        if (notifModel_isOpen == 0) {
            notifModel.setVisible(true);
            notifModel_isOpen = 1;
            return;
        }

        if (notifModel_isOpen == 1) {
            notifModel.setVisible(false);
            notifModel_isOpen = 0;
        }

    }

}
