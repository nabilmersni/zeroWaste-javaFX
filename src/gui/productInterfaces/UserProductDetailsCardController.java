package gui.productInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Categorie_produit;
import entities.Produit;
import entities.Reviews;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
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
import java.io.IOException;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserProductDetailsCardController implements Initializable {

    @FXML
    private Text category;

    @FXML
    private GridPane commentsListContainer;

    @FXML
    private VBox content_area;

    @FXML
    private Text description;

    @FXML
    private ImageView img;

    @FXML
    private Text offre;

    @FXML
    private HBox offreRow;

    @FXML
    private HBox rangeTotal1;

    @FXML
    private HBox rangeTotal2;

    @FXML
    private HBox rangeTotal3;

    @FXML
    private HBox rangeTotal4;

    @FXML
    private HBox rangeTotal5;

    @FXML
    private Text points;

    @FXML
    private Text priceAfter;

    @FXML
    private Text priceBefore;

    @FXML
    private Text title;

    @FXML
    private Text total_verif_reviews;

    @FXML
    private Text reviews_totalRev_title;

    @FXML
    private Text reviews_total_box;

    @FXML
    private Text percentReviews;

    @FXML
    private Text totalBy1;

    @FXML
    private Text totalBy2;

    @FXML
    private Text totalBy3;

    @FXML
    private Text totalBy4;

    @FXML
    private Text totalBy5;

    @FXML
    private Text percentBy5;

    @FXML
    private Text percentBy4;

    @FXML
    private Text percentBy3;

    @FXML
    private Text percentBy2;

    @FXML
    private Text percentBy1;

    @FXML
    private ImageView star1;

    @FXML
    private ImageView star2;

    @FXML
    private ImageView star3;

    @FXML
    private ImageView star4;

    @FXML
    private ImageView star5;

    @FXML
    private ImageView reviewsBox_star1;

    @FXML
    private ImageView reviewsBox_star2;

    @FXML
    private ImageView reviewsBox_star3;

    @FXML
    private ImageView reviewsBox_star4;

    @FXML
    private ImageView reviewsBox_star5;

    @FXML
    private ImageView reviewsBox_star51;

    @FXML
    private ImageView reviewsBox_star41;

    @FXML
    private ImageView reviewsBox_star31;

    @FXML
    private ImageView reviewsBox_star21;

    @FXML
    private ImageView reviewsBox_star11;

    private User user = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

        // set product details
        ProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        try {
            produit = produitService.getOneProduct(Produit.getIdProduit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(produit);

        title.setText(produit.getNom_produit());

        Image image = new Image(
                getClass().getResource("/assets/ProductUploads/" + produit.getImage()).toExternalForm());
        img.setImage(image);

        // get category Name
        String categoryName = produitService.getCategory(produit.getCategorie_produit_id());
        category.setText(categoryName);

        float prixApresOffre = 0;

        if (produit.getRemise() == 0) {
            offreRow.setVisible(false);

            priceAfter.setText("" + produit.getPrix_produit());
        } else {
            offreRow.setVisible(true);
            priceBefore.setText("" + produit.getPrix_produit());

            prixApresOffre = (float) (produit.getPrix_produit()
                    - (produit.getPrix_produit() * produit.getRemise() / 100.0));
            String prixApresOffreStr = String.format("%.1f", prixApresOffre);
            priceAfter.setText(prixApresOffreStr);
        }

        description.setText(produit.getDescription());

        int produitId = Produit.getIdProduit();
        int totalReviews = produitService.getTotalProductReviews(produitId);
        total_verif_reviews.setText("" + totalReviews);
        Image starImage = new Image(
                getClass().getResource("/assets/img/star-empty.png").toExternalForm());
        if (totalReviews == 0) {

            star1.setImage(starImage);
            star2.setImage(starImage);
            star3.setImage(starImage);
            star4.setImage(starImage);
            star5.setImage(starImage);
        }

        // END - set product details

        // set comments details
        reviews_totalRev_title.setText("" + totalReviews);
        reviews_total_box.setText("" + totalReviews);

        float totalPercentStar = 0;
        float percent5 = 0;
        float percent4 = 0;
        float percent3 = 0;
        float percent2 = 0;
        float percent1 = 0;
        if (totalReviews == 0) {
            reviewsBox_star1.setImage(starImage);
            reviewsBox_star2.setImage(starImage);
            reviewsBox_star3.setImage(starImage);
            reviewsBox_star4.setImage(starImage);
            reviewsBox_star5.setImage(starImage);
            percentReviews.setText("" + 0);

            percentBy5.setText("0");
            percentBy4.setText("0");
            percentBy3.setText("0");
            percentBy2.setText("0");
            percentBy1.setText("0");
            rangeTotal5.setPrefWidth(0);
            rangeTotal4.setPrefWidth(0);
            rangeTotal3.setPrefWidth(0);
            rangeTotal2.setPrefWidth(0);
            rangeTotal1.setPrefWidth(0);

        }

        int totalCountBy5 = produitService.getTotalProductReviewsByStar(produitId, 5);
        int totalCountBy4 = produitService.getTotalProductReviewsByStar(produitId, 4);
        int totalCountBy3 = produitService.getTotalProductReviewsByStar(produitId, 3);
        int totalCountBy2 = produitService.getTotalProductReviewsByStar(produitId, 2);
        int totalCountBy1 = produitService.getTotalProductReviewsByStar(produitId, 1);
        totalBy5.setText("" + totalCountBy5);
        totalBy4.setText("" + totalCountBy4);
        totalBy3.setText("" + totalCountBy3);
        totalBy2.setText("" + totalCountBy2);
        totalBy1.setText("" + totalCountBy1);
        // Width: total: 114
        if (totalReviews != 0) {
            rangeTotal5.setPrefWidth(totalCountBy5 * 114 / totalReviews);
            rangeTotal4.setPrefWidth(totalCountBy4 * 114 / totalReviews);
            rangeTotal3.setPrefWidth(totalCountBy3 * 114 / totalReviews);
            rangeTotal2.setPrefWidth(totalCountBy2 * 114 / totalReviews);
            rangeTotal1.setPrefWidth(totalCountBy1 * 114 / totalReviews);

            percent5 = (float) (totalCountBy5 * 100 / totalReviews);
            String percent5Str = String.format("%.1f", percent5);
            percentBy5.setText(percent5Str);

            percent4 = (float) (totalCountBy4 * 100 / totalReviews);
            String percent4Str = String.format("%.1f", percent4);
            percentBy4.setText(percent4Str);

            percent3 = (float) (totalCountBy3 * 100 / totalReviews);
            String percent3Str = String.format("%.1f", percent3);
            percentBy3.setText(percent3Str);

            percent2 = (float) (totalCountBy2 * 100 / totalReviews);
            String percent2Str = String.format("%.1f", percent2);
            percentBy2.setText(percent2Str);

            percent1 = (float) (totalCountBy1 * 100 / totalReviews);
            String percent1Str = String.format("%.1f", percent1);
            percentBy1.setText(percent1Str);

            totalPercentStar = (percent5 / 100 * 5) + (percent4 / 100 * 4) + (percent3 / 100 * 3) + (percent2 / 100 * 2)
                    + (percent1 / 100 * 1);
            String totalPercentStarStr = String.format("%.1f", totalPercentStar);
            percentReviews.setText(totalPercentStarStr);

        }

        Image starEmptyImage = new Image(
                getClass().getResource("/assets/img/star-empty.png").toExternalForm());
        Image fullStar = new Image(
                getClass().getResource("/assets/img/star.png").toExternalForm());
        Image star1 = new Image(
                getClass().getResource("/assets/img/star1.png").toExternalForm());
        Image star2 = new Image(
                getClass().getResource("/assets/img/star2.png").toExternalForm());
        Image star3 = new Image(
                getClass().getResource("/assets/img/star3.png").toExternalForm());
        Image star4 = new Image(
                getClass().getResource("/assets/img/star4.png").toExternalForm());
        Image star5 = new Image(
                getClass().getResource("/assets/img/star5.png").toExternalForm());
        Image star6 = new Image(
                getClass().getResource("/assets/img/star6.png").toExternalForm());
        Image star7 = new Image(
                getClass().getResource("/assets/img/star7.png").toExternalForm());
        Image star8 = new Image(
                getClass().getResource("/assets/img/star8.png").toExternalForm());
        Image star9 = new Image(
                getClass().getResource("/assets/img/star9.png").toExternalForm());

        if (totalPercentStar == 0) {
            reviewsBox_star1.setImage(starEmptyImage);
            reviewsBox_star2.setImage(starEmptyImage);
            reviewsBox_star3.setImage(starEmptyImage);
            reviewsBox_star4.setImage(starEmptyImage);
            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }
        if (totalPercentStar == 1) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(starEmptyImage);
            reviewsBox_star3.setImage(starEmptyImage);
            reviewsBox_star4.setImage(starEmptyImage);
            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar == 2) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(fullStar);
            reviewsBox_star3.setImage(starEmptyImage);
            reviewsBox_star4.setImage(starEmptyImage);
            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar == 3) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(fullStar);
            reviewsBox_star3.setImage(fullStar);
            reviewsBox_star4.setImage(starEmptyImage);
            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar == 4) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(fullStar);
            reviewsBox_star3.setImage(fullStar);
            reviewsBox_star4.setImage(fullStar);
            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar == 5) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(fullStar);
            reviewsBox_star3.setImage(fullStar);
            reviewsBox_star4.setImage(fullStar);
            reviewsBox_star5.setImage(fullStar);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar > 1 && totalPercentStar < 2) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(starEmptyImage);
            if (totalPercentStar > 1 && totalPercentStar < 1.1) {
                reviewsBox_star21.setImage(starEmptyImage);
            }
            if (totalPercentStar >= 1.1 && totalPercentStar < 1.2) {
                reviewsBox_star21.setImage(star1);
            }
            if (totalPercentStar >= 1.2 && totalPercentStar < 1.3) {
                reviewsBox_star21.setImage(star2);
            }
            if (totalPercentStar >= 1.3 && totalPercentStar < 1.4) {
                reviewsBox_star21.setImage(star3);
            }
            if (totalPercentStar >= 1.4 && totalPercentStar < 1.5) {
                reviewsBox_star21.setImage(star4);
            }
            if (totalPercentStar >= 1.5 && totalPercentStar < 1.6) {
                reviewsBox_star21.setImage(star5);
            }
            if (totalPercentStar >= 1.6 && totalPercentStar < 1.7) {
                reviewsBox_star21.setImage(star6);
            }
            if (totalPercentStar >= 1.7 && totalPercentStar < 1.8) {
                reviewsBox_star21.setImage(star7);
            }
            if (totalPercentStar >= 1.8 && totalPercentStar < 1.9) {
                reviewsBox_star21.setImage(star8);
            }
            if (totalPercentStar >= 1.9 && totalPercentStar < 2) {
                reviewsBox_star21.setImage(star9);
            }

            reviewsBox_star3.setImage(starEmptyImage);
            reviewsBox_star4.setImage(starEmptyImage);
            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star1.setVisible(true);
            reviewsBox_star2.setVisible(true);
            reviewsBox_star3.setVisible(true);
            reviewsBox_star4.setVisible(true);
            reviewsBox_star5.setVisible(true);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(true);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar > 2 && totalPercentStar < 3) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(fullStar);
            reviewsBox_star3.setImage(starEmptyImage);
            if (totalPercentStar > 2 && totalPercentStar < 2.1) {
                reviewsBox_star31.setImage(starEmptyImage);
            }
            if (totalPercentStar >= 2.1 && totalPercentStar < 2.2) {
                reviewsBox_star31.setImage(star1);
            }
            if (totalPercentStar >= 2.2 && totalPercentStar < 2.3) {
                reviewsBox_star31.setImage(star2);
            }
            if (totalPercentStar >= 2.3 && totalPercentStar < 2.4) {
                reviewsBox_star31.setImage(star3);
            }
            if (totalPercentStar >= 2.4 && totalPercentStar < 2.5) {
                reviewsBox_star31.setImage(star4);
            }
            if (totalPercentStar >= 2.5 && totalPercentStar < 2.6) {
                reviewsBox_star31.setImage(star5);
            }
            if (totalPercentStar >= 2.6 && totalPercentStar < 2.7) {
                reviewsBox_star31.setImage(star6);
            }
            if (totalPercentStar >= 2.7 && totalPercentStar < 2.8) {
                reviewsBox_star31.setImage(star7);
            }
            if (totalPercentStar >= 2.8 && totalPercentStar < 2.9) {
                reviewsBox_star31.setImage(star8);
            }
            if (totalPercentStar >= 2.9 && totalPercentStar < 3) {
                reviewsBox_star31.setImage(star9);
            }

            reviewsBox_star4.setImage(starEmptyImage);
            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star1.setVisible(true);
            reviewsBox_star2.setVisible(true);
            reviewsBox_star3.setVisible(true);
            reviewsBox_star4.setVisible(true);
            reviewsBox_star5.setVisible(true);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(true);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar > 3 && totalPercentStar < 4) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(fullStar);
            reviewsBox_star3.setImage(fullStar);
            reviewsBox_star4.setImage(starEmptyImage);
            if (totalPercentStar > 3 && totalPercentStar < 3.1) {
                reviewsBox_star41.setImage(starEmptyImage);
            }
            if (totalPercentStar >= 3.1 && totalPercentStar < 3.2) {
                reviewsBox_star41.setImage(star1);
            }
            if (totalPercentStar >= 3.2 && totalPercentStar < 3.3) {
                reviewsBox_star41.setImage(star2);
            }
            if (totalPercentStar >= 3.3 && totalPercentStar < 3.4) {
                reviewsBox_star41.setImage(star3);
            }
            if (totalPercentStar >= 3.4 && totalPercentStar < 3.5) {
                reviewsBox_star41.setImage(star4);
            }
            if (totalPercentStar >= 3.5 && totalPercentStar < 3.6) {
                reviewsBox_star41.setImage(star5);
            }
            if (totalPercentStar >= 3.6 && totalPercentStar < 3.7) {
                reviewsBox_star41.setImage(star6);
            }
            if (totalPercentStar >= 3.7 && totalPercentStar < 3.8) {
                reviewsBox_star41.setImage(star7);
            }
            if (totalPercentStar >= 3.8 && totalPercentStar < 3.9) {
                reviewsBox_star41.setImage(star8);
            }
            if (totalPercentStar >= 3.9 && totalPercentStar < 4) {
                reviewsBox_star41.setImage(star9);
            }

            reviewsBox_star5.setImage(starEmptyImage);

            reviewsBox_star1.setVisible(true);
            reviewsBox_star2.setVisible(true);
            reviewsBox_star3.setVisible(true);
            reviewsBox_star4.setVisible(true);
            reviewsBox_star5.setVisible(true);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(true);
            reviewsBox_star51.setVisible(false);
        }

        if (totalPercentStar > 4 && totalPercentStar < 5) {
            reviewsBox_star1.setImage(fullStar);
            reviewsBox_star2.setImage(fullStar);
            reviewsBox_star3.setImage(fullStar);
            reviewsBox_star4.setImage(fullStar);
            reviewsBox_star5.setImage(starEmptyImage);
            if (totalPercentStar > 4 && totalPercentStar < 4.1) {
                reviewsBox_star51.setImage(starEmptyImage);
            }
            if (totalPercentStar >= 4.1 && totalPercentStar < 4.2) {
                reviewsBox_star51.setImage(star1);
            }
            if (totalPercentStar >= 4.2 && totalPercentStar < 4.3) {
                reviewsBox_star51.setImage(star2);
            }
            if (totalPercentStar >= 4.3 && totalPercentStar < 4.4) {
                reviewsBox_star51.setImage(star3);
            }
            if (totalPercentStar >= 4.4 && totalPercentStar < 4.5) {
                reviewsBox_star51.setImage(star4);
            }
            if (totalPercentStar >= 4.5 && totalPercentStar < 4.6) {
                reviewsBox_star51.setImage(star5);
            }
            if (totalPercentStar >= 4.6 && totalPercentStar < 4.7) {
                reviewsBox_star51.setImage(star6);
            }
            if (totalPercentStar >= 4.7 && totalPercentStar < 4.8) {
                reviewsBox_star51.setImage(star7);
            }
            if (totalPercentStar >= 4.8 && totalPercentStar < 4.9) {
                reviewsBox_star51.setImage(star8);
            }
            if (totalPercentStar >= 4.9 && totalPercentStar < 5) {
                reviewsBox_star51.setImage(star9);
            }

            reviewsBox_star1.setVisible(true);
            reviewsBox_star2.setVisible(true);
            reviewsBox_star3.setVisible(true);
            reviewsBox_star4.setVisible(true);
            reviewsBox_star5.setVisible(true);

            reviewsBox_star11.setVisible(false);
            reviewsBox_star21.setVisible(false);
            reviewsBox_star31.setVisible(false);
            reviewsBox_star41.setVisible(false);
            reviewsBox_star51.setVisible(true);
        }

        // END - set comments details

        Reviews review = new Reviews();
        produitService = new ProduitService();

        List<Reviews> reviewsList = produitService.getAllComments(Produit.getIdProduit());

        // Set comments List
        int CommentColumn = 0;
        int CommentRow = 1;
        try {
            for (int i = 0; i < reviewsList.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/productInterfaces/UserCommentItem.fxml"));
                VBox commentItem = fxmlLoader.load();
                UserCommentItemController userCommentItemController = fxmlLoader.getController();
                userCommentItemController.setReviewData(reviewsList.get(i));

                if (CommentColumn == 1) {
                    CommentColumn = 0;
                    ++CommentRow;
                }
                commentsListContainer.add(commentItem, CommentColumn++, CommentRow);
                GridPane.setMargin(commentItem, new Insets(0, 10, 15, 10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // try {

        // // Ajouter productContainer à la première ligne de AddproductContainer
        // FXMLLoader fxmlLoader1 = new FXMLLoader();
        // fxmlLoader1.setLocation(getClass().getResource("/gui/productInterfaces/UserProductDetailsCard.fxml"));
        // VBox productContainer1 = fxmlLoader1.load();
        // productDetailsContainer.add(productContainer1, 0, 1);
        // GridPane.setMargin(productContainer1, new Insets(0, 10, 25, 10));
        // // productContainer1.setStyle("-fx-effect: dropshadow(three-pass-box,
        // // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // END - Set comments List

    }

    @FXML
    void partageFacebook(MouseEvent event) {
        String appId = "232528662540085";
        String appSecret = "60988e9928012f06c205e07717bb4196";
        String accessTokenString = "EAACT381qg9IBAKAFRippdS4KyLHH5wYXhkskxPQ75wmQflNqD4ZAMMjqOblZAYNZAmEuLog2bA6cwEkwaU7UTDdKS3JDc7d7PZCZCxWlLwcVFDbrAtFMBfLCSVYVSBW9dnJ7ZCOoflxRy39nyCZC4EIZAMkPHN42sY8P2EKA8jcZBTWKcwq86uIZAAhSY9MVTbj4rZCl6zX0kiNGeQwz8AzbO6M";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        try {
            facebook.postStatusMessage("Mon message à partager sur Facebook !");
        } catch (FacebookException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void addToFavoriteList(MouseEvent event) {
        ProduitService produitService = new ProduitService();
        produitService.addProductToFavoriteList(Produit.getIdProduit(), user.getId());

    }

}
