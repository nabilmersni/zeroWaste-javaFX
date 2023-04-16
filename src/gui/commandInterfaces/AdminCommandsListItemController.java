package gui.commandInterfaces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.collections4.sequence.EditCommand;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.sql.SQLException;

import entities.Achats;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.AchatsService;
import services.IAchatsService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import javafx.util.Duration;
import javafx.scene.Node;

import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.pdf.TextField;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class AdminCommandsListItemController  {

    @FXML
    private Text date_achat;

    @FXML
    private HBox deleteCommand;
    @FXML
    private HBox detailsCommand;
    @FXML
    private HBox qrCodeCommand;

    public void setCommandData(Achats achat) {
        // Instancier le service de achat
        IAchatsService achatService = new AchatsService();

        date_achat.setText(achat.getDate_achat());
        

        // deleteCommand btn click
        //deleteCommand.setId(String.valueOf(achat.getId()));

        deleteCommand.setOnMouseClicked(event -> {
            System.out.println("ID du commande à supprimer : " + achat.getId());

            achatService.adminDeleteAchat(achat.getId());
            TrayNotificationAlert.notif("Command", "Command deleted successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

            // supprimer le contenu de la liste et afficher la nouvelle liste
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/AdminCommandsList.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis ce controller
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de ProductsList.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // end
        });
        //END deleteCommand btn click



         // showCommand btn click
        //showCommand.setId(String.valueOf(achat.getId()));

        detailsCommand.setOnMouseClicked(event -> {
            System.out.println("ID du commande est affichée : " + achat.getId());

            Achats.achatModelTest =1;
            Achats.setCommandeId(achat.getId());
            Achats.setAchatId(achat.getId());

            // afficher le contenu de la liste et afficher la nouvelle liste
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/AdminCommandsList.fxml"));
            try {
                Parent root = loader.load();


                // Accéder à la pane content_area depuis ce controller
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de ProductsList.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // end
        });
        //END deleteCommand btn click

 // qrCodeProduit btn click
        qrCodeCommand.setOnMouseClicked(event -> {
            System.out.println("ID du achat à générer qr Code : " + achat.getId());
            Achats.setAchatId(achat.getId());

            String text = "achat ID: " + achat.getId() + "\n commande id: " + achat.getCommande_id()
                    + "\nAddress: " + achat.getAddress() + "\n Date achat: "
                    + achat.getDate_achat() + "\nFull name: " + achat.getFull_name()
                    + "\nEmail: " + achat.getEmail()  + "\nPhone: " + achat.getTel();
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
    }
    
   
    

}
