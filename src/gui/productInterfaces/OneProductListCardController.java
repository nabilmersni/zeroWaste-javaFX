package gui.productInterfaces;

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

import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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

    @FXML
    private HBox qrCodeProduit;

    @FXML
    private HBox offerProduit;

    @FXML
    private Text priceAfterOffer;

    @FXML
    private HBox priceAfterOfferHbox;

    @FXML
    private Text priceBeforeOffer;

    @FXML
    private HBox priceHbox;

    public void setProductData(Produit produit) {
        float prixApresOffre = 0;

       
        if (produit.getRemise() == 0) {
            priceAfterOfferHbox.setVisible(false);
            priceHbox.setVisible(true);
            priceProduit.setText("" + produit.getPrix_produit());
        } else {
            priceHbox.setVisible(false);
            priceAfterOfferHbox.setVisible(true);
            priceBeforeOffer.setText("" + produit.getPrix_produit());

            prixApresOffre = (float) (produit.getPrix_produit()
                    - (produit.getPrix_produit() * produit.getRemise() / 100.0));
            String prixApresOffreStr = String.format("%.1f", prixApresOffre);
            priceAfterOffer.setText(prixApresOffreStr);
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/productInterfaces/ProductsList.fxml"));
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/productInterfaces/AddProduct.fxml"));
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

       // qrCodeProduit btn click
       editProduit.setId(String.valueOf(produit.getId()));

       qrCodeProduit.setOnMouseClicked(event -> {
           System.out.println("ID du produit à générer qr Code : " + produit.getId());
           Produit.setIdProduit(produit.getId());

           String text = "Product ID: " + produit.getId() + "\nProduct Name: " + produit.getNom_produit()
                   + "\nProduct Description: " + produit.getDescription() + "\nProduct Price: "
                   + produit.getPrix_produit() + "\nProduct Points: " + produit.getPrix_point_produit()
                   + "\nProduct Category: " + produitService.getCategory(produit.getCategorie_produit_id());
           // Créer un objet QRCodeWriter pour générer le QR code
           QRCodeWriter qrCodeWriter = new QRCodeWriter();
           // Générer la matrice de bits du QR code à partir du texte saisi
           BitMatrix bitMatrix;
           try {
               bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
               // Convertir la matrice de bits en image BufferedImage
               BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
               // Enregistrer l'image en format PNG
               // File outputFile = new File("qrcode.png");
               // ImageIO.write(bufferedImage, "png", outputFile);
               // Afficher l'image dans l'interface utilisateur

               ImageView qrCodeImg = (ImageView) ((Node) event.getSource()).getScene().lookup("#qrCodeImg");
               qrCodeImg.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

               HBox qrCodeImgModel = (HBox) ((Node) event.getSource()).getScene().lookup("#qrCodeImgModel");
               qrCodeImgModel.setVisible(true);
           } catch (WriterException e) {
               e.printStackTrace();
           }

       });
       // END qrCodeProduit btn click


        // offreProduit btn click
        offerProduit.setId(String.valueOf(produit.getId()));

        offerProduit.setOnMouseClicked(event -> {
            System.out.println("ID du produit à créer une offre : " + produit.getId());
            Produit.setIdProduit(produit.getId());

            HBox offreModel = (HBox) ((Node) event.getSource()).getScene().lookup("#offreModel");
            offreModel.setVisible(true);

        });
        // END offreProduit btn click

    }

}
