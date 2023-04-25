package gui.productInterfaces;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Categorie_produit;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.Categorie_produitService;
import services.ICategorie_produitService;
import services.IProduitService;
import services.ProduitService;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserProductDetailsController implements Initializable {

    @FXML
    private Pane content_area;

    @FXML
    private GridPane productDetailsContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox addReviewsModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addReviewsModel.setVisible(false);

        try {

            // Ajouter productContainer à la première ligne de AddproductContainer
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("/gui/productInterfaces/UserProductDetailsCard.fxml"));
            VBox productContainer1 = fxmlLoader1.load();
            productDetailsContainer.add(productContainer1, 0, 1);
            GridPane.setMargin(productContainer1, new Insets(0, 10, 25, 10));
            // productContainer1.setStyle("-fx-effect: dropshadow(three-pass-box,
            // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
