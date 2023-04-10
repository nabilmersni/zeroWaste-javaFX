package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Categorie_produit;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.Categorie_produitService;
import services.ICategorie_produitService;
import services.IProduitService;
import services.ProduitService;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import javafx.scene.Node;

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
public class ProductsListController implements Initializable {

    @FXML
    private GridPane productsListContainer;

    @FXML
    private Pane content_area;

    @FXML
    private HBox categoriesModel;

    @FXML
    private GridPane categoriesListContainer;

    @FXML
    private TextField productSearchInput;

    @FXML
    private Button stockBtn;

    @FXML
    private ComboBox<String> categoryInput;

    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModel;

    private int categId = -1;

    private int sortValue = -1; // 1: sort by stock *** 0: filter by category *** 2: filter by category and sort
                                // by stock

    private static int categoryModelShow = 0;

    public static int getCategoryModelShow() {
        return categoryModelShow;
    }

    public static void setCategoryModelShow(int categoryModelShow) {
        ProductsListController.categoryModelShow = categoryModelShow;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        qrCodeImgModel.setVisible(false);
        if (ProductsListController.getCategoryModelShow() == 0) {
            categoriesModel.setVisible(false);
        } else if (ProductsListController.getCategoryModelShow() == 1) {
            categoriesModel.setVisible(true);
        }

        // Afficher les produits dans la console (juste pour tester)
        /*
         * System.out.println("Liste des produits:");
         * for (Produit produit : produits) {
         * System.out.println(produit);
         * }
         */

        // set the product list in the grid pane***************************************
        this.setProductGridPaneList();

        // categories list ---------------------------------------------------------
        // Ajouter AddCategoryCard (forum) au debut de la liste
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("AddCategoryCard.fxml"));
        VBox CategoryAddCard;
        try {
            CategoryAddCard = fxmlLoader1.load();
            categoriesListContainer.add(CategoryAddCard, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instancier le service de categorie
        ICategorie_produitService categoryService = new Categorie_produitService();

        // Récupérer toutes les categories
        List<Categorie_produit> categories = categoryService.getAllCategories();

        int CategColumn = 0;
        int CategRow = 2;
        try {
            for (int i = 0; i < categories.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OneCategoriesListCard.fxml"));
                HBox oneCategoryCard = fxmlLoader.load();
                OneCategoriesListCardController categorieCardController = fxmlLoader.getController();
                categorieCardController.setCategoryData(categories.get(i));

                if (CategColumn == 1) {
                    CategColumn = 0;
                    ++CategRow;
                }
                categoriesListContainer.add(oneCategoryCard, CategColumn++, CategRow);
                GridPane.setMargin(oneCategoryCard, new Insets(0, 10, 25, 10));
                oneCategoryCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajouter la liste des categories au combobox-----------------

        Map<String, Integer> valuesMap = new HashMap<>();
        for (Categorie_produit categorie : categories) {
            categoryInput.getItems().add(categorie.getNom_categorie());
            valuesMap.put(categorie.getNom_categorie(), categorie.getId());
        }

        categoryInput.setOnAction(event -> {
            String selectedOption = categoryInput.getValue();
            int selectedValue = valuesMap.get(selectedOption);
            categId = selectedValue;
            Produit.setSearchValue(null);
            // System.out.println("Selected option: " + selectedOption);
            // System.out.println("Selected value: " + categId);
            GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
            productsListContainer.getChildren().clear();
            this.setProductGridPaneList();
        });

    }

    @FXML
    private void open_addProduct(MouseEvent event) throws IOException {
        Produit.actionTest = 0;
        Parent fxml = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

    }

    @FXML
    void open_CategoriesModel(MouseEvent event) {
        categoriesModel.setVisible(true);
    }

    @FXML
    void close_CategoriesModel(MouseEvent event) {
        categoriesModel.setVisible(false);
        ProductsListController.setCategoryModelShow(0);
    }

    @FXML
    void searchProduct(KeyEvent event) throws IOException {
        Produit.setSearchValue(((TextField) event.getSource()).getText());
        // System.out.println("Recherche en cours: " + Produit.getSearchValue());
        if (stockBtn.getStyleClass().contains("sort__stockBtn-active")) {
            stockBtn.getStyleClass().remove("sort__stockBtn-active");
        }
        categId = -1;

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
        productsListContainer.getChildren().clear();
        this.setProductGridPaneList();
    }

    private void setProductGridPaneList() {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();

        List<Produit> produits = null;
        System.out.println("searchValue" + Produit.getSearchValue());
        if (Produit.getSearchValue() == null) {
            if (sortValue == -1 && categId == -1) {
                // Récupérer tous les produits
                produits = produitService.getAllProducts();
            }
            if (sortValue == 1 && categId == -1) {
                // sort by stock
                produits = produitService.sortProducts(1, -1);
            }
            if (sortValue == -1 && categId != -1) {
                // filter by category
                produits = produitService.sortProducts(0, categId);
            }
            if (sortValue == 1 && categId != -1) {
                // filter by category and sort by stock
                produits = produitService.sortProducts(1, categId);
            }
            System.out.println("sortValue : " + sortValue);
            System.out.println("categId : " + categId);

        } else {
            produits = produitService.searchProducts(Produit.getSearchValue());
        }

        // product list ------------------------------------------------
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OneProductListCard.fxml"));
                HBox oneProductCard = fxmlLoader.load();
                OneProductListCardController productCardController = fxmlLoader.getController();
                productCardController.setProductData(produits.get(i));

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                productsListContainer.add(oneProductCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneProductCard, new Insets(0, 10, 25, 10));
                oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sort__ByStock(MouseEvent event) {
        Produit.setSearchValue(null);
        if (!stockBtn.getStyleClass().contains("sort__stockBtn-active")) {
            stockBtn.getStyleClass().add("sort__stockBtn-active");
            // Button stock = (Button) content_area.lookup("#stockBtn");
            // stock.getStyleClass().add("sort__stockBtn-active");
            sortValue = 1;
        } else {
            stockBtn.getStyleClass().remove("sort__stockBtn-active");
            sortValue = -1;
        }

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
        productsListContainer.getChildren().clear();
        this.setProductGridPaneList();

    }

    @FXML
    void genererPdf(ActionEvent event) {
        // Afficher la boîte de dialogue de sélection de fichier
        /*
         * FileChooser fileChooser = new FileChooser();
         * fileChooser.setTitle("Enregistrer le fichier PDF");
         * fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers PDF",
         * "*.pdf"));
         * File selectedFile = fileChooser.showSaveDialog(((Node)
         * event.getSource()).getScene().getWindow());
         * 
         * if (selectedFile != null) {
         * // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
         * generatePDF(selectedFile);
         * }
         */
    }

    @FXML
    void pdfBtn(MouseEvent event) {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des produits
            IProduitService produitService = new ProduitService();
            List<Produit> productList = produitService.getAllProducts();

            try {
                // Créer le document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Créer une instance de l'image
                Image image = Image
                        .getInstance("C:/Users/ALI/Desktop/ZeroWaste - JavaFx/zeroWaste-javaFX/src/assets/img/logo.png");

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

                PdfPCell cell2 = new PdfPCell(new Paragraph("Nom", hrFont));
                cell2.setBackgroundColor(bgColor);
                cell2.setBorderColor(titleColor);
                cell2.setPaddingTop(20);
                cell2.setPaddingBottom(20);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell3 = new PdfPCell(new Paragraph("Quantité", hrFont));
                cell3.setBackgroundColor(bgColor);
                cell3.setBorderColor(titleColor);
                cell3.setPaddingTop(20);
                cell3.setPaddingBottom(20);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell4 = new PdfPCell(new Paragraph("Prix", hrFont));
                cell4.setBackgroundColor(bgColor);
                cell4.setBorderColor(titleColor);
                cell4.setPaddingTop(20);
                cell4.setPaddingBottom(20);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell5 = new PdfPCell(new Paragraph("Points", hrFont));
                cell5.setBackgroundColor(bgColor);
                cell5.setBorderColor(titleColor);
                cell5.setPaddingTop(20);
                cell5.setPaddingBottom(20);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell6 = new PdfPCell(new Paragraph("Categorie", hrFont));
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
                for (Produit produit : productList) {
                    PdfPCell cellR1 = new PdfPCell(new Paragraph(String.valueOf(produit.getId()), hdFont));
                    cellR1.setBorderColor(titleColor);
                    cellR1.setPaddingTop(10);
                    cellR1.setPaddingBottom(10);
                    cellR1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR1);

                    PdfPCell cellR2 = new PdfPCell(new Paragraph(produit.getNom_produit(), hdFont));
                    cellR2.setBorderColor(titleColor);
                    cellR2.setPaddingTop(10);
                    cellR2.setPaddingBottom(10);
                    cellR2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR2);

                    PdfPCell cellR3 = new PdfPCell(new Paragraph(String.valueOf(produit.getQuantite()), hdFont));
                    cellR3.setBorderColor(titleColor);
                    cellR3.setPaddingTop(10);
                    cellR3.setPaddingBottom(10);
                    cellR3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR3);

                    PdfPCell cellR4 = new PdfPCell(new Paragraph(String.valueOf(produit.getPrix_produit()), hdFont));
                    cellR4.setBorderColor(titleColor);
                    cellR4.setPaddingTop(10);
                    cellR4.setPaddingBottom(10);
                    cellR4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR4);

                    PdfPCell cellR5 = new PdfPCell(
                            new Paragraph(String.valueOf(produit.getPrix_point_produit()), hdFont));
                    cellR5.setBorderColor(titleColor);
                    cellR5.setPaddingTop(10);
                    cellR5.setPaddingBottom(10);
                    cellR5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR5);

                    PdfPCell cellR6 = new PdfPCell(
                            new Paragraph(produitService.getCategory(produit.getCategorie_produit_id()), hdFont));
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

}
