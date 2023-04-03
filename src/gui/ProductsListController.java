package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.Categorie_produit;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.Categorie_produitService;
import services.ICategorie_produitService;
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

    @FXML
    private HBox categoriesModel;

    @FXML
    private GridPane categoriesListContainer;

    @FXML
    private TextField productSearchInput;

    private static int categoryModelShow = 0;
    
    public static int getCategoryModelShow() {
        return categoryModelShow;
    }

    public static void setCategoryModelShow(int categoryModelShow) {
        ProductsListController.categoryModelShow = categoryModelShow;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(ProductsListController.getCategoryModelShow() == 0){
            categoriesModel.setVisible(false);
        }else if(ProductsListController.getCategoryModelShow() == 1){
            categoriesModel.setVisible(true);
        }
        
        // Afficher les produits dans la console (juste pour tester)
      /*   System.out.println("Liste des produits:");
        for (Produit produit : produits) {
            System.out.println(produit);
        }*/

        //set the product list in the grid pane***************************************
        this.setProductGridPaneList();

        //categories list ---------------------------------------------------------
        // Ajouter AddCategoryCard (forum) au debut de la liste 
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("AddCategoryCard.fxml"));
        VBox CategoryAddCard;
        try {
            CategoryAddCard = fxmlLoader1.load();
            categoriesListContainer.add(CategoryAddCard, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instancier le service de categorie
        ICategorie_produitService categoryService = new Categorie_produitService();
        
        // Récupérer toutes les categories
        List<Categorie_produit> categories = categoryService.getAllCategories();
                
        int CategColumn = 0;
        int CategRow = 2;
        try {
            for(int i=0 ; i<categories.size(); i++){
    
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OneCategoriesListCard.fxml"));
                HBox oneCategoryCard = fxmlLoader.load();
                OneCategoriesListCardController categorieCardController = fxmlLoader.getController();
                categorieCardController.setCategoryData(categories.get(i));
                
                if(CategColumn == 1){
                    CategColumn=0;
                    ++CategRow;
                }
                categoriesListContainer.add(oneCategoryCard, CategColumn++, CategRow);
                GridPane.setMargin(oneCategoryCard, new Insets(0, 10, 25, 10));
                oneCategoryCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    
    }    


    @FXML
    private void open_addProduct(MouseEvent event) throws IOException {
        Produit.actionTest = 0;
        Parent fxml = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
   
    }

    @FXML
    void open_CategoriesModel(MouseEvent event) {
        categoriesModel.setVisible(true);
    }

    @FXML
    void close_CategoriesModel(MouseEvent event) {
        categoriesModel.setVisible(false);
        ProductsListController.setCategoryModelShow(0);
    }

    @FXML
    void searchProduct(KeyEvent event) throws IOException {
        Produit.setSearchValue(((TextField) event.getSource()).getText());
        System.out.println("Recherche en cours: " + Produit.getSearchValue());
        
        Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
        productsListContainer.getChildren().clear();
        this.setProductGridPaneList();
}
   

    private void setProductGridPaneList(  ){
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();
        
        List<Produit> produits = null;
        System.out.println("searchValue" + Produit.getSearchValue());
        if(Produit.getSearchValue() == null){
            // Récupérer tous les produits
            produits = produitService.getAllProducts();
        }else{
            produits = produitService.searchProducts(Produit.getSearchValue());
        }
        
        //product list ------------------------------------------------
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
            e.printStackTrace();
        }
    }
    
}
