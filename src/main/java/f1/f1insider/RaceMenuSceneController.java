package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import storage.EntityNotFoundException;
import storage.Race;
import storage.User;

import javax.naming.Name;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RaceMenuSceneController {

    private Race race;
    private User user;
    @FXML
    private Label FifthSessionLabel;

    @FXML
    private Label FirstSessionLabel;

    @FXML
    private Label FourthSessionLabel;

    @FXML
    private Label NameOfGrandPrixLabel;

    @FXML
    private Label PlaceOfGrandPrixLabel;

    @FXML
    private Label SecondSessionLabel;

    @FXML
    private Label ThirdSessionLabel;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label WhenFifthSessionLabel;

    @FXML
    private Label WhenFirstSessionLabel;

    @FXML
    private Label WhenFourthSessionLabel;

    @FXML
    private Label WhenSecondSessionLabel;

    @FXML
    private Label WhenThirdSessionLabel;

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

    public RaceMenuSceneController(User user, Race race) {
        this.user = user;
        this.race = race;
    }
    @FXML
    void initialize() throws EntityNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        UsernameLabel.setText(user.toString());
        PlaceOfGrandPrixLabel.setText(race.getPlace());
        NameOfGrandPrixLabel.setText(race.getName());
        WhenFirstSessionLabel.setText(formatter.format(race.getWhenFirstSession()));
        FirstSessionLabel.setText("Practice 1:");
        WhenFifthSessionLabel.setText(formatter.format(race.getWhenRace()));
        FifthSessionLabel.setText("Race:");

        if (race.isSprintWeekend()){
            WhenSecondSessionLabel.setText(formatter.format(race.getWhenQuali()));
            SecondSessionLabel.setText("Qualifying:");
            WhenThirdSessionLabel.setText(formatter.format(race.getWhenSecondSession()));
            ThirdSessionLabel.setText("Sprint Shootout:");
            WhenFourthSessionLabel.setText(formatter.format(race.getWhenThirdSession()));
            FourthSessionLabel.setText("Sprint Race:");
        }else{
            WhenSecondSessionLabel.setText(formatter.format(race.getWhenSecondSession()));
            SecondSessionLabel.setText("Practice 2:");
            WhenThirdSessionLabel.setText(formatter.format(race.getWhenThirdSession()));
            ThirdSessionLabel.setText("Practice 3:");
            WhenFourthSessionLabel.setText(formatter.format(race.getWhenQuali()));
            FourthSessionLabel.setText("Qualifying:");
        }
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
            loginStage.centerOnScreen();
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowHistory(ActionEvent event) {
        MenuItem clickedMenuItem = (MenuItem) event.getSource();
        String selectedOption = clickedMenuItem.getText();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("RacingMenuScene.fxml"));
            RacingMenuSceneController controller = new RacingMenuSceneController(user, selectedOption);
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

    @FXML
    void onChooseHistory(ActionEvent event) {

    }
}
