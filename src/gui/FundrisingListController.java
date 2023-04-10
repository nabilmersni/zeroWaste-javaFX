package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Fundrising;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instancier le service de fund
        IFundrisingService fundService = new FundrisingService();
        
        // Récupérer tous les funds
        List<Fundrising> funds = fundService.getAllFunds();
        
        // Afficher les funds dans la console (juste pour tester)
        System.out.println("Liste des funds:");
        for (Fundrising fund : funds) {
            System.out.println(fund);
        }

        int column = 0;
        int row = 1;
        try {
            for(int i=0 ; i<funds.size(); i++){
    
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OneFundListCard.fxml"));
                HBox oneFundCard = fxmlLoader.load();
                OneFundListCardController fundCardController = fxmlLoader.getController();
                fundCardController.setFundData(funds.get(i));
                
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }

    @FXML
    private void open_addFunds(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AddFundraising.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

        Fundrising.actionTest = 0;
    }

            
      
}