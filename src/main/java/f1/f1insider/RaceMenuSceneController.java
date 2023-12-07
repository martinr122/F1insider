package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import storage.EntityNotFoundException;
import storage.Race;
import storage.User;

import java.io.IOException;

public class RaceMenuSceneController {

    private Race race;
    private User user;
    @FXML
    private Label FifthSession;

    @FXML
    private Label FirstSession;

    @FXML
    private Label FourthSession;

    @FXML
    private Label NameOfGrandPrix;

    @FXML
    private Label PlaceOfGrandPrix;

    @FXML
    private Label SecondSession;

    @FXML
    private Label ThirdSession;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label WhenFifthosession;

    @FXML
    private Label WhenFirstSession;

    @FXML
    private Label WhenFourthSession;

    @FXML
    private Label WhenSecondSession;

    @FXML
    private Label WhenThirdSession;

    @FXML
    private MenuButton chooseHistory;

    @FXML
    private Button logoutButton;

    @FXML
    private Button showHomeButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    @FXML
    void onChooseHistory(ActionEvent event) {

    }

    public RaceMenuSceneController(User user, Race race) {
        this.user = user;
        this.race = race;
    }
    @FXML
    void initialize() throws EntityNotFoundException {
        UsernameLabel.setText(user.toString());
    }
    @FXML
    void onLogout(ActionEvent event) {

    }

    @FXML
    void onShowHistory(ActionEvent event) {

    }

    @FXML
    void onShowHome(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("MainScene.fxml"));
            MainSceneController controller = new MainSceneController(user);
            loader.setController(controller);
            Parent mainMenuScene = loader.load();
            Stage mainMenuStage = (Stage) showHomeButton.getScene().getWindow();
            mainMenuStage.setScene(new Scene(mainMenuScene));
            mainMenuStage.setTitle("Standings");
            mainMenuStage.centerOnScreen();
            mainMenuStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onShowRacing(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("RacingMenuScene.fxml"));
            RacingMenuSceneController controller = new RacingMenuSceneController(user, String.valueOf(race.getYear()));
            loader.setController(controller);
            Parent racingMenuScene = loader.load();
            Stage racingMenuStage = (Stage) logoutButton.getScene().getWindow();;
            racingMenuStage.setScene(new Scene(racingMenuScene));
            racingMenuStage.setTitle("Racing");
            racingMenuStage.centerOnScreen();
            racingMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowStandings(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("StandingsMenuScene.fxml"));
            StandingsMenuSceneController controller = new StandingsMenuSceneController(user, String.valueOf(race.getYear()));
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
