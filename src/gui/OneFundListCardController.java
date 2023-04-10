package gui;



import java.io.IOException;
import java.sql.SQLException;

import entities.Fundrising;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.IFundrisingService;
import services.FundrisingService;
import javafx.scene.Node;


public class OneFundListCardController {

    @FXML
    private ImageView img;

    @FXML
    private HBox deleteFund;

    @FXML
    private HBox editFund;

    @FXML
    private Text fundName;

    @FXML
    private Text objectif;


    
    public void setFundData(Fundrising fundrising){
         // Instancier le service de produit
         IFundrisingService fundrisingService = new FundrisingService();
         
        Image image = new Image(getClass().getResource("/assets/img/").toExternalForm());
        img.setImage(image);

        fundName.setText(fundrising.getTitre_don());
        //get category Name
       // String etat = fundrisingSer(fun.getCategorie_produit_id());
       // categoryProduit.setText(categoryName);

        objectif.setText(""+fundrising.getObjectif());

        //deleteProduit btn click
        deleteFund.setId(String.valueOf(fundrising.getId()));

        deleteFund.setOnMouseClicked(event -> {
            System.out.println("ID du fond à supprimer : " + fundrising.getId());
            try {
                fundrisingService.supprimerFunds(fundrising.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //supprimer le contenu de la liste et afficher la nouvelle liste(apres supprimer)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Fundrising.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis le controller de OneFundListCard.fxml
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de FundsList.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //end  
        });
        //END deleteProduit btn click

        //editProduit btn click
        editFund.setId(String.valueOf(fundrising.getId()));

        editFund.setOnMouseClicked(event -> {
            System.out.println("ID du produit à modifier : " + fundrising.getId());
            fundrising.setId(fundrising.getId());

            fundrising.actionTest = 1;  // pour afficher le bouton update
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFund.fxml"));
            try {
                Parent root = loader.load();
                // Accéder à la pane content_area depuis le controller de OneFundListCard.fxml
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de AddFund.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        });
        //END editProduit btn click


    }
    
    


    
}
