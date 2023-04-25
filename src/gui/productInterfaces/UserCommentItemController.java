package gui.productInterfaces;

import java.io.IOException;
import java.sql.SQLException;

import entities.Commands;
import entities.Produit;
import entities.Reviews;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.CommandsProduitService;
import services.CommandsService;
import services.IProduitService;
import services.ProduitService;
import services.UserService;
import javafx.scene.Node;
import javafx.scene.Parent;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserCommentItemController {

    @FXML
    private Text comment;

    @FXML
    private ImageView commentStar1;

    @FXML
    private ImageView commentStar2;

    @FXML
    private ImageView commentStar3;

    @FXML
    private ImageView commentStar4;

    @FXML
    private ImageView commentStar5;

    @FXML
    private Text date;

    @FXML
    private Text title;

    @FXML
    private Text userName;

    public void setReviewData(Reviews review) {
        // Instancier le service de produit
        // IProduitService produitService = new ProduitService();
        title.setText(review.getTitle());
        comment.setText(review.getComment());
        date.setText("" + review.getDate_ajout());

        if (review.getValue() == 4) {
            commentStar5.setVisible(false);
        }
        if (review.getValue() == 3) {
            commentStar5.setVisible(false);
            commentStar4.setVisible(false);
        }
        if (review.getValue() == 2) {
            commentStar5.setVisible(false);
            commentStar4.setVisible(false);
            commentStar3.setVisible(false);
        }
        if (review.getValue() == 1) {
            commentStar5.setVisible(false);
            commentStar4.setVisible(false);
            commentStar3.setVisible(false);
            commentStar2.setVisible(false);
        }

        User user = new User();
        UserService userService = new UserService();

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

        // Open Product Details
        // open_productDetails.setOnMouseClicked(event -> {
        // System.out.println("ID du produit à afficher les details : " +
        // produit.getId());
        // Produit.setIdProduit(produit.getId());

        // FXMLLoader loader = new FXMLLoader(
        // getClass().getResource("/gui/productInterfaces/UserProductDetails.fxml"));
        // try {
        // Parent root = loader.load();
        // // Accéder à la pane content_area depuis ce controller
        // Pane contentArea = (Pane) ((Node)
        // event.getSource()).getScene().lookup("#content_area");

        // // Vider la pane et afficher le contenu de AddProduct.fxml
        // contentArea.getChildren().clear();
        // contentArea.getChildren().add(root);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // });
        // END - Open Product Details
    }

}
