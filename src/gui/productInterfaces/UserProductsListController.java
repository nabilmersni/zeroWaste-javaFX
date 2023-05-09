package gui.productInterfaces;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Categorie_Collecte;
import entities.Collecte;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
import javafx.scene.Node;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserProductsListController implements Initializable {

    @FXML
    private Pane content_area;

    @FXML
    private GridPane productsListContainer;

    @FXML
    private HBox addedCartModel;

    @FXML
    private Text addedCartModelText;

    @FXML
    private TextField productSearchInput;

    @FXML
    private ComboBox<String> categoryInput;

    @FXML
    private ComboBox<String> priceInput;

    @FXML
    private ComboBox<String> pointsInput;

    @FXML
    private HBox getPromotionalItemsBtn;

    private int categId = -1;
    private String priceComboboxData = null;
    private String pointsComboboxData = null;
    private String promotionaProductsData = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addedCartModel.setVisible(false);
        if (Collecte.getSortByCateg() != null) {
            if (Collecte.getSortByCateg().equals("Price - Low To High")) {
                priceInput.setValue("Price - Low To High");
                priceComboboxData = "Price - Low To High";
            }
            if (Collecte.getSortByCateg().equals("Price - High To Low")) {
                priceInput.setValue("Price - High To Low");
                priceComboboxData = "Price - High To Low";
            }

            if (Collecte.getSortByCateg().equals("Points - Low To High")) {
                pointsInput.setValue("Points - Low To High");
                pointsComboboxData = "Points - Low To High";
            }
            if (Collecte.getSortByCateg().equals("Points - High To Low")) {
                pointsInput.setValue("Points - High To Low");
                pointsComboboxData = "Points - High To Low";
            }

            if (Collecte.getSortByCategId() != -1) {
                categoryInput.setValue(Collecte.getSortByCateg());
                categId = Collecte.getSortByCategId();
            }

            if (Collecte.getSortByCateg().equals("promo")) {
                promotionaProductsData = "promo";
                getPromotionalItemsBtn.getStyleClass().add("promotionalItemsBtnSelected");
            }

            if (Collecte.getSearchImageScore() != -1) {

            }

        }

        // set the product list in the grid pane***************************************
        this.setProductGridPaneList();

        // Instancier le service de categorie
        ICategorie_produitService categoryService = new Categorie_produitService();

        // Récupérer toutes les categories
        List<Categorie_Collecte> categories = categoryService.getAllCategories();
        // Ajouter la liste des categories au combobox-----------------
        this.setCategoriesCombobox();

        // ****************set price choices combobox ********************/
        this.setPriceCombobox();

        // ****************set points choices combobox ********************/
        this.setPointsCombobox();

    }

    private void setCategoriesCombobox() {
        // Instancier le service de categorie
        ICategorie_produitService categoryService = new Categorie_produitService();
        // Récupérer toutes les categories
        List<Categorie_Collecte> categories = categoryService.getAllCategories();

        Map<String, Integer> valuesMap = new HashMap<>();
        categoryInput.getItems().add("All");
        valuesMap.put("All", -1);
        for (Categorie_Collecte categorie : categories) {
            categoryInput.getItems().add(categorie.getNom_categorie());
            valuesMap.put(categorie.getNom_categorie(), categorie.getId());
        }

        categoryInput.setOnAction(event -> {
            String selectedOption = categoryInput.getValue();
            int selectedValue = valuesMap.get(selectedOption);
            categId = selectedValue;
            Collecte.setSortByCategId(categId);
            Collecte.setSearchValue(null);

            Collecte.setSortByCateg(selectedOption);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setPriceCombobox() {

        priceInput.getItems().add("Price - Low To High");
        priceInput.getItems().add("Price - High To Low");
        priceInput.setOnAction(event -> {
            String selectedOption = priceInput.getValue();

            Collecte.setSortByCateg(selectedOption);
            Collecte.setSortByCategId(-1);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private void setPointsCombobox() {
        pointsInput.getItems().add("Points - Low To High");
        pointsInput.getItems().add("Points - High To Low");
        pointsInput.setOnAction(event -> {
            String selectedOption = pointsInput.getValue();
            Collecte.setSortByCateg(selectedOption);
            Collecte.setSortByCategId(-1);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    @FXML
    void closeAddToCartModel(MouseEvent event) {
        addedCartModel.setVisible(false);
    }

    @FXML
    void open_CommandsList(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));

        Parent root = loader.load();
        // Accéder à la pane content_area depuis le controller de
        // OneProductListCard.fxml
        Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

        // Vider la pane et afficher le contenu de UserCommandsList.fxml
        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);
    }

    private void setProductGridPaneList() {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();
        List<Collecte> produits = null;

        System.out.println("searchValue" + Collecte.getSearchValue());
        if (Collecte.getSearchValue() == null) {
            if (categId != -1) {
                // filter by category
                produits = produitService.sortProducts(0, categId);
                categId = -1;
                Collecte.setSortByCategId(-1);
            } else if (priceComboboxData != null) {
                // sort by price
                produits = produitService.sortProductsUser("price", priceComboboxData);
                priceComboboxData = null;
                Collecte.setSortByCateg(null);
            } else if (pointsComboboxData != null) {
                // sort by points
                produits = produitService.sortProductsUser("points", pointsComboboxData);
                pointsComboboxData = null;
                Collecte.setSortByCateg(null);
            } else if (promotionaProductsData != null) {
                // show promotional products only
                produits = produitService.getPromotionalProducts();
                promotionaProductsData = null;
                Collecte.setSortByCateg(null);
            } else if (Collecte.getSearchImageScore() != -1) {
                // search by image
                produits = produitService.searchProductByImage(Collecte.getSearchImageEtiquette(),
                        Collecte.getSearchImageScore());
                Collecte.setSearchImageScore(-1);
            } else {
                produits = produitService.getAllProducts();
            }
        } else {
            produits = produitService.searchProducts(Collecte.getSearchValue());
            Collecte.setSearchValue(null);
        }

        // product list ------------------------------------------------
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/productInterfaces/UserProductCard.fxml"));
                VBox oneProductCard = fxmlLoader.load();
                UserProductCardController productCardController = fxmlLoader.getController();
                productCardController.setProductData(produits.get(i));

                if (column == 3) {
                    column = 0;
                    ++row;
                }
                productsListContainer.add(oneProductCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneProductCard, new Insets(0, 20, 20, 10));
                // oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box,
                // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchProduct(KeyEvent event) throws IOException {
        Collecte.setSearchValue(((TextField) event.getSource()).getText());
        System.out.println("Recherche en cours: " + Collecte.getSearchValue());

        // categId = -1;

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
        productsListContainer.getChildren().clear();
        this.setProductGridPaneList();
    }

    @FXML
    void getPromotionalItems(MouseEvent event) {
        if (!getPromotionalItemsBtn.getStyleClass().contains("promotionalItemsBtnSelected")) {

            Collecte.setSortByCateg("promo");
        } else {
            getPromotionalItemsBtn.getStyleClass().remove("promotionalItemsBtnSelected");
            Collecte.setSortByCateg(null);
        }

        Collecte.setSortByCategId(-1);

        Parent fxml;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void SearchByImage(MouseEvent event) {
        System.out.println("annotation.getName()");
        ImageAnnotatorClient client;

        try {
            // Load the credentials file
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream("src/utils/google_cloud_credentials.json"));

            // Build the ImageAnnotatorSettings object with the credentials provider
            ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            // Create the client with the settings
            client = ImageAnnotatorClient.create(settings);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Créer un nouveau FileChooser
        FileChooser fileChooser = new FileChooser();

        // Configurer le FileChooser pour n'afficher que les fichiers d'image
        fileChooser.setTitle("Sélectionnez une image");
        fileChooser.getExtensionFilters()
                .addAll(new ExtensionFilter("Fichiers d'image", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // Afficher la boîte de dialogue de sélection de fichier et attendre la
        // sélection de l'utilisateur
        File selectedFile = fileChooser.showOpenDialog(null);

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            // Charger le fichier et exécuter le code d'annotation

            // Annotate the image
            try {
                // Load the image file
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());

                ByteString byteString = ByteString.copyFrom(imageBytes);

                // Create the image object
                com.google.cloud.vision.v1.Image image = com.google.cloud.vision.v1.Image.newBuilder()
                        .setContent(byteString).build();

                // Create the feature object
                Feature feature = Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build();

                // Create the request object
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image)
                        .build();

                // Send the request and get the response
                BatchAnnotateImagesResponse response = client.batchAnnotateImages(ImmutableList.of(request));

                // Extract labels and objects from the response
                List<LocalizedObjectAnnotation> objectAnnotations = response.getResponses(0)
                        .getLocalizedObjectAnnotationsList();
                Collecte.setSearchImageEtiquette(objectAnnotations.get(0).getName());
                Collecte.setSearchImageScore(objectAnnotations.get(0).getScore());
                System.out.println(objectAnnotations.get(0).getName() + " " + objectAnnotations.get(0).getScore());

                Parent fxml;
                try {
                    fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
                    content_area.getChildren().removeAll();
                    content_area.getChildren().setAll(fxml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // for (LocalizedObjectAnnotation annotation : objectAnnotations) {
                // System.out.println(annotation.getName() + " " + annotation.getScore());
                // }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Shut down the client
                client.shutdown();
            }
        }
    }

}
