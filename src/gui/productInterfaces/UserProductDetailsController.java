package gui.productInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import entities.Categorie_Collecte;
import entities.Collecte;
import entities.Reviews;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.Categorie_produitService;
import services.ICategorie_produitService;
import services.IProduitService;
import services.ProduitService;
import services.UserService;
import utils.UserSession;
import javafx.scene.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;

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
public class UserProductDetailsController implements Initializable {

    @FXML
    private Pane content_area;

    @FXML
    private GridPane productDetailsContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox addReviewsModel;

    @FXML
    private ImageView reviewAddStar1;

    @FXML
    private ImageView reviewAddStar2;

    @FXML
    private ImageView reviewAddStar3;

    @FXML
    private ImageView reviewAddStar4;

    @FXML
    private ImageView reviewAddStar5;

    @FXML
    private HBox submitBtn;

    @FXML
    private HBox updateBtnContainer;

    @FXML
    private TextField titleInput;

    @FXML
    private TextArea commentInput;

    private int value = 0;
    private User user = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addReviewsModel.setVisible(false);
        updateBtnContainer.setVisible(false);

        value = Collecte.getValueReviews();
        Collecte.setValueReviews(0);

        try {

            // Ajouter productContainer à la première ligne de AddproductContainer
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("/gui/productInterfaces/UserProductDetailsCard.fxml"));
            VBox productContainer1 = fxmlLoader1.load();
            productDetailsContainer.add(productContainer1, 0, 1);
            GridPane.setMargin(productContainer1, new Insets(0, 10, 25, 10));
            // productContainer1.setStyle("-fx-effect: dropshadow(three-pass-box,
            // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // recuperer user connecté
        user = new User();

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

    }

    @FXML
    void close_addReviewsModel(MouseEvent event) {
        addReviewsModel.setVisible(false);
    }

    @FXML
    void hoverValue1(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(fullImage);
            reviewAddStar2.setImage(emptyImage);
            reviewAddStar3.setImage(emptyImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void cancelHoverValue1(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(emptyImage);
            reviewAddStar2.setImage(emptyImage);
            reviewAddStar3.setImage(emptyImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void setValue1(MouseEvent event) {

        value = 1;
        Image fullImage = new Image("/assets/img/star.png");
        Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

        reviewAddStar1.setImage(fullImage);
        reviewAddStar2.setImage(emptyImage);
        reviewAddStar3.setImage(emptyImage);
        reviewAddStar4.setImage(emptyImage);
        reviewAddStar5.setImage(emptyImage);
    }

    @FXML
    void hoverValue2(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(fullImage);
            reviewAddStar2.setImage(fullImage);
            reviewAddStar3.setImage(emptyImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void cancelHoverValue2(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(emptyImage);
            reviewAddStar2.setImage(emptyImage);
            reviewAddStar3.setImage(emptyImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void setValue2(MouseEvent event) {

        value = 2;
        Image fullImage = new Image("/assets/img/star.png");
        Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

        reviewAddStar1.setImage(fullImage);
        reviewAddStar2.setImage(fullImage);
        reviewAddStar3.setImage(emptyImage);
        reviewAddStar4.setImage(emptyImage);
        reviewAddStar5.setImage(emptyImage);
    }

    @FXML
    void hoverValue3(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(fullImage);
            reviewAddStar2.setImage(fullImage);
            reviewAddStar3.setImage(fullImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void cancelHoverValue3(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(emptyImage);
            reviewAddStar2.setImage(emptyImage);
            reviewAddStar3.setImage(emptyImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void setValue3(MouseEvent event) {

        value = 3;
        Image fullImage = new Image("/assets/img/star.png");
        Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

        reviewAddStar1.setImage(fullImage);
        reviewAddStar2.setImage(fullImage);
        reviewAddStar3.setImage(fullImage);
        reviewAddStar4.setImage(emptyImage);
        reviewAddStar5.setImage(emptyImage);
    }

    @FXML
    void hoverValue4(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(fullImage);
            reviewAddStar2.setImage(fullImage);
            reviewAddStar3.setImage(fullImage);
            reviewAddStar4.setImage(fullImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void cancelHoverValue4(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(emptyImage);
            reviewAddStar2.setImage(emptyImage);
            reviewAddStar3.setImage(emptyImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void setValue4(MouseEvent event) {

        value = 4;
        Image fullImage = new Image("/assets/img/star.png");
        Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

        reviewAddStar1.setImage(fullImage);
        reviewAddStar2.setImage(fullImage);
        reviewAddStar3.setImage(fullImage);
        reviewAddStar4.setImage(fullImage);
        reviewAddStar5.setImage(emptyImage);
    }

    @FXML
    void hoverValue5(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(fullImage);
            reviewAddStar2.setImage(fullImage);
            reviewAddStar3.setImage(fullImage);
            reviewAddStar4.setImage(fullImage);
            reviewAddStar5.setImage(fullImage);
        }

    }

    @FXML
    void cancelHoverValue5(MouseEvent event) {
        if (value == 0) {
            Image fullImage = new Image("/assets/img/star.png");
            Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

            reviewAddStar1.setImage(emptyImage);
            reviewAddStar2.setImage(emptyImage);
            reviewAddStar3.setImage(emptyImage);
            reviewAddStar4.setImage(emptyImage);
            reviewAddStar5.setImage(emptyImage);
        }

    }

    @FXML
    void setValue5(MouseEvent event) {

        value = 5;
        Image fullImage = new Image("/assets/img/star.png");
        Image emptyImage = new Image("/assets/img/reviewsEmptyStar.png");

        reviewAddStar1.setImage(fullImage);
        reviewAddStar2.setImage(fullImage);
        reviewAddStar3.setImage(fullImage);
        reviewAddStar4.setImage(fullImage);
        reviewAddStar5.setImage(fullImage);
    }

    @FXML
    void add_new_comment(MouseEvent event) {
        Reviews review = new Reviews();

        review.setUser_id(user.getId());
        review.setProduct_id(Collecte.getIdProduit());
        review.setTitle(titleInput.getText());
        review.setComment(commentInput.getText());

        review.setValue(value);

        String comment = commentInput.getText();
        if (containsBadWords(comment)) {
            // Le commentaire contient des mots inappropriés, ne pas l'ajouter à la review
            // Afficher un message à l'utilisateur pour lui demander de modifier son
            // commentaire
            TrayNotificationAlert.notif("Bad Word Detected", "you should not use bad words.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));
            return;
        } else {
            review.setComment(comment);
        }

        ProduitService produitService = new ProduitService();
        if (value != 0) {
            produitService.addReview(review);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductDetails.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TrayNotificationAlert.notif("Review", "Review added successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
        } else {
            TrayNotificationAlert.notif("Review", "you should select a value.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));

        }
    }

    public boolean containsBadWords(String comment) {
        try {
            File file = new File("src/utils/badwords.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String badWord = scanner.nextLine();
                if (comment.toLowerCase().contains(badWord.toLowerCase())) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    void update_review(MouseEvent event) {
        Reviews review = new Reviews();

        review.setId(Collecte.getReviewId());
        review.setTitle(titleInput.getText());
        review.setComment(commentInput.getText());
        review.setValue(Collecte.getValueReviews());

        String comment = commentInput.getText();
        if (containsBadWords(comment)) {
            // Le commentaire contient des mots inappropriés, ne pas l'ajouter à la review
            // Afficher un message à l'utilisateur pour lui demander de modifier son
            // commentaire
            TrayNotificationAlert.notif("Bad Word Detected", "you should not use bad words.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));
            return;
        } else {
            review.setComment(comment);
        }

        ProduitService produitService = new ProduitService();
        if (Collecte.getValueReviews() != 0) {
            produitService.updateReview(review);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductDetails.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TrayNotificationAlert.notif("Review", "Review added successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
        } else {
            TrayNotificationAlert.notif("Review", "you should select a value.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));

        }
    }

}
