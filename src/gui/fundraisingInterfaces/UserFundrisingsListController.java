package gui.fundraisingInterfaces;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Fundrising;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.FundrisingService;
import services.IFundrisingService;

/**
 * FXML Controller class
 *
 */
public class UserFundrisingsListController implements Initializable {

    @FXML
    private Pane FundsPane;

    @FXML
    private GridPane fundsListContainer;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instancier le service de fund
        IFundrisingService fundrisingService = new FundrisingService();
        
        List<Fundrising> funds = fundrisingService.getAllFunds();
        
        //product list ------------------------------------------------
        int column = 0;
        int row = 1;
        try {
            for(int i=0 ; i<funds.size(); i++){
    
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UserFundrisingCard.fxml"));
                VBox oneFundCard = fxmlLoader.load();
                UserFundrisingCardController fundCardController = fxmlLoader.getController();
                fundCardController.setFundData(funds.get(i));
                
                if(column == 3){
                    column=0;
                    ++row;
                }
                fundsListContainer.add(oneFundCard, column++, row);
                //GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneFundCard, new Insets(0, 20, 20, 10));
              //  oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }    


    
}