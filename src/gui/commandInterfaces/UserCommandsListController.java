package gui.commandInterfaces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.awt.geom.Point;

import entities.Achats;
import entities.Commands;
import entities.Produit;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.AchatsService;
import services.CommandsService;
import services.UserService;
import utils.UserSession;

import javafx.scene.Node;
import javafx.scene.Parent;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import javafx.util.Duration;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;


import java.util.HashMap;
import java.util.Map;

/**
 * FXML Controller class
 *
 * @author ALI
 */
public class UserCommandsListController implements Initializable {

    @FXML
    private GridPane commandsListContainer;

    @FXML
    private Pane content_area;

    @FXML
    private HBox checkoutModel;

    @FXML
    private HBox paymentModel;

    @FXML
    private TextField addressInput;

    @FXML
    private Text addressInputError;

    @FXML
    private HBox addressInputErrorHbox;

    @FXML
    private TextField cityInput;

    @FXML
    private Text cityInputError;

    @FXML
    private HBox cityInputErrorHbox;

   

    @FXML
    private TextField emailInput;

    @FXML
    private Text emailInputError;

    @FXML
    private HBox emailInputErrorHbox;

    @FXML
    private TextField fullnameInput;

    @FXML
    private Text fullnameInputError;

    @FXML
    private HBox fullnameInputErrorHbox;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField phoneInput;

    @FXML
    private Text phoneInputError;

    @FXML
    private HBox phoneInputErrorHbox;

    @FXML
    private TextField zipcodeInput;

    @FXML
    private Text zipcodeInputError;

    @FXML
    private HBox zipcodeInputErrorHbox;

    @FXML
    private Text address;

    @FXML
    private Text city;

    @FXML
    private Text fullname;

    @FXML
    private Text phone;

    @FXML
    private HBox addCheckoutBtn;

    @FXML
    private HBox updateCheckoutBtn;

    @FXML
    private Text totalPoints;

    @FXML
    private Text totalPrice;

    @FXML
    private HBox selectPaymentMethod;

    @FXML
    private Text paymentMethod;

    @FXML
    private HBox paymentQuestion;

    @FXML
    private HBox paymentValidate;

    @FXML
    private Text paymentModelTitle;

    @FXML
    private Text totalPointsValidate;



    private int achatId = -1; 
    
    int totalPts=0;
    float totalPrx = 0;
    int command_Id;
    int user_Id;
    int point;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkoutModel.setVisible(false);
        paymentModel.setVisible(false);
        fullnameInputErrorHbox.setVisible(false);
        emailInputErrorHbox.setVisible(false);
        cityInputErrorHbox.setVisible(false);
        phoneInputErrorHbox.setVisible(false);
        addressInputErrorHbox.setVisible(false);
        zipcodeInputErrorHbox.setVisible(false);

        updateCheckoutBtn.setVisible(false);
        paymentValidate.setVisible(false);

      

            
         
