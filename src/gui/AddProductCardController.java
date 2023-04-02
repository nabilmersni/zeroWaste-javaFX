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

import entities.Produit;
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
import javafx.stage.FileChooser;
import services.IProduitService;
import services.ProduitService;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Node;



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
    
    private int categId;
    private File selectedImageFile;
    private String imageName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Produit.actionTest);
        if(Produit.actionTest == 0){ //add product
            update_productBtn.setVisible(false);

            nameInput.setText("");
            descriptionInput.setText("");
            numberInput.setText("");
            priceInput.setText("");
            pointsInput.setText("");
          //  Image image = new Image(getClass().getResource("/assets/img/" + p.getImage()).toExternalForm());
          //  imageInput.setImage(image);
        }else{ // update product
            add_new_productBtn.setVisible(false);

            // Instancier le service de produit
            IProduitService produitService = new ProduitService();
            Produit p = new Produit();
            try {
                p = produitService.getOneProduct(Produit.getIdProduit());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            nameInput.setText(p.getNom_produit());
            descriptionInput.setText(p.getDescription());
            numberInput.setText(String.valueOf(p.getQuantite()));
            priceInput.setText(String.valueOf(p.getPrix_produit()));
            pointsInput.setText(String.valueOf(p.getPrix_point_produit()));
            Image image = new Image(getClass().getResource("/assets/img/" + p.getImage()).toExternalForm());
            imageInput.setImage(image);

        }
    
        // ComboBox<String> comboBox = new ComboBox<>();
        categoryInput.getItems().addAll(
                "Option 1",
                "Option 2",
                "Option 3"
        );
        
        Map<String, Integer> valuesMap = new HashMap<>();
        valuesMap.put("Option 1", 38);
        valuesMap.put("Option 2", 38);
        valuesMap.put("Option 3", 38);
        
        categoryInput.setOnAction(event -> {
            String selectedOption = categoryInput.getValue();
            int selectedValue = valuesMap.get(selectedOption);
            categId = selectedValue;
            System.out.println("Selected option: " + selectedOption);
            System.out.println("Selected value: " + selectedValue);
        });
        
    
    }    

    @FXML
    void addNewProduct(MouseEvent event) throws SQLException {
     
        Produit produit = new Produit();
        produit.setNom_produit(nameInput.getText());
        produit.setDescription(descriptionInput.getText());
        produit.setCategorie_produit_id(categId);
        produit.setPrix_produit(Float.parseFloat(priceInput.getText()));
        produit.setPrix_point_produit(Integer.parseInt(pointsInput.getText()));
        produit.setQuantite(Integer.parseInt(numberInput.getText()));
        
        produit.setImage(imageName);

         // Instancier le service de produit
         IProduitService produitService = new ProduitService();

         produitService.ajouter(produit);

         FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductsList.fxml"));
            try {
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

    @FXML
    void ajouter_image(MouseEvent event) throws IOException {
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
            Path destination = Paths.get("C:/Users/ALI/Desktop/ZeroWaste - JavaFx/zeroWaste-javaFX/src/assets/img/" + imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @FXML
    void updateProduct(MouseEvent event) {
        
        Produit produit = new Produit();
        produit.setId(Produit.getIdProduit());
        produit.setNom_produit(nameInput.getText());
        produit.setDescription(descriptionInput.getText());
        produit.setCategorie_produit_id(categId);
        produit.setPrix_produit(Float.parseFloat(priceInput.getText()));
        produit.setPrix_point_produit(Integer.parseInt(pointsInput.getText()));
        produit.setQuantite(Integer.parseInt(numberInput.getText()));
        
        if(imageName == ""){
            produit.setImage(produit.getImage());
        }else{
            produit.setImage(imageName);
        }
        

         // Instancier le service de produit
         IProduitService produitService = new ProduitService();

         produitService.updateProduct(produit);
         
         FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductsList.fxml"));
            try {
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