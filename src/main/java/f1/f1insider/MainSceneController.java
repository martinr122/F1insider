package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController {

    @FXML
    private ListView<?> lastGrandPrixTextField;

    @FXML
    private Pagination lastRaceCommentsField;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<?> nextGrandPrixTextField;

    @FXML
    private Button showHistoryButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    @FXML
    void loadLastGrandPrixTextField(ActionEvent event) {

    }

    @FXML
    void loadNextGrandPrixTextField(ActionEvent event) {

    }

    @FXML
    void onLogout(ActionEvent event) {

    }

    @FXML
    void onShowHistory(ActionEvent event) {

    }

    @FXML
    void onShowRacing(ActionEvent event) {
    }

    @FXML
    void onShowStandings(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("StandingsMenuScene.fxml"));
            StandingsMenuSceneController controller = new StandingsMenuSceneController();
            loader.setController(controller);
            Parent standingsMenuScene = loader.load();
            Stage standingsMenuStage = (Stage) showStandingsButton.getScene().getWindow();
            standingsMenuStage.setScene(new Scene(standingsMenuScene));
            standingsMenuStage.setTitle("Standings");
            standingsMenuStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
