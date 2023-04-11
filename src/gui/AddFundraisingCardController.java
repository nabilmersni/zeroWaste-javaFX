package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Fundrising;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

    @FXML
    private TextField objectif;

    @FXML
    private DatePicker dateDon;

    @FXML
    private DatePicker dateDonLimite;

    @FXML
    private Button add_new_productBtn;

    @FXML
    private Button update_productBtn;

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
             update_productBtn.setVisible(false);

            titre_don.setText("");
            description_don.setText("");
            objectif.setText("");
            dateDon.setValue(LocalDate.now());
            dateDonLimite.setValue(LocalDate.now());
            

        //  Image image = new Image(getClass().getResource("/assets/img/" +
        //     // p.getImage()).toExternalForm());
        //      imageInput.setImage(image);
         } else { // update product
             add_new_productBtn.setVisible(false);

            // Instancier le service de produit
            IFundrisingService fundrisingService = new FundrisingService();
            Fundrising p = new Fundrising();
            try {
                p = fundrisingService.getOneFund(Fundrising.getIdFund());
            } catch (SQLException e) {
                e.printStackTrace();
            }
          
            titre_don.setText(p.getTitre_don());
            description_don.setText(p.getDescription_don());
            objectif.setText(String.valueOf(p.getObjectif()));
            dateDon.setValue(LocalDate.now());
            dateDonLimite.setValue(LocalDate.now());


            // Image image = new Image(getClass().getResource("/assets/img/" + fundrising.getImage_don()).toExternalForm());
            // imageInput.setImage(image);

        }

        etat.getItems().addAll("In progress", "Completed");
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("In progress", "In progress");
        valuesMap.put("Completed", "Completed");

        etat.setOnAction(event -> {
            String selectedOption = etat.getValue();
            String selectedValue = valuesMap.get(selectedOption);
            System.out.println("Selected option: " + selectedOption);
            System.out.println("Selected value: " + selectedValue);
        });

    }
    @FXML
    void ajouter_image1(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageInput.setImage(image);

            // Récupérer le nom de l'image sélectionnée
            imageName = selectedImageFile.getName();
            //System.out.println(imageName);
            
            // Enregistrer l'image dans le répertoire d'images
            Path destination = Paths.get("C:/Users/Farah Torkhani/Desktop/javafarah/ - zeroWaste-javaFX/src/assets/img" + imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @FXML
    void addNewFund(MouseEvent event) throws SQLException {

        Fundrising fundrising = new Fundrising();
        String selectedOption = etat.getValue();
        if (selectedOption != null) {
            String etatValue = selectedOption;
            fundrising.setEtat(etatValue);
        } 
        fundrising.setTitre_don(titre_don.getText());
        fundrising.setDescription_don(description_don.getText());
        fundrising.setObjectif(Float.parseFloat(objectif.getText()));
        fundrising.setImage("test");
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        LocalDate date_don_limite = dateDonLimite.getValue();
        Date sqlDate = Date.valueOf(date_don_limite);
        if (sqlDate.before(currentDate)) {
            // Display an error message using an Alert dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The selected date is earlier than the current date");
            alert.setContentText("Please select a valid date.");
            alert.showAndWait();
    
            // Return from the method and prevent the data from being added to the database
            return;
        }
        Date dateDon = fundrising.getDate_don();
        if (dateDon != null && sqlDate.before(dateDon)) {
            // Display an error message using an Alert dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The limit date for donations is earlier than the beginning date of the fund");
            alert.setContentText("Please select a valid date.");
            alert.showAndWait();
    
            // Return from the method and prevent the data from being added to the database
            return;
        }
        fundrising.setDate_don(currentDate);
        fundrising.setDate_don_limite(sqlDate);

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


    @FXML
    void updateFund(MouseEvent event) {
        
        
        Fundrising fundrising = new Fundrising();
        fundrising.setId(Fundrising.getIdFund());
        fundrising.setTitre_don(titre_don.getText());
        fundrising.setDescription_don(description_don.getText());
        fundrising.setEtat(etat.getValue());
        fundrising.setObjectif(Float.parseFloat(objectif.getText()));
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        LocalDate date_don_limite = dateDonLimite.getValue();
        Date sqlDate = Date.valueOf(date_don_limite);
        fundrising.setDate_don(currentDate);
        fundrising.setDate_don_limite(sqlDate);
        
        // if(imageName == ""){
        //     produit.setImage(produit.getImage());
        // }else{
        //     produit.setImage(imageName);
        // }
        

         // Instancier le service de produit
         IFundrisingService fundrisingService = new FundrisingService();

         try {

         fundrisingService.updateFunds(fundrising);
         
         FXMLLoader loader = new FXMLLoader(getClass().getResource("FundrisingList.fxml"));
            
                Parent root = loader.load();
                // Accéder à la pane content_area depuis le controller de OneProductListCard.fxml
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de ProductsList.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }


}
