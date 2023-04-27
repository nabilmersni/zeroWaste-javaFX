package gui.fundraisingInterfaces;

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
import java.util.UUID;

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

    @FXML
    private Text titreInputError;

    @FXML
    private Text descriptionInputError;

    @FXML
    private Text etatInputError;

    @FXML
    private Text objectifInputError;

    @FXML
    private Text imageInputError;

    @FXML
    private HBox titreInputErrorHbox;

    @FXML
    private HBox descriptionInputErrorHbox;

    @FXML
    private HBox etatInputErrorHbox;

    @FXML
    private HBox objectifInputErrorHbox;

    @FXML
    private HBox imageInputErrorHbox;

    private String etatOption = "";
    private int titreTest = 0;
    private int descriptionTest = 0;
    private int objectifTest = 0;
    private int etatTest = 0;
    private int imageTest = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Fundrising.actionTest);
        titreInputErrorHbox.setVisible(false);
        descriptionInputErrorHbox.setVisible(false);
        etatInputErrorHbox.setVisible(false);
        objectifInputErrorHbox.setVisible(false);
        imageInputErrorHbox.setVisible(false);

        if (Fundrising.actionTest == 0) { 
             update_productBtn.setVisible(false);
            
         } else { // update fundraising
             add_new_productBtn.setVisible(false);

            IFundrisingService fundrisingService = new FundrisingService();
            Fundrising fundrising = new Fundrising();
            try {
                fundrising = fundrisingService.getOneFund(Fundrising.getIdFund());
            } catch (SQLException e) {
                e.printStackTrace();
            }
          
            titre_don.setText(fundrising.getTitre_don());
            description_don.setText(fundrising.getDescription_don());
            objectif.setText(String.valueOf(fundrising.getObjectif()));
            etatOption = fundrising.getEtat();
            dateDon.setValue(LocalDate.now());
            dateDonLimite.setValue(LocalDate.now());

            titreTest = 1;
            descriptionTest = 1;
            objectifTest = 1;
            etatTest = 1;
            imageTest = 1;


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
            etatOption = selectedValue;
            etatTest = 1;
            etatInputErrorHbox.setVisible(false);
        });

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

            Path destination = Paths.get(System.getProperty("user.dir"), "src", "assets", "FundraisingUploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            imageTest = 1;
            imageInputErrorHbox.setVisible(false);
        }
    }

    @FXML
    void addNewFund(MouseEvent event) throws SQLException {

        Fundrising fundrising = new Fundrising();

        if (etatOption == "") {
            etatTest = 0;
            etatInputErrorHbox.setVisible(true);
        } else {
            etatTest = 1;
            fundrising.setEtat(etatOption);
            
        }
        
        if (titre_don.getText().isEmpty()) {
            titreTest = 0;
            titreInputErrorHbox.setVisible(true);
        } else {    
            titreTest = 1;
            fundrising.setTitre_don(titre_don.getText());
        }

        if (description_don.getText().isEmpty()) {
            descriptionTest = 0;
            descriptionInputErrorHbox.setVisible(true);
        } else {    
            descriptionTest = 1;
            fundrising.setDescription_don(description_don.getText());
        }

        if (objectif.getText().isEmpty()) {
            objectifTest = 0;
            objectifInputErrorHbox.setVisible(true);
        } else {    
            objectifTest = 1; 
            fundrising.setObjectif(Float.parseFloat(objectif.getText()));
        }

        if (imageName == null) {
            imageTest = 0;
            imageInputErrorHbox.setVisible(true);
        } else {
            imageTest = 1;
            fundrising.setImage(imageName);
        }

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

        System.out.println(titreTest);
        System.out.println(descriptionTest);
        System.out.println(etatTest);
        System.out.println(objectifTest);
        System.out.println(imageTest);


        if (titreTest == 1 && descriptionTest == 1 && etatTest == 1 && objectifTest == 1 && imageTest == 1) {
            IFundrisingService fundrisingService = new FundrisingService();

            fundrisingService.ajouterFunds(fundrising);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FundrisingList.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis le controller de
         
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
    void updateFund(MouseEvent event) {
        Fundrising fundrising = new Fundrising();
        fundrising.setId(Fundrising.getIdFund());

        if (etatOption == "") {
            etatTest = 0;
            etatInputErrorHbox.setVisible(true);
        } else {
            if (titreTest == 1) {
                fundrising.setEtat(etatOption);
            }
        }
        
        if (titre_don.getText().isEmpty()) {
            titreTest = 0;
            titreInputErrorHbox.setVisible(true);
        } else {    
            if (titreTest == 1) {
                fundrising.setTitre_don(titre_don.getText());
            }
        }

        if (description_don.getText().isEmpty()) {
            descriptionTest = 0;
            descriptionInputErrorHbox.setVisible(true);
        } else {    
            if (descriptionTest == 1) {
                fundrising.setDescription_don(description_don.getText());
            }
        }

        if (objectif.getText().isEmpty()) {
            objectifTest = 0;
            objectifInputErrorHbox.setVisible(true);
        } else {    
            if (objectifTest == 1) {
                fundrising.setObjectif(Float.parseFloat(objectif.getText()));
            }
        }

        if (imageName == null) {
            imageTest = 0;
            imageInputErrorHbox.setVisible(true);
        } else {
            imageTest = 1;
            fundrising.setImage(imageName);
        }

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
        
       
        if (titreTest == 1 && descriptionTest == 1 && etatTest == 1 && objectifTest == 1 && imageTest == 1) {
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


}
