package gui.productInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.mail.MessagingException;

import entities.Achats;
import entities.Categorie_produit;
import entities.Produit;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.AchatsService;
import services.Categorie_produitService;
import services.ICategorie_produitService;
import services.IProduitService;
import services.ProduitService;
import services.UserService;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.SendMail;
import utils.TrayNotificationAlert;
import javafx.util.Duration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    @FXML
    private Button stockBtn;

    @FXML
    private ComboBox<String> categoryInput;

    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModel;

    @FXML
    private HBox offreModel;

    @FXML
    private TextField reductionInput;

    @FXML
    private TextField couponInput;

    @FXML
    private Text reductionInputError;

    @FXML
    private Text couponInputError;

    @FXML
    private Text backToReductionBtn;

    @FXML
    private Text addNewCouponBtn;

    @FXML
    private HBox reductionInputErrorHbox;

    @FXML
    private HBox couponInputErrorHbox;

    @FXML
    private VBox couponForm;

    @FXML
    private VBox reductionForm;

    @FXML
    private HBox submitCouponBtn;

    @FXML
    private HBox submitOfferBtn;

    @FXML
    private ComboBox<String> couponCombobox;
    

    private int categId = -1;

    private int sortValue = -1; // 1: sort by stock *** 0: filter by category *** 2: filter by category and sort
                                // by stock
    private int submitOfferTest = 0;
    private int submitCouponTest = 0;

    private static int categoryModelShow = 0;
    private String selectedOption=null;

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
        qrCodeImgModel.setVisible(false);
        offreModel.setVisible(false);
        reductionInputErrorHbox.setVisible(false);
        couponForm.setVisible(false);
        submitCouponBtn.setVisible(false);
        couponInputErrorHbox.setVisible(false);
        backToReductionBtn.setVisible(false);

        

        if (ProductsListController.getCategoryModelShow() == 0) {
            categoriesModel.setVisible(false);
        } else if (ProductsListController.getCategoryModelShow() == 1) {
            categoriesModel.setVisible(true);
        }

        // Afficher les produits dans la console (juste pour tester)
        /*
         * System.out.println("Liste des produits:");
         * for (Produit produit : produits) {
         * System.out.println(produit);
         * }
         */

        // set the product list in the grid pane***************************************
        this.setProductGridPaneList();

        // categories list ---------------------------------------------------------
        // Ajouter AddCategoryCard (forum) au debut de la liste
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("/gui/productInterfaces/AddCategoryCard.fxml"));
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
            for (int i = 0; i < categories.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/productInterfaces/OneCategoriesListCard.fxml"));
                HBox oneCategoryCard = fxmlLoader.load();
                OneCategoriesListCardController categorieCardController = fxmlLoader.getController();
                categorieCardController.setCategoryData(categories.get(i));

                if (CategColumn == 1) {
                    CategColumn = 0;
                    ++CategRow;
                }
                categoriesListContainer.add(oneCategoryCard, CategColumn++, CategRow);
                GridPane.setMargin(oneCategoryCard, new Insets(0, 10, 25, 10));
                oneCategoryCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajouter la liste des categories au combobox-----------------

        Map<String, Integer> valuesMap = new HashMap<>();
        for (Categorie_produit categorie : categories) {
            categoryInput.getItems().add(categorie.getNom_categorie());
            valuesMap.put(categorie.getNom_categorie(), categorie.getId());
        }

        categoryInput.setOnAction(event -> {
            String selectedOption = categoryInput.getValue();
            int selectedValue = valuesMap.get(selectedOption);
            categId = selectedValue;
            Produit.setSearchValue(null);
            // System.out.println("Selected option: " + selectedOption);
            // System.out.println("Selected value: " + categId);
            GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
            productsListContainer.getChildren().clear();
            this.setProductGridPaneList();
        });
        //Coupon Set email comboBox
    UserService userService = new UserService();
    List<User> EmailList = new ArrayList<>();
    User user = new User();
    try {
        EmailList = userService.getAllUser();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    for (int i =0 ; i<EmailList.size(); i++){
        couponCombobox.getItems().add(EmailList.get(i).getEmail());
    }


    //filter by combobox values
    couponCombobox.setOnAction(event -> {
        selectedOption = couponCombobox.getValue();
        System.out.println("Selected option: " + selectedOption);

    });
    }

    @FXML
    private void open_addProduct(MouseEvent event) throws IOException {
        Produit.actionTest = 0;
        Parent fxml = FXMLLoader.load(getClass().getResource("/gui/productInterfaces/AddProduct.fxml"));
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
        // System.out.println("Recherche en cours: " + Produit.getSearchValue());
        if (stockBtn.getStyleClass().contains("sort__stockBtn-active")) {
            stockBtn.getStyleClass().remove("sort__stockBtn-active");
        }
        categId = -1;

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
        productsListContainer.getChildren().clear();
        this.setProductGridPaneList();
    }

    private void setProductGridPaneList() {
        // Instancier le service de produit
        IProduitService produitService = new ProduitService();

        List<Produit> produits = null;
        System.out.println("searchValue" + Produit.getSearchValue());
        if (Produit.getSearchValue() == null) {
            if (sortValue == -1 && categId == -1) {
                // Récupérer tous les produits
                produits = produitService.getAllProducts();
            }
            if (sortValue == 1 && categId == -1) {
                // sort by stock
                produits = produitService.sortProducts(1, -1);
            }
            if (sortValue == -1 && categId != -1) {
                // filter by category
                produits = produitService.sortProducts(0, categId);
            }
            if (sortValue == 1 && categId != -1) {
                // filter by category and sort by stock
                produits = produitService.sortProducts(1, categId);
            }
            System.out.println("sortValue : " + sortValue);
            System.out.println("categId : " + categId);

        } else {
            produits = produitService.searchProducts(Produit.getSearchValue());
        }

        // product list ------------------------------------------------
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/productInterfaces/OneProductListCard.fxml"));
                HBox oneProductCard = fxmlLoader.load();
                OneProductListCardController productCardController = fxmlLoader.getController();
                productCardController.setProductData(produits.get(i));

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                productsListContainer.add(oneProductCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneProductCard, new Insets(0, 10, 25, 10));
                oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sort__ByStock(MouseEvent event) {
        Produit.setSearchValue(null);
        if (!stockBtn.getStyleClass().contains("sort__stockBtn-active")) {
            stockBtn.getStyleClass().add("sort__stockBtn-active");
            // Button stock = (Button) content_area.lookup("#stockBtn");
            // stock.getStyleClass().add("sort__stockBtn-active");
            sortValue = 1;
        } else {
            stockBtn.getStyleClass().remove("sort__stockBtn-active");
            sortValue = -1;
        }

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));
        GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
        productsListContainer.getChildren().clear();
        this.setProductGridPaneList();

    }


    @FXML
    void close_QrCodeModel(MouseEvent event) {
        qrCodeImgModel.setVisible(false);
    }

    @FXML
    void close_offerModel(MouseEvent event) {
        offreModel.setVisible(false);
    }

    @FXML
    void submit_offer(MouseEvent event) {
        Produit produit = new Produit();
        produit.setId(Produit.getIdProduit());
        produit.setRemise(Float.parseFloat(reductionInput.getText()));

        // Instancier le service de produit
        IProduitService produitService = new ProduitService();

        if (submitOfferTest == 1) {
            produitService.AddProductOffer(produit);

            TrayNotificationAlert.notif("Product", "Offer added successfully.",
                    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

            offreModel.setVisible(false);
            reductionInput.clear();

            // reflesh list
            GridPane productsListContainer = (GridPane) content_area.lookup("#productsListContainer");
            productsListContainer.getChildren().clear();
            this.setProductGridPaneList();
        }
    }

    @FXML
    void reductionTypedInput(KeyEvent event) {
        String reductionText = reductionInput.getText();
        if (!reductionText.matches("-?\\d+(\\.\\d+)?")) {
            reductionInputError.setText("reduction should be a positive number");
            reductionInputErrorHbox.setVisible(true);
            submitOfferTest = 0;
        } else {
            double reduction = Double.parseDouble(reductionText);
            if (reduction < 0) {
                reductionInputError.setText("reduction cannot be negative");
                reductionInputErrorHbox.setVisible(true);
                submitOfferTest = 0;
            } else {
                reductionInputErrorHbox.setVisible(false);
                submitOfferTest = 1;
            }
        }

        double reduction = Double.parseDouble(reductionText);
        if (reduction > 100) {
            reductionInputError.setText("reduction should be less then 100");
            reductionInputErrorHbox.setVisible(true);
            submitOfferTest = 0;
        }
    }

    @FXML
    void addNewCoupon(MouseEvent event) throws IOException {
        reductionForm.setVisible(false);
        submitOfferBtn.setVisible(false);
        couponForm.setVisible(true);
        submitCouponBtn.setVisible(true);
        addNewCouponBtn.setVisible(false);
        backToReductionBtn.setVisible(true);
    }

    @FXML
    void submit_coupon(MouseEvent event) throws MessagingException, SQLException {

            int produit_id=Produit.getIdProduit();
            System.out.println("produit id:"+produit_id);
            String email= selectedOption;
            System.out.println("email :"+email);
          
 if(selectedOption!=null){           
AchatsService achatsService =new AchatsService();
int length;
int min;
int max;
Random random;
int couponCode;

     length = 6; // desired length of the coupon code
     min = (int) Math.pow(10, length - 1); // minimum value of the code
     max = (int) Math.pow(10, length) - 1; // maximum value of the code
     random = new Random();
     couponCode = random.nextInt(max - min + 1) + min;
System.out.println("couponCode :"+couponCode);

/*while(achatsService.VerifCoupon(couponCode)==1){
    length = 6; // desired length of the coupon code
    min = (int) Math.pow(10, length - 1); // minimum value of the code
    max = (int) Math.pow(10, length) - 1; // maximum value of the code
    random = new Random();
    couponCode = random.nextInt(max - min + 1) + min;
}*/
achatsService.addCoupon(couponCode, produit_id, email);


//email
Map<String, String> data = new HashMap<>();
data.put("emailSubject", "Use this coupon code and get a reduction on our site");
data.put("titlePlaceholder", "Get this product for free");
data.put("msgPlaceholder", "Here's the coupon code:");
User user =new User();
user.setEmail(email);
user.setVerificationCode(couponCode);
SendMail.send(user, data);
TrayNotificationAlert.notif("Coupon", "Coupon sent successfully by mail",
NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
offreModel.setVisible(false);

}else {
        TrayNotificationAlert.notif("Coupon", "Select an email",
        NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
    }
  
    }

    @FXML
    void couponTypedInput(KeyEvent event) {
        String couponText = couponInput.getText();
        if (!couponText.matches("-?\\d+(\\.\\d+)?")) {
            couponInputError.setText("coupon should be a positive number");
            couponInputErrorHbox.setVisible(true);
            submitCouponTest = 0;
        } else {
            double coupon = Double.parseDouble(couponText);
            if (coupon < 0) {
                couponInputError.setText("coupon cannot be negative");
                couponInputErrorHbox.setVisible(true);
                submitCouponTest = 0;
            } else {
                couponInputErrorHbox.setVisible(false);
                submitCouponTest = 1;
            }
        }

    }

    @FXML
    void back_toReduction(MouseEvent event) {
        reductionForm.setVisible(true);
        submitOfferBtn.setVisible(true);
        couponForm.setVisible(false);
        submitCouponBtn.setVisible(false);
        addNewCouponBtn.setVisible(true);
        backToReductionBtn.setVisible(false);

    }

    @FXML
    void excelBtn(MouseEvent event) throws IOException {
        // Créer un nouveau classeur
        Workbook workbook = new XSSFWorkbook();

        // Créer une nouvelle feuille de calcul
        Sheet sheet = workbook.createSheet("Produits");

        // Récupérer la liste des produits
        IProduitService produitService = new ProduitService();
        List<Produit> productList = produitService.getAllProducts();

         // Créer la première ligne pour les en-têtes des colonnes
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Quantité");
        headerRow.createCell(3).setCellValue("Prix");
        headerRow.createCell(4).setCellValue("Points");
        headerRow.createCell(5).setCellValue("Catégorie");

        // Remplir les données des produits
        int rowNum = 1;
        for (Produit produit : productList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(produit.getId());
            row.createCell(1).setCellValue(produit.getNom_produit());
            row.createCell(2).setCellValue(produit.getQuantite());
            row.createCell(3).setCellValue(produit.getPrix_produit());
            row.createCell(4).setCellValue(produit.getPrix_point_produit());
            row.createCell(5).setCellValue(produitService.getCategory(produit.getCategorie_produit_id()));
        }

        // Définir la largeur de chaque cellule en fonction de son contenu
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
        

        // Ouvrir une boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers Excel", "*.xlsx"));
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            // Enregistrer le fichier dans l'emplacement choisi par l'utilisateur
            try (FileOutputStream outputStream = new FileOutputStream(selectedFile)) {
                workbook.write(outputStream);
            }
        }
    }
}
