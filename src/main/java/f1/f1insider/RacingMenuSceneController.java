package f1.f1insider;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.stage.Stage;
import storage.DaoFactory;
import storage.EntityNotFoundException;
import storage.Race;
import storage.RaceDao;
import storage.User;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class RacingMenuSceneController {

    private User user;

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
    private Button showHistoryButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    public RacingMenuSceneController(User user) {
        this.user = user;
    }

    @FXML
    void initialize() throws EntityNotFoundException {

        RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
        UsernameLabel.setText(user.toString());

        //filling the table
        dateColumn.setCellValueFactory(new PropertyValueFactory<Race, String>("whenRace"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<Race, String>("place"));
        nameOfGrandPrix.setCellValueFactory(new PropertyValueFactory<Race, String>("name"));

        racingTable.setItems(FXCollections.observableList(raceDao.getAllRaces()));

        racingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    openResultsWindow(newSelection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        racingTable.setItems(FXCollections.observableList(raceDao.getAllRaces()));
    }
    private void openResultsWindow(Race selectedRace) throws IOException {
        // V tomto mieste otvorte okno s výsledkami pre vybraný pretek
        // Môžete napríklad použiť FXMLLoader na nahratie fxml súboru pre druhé okno

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("RaceMenuScene.fxml"));
        RaceMenuSceneController controller = new RaceMenuSceneController(user);
        loader.setController(controller);
        Parent raceScene = loader.load();
        // Vytvorenie a zobrazenie druhého okna
        Stage raceStage = (Stage) logoutButton.getScene().getWindow();
        raceStage.setScene(new Scene(raceScene));
        raceStage.setTitle("Race Results");
        raceStage.centerOnScreen();
        raceStage.show();
    }

    @FXML
    void onLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("LoginScene.fxml"));
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

    }

    @FXML
    void onShowStandings(ActionEvent event) {

    }

}
