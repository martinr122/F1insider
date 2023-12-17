package f1.f1insider;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import f1.f1insider.storage.DaoFactory;
import f1.f1insider.storage.EntityNotFoundException;
import f1.f1insider.storage.Race;
import f1.f1insider.storage.RaceDao;
import f1.f1insider.storage.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RacingMenuSceneController {

    private User user;
    private String season;
    @FXML
    private Label UsernameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Race> racingTable;

    @FXML
    private TableColumn<Race, Integer> roundNumberColumn;

    @FXML
    private TableColumn<Race, String> dateColumn;

    @FXML
    private TableColumn<Race, String> placeColumn;

    @FXML
    private TableColumn<Race, String> nameOfGrandPrix;

    @FXML
    private Button showHomeButton;

    @FXML
    private MenuButton chooseHistory;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    public RacingMenuSceneController(User user, String season) {
        this.user = user;
        this.season = season;
    }

    @FXML
    void initialize() throws EntityNotFoundException {

        RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
        UsernameLabel.setText(user.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        roundNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getTableView().
                getItems().indexOf(cellData.getValue()) + 1).asObject());

        dateColumn.setCellValueFactory(cellData -> {
            Race race = cellData.getValue();
            if (race != null) {
                LocalDateTime raceDateTime = race.getWhenRace();
                String formattedDateTime = formatter.format(raceDateTime);
                return new SimpleStringProperty(formattedDateTime);
            } else {
                return new SimpleStringProperty("");
            }
        });
        placeColumn.setCellValueFactory(new PropertyValueFactory<Race, String>("place"));
        nameOfGrandPrix.setCellValueFactory(new PropertyValueFactory<Race, String>("name"));

        racingTable.setItems(FXCollections.observableList(raceDao.getAllRaces(season)));

        racingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    openResultsWindow(newSelection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        for (String season : raceDao.getAllSeason()) {
            MenuItem menuItem = new MenuItem(season);
            menuItem.setOnAction(this::onChooseHistory);
            chooseHistory.getItems().add(menuItem);
        }
    }
    private void openResultsWindow(Race selectedRace) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RaceMenuScene.fxml"));
        RaceMenuSceneController controller = new RaceMenuSceneController(user, selectedRace);
        loader.setController(controller);
        Parent raceScene = loader.load();
        Stage raceStage = (Stage) logoutButton.getScene().getWindow();
        raceStage.setScene(new Scene(raceScene));
        raceStage.setTitle("Race Results");
        raceStage.centerOnScreen();
        raceStage.show();
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
    void onChooseHistory(ActionEvent event){

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

    }

    @FXML
    void onShowStandings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StandingsMenuScene.fxml"));
            StandingsMenuSceneController controller = new StandingsMenuSceneController(user, season);
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

}