        //set one command details Model***************************************
         try {
            FXMLLoader fxmlLoader1 = new FXMLLoader();
            fxmlLoader1.setLocation(getClass().getResource("/gui/commandInterfaces/UsercommandsHeader.fxml"));
   
            HBox commandinfoCard = fxmlLoader1.load();
            //AdminCommandLivraisonCardController commandLivraisonController = fxmlLoader1.getController();
            //commandLivraisonController.setCommandLivraison();
            commandsListContainer.add(commandinfoCard, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User() ;
        
        UserService userService = new UserService();

         if (UserSession.getInstance().getEmail() == null ) {
          
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
        CommandsService commandsService = new CommandsService();
        Commands command = new Commands();
        command = commandsService.getOneCommand(user.getId()); 
        if ( command != null ){
            AchatsService achatsService = new AchatsService();
            List<Produit> listpProduits = new ArrayList<>();
            listpProduits = achatsService.getAllProducts(command.getId()); 
    
       


        int column = 0;
        int row = 2;
        try {
            for (int i = 0; i < listpProduits.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/commandInterfaces/UserCommandsListItem.fxml"));
                HBox oneCommandCard = fxmlLoader.load();
                

                UserCommandsListItemController commandCardController = fxmlLoader.getController();
                commandCardController.setCommandProduit(listpProduits.get(i), command.getId() );

                if (column == 1) {
                    column = 0;
                    ++row;
                }
                commandsListContainer.add(oneCommandCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneCommandCard, new Insets(0, 10, 15, 10));
             //   oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        //END - set one command details Model


        //********************************************** */
          //set total price and total points
          AchatsService achatsServ = new AchatsService();
          List<Produit> produit = new ArrayList<>();
          if(command != null){
          produit = achatsServ.getAllProducts( command.getId() );
          command_Id = command.getId();
          user_Id =user.getId();
          point =user.getPoint();
          for(int i = 0 ; i < produit.size() ; i++){
              totalPts += produit.get(i).getPrix_point_produit() * produit.get(i).getQuantite();
              totalPrx += produit.get(i).getPrix_produit() * produit.get(i).getQuantite();
          }
          totalPoints.setText(String.valueOf(totalPts));
          totalPrice.setText(String.valueOf(totalPrx));
        }
        else {
            totalPoints.setText(String.valueOf(0));
            totalPrice.setText(String.valueOf(0));
        }
        // test pour l'affichage du paiment methode
        
        System.out.println(command);
        if(command != null){
            Achats oneAchat = new Achats();

            try {
                oneAchat = achatsServ.getAddressDetails(command.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(oneAchat != null){
          //  System.out.println("Payment_method: " + oneAchat.getPayment_method());
            if(oneAchat.getPayment_method()!=null){
            if(oneAchat.getPayment_method().equals("Points") ){
                paymentQuestion.setVisible(false);
                selectPaymentMethod.setVisible(false);
                paymentValidate.setVisible(true);
                paymentModelTitle.setText("3.  Validate  ");
        
                totalPointsValidate.setText(String.valueOf(totalPts));
            }
            }
            }

        }

    }

    @FXML
    void close_commandCheckoutModel(MouseEvent event) {
        checkoutModel.setVisible(false);
    }

    @FXML
    void open_checkoutModel(MouseEvent event) throws SQLException {
        
        //récupérer user connecté
        User user = new User() ;
        UserService userService = new UserService();

         if (UserSession.getInstance().getEmail() == null ) {
          
                try {
                    user = userService.getOneUser("nabilkdp0@gmail.com");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // System.out.println(user.getId()); 
       
        } else {
                try {
                    user = userService.getOneUser(UserSession.getInstance().getEmail());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // System.out.println(user.getId()); 

        }
        //récupérer la commande courante
        CommandsService commandeservice = new CommandsService();
        Commands command = new Commands();
        command = commandeservice.getOneCommand(user.getId());
           
        //get checkout address details
        Achats achat = new Achats();
        AchatsService achatsService = new AchatsService();
        achat = achatsService.getAddressDetails(command.getId());
        if(achat == null){
            paymentModel.setVisible(false);
            addCheckoutBtn.setVisible(true);
            checkoutModel.setVisible(true);
        }else{
            //set address details the open paymentModel
            fullname.setText(achat.getFull_name());
            address.setText(achat.getAddress());
            city.setText(achat.getCity());
            phone.setText(String.valueOf(achat.getTel()));
            
            //recuperer achatID (bech man3awdouch nrecuperio el userId + commande courante bech najmou nrecuperio el achat )
            achatId = achat.getId();

            paymentModel.setVisible(true);
    
        }
        
    }

    @FXML
    void close_commandPaymentModel(MouseEvent event) {
        paymentModel.setVisible(false);
    }

    @FXML
    void switchToPaymentModel(MouseEvent event) throws SQLException { // add checkout btn
        Achats achat= new Achats();
        achat.setFull_name(fullnameInput.getText());
        achat.setEmail(emailInput.getText());
        achat.setCity(cityInput.getText());
        achat.setTel(Integer.parseInt(phoneInput.getText()));
        achat.setAddress(addressInput.getText());
        achat.setZip_code(Integer.parseInt(zipcodeInput.getText()));
        CommandsService commandeservice = new CommandsService();
        //recuperation user
        User user = new User() ;
        
        UserService userService = new UserService();

         if (UserSession.getInstance().getEmail() == null ) {
          
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
        Commands command = new Commands();
        command = commandeservice.getOneCommand(user.getId());
        achat.setCommande_id(command.getId());
       AchatsService achatsService = new AchatsService();
       //ajouter checkout details(address) dans la base puis afficher payment model
       achatsService.Checkout(achat);
        checkoutModel.setVisible(false);

        //set address details the open paymentModel
        achat = achatsService.getAddressDetails(command.getId());

        fullname.setText(achat.getFull_name());
        address.setText(achat.getAddress());
        city.setText(achat.getCity());
        phone.setText(String.valueOf(achat.getTel()));
        paymentModel.setVisible(true);

        //recuperer achatID (bech man3awdouch nrecuperio el userId + commande courante bech najmou nrecuperio el achat )
        achatId = achat.getId();
    }
   

    @FXML
    void DeleteCheckout(MouseEvent event) throws SQLException, IOException {
      
        AchatsService achatsService = new AchatsService();
        achatsService.supprimerAddress(achatId);    
        //afficher une notification et actualiser la page
        TrayNotificationAlert.notif("Address", "Address deleted successfully.",
                        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));

                Parent root = loader.load();
                // Accéder à la pane content_area depuis ce controller
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                // Vider la pane et afficher le contenu de UserCommandsList.fxml
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
    }


    @FXML
    void updateCheckout(MouseEvent event) throws SQLException { // open update checkout model
        //set input data then open the checkout model
        Achats achat = new Achats();
        AchatsService achatsService = new AchatsService();
        achat = achatsService.getOneAchat(achatId);
        
        fullnameInput.setText(achat.getFull_name());
        emailInput.setText(achat.getEmail());
        cityInput.setText(achat.getCity());
        phoneInput.setText(String.valueOf(achat.getTel()));
        addressInput.setText(achat.getAddress());
        zipcodeInput.setText(String.valueOf(achat.getZip_code()));

        paymentModel.setVisible(false);
        addCheckoutBtn.setVisible(false);
        updateCheckoutBtn.setVisible(true);
        checkoutModel.setVisible(true);
        
        
    }

    @FXML
    void UpdateCheckoutBtn(MouseEvent event) { //update checkout (submit modifications) then back to payment model
        Achats achat= new Achats();
        achat.setFull_name(fullnameInput.getText());
        achat.setEmail(emailInput.getText());
        achat.setCity(cityInput.getText());
        achat.setTel(Integer.parseInt(phoneInput.getText()));
        achat.setAddress(addressInput.getText());
        achat.setZip_code(Integer.parseInt(zipcodeInput.getText()));
        achat.setId(achatId);

        AchatsService achatsService = new AchatsService();
        achatsService.updateCheckout(achat);

        TrayNotificationAlert.notif("Checkout Address", "Address updated successfully.",
            NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

            checkoutModel.setVisible(false);
            //set address details the open paymentModel
            fullname.setText(achat.getFull_name());
            address.setText(achat.getAddress());
            city.setText(achat.getCity());
            phone.setText(String.valueOf(achat.getTel()));
            paymentModel.setVisible(true);
    }

    @FXML
    void on_zeroWastePoints_methodPayment_click(MouseEvent event) {
        paymentQuestion.setVisible(false);
        selectPaymentMethod.setVisible(false);
        paymentValidate.setVisible(true);
        paymentModelTitle.setText("3.  Validate  ");

        totalPointsValidate.setText(String.valueOf(totalPts));

        AchatsService achatsService = new AchatsService();
        achatsService.updatePaymentMethod(1, achatId, "Points");
    }


    @FXML
    void updatePaymentMethod(MouseEvent event) {
        paymentValidate.setVisible(false);
        paymentModelTitle.setText("2.  Payment");
        paymentQuestion.setVisible(true);
        selectPaymentMethod.setVisible(true);
        
        
    }

    @FXML
    void deletePaymentMethod(MouseEvent event) {
        paymentValidate.setVisible(false);
        paymentModelTitle.setText("2.  Payment");
        paymentQuestion.setVisible(true);
        selectPaymentMethod.setVisible(true);
        
        AchatsService achatsService = new AchatsService();
        achatsService.updatePaymentMethod(2, achatId, "null");
        
    }

    @FXML
    void validateCheckout(MouseEvent event) throws IOException, SQLException {
     AchatsService achatsService = new AchatsService();
     System.out.println("point"+point);
     System.out.println("total"+totalPts);
    Achats achat = new Achats();
    achat = achatsService.getOneAchat(achatId);
    if(achat.getPayment_method().equals("Points")){
     if(point < totalPts){
        TrayNotificationAlert.notif("Checkout", "Not enough point.",
        NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));

     }else{
    point =point-totalPts;
    achatsService.ValidateCheckoutPoints(command_Id ,achatId, user_Id , point);
    TrayNotificationAlert.notif("Checkout", "Checkout done.",
        NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
    }
}
if(achat.getPayment_method().equals("Livraison")){
    achatsService.ValidateCheckoutLivraison(command_Id, achatId);
    TrayNotificationAlert.notif("Checkout", "Checkout done.",
    NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
}

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commandInterfaces/UserCommandsList.fxml"));

    Parent root = loader.load();
    // Accéder à la pane content_area depuis ce controller
    Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

    // Vider la pane et afficher le contenu de UserCommandsList.fxml
    contentArea.getChildren().clear();
    contentArea.getChildren().add(root);
}

@FXML
void on_Livraison_methodPayment_click(MouseEvent event) throws IOException {
    paymentQuestion.setVisible(false);
    selectPaymentMethod.setVisible(false);
    paymentValidate.setVisible(true);
    paymentModelTitle.setText("3.  Validate  ");

    totalPointsValidate.setText(String.valueOf(totalPrx));

    AchatsService achatsService = new AchatsService();
    achatsService.updatePaymentMethod(1, achatId, "Livraison");

}


@FXML
void on_Stripe_methodPayment_click(MouseEvent event) throws IOException {
    
   String STRIPE_SECRET_KEY = "sk_test_51MgYOOFXYK38vFYwOPGPKxftWYpStBWSuhx2ltC4jYfuyWkTxrXbpuVAGx6VrBBehZQtX5uJFFA7os4WQTVCFORz00pGTPG1FH";
   String STRIPE_PUBLIC_KEY = "pk_test_51MgYOOFXYK38vFYwIPidqQ0ZOb5oDmWKIqQMloHO4dMdBXZLsv9wHyqFn3w2Ould8hJwDSf3FxVb1WCzzDLGohH000x1WOS8gg";
    // Set up the Stripe API key
    Stripe.apiKey = STRIPE_SECRET_KEY;
     // Get the credit card details from the text fields
     String cardNumber = "4242424242424242";
     int expMonth = 03;
     int expYear = 45;
     String cvc = "678";
     String zip = "123456" ;// Get the zip code value

      // Create a map of the credit card details
      Map<String, Object> cardParams = new HashMap<>();
      cardParams.put("number", cardNumber);
      cardParams.put("exp_month", expMonth);
      cardParams.put("exp_year", expYear);
      cardParams.put("cvc", cvc);
      cardParams.put("address_zip", zip); // Add the zip code to the cardParams map

  // Create a Stripe token for the credit card details
Token token = null;
try {
    token = Token.create(cardParams);
} catch (StripeException e) {
    e.printStackTrace();
    // handle the error appropriately
}

// Create a charge for the payment
Charge charge = null;
try {
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("amount", 1000);
    chargeParams.put("currency", "usd");
    chargeParams.put("source", token.getId()); // Use the token ID as the source for the charge
    charge = Charge.create(chargeParams);
} catch (StripeException e) {
    System.out.println("Error creating charge: " + e.getMessage());
    e.printStackTrace();
}

if (charge == null || charge.getFailureMessage() != null) {
    System.out.println("Charge failed: " + charge.getFailureMessage());
}

    // handle the error appropriately

// Display the results of the charge
if (charge != null && "succeeded".equals(charge.getStatus())) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText("Payment successful!");
    alert.setContentText("Charge ID: " + charge.getId());
    alert.showAndWait();
} else {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Payment failed!");
    alert.setContentText("Error message: " + charge.getFailureMessage());
    alert.showAndWait();
}


}


}







