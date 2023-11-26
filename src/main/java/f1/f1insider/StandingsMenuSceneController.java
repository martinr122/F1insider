package f1.f1insider;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;
import storage.Driver;
import storage.User;
import storage.WebPageReader;

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

    public StandingsMenuSceneController(User user, int season){
        this.user = user;
        this.season = String.valueOf(season);
    }

    @FXML
    void initialize() {
        UsernameLabel.setText(user.toString());

        //filling the table
        positionColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getSurname()));
        pointscolumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        standingsDriverTable.setItems(FXCollections.observableList(WebPageReader.getDriversStandings("https://www.formula1.com/en/results.html/"+season+"/drivers.html")));
//
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

    }

    @FXML
    void onShowStandings(ActionEvent event) {

    }


}
