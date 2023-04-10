package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Fundrising;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.IFundrisingService;
import services.FundrisingService;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class AddFundraisingCardController implements Initializable {

    @FXML
    private TextField titre_don;
    @FXML
    private ComboBox<String> etat;

    @FXML
    private TextArea description_don;

    // @FXML
    // private TextField numberInput;

    @FXML
    private TextField objectif;

    /*
     * @FXML
     * private TextField pointsInput;
     */

    @FXML
    private Button add_new_productBtn;

    // @FXML
    // private Button update_productBtn;

    @FXML
    private ImageView imageInput;

    @FXML
    private HBox choose_photoBtn;

    private File selectedImageFile;
    private String imageName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Fundrising.actionTest);
        if (Fundrising.actionTest == 0) { // add product
            // update_productBtn.setVisible(false);

            titre_don.setText("");
            description_don.setText("");
            // numberInput.setText("");
            objectif.setText("");

            // Image image = new Image(getClass().getResource("/assets/img/" +
            // p.getImage()).toExternalForm());
            // imageInput.setImage(image);
        } else { // update product
            add_new_productBtn.setVisible(false);

            // Instancier le service de fund
            IFundrisingService fundrisingService = new FundrisingService();
            Fundrising fundrising = new Fundrising();
            /*
             * try {
             * p = fundrisingService.getOneProduct(fundrising.getId());
             * } catch (SQLException e) {
             * e.printStackTrace();
             * }
             */
            titre_don.setText(fundrising.getTitre_don());
            description_don.setText(fundrising.getDescription_don());
            objectif.setText(String.valueOf(fundrising.getObjectif()));

            Image image = new Image(
                    getClass().getResource("/assets/img/" + fundrising.getImage_don()).toExternalForm());
            imageInput.setImage(image);

        }

        // ComboBox<String> comboBox = new ComboBox<>();
        etat.getItems().addAll(
                "In progress",
                "Completed");

        Map<String, Boolean> valuesMap = new HashMap<>();
        valuesMap.put("In progress", false);
        valuesMap.put("Completed", true);

        etat.setOnAction(event -> {
            String selectedOption = etat.getValue();
            boolean selectedValue = valuesMap.get(selectedOption);
            System.out.println("Selected option: " + selectedOption);
            System.out.println("Selected value: " + selectedValue);
        });

    }

    @FXML
    void addNewFund(MouseEvent event) throws SQLException {

        Fundrising fundrising = new Fundrising();
        String selectedOption = etat.getValue();
        if (selectedOption != null) {
            boolean etatValue = selectedOption.equals("Completed");
            fundrising.setEtat(etatValue);
        } 
        fundrising.setTitre_don(titre_don.getText());
        fundrising.setDescription_don(description_don.getText());
        
        fundrising.setObjectif(Float.parseFloat(objectif.getText()));

        // fundrising.setQuantite(Integer.parseInt(numberInput.getText()));

        fundrising.setImage(imageName);

        // Instancier le service de produit
        IFundrisingService fundrisingService = new FundrisingService();

        fundrisingService.ajouterFunds(fundrising);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FundrisingList.fxml"));
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

            // Récupérer le nom de l'image sélectionnée
            imageName = selectedImageFile.getName();
            // System.out.println(imageName);

            // Enregistrer l'image dans le répertoire d'images
            Path destination = Paths.get(
                    "C:/Users/Farah Torkhani/Desktop/javafarah - JavaFx/zeroWaste-javaFX/src/assets/img/" + imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
