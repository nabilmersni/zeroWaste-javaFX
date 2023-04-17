package gui.productInterfaces;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.IProduitService;
import services.ProduitService;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserProductsListController implements Initializable {

    @FXML
    private Pane ProductsPane;

    @FXML
    private GridPane productsListContainer;

    @FXML
    private HBox addedCartModel;

    @FXML
    private Text addedCartModelText;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addedCartModel.setVisible(false);

        // Instancier le service de produit
        IProduitService produitService = new ProduitService();

        List<Produit> produits = produitService.getAllProducts();

        // product list ------------------------------------------------
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/productInterfaces/UserProductCard.fxml"));
                VBox oneProductCard = fxmlLoader.load();
                UserProductCardController productCardController = fxmlLoader.getController();
                productCardController.setProductData(produits.get(i));

                if (column == 3) {
                    column = 0;
                    ++row;
                }
                productsListContainer.add(oneProductCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneProductCard, new Insets(0, 20, 20, 10));
                // oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box,
                // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void closeAddToCartModel(MouseEvent event) {
        addedCartModel.setVisible(false);
    }

    @FXML
    void open_CommandsList(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));

        Parent root = loader.load();
        // Accéder à la pane content_area depuis le controller de
        // OneProductListCard.fxml
        Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

        // Vider la pane et afficher le contenu de UserCommandsList.fxml
        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);
    }

}
