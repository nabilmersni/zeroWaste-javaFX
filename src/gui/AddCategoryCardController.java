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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.Locale.Category;

import entities.Categorie_produit;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import services.Categorie_produitService;
import services.ICategorie_produitService;
import services.IProduitService;
import services.ProduitService;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Node;



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
    
    private File selectedImageFile;
    private String imageName;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Categorie_produit.actionTest);
        if(Categorie_produit.actionTest == 0){ //add product
            update_categoryBtn.setVisible(false);

        }else{ // update product
            add_new_categoryBtn.setVisible(false);

            // Instancier le service de categorie
            ICategorie_produitService categoryService = new Categorie_produitService() ;
            Categorie_produit c = new Categorie_produit();
            try {
                c = categoryService.getOneCategory(Categorie_produit.getIdCategory());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            nameInput.setText(c.getNom_categorie());
            Image image = new Image(getClass().getResource("/assets/ProductUploads/" + c.getImage_categorie()).toExternalForm());
            imageInput.setImage(image);
            imageName = c.getImage_categorie();


        }
        
    
    }    

    @FXML
    void addNewCategory(MouseEvent event) throws SQLException {
     
        Categorie_produit categorie = new Categorie_produit();
        categorie.setNom_categorie(nameInput.getText());
        
        categorie.setImage_categorie(imageName);

         // Instancier le service de categorie
         ICategorie_produitService categoryService = new Categorie_produitService();

         categoryService.ajouter(categorie);

         ProductsListController.setCategoryModelShow(1);
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
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf(".")); // Récupérer l'extension de l'image
            imageName = uniqueID + extension;
            
            // Enregistrer l'image dans le dossier "uploads"
            Path destination = Paths.get("C:/Users/ALI/Desktop/ZeroWaste - JavaFx/zeroWaste-javaFX/src/assets/ProductUploads/" + imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }
    

    @FXML
    void updateCategory(MouseEvent event) {
        
        Categorie_produit category = new Categorie_produit();
        category.setId(Categorie_produit.getIdCategory());
        category.setNom_categorie(nameInput.getText());
        
        category.setImage_categorie(imageName);

         // Instancier le service de category
         ICategorie_produitService categoryService = new Categorie_produitService();

         categoryService.updateCategory(category);
         
         ProductsListController.setCategoryModelShow(1);
         Categorie_produit.actionTest = 0;  // pour afficher le bouton ajouter et cacher le bouton modifier
         
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
