package gui;



import java.io.IOException;
import java.sql.SQLException;

import entities.Categorie_produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.Categorie_produitService;
import services.ICategorie_produitService;
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
public class OneCategoriesListCardController {

    @FXML
    private HBox deleteCategory;

    @FXML
    private HBox editCategory;

    @FXML
    private ImageView img;

    @FXML
    private Text nomCategorie;


    
    public void setCategoryData(Categorie_produit category){
         // Instancier le service de categorie
         ICategorie_produitService categoryService = new Categorie_produitService();
         
        Image image = new Image(getClass().getResource("/assets/ProductUploads/" + category.getImage_categorie()).toExternalForm());
        img.setImage(image);

        nomCategorie.setText(category.getNom_categorie());

        //deleteCategory btn click
        deleteCategory.setId(String.valueOf(category.getId()));

        deleteCategory.setOnMouseClicked(event -> {
            System.out.println("ID du categorie à supprimer : " + category.getId());
            try {
                categoryService.supprimer(category.getId());
                ProductsListController.setCategoryModelShow(1);
                TrayNotificationAlert.notif("Category", "Category deleted successfully.",
                                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //supprimer le contenu de la liste et afficher la nouvelle liste(apres supprimer)
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
            //end  
        });
        //END deleteProduit btn click

        //editProduit btn click
        editCategory.setId(String.valueOf(category.getId()));

        editCategory.setOnMouseClicked(event -> {
            System.out.println("ID du category à modifier : " + category.getId());
            Categorie_produit.setIdCategory(category.getId());

            Categorie_produit.actionTest = 1;  // pour afficher le bouton update
            ProductsListController.setCategoryModelShow(1);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductsList.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis le controller de OneProductListCard.fxml
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de AddProduct.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        });
        //END editProduit btn click



        
        
    }
    
    


    
}
