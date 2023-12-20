package com.f1insider;

import com.f1insider.storage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class DriverDetailSceneController {

    @FXML
    private Button backButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label championshipsLabel;

    @FXML
    private MenuButton chooseHistory;

    @FXML
    private TableView<?> driverCareerTable;

    @FXML
    private Label driversNameLabel;

    @FXML
    private TableColumn<?, ?> fromColumn;

    @FXML
    private Label highestFinishLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView driverPhoto;

    @FXML
    private Label nationalityLabel;

    @FXML
    private Label podiumsLabel;

    @FXML
    private Label raceNumberLabel;

    @FXML
    private Label racesLabel;

    @FXML
    private Label secondPlacesLabel;

    @FXML
    private Button showHomeButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    @FXML
    private Label teamLabel;

    @FXML
    private TableColumn<?, ?> teamNameColumn;

    @FXML
    private Label thirdPlacesLabel;

    @FXML
    private TableColumn<?, ?> toColumn;

    @FXML
    private Label winsLabel;
    @FXML
    private Button manageButton;

    private Image driverImage;
    private User user;
    private Driver driver;
    private int season;
    private Race race;
    private TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();
    private RaceResultsDao raceResultsDao = DaoFactory.INSTANCE.getRaceResultsDao();
    private SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
    @FXML
    private Pane teamColorPane;
    public DriverDetailSceneController(User user, Driver driver, int season, Race race) {
        this.user = user;
        this.driver = driver;
        this.season = season;
        this.race = race;
    }

    @FXML
    void initialize() throws EntityNotFoundException {
        if (!user.isAdmin()) {
            manageButton.setVisible(false);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        UsernameLabel.setText(user.toString());

        driversNameLabel.setText(driver.getFirstName() + " " + driver.getSurname());
        nationalityLabel.setText(driver.getCountry());
        birthdayLabel.setText(formatter.format(driver.getBirthday()));
        raceNumberLabel.setText(String.valueOf(driver.getRaceNumber()));
        teamLabel.setText(teamDao.getTeamByDriver(driver.getId(), season).toString());

        int wins = raceResultsDao.getWinsCount(driver.getId());
        int secondPlaces = raceResultsDao.getSecondCount(driver.getId());
        int thirdPlaces = raceResultsDao.getThirdCount(driver.getId());
        winsLabel.setText(String.valueOf(wins));
        secondPlacesLabel.setText(String.valueOf(secondPlaces));
        thirdPlacesLabel.setText(String.valueOf(thirdPlaces));
        podiumsLabel.setText(String.valueOf(wins + secondPlaces + thirdPlaces));
        racesLabel.setText(String.valueOf(raceResultsDao.getTotalRaces(driver.getId())));
        championshipsLabel.setText(String.valueOf(seasonDao.getChampionshipsOfDriver(driver.getId())));
        highestFinishLabel.setText(String.valueOf(raceResultsDao.getHighestFinish(driver.getId())));
        teamColorPane.setStyle(" -fx-background-color: #" + teamDao.getTeamByDriver(driver.getId(), season).getTeamColor() + ";");
        driverPhoto.setImage(driver.getPhoto());
        driverImage = driver.getPhoto();

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
            loginStage.setTitle("Login");
            loginStage.setResizable(false);
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
            racingMenuStage.setResizable(false);
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
            mainMenuStage.setResizable(false);
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
            racingMenuStage.setResizable(false);
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
            standingsMenuStage.setResizable(false);
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
            racingMenuStage.setResizable(false);
            racingMenuStage.centerOnScreen();
            racingMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onBack(ActionEvent event) throws IOException {
        if (race != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RaceMenuScene.fxml"));
            RaceMenuSceneController controller = new RaceMenuSceneController(user, race);
            loader.setController(controller);
            Parent raceScene = loader.load();
            Stage raceStage = (Stage) logoutButton.getScene().getWindow();
            raceStage.setScene(new Scene(raceScene));
            raceStage.setTitle("Race Results");
            raceStage.setResizable(false);
            raceStage.centerOnScreen();
            raceStage.show();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StandingsMenuScene.fxml"));
            StandingsMenuSceneController controller = new StandingsMenuSceneController(user, String.valueOf(season));
            loader.setController(controller);
            Parent raceScene = loader.load();
            Stage raceStage = (Stage) logoutButton.getScene().getWindow();
            raceStage.setScene(new Scene(raceScene));
            raceStage.setTitle("Standings");
            raceStage.setResizable(false);
            raceStage.centerOnScreen();
            raceStage.show();
        }
    }
    @FXML
    void onManage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("ManageScene.fxml"));
        ManageSceneController controller = new ManageSceneController(user);
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Manager");
        stage.setResizable(false);
        stage.getIcons().add(new javafx.scene.image.Image("images/logo.png"));
        stage.show();
    }
}
