package gui.collectInterfaces;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import entities.Categorie_Collecte;
import entities.Collecte;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.Categorie_CollectService;
import services.ICategorie_CollectService;
import services.IProduitService;
import services.CollectService;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Node;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import javafx.util.Duration;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import java.io.FileInputStream;
import java.nio.file.Files;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class AddProductCardController implements Initializable {

    @FXML
    private TextField nameInput;

    @FXML
    private ComboBox<String> categoryInput;

    @FXML
    private TextArea descriptionInput;

    @FXML
    private TextField numberInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField pointsInput;

    @FXML
    private Button add_new_productBtn;

    @FXML
    private Button update_productBtn;

    @FXML
    private ImageView imageInput;

    @FXML
    private HBox choose_photoBtn;

    @FXML
    private Text nameInputError;

    @FXML
    private Text descriptionInputError;

    @FXML
    private Text categoryInputError;

    @FXML
    private Text numberInputError;

    @FXML
    private Text priceInputError;

    @FXML
    private Text pointsInputError;

    @FXML
    private Text photoInputError;

    @FXML
    private HBox nameInputErrorHbox;

    @FXML
    private HBox descriptionInputErrorHbox;

    @FXML
    private HBox categoryInputErrorHbox;

    @FXML
    private HBox numberInputErrorHbox;

    @FXML
    private HBox priceInputErrorHbox;

    @FXML
    private HBox pointsInputErrorHbox;

    @FXML
    private HBox photoInputErrorHbox;

    private int categId = -1;
    private File selectedImageFile;
    private String imageName = null;
    private int nomTest = 0;
    private int descriptionTest = 0;
    private int categoryTest = 0;
    private int numberTest = 0;
    private int priceTest = 0;
    private int pointsTest = 0;
    private int photoTest = 0;
    private String etiquette = null;

    private double score;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Collecte.actionTest);
        // errorsField
        nameInputErrorHbox.setVisible(false);
        descriptionInputErrorHbox.setVisible(false);
        categoryInputErrorHbox.setVisible(false);
        numberInputErrorHbox.setVisible(false);
        priceInputErrorHbox.setVisible(false);
        pointsInputErrorHbox.setVisible(false);
        photoInputErrorHbox.setVisible(false);

        if (Collecte.actionTest == 0) { // add product
            update_productBtn.setVisible(false);

        } else { // update product
            add_new_productBtn.setVisible(false);

            // Instancier le service de produit
            IProduitService produitService = new CollectService();
            Collecte p = new Collecte();
            try {
                p = produitService.getOneProduct(Collecte.getIdProduit());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            nameInput.setText(p.getNom_produit());
            descriptionInput.setText(p.getDescription());
            numberInput.setText(String.valueOf(p.getQuantite()));
            priceInput.setText(String.valueOf(p.getPrix_produit()));
            pointsInput.setText(String.valueOf(p.getPrix_point_produit()));
            Image image = new Image(getClass().getResource("/assets/ProductUploads/" + p.getImage()).toExternalForm());
            imageInput.setImage(image);
            imageName = p.getImage();

            categoryInput.setValue(produitService.getCategory(p.getCategorie_produit_id()));
            categId = p.getCategorie_produit_id();
            // System.out.println("catgevalueeeee : " +
            // produitService.getCategory(p.getCategorie_produit_id()));
            nomTest = 1;
            descriptionTest = 1;
            categoryTest = 1;
            numberTest = 1;
            priceTest = 1;
            pointsTest = 1;
        }

        // Ajouter la liste des categories au combobox-----------------
        // Instancier le service de categorie
        ICategorie_CollectService categoryService = new Categorie_CollectService();

        // Récupérer tous les categories
        List<Categorie_Collecte> categories = categoryService.getAllCategories();

        // Afficher les categories dans la console (juste pour tester)
        /*
         * System.out.println("Liste des produits:");
         * for (Categorie_produit categorie : categories) {
         * System.out.println(categorie);
         * }
         */

        Map<String, Integer> valuesMap = new HashMap<>();
        for (Categorie_Collecte categorie : categories) {
            categoryInput.getItems().add(categorie.getNom_categorie());
            valuesMap.put(categorie.getNom_categorie(), categorie.getId());
        }

        categoryInput.setOnAction(event -> {
            String selectedOption = categoryInput.getValue();
            int selectedValue = valuesMap.get(selectedOption);
            categId = selectedValue;
            categoryTest = 1;
            categoryInputErrorHbox.setVisible(false);
            // System.out.println("Selected option: " + selectedOption);
            // System.out.println("Selected value: " + selectedValue);
        });

    }

    @FXML
    void addNewProduct(MouseEvent event) throws SQLException {

        Collecte produit = new Collecte();

        if (nameInput.getText().isEmpty()) {
            nomTest = 0;
            nameInputErrorHbox.setVisible(true);
        } else {
            if (nomTest == 1) {
                produit.setNom_produit(nameInput.getText());
            }
        }

        if (descriptionInput.getText().isEmpty()) {
            descriptionTest = 0;
            descriptionInputErrorHbox.setVisible(true);
        } else {
            if (descriptionTest == 1) {
                produit.setDescription(descriptionInput.getText());
            }
        }

        if (categId == -1) {
            categoryTest = 0;
            categoryInputErrorHbox.setVisible(true);
        } else {
            if (categoryTest == 1) {
                produit.setCategorie_produit_id(categId);
            }
        }

        if (numberInput.getText().isEmpty()) {
            numberTest = 0;
            numberInputErrorHbox.setVisible(true);
        } else {
            if (numberTest == 1) {
                produit.setQuantite(Integer.parseInt(numberInput.getText()));
            }
        }

        if (priceInput.getText().isEmpty()) {
            priceTest = 0;
            priceInputErrorHbox.setVisible(true);
        } else {
            if (priceTest == 1) {
                produit.setPrix_produit(Float.parseFloat(priceInput.getText()));
            }
        }

        if (pointsInput.getText().isEmpty()) {
            pointsTest = 0;
            pointsInputErrorHbox.setVisible(true);
        } else {
            if (pointsTest == 1) {
                produit.setPrix_point_produit(Integer.parseInt(pointsInput.getText()));
            }
        }

        if (imageName == null) {
            photoTest = 0;
            photoInputErrorHbox.setVisible(true);
        } else {
            photoTest = 1;
            produit.setImage(imageName);
            produit.setEtiquette(etiquette);
            produit.setScore(score);
        }

        if (nomTest == 1 && descriptionTest == 1 && categoryTest == 1 && numberTest == 1 && priceTest == 1
                && pointsTest == 1 && photoTest == 1) {
            // Instancier le service de produit
            IProduitService produitService = new CollectService();
            try {
                produitService.ajouter(produit);
                TrayNotificationAlert.notif("Product", "Product added successfully.",
                        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/collectInterfaces/ProductsList.fxml"));

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

        }

    }

    @FXML
    void ajouter_image(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src", "assets", "ProductUploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            // pour le controle de saisie
            photoTest = 1;
            photoInputErrorHbox.setVisible(false);

            // *************************get image object and score
            // *************************************/
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

            // Annotate the image
            try {
                // Load the image file
                byte[] imageBytes = Files.readAllBytes(selectedImageFile.toPath());

                ByteString byteString = ByteString.copyFrom(imageBytes);

                // Create the image object
                com.google.cloud.vision.v1.Image image2 = com.google.cloud.vision.v1.Image.newBuilder()
                        .setContent(byteString).build();

                // Create the feature object
                Feature feature = Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build();

                // Create the request object
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image2)
                        .build();

                // Send the request and get the response
                BatchAnnotateImagesResponse response = client.batchAnnotateImages(ImmutableList.of(request));

                // Extract labels and objects from the response
                List<LocalizedObjectAnnotation> objectAnnotations = response.getResponses(0)
                        .getLocalizedObjectAnnotationsList();
                etiquette = objectAnnotations.get(0).getName();
                score = objectAnnotations.get(0).getScore();
                System.out.println(objectAnnotations.get(0).getName() + " " + objectAnnotations.get(0).getScore());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Shut down the client
                client.shutdown();
            }
        }

    }

    @FXML
    void updateProduct(MouseEvent event) {

        Collecte produit = new Collecte();

        produit.setId(Collecte.getIdProduit());

        if (nameInput.getText().isEmpty()) {
            nomTest = 0;
            nameInputErrorHbox.setVisible(true);
        } else {
            if (nomTest == 1) {
                produit.setNom_produit(nameInput.getText());
            }
        }

        if (descriptionInput.getText().isEmpty()) {
            descriptionTest = 0;
            descriptionInputErrorHbox.setVisible(true);
        } else {
            if (descriptionTest == 1) {
                produit.setDescription(descriptionInput.getText());
            }
        }

        if (categId == -1) {
            categoryTest = 0;
            categoryInputErrorHbox.setVisible(true);
        } else {
            if (categoryTest == 1) {
                produit.setCategorie_produit_id(categId);
            }
        }

        if (numberInput.getText().isEmpty()) {
            numberTest = 0;
            numberInputErrorHbox.setVisible(true);
        } else {
            if (numberTest == 1) {
                produit.setQuantite(Integer.parseInt(numberInput.getText()));
            }
        }

        if (priceInput.getText().isEmpty()) {
            priceTest = 0;
            priceInputErrorHbox.setVisible(true);
        } else {
            if (priceTest == 1) {
                produit.setPrix_produit(Float.parseFloat(priceInput.getText()));
            }
        }

        if (pointsInput.getText().isEmpty()) {
            pointsTest = 0;
            pointsInputErrorHbox.setVisible(true);
        } else {
            if (pointsTest == 1) {
                produit.setPrix_point_produit(Integer.parseInt(pointsInput.getText()));
            }
        }

        if (imageName == null) {
            photoTest = 0;
            photoInputErrorHbox.setVisible(true);
        } else {
            photoTest = 1;
            produit.setImage(imageName);
            produit.setEtiquette(etiquette);
            produit.setScore(score);
        }

        if (nomTest == 1 && descriptionTest == 1 && categoryTest == 1 && numberTest == 1 && priceTest == 1
                && pointsTest == 1 && photoTest == 1) {
            // Instancier le service de produit
            IProduitService produitService = new CollectService();

            try {
                produitService.updateProduct(produit);
                TrayNotificationAlert.notif("Collect", "Collect updated successfully.",
                        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/collectInterfaces/ProductsList.fxml"));

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
        }

    }

    @FXML
    void numberTypedInput(KeyEvent event) {
        String numberText = ((TextField) event.getSource()).getText();
        if (!numberText.matches("-?\\d+")) {
            numberInputError.setText("number should be positive");
            numberInputErrorHbox.setVisible(true);
            numberTest = 0;
        } else {
            int number = Integer.parseInt(numberText);
            if (number < 0) {
                numberInputError.setText("number cannot be negative");
                numberInputErrorHbox.setVisible(true);
                numberTest = 0;
            } else {
                numberInputErrorHbox.setVisible(false);
                numberTest = 1;
            }
        }
    }

    @FXML
    void priceTypedInput(KeyEvent event) {
        String priceText = ((TextField) event.getSource()).getText();
        if (!priceText.matches("-?\\d+(\\.\\d+)?")) {
            priceInputError.setText("price should be a positive number");
            priceInputErrorHbox.setVisible(true);
            priceTest = 0;
        } else {
            double price = Double.parseDouble(priceText);
            if (price < 0) {
                priceInputError.setText("price cannot be negative");
                priceInputErrorHbox.setVisible(true);
                priceTest = 0;
            } else {
                priceInputErrorHbox.setVisible(false);
                priceTest = 1;
            }
        }

    }

    @FXML
    void pointsTypedInput(KeyEvent event) {
        String pointsText = ((TextField) event.getSource()).getText();
        if (!pointsText.matches("-?\\d+")) {
            pointsInputError.setText("points should be a positive number");
            pointsInputErrorHbox.setVisible(true);
            pointsTest = 0;
        } else {
            int points = Integer.parseInt(pointsText);
            if (points < 0) {
                pointsInputError.setText("points cannot be negative");
                pointsInputErrorHbox.setVisible(true);
                pointsTest = 0;
            } else {
                pointsInputErrorHbox.setVisible(false);
                pointsTest = 1;
            }
        }

    }

    @FXML
    void nameTypedInput(KeyEvent event) {
        String nameText = ((TextField) event.getSource()).getText();
        if (!nameText.isEmpty()) {
            nameInputErrorHbox.setVisible(false);
            nomTest = 1;
        }
    }

    @FXML
    void descriptionTypedInput(KeyEvent event) {
        String descriptionText = ((TextArea) event.getSource()).getText();
        if (!descriptionText.isEmpty()) {
            descriptionInputErrorHbox.setVisible(false);
            descriptionTest = 1;
        }
    }

}
