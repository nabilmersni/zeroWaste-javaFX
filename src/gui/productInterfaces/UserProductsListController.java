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

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserProductsListController implements Initializable {

    @FXML
    private Pane content_area;

    @FXML
    private GridPane productsListContainer;

    @FXML
    private HBox addedCartModel;

    @FXML
    private Text addedCartModelText;

    @FXML
    private TextField productSearchInput;

    @FXML
    private ComboBox<String> categoryInput;

    @FXML
    private ComboBox<String> priceInput;

    @FXML
    private ComboBox<String> pointsInput;

    @FXML
    private HBox getPromotionalItemsBtn;

    private int categId = -1;
    private String priceComboboxData = null;
    private String pointsComboboxData = null;
    private String promotionaProductsData = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addedCartModel.setVisible(false);
        if (Produit.getSortByCateg() != null) {
            if (Produit.getSortByCateg().equals("Price - Low To High")) {
                priceInput.setValue("Price - Low To High");
                priceComboboxData = "Price - Low To High";
            }
            if (Produit.getSortByCateg().equals("Price - High To Low")) {
                priceInput.setValue("Price - High To Low");
                priceComboboxData = "Price - High To Low";
            }

            if (Produit.getSortByCateg().equals("Points - Low To High")) {
                pointsInput.setValue("Points - Low To High");
                pointsComboboxData = "Points - Low To High";
            }
            if (Produit.getSortByCateg().equals("Points - High To Low")) {
                pointsInput.setValue("Points - High To Low");
                pointsComboboxData = "Points - High To Low";
            }

            if (Produit.getSortByCategId() != -1) {
                categoryInput.setValue(Produit.getSortByCateg());
                categId = Produit.getSortByCategId();
            }

            if (Produit.getSortByCateg().equals("promo")) {
                promotionaProductsData = "promo";
                getPromotionalItemsBtn.getStyleClass().add("promotionalItemsBtnSelected");
            }

        }

        // set the product list in the grid pane***************************************
        this.setProductGridPaneList();

        // Instancier le service de categorie
        ICategorie_produitService categoryService = new Categorie_produitService();

        // Récupérer toutes les categories
        List<Categorie_produit> categories = categoryService.getAllCategories();
        // Ajouter la liste des categories au combobox-----------------
        this.setCategoriesCombobox();

        // ****************set price choices combobox ********************/
        this.setPriceCombobox();

        // ****************set points choices combobox ********************/
        this.setPointsCombobox();

    }

    private void setCategoriesCombobox() {
        // Instancier le service de categorie
        ICategorie_produitService categoryService = new Categorie_produitService();
        // Récupérer toutes les categories
        List<Categorie_produit> categories = categoryService.getAllCategories();

        Map<String, Integer> valuesMap = new HashMap<>();
        categoryInput.getItems().add("All");
        valuesMap.put("All", -1);
        for (Categorie_produit categorie : categories) {
            categoryInput.getItems().add(categorie.getNom_categorie());
            valuesMap.put(categorie.getNom_categorie(), categorie.getId());
        }

        categoryInput.setOnAction(event -> {
            String selectedOption = categoryInput.getValue();
            int selectedValue = valuesMap.get(selectedOption);
            categId = selectedValue;
            Produit.setSortByCategId(categId);
            Produit.setSearchValue(null);

            Produit.setSortByCateg(selectedOption);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setPriceCombobox() {

        priceInput.getItems().add("Price - Low To High");
        priceInput.getItems().add("Price - High To Low");
        priceInput.setOnAction(event -> {
            String selectedOption = priceInput.getValue();

            Produit.setSortByCateg(selectedOption);
            Produit.setSortByCategId(-1);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private void setPointsCombobox() {
        pointsInput.getItems().add("Points - Low To High");
        pointsInput.getItems().add("Points - High To Low");
        pointsInput.setOnAction(event -> {
            String selectedOption = pointsInput.getValue();
            Produit.setSortByCateg(selectedOption);
            Produit.setSortByCategId(-1);

            Parent fxml;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
                content_area.getChildren().removeAll();
                content_area.getChildren().setAll(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
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

    private void setProductGridPaneList() {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();
        List<Produit> produits = null;

        System.out.println("searchValue" + Produit.getSearchValue());
        if (Produit.getSearchValue() == null) {
            if (categId != -1) {
                // filter by category
                produits = produitService.sortProducts(0, categId);
            } else if (priceComboboxData != null) {
                // sort by price
                produits = produitService.sortProductsUser("price", priceComboboxData);
            } else if (pointsComboboxData != null) {
                // sort by points
                produits = produitService.sortProductsUser("points", pointsComboboxData);
            } else if (promotionaProductsData != null) {
                // sort by points
                produits = produitService.getPromotionalProducts();
            } else {
                produits = produitService.getAllProducts();
            }
        } else {
            produits = produitService.searchProducts(Produit.getSearchValue());
        }

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
    void searchProduct(KeyEvent event) throws IOException {
        Produit.setSearchValue(((TextField) event.getSource()).getText());
        System.out.println("Recherche en cours: " + Produit.getSearchValue());

        // categId = -1;

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
        productsListContainer.getChildren().clear();
        this.setProductGridPaneList();
    }

    @FXML
    void getPromotionalItems(MouseEvent event) {
        if (!getPromotionalItemsBtn.getStyleClass().contains("promotionalItemsBtnSelected")) {

            Produit.setSortByCateg("promo");
        } else {
            getPromotionalItemsBtn.getStyleClass().remove("promotionalItemsBtnSelected");
            Produit.setSortByCateg(null);
        }

        Produit.setSortByCategId(-1);

        Parent fxml;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/UserProductsList.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
