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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import storage.Driver;
import storage.User;
import storage.WebPageReader;

import java.io.IOException;
import java.util.List;

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
    private Button showHomeButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private TableView<Driver> standingsDriverTable;

    @FXML
    private TableColumn<Driver,Integer> positionColumn;

    @FXML
    private TableColumn<Driver, String> nameColumn;

    @FXML
    private TableColumn<Driver, String> teamColumn;

    @FXML
    private TableColumn<Driver, Integer> pointscolumn;

    private User user;
    private String season;

    public StandingsMenuSceneController(User user, String season){
        this.user = user;
        this.season = season;
    }
    @FXML
    void initialize() {
        UsernameLabel.setText(user.toString());


        //filling the table
        positionColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getSurname()));
        pointscolumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        List<Driver> driversStandings = WebPageReader.getDriversStandings("https://www.formula1.com/en/results.html/"+season+"/drivers.html");

        if (driversStandings != null) {
            standingsDriverTable.setItems(FXCollections.observableList(driversStandings));
        } else {
            standingsDriverTable.setPlaceholder(new Label("The new season hasn't started yet! But you can check the standings from previous seasons!"));
            standingsDriverTable.setItems(FXCollections.emptyObservableList());
        }

//        racingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                try {
//                    openResultsWindow(newSelection);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        racingTable.setItems(FXCollections.observableList(raceDao.getAllRaces()));
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
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("RacingMenuScene.fxml"));
            RacingMenuSceneController controller = new RacingMenuSceneController(user, season);
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

    }

    @FXML
    void onShowHome(ActionEvent event){
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
}
