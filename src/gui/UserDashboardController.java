package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    private HBox usersBtn;

    @FXML
    private Label usersText;

    @FXML
    private ImageView usersIcon;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

         Image img = new Image("assets/img/avatar2.png");
         circle.setFill(new ImagePattern(img));
    }    

    

    @FXML
    private void open_dashboard(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserDashboard.fxml"));
        ZeroWaste.stage.getScene().setRoot(root);
    }

    @FXML
    private void open_usersList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("UsersList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        //set active class 
        if (!usersBtn.getStyleClass().contains("activeLink")) {
            usersBtn.getStyleClass().add("activeLink");
            usersText.getStyleClass().add("activeText");

            // Load the image 
            Image image = new Image("assets/img/user-active.png");
            usersIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");
                dashboardText.getStyleClass().remove("activeText");
                
                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            }else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");
                fundrisingText.getStyleClass().remove("activeText");
                
                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            }else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");
                productsText.getStyleClass().remove("activeText");
                
                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            }else if (collectBtn.getStyleClass().contains("activeLink")) {
                collectBtn.getStyleClass().remove("activeLink");
                collectText.getStyleClass().remove("activeText");
                
                Image collectImg = new Image("assets/img/recycle.png");
                collectIcon.setImage(collectImg);
            }else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");
                commandsText.getStyleClass().remove("activeText");
                
                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            }
             
        }
    }

    @FXML
    private void open_fundrisingList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("FundrisingList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        //set active class 
        if (!fundrisingBtn.getStyleClass().contains("activeLink")) {
            fundrisingBtn.getStyleClass().add("activeLink");
            fundrisingText.getStyleClass().add("activeText");

            // Load the image 
            Image image = new Image("assets/img/heart-active.png");
            fundrisingIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");
                dashboardText.getStyleClass().remove("activeText");
                
                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            }else if (usersBtn.getStyleClass().contains("activeLink")) {
                usersBtn.getStyleClass().remove("activeLink");
                usersText.getStyleClass().remove("activeText");
                
                Image usersImg = new Image("assets/img/user.png");
                usersIcon.setImage(usersImg);
            }else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");
                productsText.getStyleClass().remove("activeText");
                
                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            }else if (collectBtn.getStyleClass().contains("activeLink")) {
                collectBtn.getStyleClass().remove("activeLink");
                collectText.getStyleClass().remove("activeText");
                
                Image collectImg = new Image("assets/img/recycle.png");
                collectIcon.setImage(collectImg);
            }else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");
                commandsText.getStyleClass().remove("activeText");
                
                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            }
             
        }
    }

    @FXML
    private void open_productsList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        //set active class 
        if (!productsBtn.getStyleClass().contains("activeLink")) {
            productsBtn.getStyleClass().add("activeLink");
            productsText.getStyleClass().add("activeText");

            // Load the image 
            Image image = new Image("assets/img/store-active.png");
            productsIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");
                dashboardText.getStyleClass().remove("activeText");
                
                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            }else if (usersBtn.getStyleClass().contains("activeLink")) {
                usersBtn.getStyleClass().remove("activeLink");
                usersText.getStyleClass().remove("activeText");
                
                Image usersImg = new Image("assets/img/user.png");
                usersIcon.setImage(usersImg);
            }else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");
                fundrisingText.getStyleClass().remove("activeText");
                
                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            }else if (collectBtn.getStyleClass().contains("activeLink")) {
                collectBtn.getStyleClass().remove("activeLink");
                collectText.getStyleClass().remove("activeText");
                
                Image collectImg = new Image("assets/img/recycle.png");
                collectIcon.setImage(collectImg);
            }else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");
                commandsText.getStyleClass().remove("activeText");
                
                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            }
             
        }
    }

    @FXML
    private void open_collectList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("CollectList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        //set active class 
        if (!collectBtn.getStyleClass().contains("activeLink")) {
            collectBtn.getStyleClass().add("activeLink");
            collectText.getStyleClass().add("activeText");

            // Load the image 
            Image image = new Image("assets/img/recycle-active.png");
            collectIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");
                dashboardText.getStyleClass().remove("activeText");
                
                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            }else if (usersBtn.getStyleClass().contains("activeLink")) {
                usersBtn.getStyleClass().remove("activeLink");
                usersText.getStyleClass().remove("activeText");
                
                Image usersImg = new Image("assets/img/user.png");
                usersIcon.setImage(usersImg);
            }else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");
                fundrisingText.getStyleClass().remove("activeText");
                
                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            }else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");
                productsText.getStyleClass().remove("activeText");
                
                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            }else if (commandsBtn.getStyleClass().contains("activeLink")) {
                commandsBtn.getStyleClass().remove("activeLink");
                commandsText.getStyleClass().remove("activeText");
                
                Image commandsImg = new Image("assets/img/shopping-cart.png");
                commandsIcon.setImage(commandsImg);
            }
             
        }
    }

    @FXML
    private void open_commandsList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("CommandsList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        //set active class 
        if (!commandsBtn.getStyleClass().contains("activeLink")) {
            commandsBtn.getStyleClass().add("activeLink");
            commandsText.getStyleClass().add("activeText");

            // Load the image 
            Image image = new Image("assets/img/shopping-cart-active.png");
            commandsIcon.setImage(image);

            if (dashboardBtn.getStyleClass().contains("activeLink")) {
                dashboardBtn.getStyleClass().remove("activeLink");
                dashboardText.getStyleClass().remove("activeText");
                
                Image dashIcon = new Image("assets/img/menu.png");
                dashboardIcon.setImage(dashIcon);
            }else if (usersBtn.getStyleClass().contains("activeLink")) {
                usersBtn.getStyleClass().remove("activeLink");
                usersText.getStyleClass().remove("activeText");
                
                Image usersImg = new Image("assets/img/user.png");
                usersIcon.setImage(usersImg);
            }else if (fundrisingBtn.getStyleClass().contains("activeLink")) {
                fundrisingBtn.getStyleClass().remove("activeLink");
                fundrisingText.getStyleClass().remove("activeText");
                
                Image fundrisingImg = new Image("assets/img/heart.png");
                fundrisingIcon.setImage(fundrisingImg);
            }else if (productsBtn.getStyleClass().contains("activeLink")) {
                productsBtn.getStyleClass().remove("activeLink");
                productsText.getStyleClass().remove("activeText");
                
                Image productsImg = new Image("assets/img/store.png");
                productsIcon.setImage(productsImg);
            }else if (collectBtn.getStyleClass().contains("activeLink")) {
                collectBtn.getStyleClass().remove("activeLink");
                collectText.getStyleClass().remove("activeText");
                
                Image collectImg = new Image("assets/img/recycle.png");
                collectIcon.setImage(collectImg);
            }
             
        }
    }

   

    
}
