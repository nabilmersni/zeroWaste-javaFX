package gui;

import java.io.IOException;
import java.sql.SQLException;

import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.IProduitService;
import services.ProduitService;
import javafx.scene.Node;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author ALI
 */
public class OneProductListCardController {

    @FXML
    private ImageView img;

    @FXML
    private HBox deleteProduit;

    @FXML
    private HBox editProduit;

    @FXML
    private Text productName;

    @FXML
    private Text stockProduit;

    @FXML
    private Text priceProduit;

    @FXML
    private Label categoryProduit;

    @FXML
    private Text stockProduitValue;

    public void setProductData(Produit produit) {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();

        Image image = new Image(
                getClass().getResource("/assets/ProductUploads/" + produit.getImage()).toExternalForm());
        img.setImage(image);

        productName.setText(produit.getNom_produit());
        // get category Name
        String categoryName = produitService.getCategory(produit.getCategorie_produit_id());
        categoryProduit.setText(categoryName);

        stockProduitValue.setText("" + produit.getQuantite());
        priceProduit.setText("" + produit.getPrix_produit());

        // deleteProduit btn click
        deleteProduit.setId(String.valueOf(produit.getId()));

        deleteProduit.setOnMouseClicked(event -> {
            System.out.println("ID du produit à supprimer : " + produit.getId());
            try {
                produitService.supprimer(produit.getId());
                TrayNotificationAlert.notif("Product", "Product deleted successfully.",
                                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // supprimer le contenu de la liste et afficher la nouvelle liste(apres
            // supprimer)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductsList.fxml"));
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

        // editProduit btn click
        editProduit.setId(String.valueOf(produit.getId()));

        editProduit.setOnMouseClicked(event -> {
            System.out.println("ID du produit à modifier : " + produit.getId());
            Produit.setIdProduit(produit.getId());

            Produit.actionTest = 1; // pour afficher le bouton update

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis le controller de
                // OneProductListCard.fxml
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de AddProduct.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        // END editProduit btn click

    }

}
