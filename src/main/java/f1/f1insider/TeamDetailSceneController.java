package f1.f1insider;

import f1.f1insider.storage.Race;
import f1.f1insider.storage.Team;
import f1.f1insider.storage.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Year;

public class TeamDetailSceneController {

    @FXML
    private Label UsernameLabel;

    @FXML
    private Button backButton;

    @FXML
    private Label championshipsLabel;

    @FXML
    private MenuButton chooseHistory;

    @FXML
    private Label engineLabel;

    @FXML
    private ImageView firstDriverImage;

    @FXML
    private Label firstDriverLabel;

    @FXML
    private Label founderLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Label monopostLabel;

    @FXML
    private Label nationalityLabel;

    @FXML
    private Label podiumsLabel;

    @FXML
    private Label racesLabel;

    @FXML
    private ImageView secondDriverImage;

    @FXML
    private Label secondDriverLabel;

    @FXML
    private Button showHomeButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    @FXML
    private ImageView teamImage;

    @FXML
    private Label teamPrincipalLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label teamNameLabel;

    private User user;
    private Team team;
    private int season;
    public TeamDetailSceneController(User user, Team team, int season){
        this.user = user;
        this.team = team;
        this.season = season;
    }

    @FXML
    void initialize() {
        UsernameLabel.setText(user.toString());

        teamNameLabel.setText(team.getTeamName());
        founderLabel.setText(team.getNameFounder());
        teamPrincipalLabel.setText(team.getNamePrincipal());
        nationalityLabel.setText(team.getCountry());
        engineLabel.setText(team.getNameEngine());
        monopostLabel.setText(team.getNameMonopost());
    }

    @FXML
    void onLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
            LoginSceneController controller = new LoginSceneController();
            loader.setController(controller);
            Parent loginScene = loader.load();
            Stage loginStage = (Stage) logoutButton.getScene().getWindow();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RacingMenuScene.fxml"));
            RacingMenuSceneController controller = new RacingMenuSceneController(user, selectedOption);
            loader.setController(controller);
            Parent racingMenuScene = loader.load();
            Stage racingMenuStage = (Stage) logoutButton.getScene().getWindow();

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            MainSceneController controller = new MainSceneController(user);
            loader.setController(controller);
            Parent mainMenuScene = loader.load();
            Stage mainMenuStage = (Stage) showHomeButton.getScene().getWindow();
            mainMenuStage.setScene(new Scene(mainMenuScene));
            mainMenuStage.setTitle("Standings");
            mainMenuStage.centerOnScreen();
            mainMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowRacing(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RacingMenuScene.fxml"));
            RacingMenuSceneController controller = new RacingMenuSceneController(user, String.valueOf(Year.now().getValue()));
            loader.setController(controller);
            Parent racingMenuScene = loader.load();
            Stage racingMenuStage = (Stage) logoutButton.getScene().getWindow();

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StandingsMenuScene.fxml"));
            StandingsMenuSceneController controller = new StandingsMenuSceneController(user, String.valueOf(Year.now().getValue()));
            loader.setController(controller);
            Parent standingsMenuScene = loader.load();
            Stage standingsMenuStage = (Stage) showStandingsButton.getScene().getWindow();
            standingsMenuStage.setScene(new Scene(standingsMenuScene));
            standingsMenuStage.setTitle("Standings");
            standingsMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onChooseHistory(ActionEvent event) {
        MenuItem clickedMenuItem = (MenuItem) event.getSource();
        String selectedOption = clickedMenuItem.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RacingMenuScene.fxml"));
            RacingMenuSceneController controller = new RacingMenuSceneController(user, selectedOption);
            loader.setController(controller);
            Parent racingMenuScene = loader.load();
            Stage racingMenuStage = (Stage) logoutButton.getScene().getWindow();

            racingMenuStage.setScene(new Scene(racingMenuScene));
            racingMenuStage.setTitle("Racing");
            racingMenuStage.centerOnScreen();
            racingMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RaceMenuScene.fxml"));
        StandingsMenuSceneController controller = new StandingsMenuSceneController(user, String.valueOf(season));
        loader.setController(controller);
        Parent raceScene = loader.load();
        Stage raceStage = (Stage) logoutButton.getScene().getWindow();
        raceStage.setScene(new Scene(raceScene));
        raceStage.setTitle("Race Results");
        raceStage.centerOnScreen();
        raceStage.show();
    }

}
