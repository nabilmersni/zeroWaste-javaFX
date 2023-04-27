package gui.fundraisingInterfaces;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entities.Fundrising;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import services.FundrisingService;
import services.IFundrisingService;

public class FundrisingListController implements Initializable {
    private FundrisingService fundService = new FundrisingService();

    @FXML
    private Button fundAddBTN;
    @FXML
    private GridPane FundsListContainer;
    
    @FXML
    private Pane content_area;

    @FXML
    private ComboBox<String> statusInput;

    private static int filter = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instancier le service de fund
        IFundrisingService fundService = new FundrisingService();
        
        // Récupérer tous les funds
        // List<Fundrising> funds = fundService.getAllFunds();
        
        // Afficher les funds dans la console (juste pour tester)
        // System.out.println("Liste des funds:");
        // for (Fundrising fund : funds) {
        //     System.out.println(fund);
        // }

        int column = 0;
        int row = 1;
        try {
            List<Fundrising> fundsList;
            if (filter == 0) {
                fundsList = fundService.getAllFunds();
            } else if (filter == 1) {
                fundsList = fundService.getInProgressFunds();
            } else {
                fundsList = fundService.getCompletedFunds();
            }
            for(int i=0 ; i<fundsList.size(); i++){
    
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OneFundListCard.fxml"));
                HBox oneFundCard = fxmlLoader.load();
                OneFundListCardController fundCardController = fxmlLoader.getController();
                fundCardController.setFundData(fundsList.get(i));
                
                if(column == 1){
                    column=0;
                    ++row;
                }
                FundsListContainer.add(oneFundCard, column++, row);
                //GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneFundCard, new Insets(0, 10, 25, 10));
                oneFundCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<String> items = statusInput.getItems();

        // Add new items to the list
        items.addAll("All", "In progress", "Completed");

        if (filter == 0) {
            statusInput.setValue("All");
        } else if (filter == 1) {
            statusInput.setValue("In progress");
        } else {
            statusInput.setValue("Completed");
        }
    
    }

    @FXML
    private void open_addFunds(MouseEvent event) throws IOException {
        Fundrising.actionTest = 0;
        Parent fxml = FXMLLoader.load(getClass().getResource("AddFundraising.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }

    @FXML
    void statusChange(ActionEvent event) {
        if (statusInput.getValue().equals("All")) {
            filter = 0;
        } else if (statusInput.getValue().equals("In progress")) {
            filter = 1;
        } else {
            filter = 2;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FundrisingList.fxml"));
        try {
            Parent root = loader.load();
            Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

            // Vider la pane et afficher le contenu de ProductsList.fxml
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void excelBtn(MouseEvent event) throws IOException {
        // Créer un nouveau classeur
        Workbook workbook = new XSSFWorkbook();

        // Créer une nouvelle feuille de calcul
        Sheet sheet = workbook.createSheet("Produits");

        // Récupérer la liste des produits
        IFundrisingService fundrisingService = new FundrisingService();
        List<Fundrising> fundsList = fundrisingService.getAllFunds();

        // Créer la première ligne pour les en-têtes des colonnes
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Titre");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Date_debut");
        headerRow.createCell(4).setCellValue("Date_fin");
        headerRow.createCell(5).setCellValue("Etat");
        headerRow.createCell(6).setCellValue("Objectif");
        headerRow.createCell(7).setCellValue("Total");

        // Remplir les données des produits
        int rowNum = 1;
        for (Fundrising fundrising : fundsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(fundrising.getId());
            row.createCell(1).setCellValue(fundrising.getTitre_don());
            row.createCell(2).setCellValue(fundrising.getDescription_don());
            row.createCell(3).setCellValue(fundrising.getDate_don());
            row.createCell(4).setCellValue(fundrising.getDate_don_limite());
            row.createCell(5).setCellValue(fundrising.getObjectif());
            row.createCell(6).setCellValue(fundrising.getTotal());
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