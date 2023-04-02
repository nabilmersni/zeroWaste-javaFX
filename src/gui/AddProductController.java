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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.IProduitService;
import services.ProduitService;


/**
 * FXML Controller class
 *
 * @author ALI
 */
public class AddProductController implements Initializable {

    @FXML
    private GridPane AddproductContainer;
    
    @FXML
    private ScrollPane scrollPane;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            //scrollPane.setFitToHeight(true);
            //scrollPane.setPrefHeight(577);
           // scrollPane.setPrefHeight(1000);

         
// Ajouter productContainer à la deuxième ligne de AddproductContainer
FXMLLoader fxmlLoader1 = new FXMLLoader();
fxmlLoader1.setLocation(getClass().getResource("AddProductCard.fxml"));
VBox productContainer1 = fxmlLoader1.load();
AddproductContainer.add(productContainer1, 0, 1);
GridPane.setMargin(productContainer1, new Insets(0, 10, 25, 10));
productContainer1.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }    

   

    
}
