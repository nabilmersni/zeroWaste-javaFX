package gui.collectInterfaces;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;

import entities.Categorie_Collecte;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import javafx.stage.FileChooser.ExtensionFilter;
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
public class AddCategoryCardController implements Initializable {

    @FXML
    private Text add_iconBtn;

    @FXML
    private HBox add_new_categoryBtn;

    @FXML
    private ImageView imageInput;

    @FXML
    private TextField nameInput;

    @FXML
    private HBox update_categoryBtn;

    @FXML
    private HBox nameInputErrorHbox;

    @FXML
    private HBox photoInputErrorHbox;

    @FXML
    private Text nameInputError;

    @FXML
    private Text photoInputError;

    private File selectedImageFile;
    private String imageName;
    private int nomTest = 0;
    private int photoTest = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Categorie_Collecte.actionTest);

        nameInputErrorHbox.setVisible(false);
        photoInputErrorHbox.setVisible(false);
        if (Categorie_Collecte.actionTest == 0) { // add product
            update_categoryBtn.setVisible(false);

        } else { // update product
            add_new_categoryBtn.setVisible(false);

            // Instancier le service de categorie
            ICategorie_CollectService categoryService = new Categorie_CollectService();
            Categorie_Collecte c = new Categorie_Collecte();
            try {
                c = categoryService.getOneCategory(Categorie_Collecte.getIdCategory());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            nameInput.setText(c.getNom_categorie());
            Image image = new Image(
                    getClass().getResource("/assets/ProductUploads/" + c.getImage_categorie()).toExternalForm());
            imageInput.setImage(image);
            imageName = c.getImage_categorie();

            nomTest = 1;
        }

    }

    @FXML
    void addNewCategory(MouseEvent event) throws SQLException {

        Categorie_Collecte categorie = new Categorie_Collecte();

        // Instancier le service de categorie
        ICategorie_CollectService categoryService = new Categorie_CollectService();

        if (nameInput.getText().isEmpty()) {
            nomTest = 0;
            nameInputErrorHbox.setVisible(true);
        } else {
            if (nomTest == 1) {
                if (categoryService.categoryExists(nameInput.getText()) == 0) {
                    categorie.setNom_categorie(nameInput.getText());
                } else {
                    nameInputError.setText("category name already exist !");
                    nomTest = 0;
                    nameInputErrorHbox.setVisible(true);
                }
            }
        }

        if (imageName == null) {
            photoTest = 0;
            photoInputErrorHbox.setVisible(true);
        } else {
            photoTest = 1;
            categorie.setImage_categorie(imageName);
        }

        if (nomTest == 1 && photoTest == 1) {

            try {
                categoryService.ajouter(categorie);
                TrayNotificationAlert.notif("Category", "Category added successfully.",
                        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

                ProductsListController.setCategoryModelShow(1);
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

            // Enregistrer l'image dans le dossier "uploads"
            // Nabil Path
            // Path destination = Paths
            // .get("D:/SSD
            // SUPORT/Desktop/pidev_java/zeroWaste-javaFX/src/assets/ProductUploads/" +
            // imageName);
            // Ali path
            Path destination = Paths.get(System.getProperty("user.dir"), "src", "assets", "ProductUploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            // pour le controle de saisie
            photoTest = 1;
            photoInputErrorHbox.setVisible(false);
        }
    }

    @FXML
    void updateCategory(MouseEvent event) throws SQLException {
        // Instancier le service de category
        ICategorie_CollectService categoryService = new Categorie_CollectService();

        Categorie_Collecte category = new Categorie_Collecte();
        category.setId(Categorie_Collecte.getIdCategory());

        if (nameInput.getText().isEmpty()) {
            nomTest = 0;
            nameInputErrorHbox.setVisible(true);
        } else {
            if (!(categoryService.getOneCategory(Categorie_Collecte.getIdCategory()).getNom_categorie()
                    .equals(nameInput.getText())) && (categoryService.categoryExists(nameInput.getText()) == 1)) {
                System.out.println("Category name : "
                        + categoryService.getOneCategory(Categorie_Collecte.getIdCategory()).getNom_categorie());
                System.out.println("Category count : " + categoryService.categoryExists(nameInput.getText()));
                System.out.println("text name : " + nameInput.getText());

                nameInputError.setText("category name already exist !");
                nomTest = 0;
                nameInputErrorHbox.setVisible(true);
            } else {
                category.setNom_categorie(nameInput.getText());
            }
        }

        if (imageName == null) {
            photoTest = 0;
            photoInputErrorHbox.setVisible(true);
        } else {
            photoTest = 1;
            category.setImage_categorie(imageName);
        }

        if (nomTest == 1 && photoTest == 1) {

            try {
                categoryService.updateCategory(category);
                TrayNotificationAlert.notif("Category", "Category updated successfully.",
                        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

                ProductsListController.setCategoryModelShow(1);
                Categorie_Collecte.actionTest = 0; // pour afficher le bouton ajouter et cacher le bouton modifier

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
    void nameTypedInput(KeyEvent event) {
        String nameText = ((TextField) event.getSource()).getText();
        if (!nameText.isEmpty()) {
            nameInputErrorHbox.setVisible(false);
            nomTest = 1;
        }
    }

}
