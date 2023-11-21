package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import storage.User;

import java.io.IOException;

public class StandingsMenuSceneController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button showHistoryButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;
    @FXML
    private Label UsernameLabel;
    private User user;

    public StandingsMenuSceneController(User user){
        this.user = user;
    }

    @FXML
    void initialize() {
        UsernameLabel.setText(user.toString());
    }
    @FXML
    void onLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("LoginScene.fxml"));
            LoginSceneController controller = new LoginSceneController();
            loader.setController(controller);
            Parent loginScene = loader.load();
            Stage loginStage = (Stage) logoutButton.getScene().getWindow();;
            loginStage.setScene(new Scene(loginScene));
            loginStage.setTitle("Login - F1Insider");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowHistory(ActionEvent event) {

    }

    @FXML
    void onShowRacing(ActionEvent event) {

    }

    @FXML
    void onShowStandings(ActionEvent event) {

    }

}
