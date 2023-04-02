package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import services.IProduitService;
import services.ProduitService;
import zerowaste.ZeroWaste;


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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();
        
        // Récupérer tous les produits
        List<Produit> produits = produitService.getAllProducts();
        
        // Afficher les produits dans la console (juste pour tester)
        System.out.println("Liste des produits:");
        for (Produit produit : produits) {
            System.out.println(produit);
        }

        int column = 0;
        int row = 1;
        try {
            for(int i=0 ; i<produits.size(); i++){
    
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OneProductListCard.fxml"));
                HBox oneProductCard = fxmlLoader.load();
                OneProductListCardController productCardController = fxmlLoader.getController();
                productCardController.setProductData(produits.get(i));
                
                if(column == 1){
                    column=0;
                    ++row;
                }
                productsListContainer.add(oneProductCard, column++, row);
                //GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneProductCard, new Insets(0, 10, 25, 10));
                oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");
                
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }    


    @FXML
    private void open_addProduct(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        Produit.actionTest = 0;
    }

   

    
}
