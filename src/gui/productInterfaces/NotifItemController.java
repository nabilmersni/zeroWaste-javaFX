package gui.productInterfaces;

import java.io.IOException;
import java.sql.SQLException;

import entities.Commands;
import entities.Produit;
import entities.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import services.CommandsProduitService;
import services.CommandsService;
import services.IProduitService;
import services.ProduitService;
import services.UserService;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import utils.UserSession;
import entities.Notification;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.SendMail;
import utils.TrayNotificationAlert;
import javafx.util.Duration;

import zerowaste.ZeroWaste;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class NotifItemController {

    @FXML
    private Text date;

    @FXML
    private Circle img;

    @FXML
    private Label title;

    @FXML
    private VBox markNotifAsReadBtn;

    User user = null;

    public void setNotifData(Notification notif) {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        try {
            produit = produitService.getOneProduct(notif.getProduct_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Image imag = new Image("assets/ProductUploads/" + produit.getImage());
        img.setFill(new ImagePattern(imag));

        title.setText(produit.getNom_produit());

        date.setText(notif.getDate());

        // recuperer user connecté

        UserService userService = new UserService();

        user = new User();
        if (UserSession.getInstance().getEmail() == null) {

            try {
                user = userService.getOneUser("nabilkdp0@gmail.com");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(user.getId());

        } else {
            try {
                user = userService.getOneUser(UserSession.getInstance().getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(user.getId());

        }
        // markNotifAsReadBtn btn click
        // deleteProduit.setId(String.valueOf(produit.getId()));

        markNotifAsReadBtn.setOnMouseClicked(event -> {

            produitService.MakeAsReadNotif(user.getId(), notif.getId());
            TrayNotificationAlert.notif("Notification", "Notification maked as read successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
            // supprimer le contenu de la liste et afficher la nouvelle liste(apres
            // supprimer)
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/gui/UserDashboard.fxml"));
                ZeroWaste.stage.getScene().setRoot(root);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // end
        });
        // END markNotifAsReadBtn btn click

    }

}
