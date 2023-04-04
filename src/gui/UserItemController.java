package gui;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class UserItemController {

    @FXML
    private Text userItemEmail;

    @FXML
    private ImageView userItemImg;

    @FXML
    private Text userItemName;

    @FXML
    private Text userItemRole;

    @FXML
    private Label userItemStateBtn;

    @FXML
    private ImageView userItemStateBtnImg;

    @FXML
    private Label userItemStateLabel;

    @FXML
    private Text userItemStateText;

    @FXML
    private Text userItemTel;

    @FXML
    private Label userItemUpdateBtn;

    @FXML
    private ImageView userItemUpdateBtnImg;

    public void setUserData(User user) {
        Image image = new Image(
                getClass().getResource("/assets/userUploads/" + user.getImgUrl()).toExternalForm());
        userItemImg.setImage(image);

        Rectangle clip = new Rectangle(
                userItemImg.getFitWidth(), userItemImg.getFitHeight());
        clip.setArcWidth(100);
        clip.setArcHeight(100);

        userItemImg.setClip(clip);

        userItemName.setText(user.getFullname());
        userItemEmail.setText(user.getEmail());
        userItemTel.setText(user.getTel());
        if (user.getRoles().equals("[\"ROLE_USER\"]")) {
            userItemRole.setText("individual");
        } else if (user.getRoles().equals("[\"ROLE_ASSOCIATION\"]")) {
            userItemRole.setText("association");
        }
        if (user.getState()) {

            if (!userItemStateLabel.getStyleClass().contains("userItem__field-active")) {
                userItemStateLabel.getStyleClass().add("userItem__field-active");
            }

            if (userItemStateLabel.getStyleClass().contains("userItem__field-unactive")) {
                userItemStateLabel.getStyleClass().remove("userItem__field-unactive");
            }

            userItemStateText.setText("active");

            Image stateBtnImg = new Image(
                    getClass().getResource("/assets/img/lock-icon.png").toExternalForm());
            userItemStateBtnImg.setImage(stateBtnImg);

        } else {
            if (!userItemStateLabel.getStyleClass().contains("userItem__field-unactive")) {
                userItemStateLabel.getStyleClass().add("userItem__field-unactive");
            }

            if (userItemStateLabel.getStyleClass().contains("userItem__field-active")) {
                userItemStateLabel.getStyleClass().remove("userItem__field-active");
            }
            userItemStateText.setText("unactive");

            Image stateBtnImg = new Image(
                    getClass().getResource("/assets/img/unlock-icon.png").toExternalForm());
            userItemStateBtnImg.setImage(stateBtnImg);

        }
    }
}
