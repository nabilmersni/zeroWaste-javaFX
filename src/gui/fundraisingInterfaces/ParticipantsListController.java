package gui.fundraisingInterfaces;

import entities.DonHistory;
import entities.Fundrising;
import entities.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.DonHistoryService;
import services.UserService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class ParticipantsListController implements Initializable {

    @FXML
    private VBox participantListContainer;

    @FXML
    private Text participantListTitle;

    @FXML
    private Pane participantPane;

    @FXML
    private HBox userTableHead;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DonHistoryService donHistoryService = new DonHistoryService();

        try {
            List<DonHistory> participantsList;
            participantsList = donHistoryService.getFundrisingHistory(Fundrising.getIdFund());
            
            // ArrayList<User> userList = userService.getAllUser();
            for (int i = 0; i < participantsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ParticipantItem.fxml"));
                HBox participantItem = fxmlLoader.load();
                ParticipantItemController participantItemController = fxmlLoader.getController();
                participantItemController.setParticipantData(participantsList.get(i));
                participantListContainer.getChildren().add(participantItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
