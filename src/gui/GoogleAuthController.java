package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import utils.TrayNotificationAlert;
import utils.UserSession;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;

public class GoogleAuthController implements Initializable {

    @FXML
    private AnchorPane left;

    @FXML
    private Hyperlink logInLink;

    @FXML
    void toLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private String email = "Hello, world!"; // initial value
    MyScheduledService service;

    @Override
    public void initialize(URL locationn, ResourceBundle resources) {

        service = new MyScheduledService();
        service.start();

    }

    private class MyScheduledService extends ScheduledService<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        URL url = new URL("http://localhost:5000/"); // replace with your URL
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        // set request method and headers
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");

                        // read response
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }
                        br.close();

                        // parse JSON response and update value
                        String newValue = response.toString();
                        if (!newValue.equals(email)) {
                            email = newValue;
                            if (!newValue.equals("undefined")) {
                                System.out.println("Updated value: " + email);
                                UserService userService = new UserService();
                                User user = userService.getOneUser(email);
                                Platform.runLater(() -> {
                                    if (user.getId() == -999) {

                                        TrayNotificationAlert.notif("Login",
                                                "email not associated with zeroWaste account",
                                                NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
                                        Platform.runLater(() -> service.cancel());

                                        Parent root;
                                        try {
                                            root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
                                            Scene scene = new Scene(root);
                                            Stage stage = (Stage) ((Node) left).getScene().getWindow();
                                            stage.setScene(scene);
                                            stage.show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Platform.runLater(() -> service.cancel());
                                        TrayNotificationAlert.notif("Login", "logged in successfully.",
                                                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
                                        UserSession.getInstance().setEmail(user.getEmail());
                                        System.out.println("to the DASHBOARD");

                                        try {

                                            if (user.getRoles().equals("[\"ROLE_USER\"]")
                                                    || user.getRoles().equals("[\"ROLE_ASSOCIATION\"]")) {

                                                Parent root;
                                                root = FXMLLoader
                                                        .load(getClass().getResource("/gui/UserDashboard.fxml"));
                                                Scene scene = new Scene(root);
                                                Stage stage = (Stage) ((Node) left).getScene().getWindow();
                                                stage.setScene(scene);
                                                stage.show();
                                            } else if (user.getRoles().equals("[\"ROLE_ADMIN\"]")) {
                                                Parent root;
                                                root = FXMLLoader
                                                        .load(getClass().getResource("/gui/AdminDashboard.fxml"));
                                                Scene scene = new Scene(root);
                                                Stage stage = (Stage) ((Node) left).getScene().getWindow();
                                                stage.setScene(scene);
                                                stage.show();
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                });
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            };
        }
    }

}