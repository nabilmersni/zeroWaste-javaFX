package gui.collectInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Categorie_Collecte;
import entities.Collecte;
import entities.User;
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
import services.Categorie_CollectService;
import services.Categorie_CollectService;
import services.IProduitService;
import services.CollectService;
import services.UserService;
import utils.UserSession;
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
public class UserProductAchatsController implements Initializable {

    @FXML
    private Pane content_area;

    @FXML
    private GridPane productListContainer;

    @FXML
    private ScrollPane scrollPane;

    private User user = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // recuperer user connecté
        user = new User();

        UserService userService = new UserService();

        if (UserSession.getInstance().getEmail() == null) {

            try {
                user = userService.getOneUser("nabilkdp0@gmail.com");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(user.getId());

        } else {
            try {
                user = userService.getOneUser(UserSession.getInstance().getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(user.getId());

        }

        CollectService produitService = new CollectService();
        List<Collecte> produits = new ArrayList<>();
        produits = produitService.getProductAchete(user.getId());
        // for (int i = 0; i < produits.size(); i++) {
        // System.out.println("produit acheté: " + produits.get(i));
        // }
        // produits = produitService.getProductFavList(user.getId());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/collectInterfaces/UserProductCard.fxml"));
                VBox oneProductCard = fxmlLoader.load();
                UserProductCardController productCardController = fxmlLoader.getController();
                productCardController.setProductData(produits.get(i));

                if (column == 3) {
                    column = 0;
                    ++row;
                }
                productListContainer.add(oneProductCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneProductCard, new Insets(0, 20, 20, 10));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
