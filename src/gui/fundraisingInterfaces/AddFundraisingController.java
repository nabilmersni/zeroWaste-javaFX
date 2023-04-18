package gui.fundraisingInterfaces;

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
public class AddFundraisingController implements Initializable {

    @FXML
    private GridPane addFundContainer;
    
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

         
// Ajouter fundContainer à la deuxième ligne de AddfundContainer
FXMLLoader fxmlLoader1 = new FXMLLoader();
fxmlLoader1.setLocation(getClass().getResource("AddFundraisingCard.fxml"));
VBox fundContainer1 = fxmlLoader1.load();
addFundContainer.add(fundContainer1, 0, 1);
GridPane.setMargin(fundContainer1, new Insets(0, 10, 25, 10));
fundContainer1.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }    

   

    
}