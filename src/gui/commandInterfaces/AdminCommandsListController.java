package gui.commandInterfaces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import entities.Achats;
import entities.Collecte;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import services.AchatsService;
import services.IAchatsService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import javafx.scene.Node;
import com.itextpdf.text.Image;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class AdminCommandsListController implements Initializable {

    @FXML
    private GridPane commandsListContainer;

    @FXML
    private HBox commandModel;

    @FXML
    private GridPane commandDetailsContainer;
    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModel;

    @FXML
    private HBox commandStatusModel;

    @FXML
    private Pane content_area;

    @FXML
    private ComboBox<String> dateComboBox;

    private String selectedOption = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        qrCodeImgModel.setVisible(false);
        commandStatusModel.setVisible(false);

        // set combobox values
        dateComboBox.getItems().add("All");
        dateComboBox.getItems().add("Today");
        dateComboBox.getItems().add("Last Week");
        dateComboBox.getItems().add("Last Month");
        // END set combobox values
        // filter by combobox values
        dateComboBox.setOnAction(event -> {
            selectedOption = dateComboBox.getValue();
            Collecte.setSearchValue(null);
            System.out.println("Selected option: " + selectedOption);

            Achats.setSearchValue(null);
            // System.out.println("Selected value: " + categId);
            GridPane commandsListContainer = (GridPane) content_area.lookup("#commandsListContainer");
            commandsListContainer.getChildren().clear();
            this.setCommandsGridPaneList();
        });
        // END filter by combobox values

        IAchatsService achatService = new AchatsService();
        List<Collecte> produits = new ArrayList<>();
        produits = achatService.getAllProducts(Achats.getCommandeId());

        if (Achats.achatModelTest == 0) {
            commandModel.setVisible(false);
        } else {
            commandModel.setVisible(true);

        }

        setCommandsGridPaneList();

        // set one command details Model
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("/gui/commandInterfaces/AdminCommandLivraisonCard.fxml"));

            VBox commandinfoCard = fxmlLoader1.load();
            AdminCommandLivraisonCardController commandLivraisonController = fxmlLoader1.getController();
            commandLivraisonController.setCommandLivraison();
            commandDetailsContainer.add(commandinfoCard, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int column = 0;
        int row = 2;
        try {
            for (int i = 0; i < produits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/commandInterfaces/AdminCommandDetailsCard.fxml"));
                HBox oneAchatCard = fxmlLoader.load();

                AdminCommandDetailsCardController commandCardController = fxmlLoader.getController();
                commandCardController.setCommandDetails(produits.get(i));

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                commandDetailsContainer.add(oneAchatCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneAchatCard, new Insets(0, 10, 15, 10));
                // oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box,
                // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // END - set one command details Model

    }

    private void setCommandsGridPaneList() {
        // Instancier le service de commande
        IAchatsService achatsService = new AchatsService();

        List<Achats> achats = null;
        if (Achats.getSearchValue() == null) {
            if (selectedOption != null) {
                if (selectedOption.equals("Today")) {
                    achats = achatsService.getAchatsToday();
                }
                if (selectedOption.equals("Last Week")) {
                    achats = achatsService.getAchatsLastWeek();
                }
                if (selectedOption.equals("Last Month")) {
                    achats = achatsService.getAchatsLastMonth();
                }
                if (selectedOption.equals("All")) {
                    achats = achatsService.getAllAchats();
                }
            } else {
                achats = achatsService.getAllAchats();
            }

        } else {
            achats = achatsService.searchCommands(Achats.getSearchValue());
        }
        // afficher tous les achats (juste pour tester)
        // for(int j =0 ; j< achats.size() ; j++){
        // System.out.println(achats.get(j));
        // }

        // achats list ------------------------------------------------
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < achats.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/commandInterfaces/AdminCommandsListItem.fxml"));
                HBox oneAchatCard = fxmlLoader.load();
                AdminCommandsListItemController commandCardController = fxmlLoader.getController();
                commandCardController.setCommandData(achats.get(i));

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                commandsListContainer.add(oneAchatCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneAchatCard, new Insets(0, 10, 15, 10));
                // oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box,
                // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close_commandDetailsModel(MouseEvent event) {
        commandModel.setVisible(false);
        Achats.achatModelTest = 0;
    }

    @FXML
    void pdf(MouseEvent event) {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des produits
            IAchatsService achatsService = new AchatsService();
            List<Achats> achatList = achatsService.getAllAchats();

            try {
                // Créer le document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Créer une instance de l'image
                Image image = Image.getInstance(System.getProperty("user.dir") + "/src/assets/img/logo.png");

                // Positionner l'image en haut à gauche
                image.setAbsolutePosition(5, document.getPageSize().getHeight() - 120);

                // Modifier la taille de l'image
                image.scaleAbsolute(150, 150);

                // Ajouter l'image au document
                document.add(image);

                // Créer une police personnalisée pour la date
                Font fontDate = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                BaseColor color = new BaseColor(50, 187, 111); // Rouge: 50, Vert: 187, Bleu: 111
                fontDate.setColor(color); // Définir la couleur de la police

                // Créer un paragraphe avec le lieu
                Paragraph tunis = new Paragraph("Tunis", fontDate);
                tunis.setIndentationLeft(455); // Définir la position horizontale
                tunis.setSpacingBefore(-30); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(tunis);

                // Obtenir la date d'aujourd'hui
                LocalDate today = LocalDate.now();

                // Créer un paragraphe avec la date
                Paragraph date = new Paragraph(today.toString(), fontDate);

                date.setIndentationLeft(437); // Définir la position horizontale
                date.setSpacingBefore(1); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(date);

                // Créer une police personnalisée
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD);
                BaseColor titleColor = new BaseColor(67, 136, 43); //
                font.setColor(titleColor);

                // Ajouter le contenu au document
                Paragraph title = new Paragraph("Liste des produits", font);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingBefore(50); // Ajouter une marge avant le titre pour l'éloigner de l'image
                title.setSpacingAfter(20);
                document.add(title);

                PdfPTable table = new PdfPTable(6); // 6 colonnes pour les 6 attributs des produits
                table.setWidthPercentage(100);
                table.setSpacingBefore(30f);
                table.setSpacingAfter(30f);

                // Ajouter les en-têtes de colonnes
                Font hrFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
                BaseColor hrColor = new BaseColor(50, 89, 74); //
                hrFont.setColor(hrColor);

                PdfPCell cell1 = new PdfPCell(new Paragraph("ID", hrFont));
                BaseColor bgColor = new BaseColor(222, 254, 230);
                cell1.setBackgroundColor(bgColor);
                cell1.setBorderColor(titleColor);
                cell1.setPaddingTop(20);
                cell1.setPaddingBottom(20);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell2 = new PdfPCell(new Paragraph("FullName", hrFont));
                cell2.setBackgroundColor(bgColor);
                cell2.setBorderColor(titleColor);
                cell2.setPaddingTop(20);
                cell2.setPaddingBottom(20);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell3 = new PdfPCell(new Paragraph("Email", hrFont));
                cell3.setBackgroundColor(bgColor);
                cell3.setBorderColor(titleColor);
                cell3.setPaddingTop(20);
                cell3.setPaddingBottom(20);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell4 = new PdfPCell(new Paragraph("Address", hrFont));
                cell4.setBackgroundColor(bgColor);
                cell4.setBorderColor(titleColor);
                cell4.setPaddingTop(20);
                cell4.setPaddingBottom(20);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell5 = new PdfPCell(new Paragraph("Phone", hrFont));
                cell5.setBackgroundColor(bgColor);
                cell5.setBorderColor(titleColor);
                cell5.setPaddingTop(20);
                cell5.setPaddingBottom(20);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell6 = new PdfPCell(new Paragraph("City", hrFont));
                cell6.setBackgroundColor(bgColor);
                cell6.setBorderColor(titleColor);
                cell6.setPaddingTop(20);
                cell6.setPaddingBottom(20);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
                table.addCell(cell6);

                Font hdFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
                BaseColor hdColor = new BaseColor(50, 89, 74); //
                hrFont.setColor(hdColor);
                // Ajouter les données des produits
                for (Achats achat : achatList) {
                    PdfPCell cellR1 = new PdfPCell(new Paragraph(String.valueOf(achat.getId()), hdFont));
                    cellR1.setBorderColor(titleColor);
                    cellR1.setPaddingTop(10);
                    cellR1.setPaddingBottom(10);
                    cellR1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR1);

                    PdfPCell cellR2 = new PdfPCell(new Paragraph(achat.getFull_name(), hdFont));
                    cellR2.setBorderColor(titleColor);
                    cellR2.setPaddingTop(10);
                    cellR2.setPaddingBottom(10);
                    cellR2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR2);

                    PdfPCell cellR3 = new PdfPCell(new Paragraph(achat.getEmail(), hdFont));
                    cellR3.setBorderColor(titleColor);
                    cellR3.setPaddingTop(10);
                    cellR3.setPaddingBottom(10);
                    cellR3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR3);

                    PdfPCell cellR4 = new PdfPCell(new Paragraph(achat.getAddress(), hdFont));
                    cellR4.setBorderColor(titleColor);
                    cellR4.setPaddingTop(10);
                    cellR4.setPaddingBottom(10);
                    cellR4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR4);

                    PdfPCell cellR5 = new PdfPCell(
                            new Paragraph(String.valueOf(achat.getTel()), hdFont));
                    cellR5.setBorderColor(titleColor);
                    cellR5.setPaddingTop(10);
                    cellR5.setPaddingBottom(10);
                    cellR5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR5);

                    PdfPCell cellR6 = new PdfPCell(
                            new Paragraph(achat.getCity(), hdFont));
                    cellR6.setBorderColor(titleColor);
                    cellR6.setPaddingTop(10);
                    cellR6.setPaddingBottom(10);
                    cellR6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR6);
                }
                table.setSpacingBefore(20);
                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void close_QrCodeModel(MouseEvent event) {
        qrCodeImgModel.setVisible(false);
    }

    @FXML
    void searchAchat(KeyEvent event) throws IOException {
        Achats.setSearchValue(((TextField) event.getSource()).getText());
        System.out.println("Recherche en cours: " + Achats.getSearchValue());

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane commandsListContainer = (GridPane) content_area.lookup("#commandsListContainer");
        commandsListContainer.getChildren().clear();
        this.setCommandsGridPaneList();
    }

    @FXML
    void close_commandStatusModel(MouseEvent event) {
        commandStatusModel.setVisible(false);

    }

    @FXML
    void setCommandInProgress(MouseEvent event) {
        AchatsService achatsService = new AchatsService();

        achatsService.updateCommandStatus(Achats.getAchatId(), 0);
        // commandStatusModel.setVisible(false);

        Parent fxml;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/gui/commandInterfaces/AdminCommandsList.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void setCommandValidate(MouseEvent event) {
        AchatsService achatsService = new AchatsService();

        achatsService.updateCommandStatus(Achats.getAchatId(), 1);
        // commandStatusModel.setVisible(false);

        Parent fxml;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/gui/commandInterfaces/AdminCommandsList.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
