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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.UserSession;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.SendMail;
import utils.TrayNotificationAlert;
import javafx.util.Duration;

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

    @FXML
    private HBox deleteReview;

    @FXML
    private HBox editReview;

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

        if (user.getId() == review.getUser_id()) {

            deleteReview.setVisible(true);
            editReview.setVisible(true);
            // deleteReview btn click
            deleteReview.setId(String.valueOf(review.getId()));
            ProduitService produitService = new ProduitService();

            deleteReview.setOnMouseClicked(event -> {
                System.out.println("ID du review à supprimer : " + review.getId());
                try {
                    produitService.supprimerReview(review.getId());
                    TrayNotificationAlert.notif("Review", "Review deleted successfully.",
                            NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // supprimer le contenu de la liste et afficher la nouvelle liste(apres
                // supprimer)
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/gui/productInterfaces/UserProductDetails.fxml"));
                try {
                    Parent root = loader.load();
                    // Accéder à la pane content_area depuis le controller de
                    // OneProductListCard.fxml
                    Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                    // Vider la pane et afficher le contenu de ProductsList.fxml
                    contentArea.getChildren().clear();
                    contentArea.getChildren().add(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // end
            });
            // END deleteProduit btn click

            // editReview btn click
            editReview.setId(String.valueOf(review.getId()));

            editReview.setOnMouseClicked(event -> {
                System.out.println("ID du review à modifier : " + review.getId());
                // produitService.supprimerReview(review.getId());
                TextField titleInput = (TextField) ((Node) event.getSource()).getScene().lookup("#titleInput");
                titleInput.setText(review.getTitle());

                TextArea commentInput = (TextArea) ((Node) event.getSource()).getScene().lookup("#commentInput");
                commentInput.setText(review.getComment());

                Produit.setValueReviews(review.getValue());
                Produit.setReviewId(review.getId());

                HBox updateBtnContainer = (HBox) ((Node) event.getSource()).getScene().lookup("#updateBtnContainer");
                updateBtnContainer.setVisible(true);

                HBox submitBtn = (HBox) ((Node) event.getSource()).getScene().lookup("#submitBtn");
                submitBtn.setVisible(false);

                HBox addReviewsModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addReviewsModel");
                addReviewsModel.setVisible(true);

                Image fullImage = new Image("/assets/img/star.png");
                Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

                ImageView reviewAddStar1 = (ImageView) ((Node) event.getSource()).getScene().lookup("#reviewAddStar1");
                ImageView reviewAddStar2 = (ImageView) ((Node) event.getSource()).getScene().lookup("#reviewAddStar2");
                ImageView reviewAddStar3 = (ImageView) ((Node) event.getSource()).getScene().lookup("#reviewAddStar3");
                ImageView reviewAddStar4 = (ImageView) ((Node) event.getSource()).getScene().lookup("#reviewAddStar4");
                ImageView reviewAddStar5 = (ImageView) ((Node) event.getSource()).getScene().lookup("#reviewAddStar5");

                if (review.getValue() == 1) {

                    reviewAddStar1.setImage(fullImage);
                    reviewAddStar2.setImage(emptyImage);
                    reviewAddStar3.setImage(emptyImage);
                    reviewAddStar4.setImage(emptyImage);
                    reviewAddStar5.setImage(emptyImage);
                }
                if (review.getValue() == 2) {

                    reviewAddStar1.setImage(fullImage);
                    reviewAddStar2.setImage(fullImage);
                    reviewAddStar3.setImage(emptyImage);
                    reviewAddStar4.setImage(emptyImage);
                    reviewAddStar5.setImage(emptyImage);
                }
                if (review.getValue() == 3) {

                    reviewAddStar1.setImage(fullImage);
                    reviewAddStar2.setImage(fullImage);
                    reviewAddStar3.setImage(fullImage);
                    reviewAddStar4.setImage(emptyImage);
                    reviewAddStar5.setImage(emptyImage);
                }
                if (review.getValue() == 4) {

                    reviewAddStar1.setImage(fullImage);
                    reviewAddStar2.setImage(fullImage);
                    reviewAddStar3.setImage(fullImage);
                    reviewAddStar4.setImage(fullImage);
                    reviewAddStar5.setImage(emptyImage);
                }
                if (review.getValue() == 5) {

                    reviewAddStar1.setImage(fullImage);
                    reviewAddStar2.setImage(fullImage);
                    reviewAddStar3.setImage(fullImage);
                    reviewAddStar4.setImage(fullImage);
                    reviewAddStar5.setImage(fullImage);
                }
                // addReviewsModel.setVisible(true);

            });
            // END editProduit btn click
        } else {
            deleteReview.setVisible(false);
            editReview.setVisible(false);
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
