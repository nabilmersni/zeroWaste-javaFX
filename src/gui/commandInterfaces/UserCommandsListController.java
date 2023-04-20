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
import javafx.scene.input.KeyEvent;
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



import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;



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

    @FXML
    private Text priceSymbole;

    @FXML
    private HBox stripeInputs;

    @FXML
    private TextField cardNumberInput;

    @FXML
    private Text cardNumberInputError;

    @FXML
    private HBox cardNumberInputErrorHbox;

    @FXML
    private TextField mmInput;

    @FXML
    private Text mmInputError;

    @FXML
    private HBox mmInputErrorHbox;

    @FXML
    private TextField yyInput;

    @FXML
    private Text yyInputError;

    @FXML
    private HBox yyInputErrorHbox;

    @FXML
    private TextField cvcInput;

    @FXML
    private Text cvcInputError;

    @FXML
    private HBox cvcInputErrorHbox;

    @FXML
    private TextField zipInput;

    @FXML
    private Text zipInputError;

    @FXML
    private HBox zipInputErrorHbox;

    @FXML
    private HBox backTo_selectPayment_btn;



    private int achatId = -1; 
    
    private int totalPts=0;
    private float totalPrx = 0;
    private int command_Id;
    private int user_Id;
    private int point;
    private int cardNumberTest = -1;
    private int mmTest = -1;
    private int yyTest = -1;
    private int cvcTest = -1;
    private int zipTest = -1;

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

        cardNumberInputErrorHbox.setVisible(false);
        mmInputErrorHbox.setVisible(false);
        yyInputErrorHbox.setVisible(false);
        cvcInputErrorHbox.setVisible(false);
        zipInputErrorHbox.setVisible(false);
        stripeInputs.setVisible(false);
        backTo_selectPayment_btn.setVisible(false);
            
         
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

                    if(oneAchat.getPayment_method().equals("Livraison") ){
                        paymentQuestion.setVisible(false);
                        selectPaymentMethod.setVisible(false);
                        paymentValidate.setVisible(true);
                        paymentModelTitle.setText("3.  Validate  ");
                
                        totalPointsValidate.setText(String.valueOf(totalPrx));
                        priceSymbole.setText("$");
                        paymentMethod.setText("Livraison");
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

        if(achat.getPayment_method().equals("Stripe")){
            this.StripeFunction();
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

    private void StripeFunction(){
        String STRIPE_SECRET_KEY = "sk_test_51MgYOOFXYK38vFYwOPGPKxftWYpStBWSuhx2ltC4jYfuyWkTxrXbpuVAGx6VrBBehZQtX5uJFFA7os4WQTVCFORz00pGTPG1FH";
            // Set up the Stripe API key
            Stripe.apiKey = STRIPE_SECRET_KEY;
            // Get the credit card details from the text fields
            String cardNumber = cardNumberInput.getText();// "4242 4242 4242 4242"
            int expMonth = Integer.parseInt(mmInput.getText());// 03 
            int expYear = Integer.parseInt(yyInput.getText());//45
            String cvc = cvcInput.getText();// "678"
            String zip = zipInput.getText();// "12345" 

            // Create a map of the credit card details
            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("number", cardNumber);
            cardParams.put("exp_month", expMonth);
            cardParams.put("exp_year", expYear);
            cardParams.put("cvc", cvc);
            cardParams.put("address_zip", zip); // Add the zip code to the cardParams map

            cardParams.put("name", "Bouraoui Asma");
            cardParams.put("address_line1", "123 Main St");
            cardParams.put("address_line2", "Apt 4");
            cardParams.put("address_city", "Anytown");
            cardParams.put("address_state", "CA");
            cardParams.put("address_country", "US");

            // Create a Stripe token for the credit card details
            Token token = null;
            try {
                Map<String, Object> tokenParams = new HashMap<>();
                        tokenParams.put("card", cardParams);
                        token = Token.create(tokenParams);
                        System.out.println("Stripe token ID: " + token.getId());
            } catch (StripeException e) {
                e.printStackTrace();
                // handle the error appropriately
            }

            // Create a charge for the payment
            Charge charge = null;
            try {
                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount", 100 * totalPrx);
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
        paymentQuestion.setVisible(false);
        selectPaymentMethod.setVisible(false);
        stripeInputs.setVisible(true);
        backTo_selectPayment_btn.setVisible(true);
        
        AchatsService achatsService = new AchatsService();
        achatsService.updatePaymentMethod(1, achatId, "Stripe");

    }

    @FXML
    void backTo_selectPayment(MouseEvent event) throws IOException {        
        stripeInputs.setVisible(false);
        backTo_selectPayment_btn.setVisible(false);
        paymentQuestion.setVisible(true);
        selectPaymentMethod.setVisible(true);
        

    }

    
    //***************************Controle de saisie STRIPE*************************************** */
    @FXML
    void cardNumberInputTyped(KeyEvent event) {
        String cardNumber = ((TextField) event.getSource()).getText();
        cardNumberInput.setStyle("-fx-border-color: #defee633; -fx-text-fill: #32594a;");
        cardNumberTest = 0;
            
        if (!cardNumber.matches("-?\\d{0,16}")) {
            cardNumberInputError.setText("cardNumber should be a positive number of 16 digits");
            cardNumberInputErrorHbox.setVisible(true);
            cardNumberTest = 0;
        } else {
            
            int number = 1;
            if (cardNumber.length() < 10) {
                 number = Integer.parseInt(cardNumber);
            }
            if(number < 0 ){
                cardNumberInputError.setText("cardNumber cannot be negative");
                cardNumberInputErrorHbox.setVisible(true);
                cardNumberTest = 0;
                
            } else {
                cardNumberInputErrorHbox.setVisible(false);
                System.out.println("cardNumber length: " + cardNumber.length());
                if (cardNumber.length() == 16) {
                    cardNumberInput.setStyle("-fx-background-color: #97d1582d; -fx-text-fill: #43882b;");
                    cardNumberTest = 1;
                } 
                
            }
        }

    }

    @FXML
    void mmInputTyped(KeyEvent event) {
        String mm = ((TextField) event.getSource()).getText();
        mmInput.setStyle("-fx-border-color: #defee633; -fx-text-fill: #32594a;");
        mmTest = 0;
            
        if (!mm.matches("-?\\d{0,2}")) {
            mmInputError.setText("mm should be a positive number of 2 digits");
            mmInputErrorHbox.setVisible(true);
            mmTest = 0;
        } else {
            
            int number = Integer.parseInt(mm);
            
            if(number < 0 ){
                mmInputError.setText("mm cannot be negative");
                mmInputErrorHbox.setVisible(true);
                mmTest = 0;
                
            } else {
                mmInputErrorHbox.setVisible(false);
                System.out.println("mm length: " + mm.length());
                if (mm.length() == 2) {
                    mmInput.setStyle("-fx-background-color: #97d1582d; -fx-text-fill: #43882b;");
                    mmTest = 1;
                } 
                
            }
        }

    }

    @FXML
    void yyInputTyped(KeyEvent event) {
        String yy = ((TextField) event.getSource()).getText();
        yyInput.setStyle("-fx-border-color: #defee633; -fx-text-fill: #32594a;");
        yyTest = 0;
            
        if (!yy.matches("-?\\d{0,2}")) {
            yyInputError.setText("yy should be a positive number of 2 digits");
            yyInputErrorHbox.setVisible(true);
            yyTest = 0;
        } else {
            
            int number = Integer.parseInt(yy);
            
            if(number < 0 ){
                yyInputError.setText("yy cannot be negative");
                yyInputErrorHbox.setVisible(true);
                yyTest = 0;
                
            } else {
                yyInputErrorHbox.setVisible(false);
                System.out.println("yy length: " + yy.length());
                if (yy.length() == 2) {
                    yyInput.setStyle("-fx-background-color: #97d1582d; -fx-text-fill: #43882b;");
                    yyTest = 1;
                } 
                
            }
        }

    }

    @FXML
    void cvcInputTyped(KeyEvent event) {
        String cvc = ((TextField) event.getSource()).getText();
        cvcInput.setStyle("-fx-border-color: #defee633; -fx-text-fill: #32594a;");
        cvcTest = 0;
            
        if (!cvc.matches("-?\\d{0,3}")) {
            cvcInputError.setText("cvc should be a positive number of 3 digits");
            cvcInputErrorHbox.setVisible(true);
            cvcTest = 0;
        } else {
            
            int number = Integer.parseInt(cvc);
            
            if(number < 0 ){
                cvcInputError.setText("cvc cannot be negative");
                cvcInputErrorHbox.setVisible(true);
                cvcTest = 0;
                
            } else {
                cvcInputErrorHbox.setVisible(false);
                System.out.println("cvc length: " + cvc.length());
                if (cvc.length() == 3) {
                    cvcInput.setStyle("-fx-background-color: #97d1582d; -fx-text-fill: #43882b;");
                    cvcTest = 1;
                } 
                
            }
        }

    }

    @FXML
    void zipInputTyped(KeyEvent event) {
        String zip = ((TextField) event.getSource()).getText();
        zipInput.setStyle("-fx-border-color: #defee633; -fx-text-fill: #32594a;");
        zipTest = 0;
            
        if (!zip.matches("-?\\d{0,5}")) {
            zipInputError.setText("zip should be a positive number of 5 digits");
            zipInputErrorHbox.setVisible(true);
            zipTest = 0;
        } else {
            
            int number = Integer.parseInt(zip);
            
            if(number < 0 ){
                zipInputError.setText("zip cannot be negative");
                zipInputErrorHbox.setVisible(true);
                zipTest = 0;
                
            } else {
                zipInputErrorHbox.setVisible(false);
                System.out.println("zip length: " + zip.length());
                if (zip.length() == 5) {
                    zipInput.setStyle("-fx-background-color: #97d1582d; -fx-text-fill: #43882b;");
                    zipTest = 1;
                } 
                
            }
        }

        if(cardNumberTest == 1 && mmTest==1 && yyTest ==1 && cvcTest ==1 && zipTest ==1){
            stripeInputs.setVisible(false);
            backTo_selectPayment_btn.setVisible(false);
            paymentModelTitle.setText("3.  Validate  ");
    
            totalPointsValidate.setText(String.valueOf(totalPrx));
            priceSymbole.setText("$");
            paymentMethod.setText("Stripe");
            paymentValidate.setVisible(true);
            System.out.println("cargdNumberText: " +cardNumberInput.getText());
            
        }

    }

}







