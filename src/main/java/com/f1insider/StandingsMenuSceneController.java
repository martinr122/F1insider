package com.f1insider;

import com.f1insider.storage.*;
import com.f1insider.storage.DaoFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class StandingsMenuSceneController {

    @FXML
    private Button showHomeButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    @FXML
    private MenuButton chooseHistory;

    @FXML
    private Button logoutButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private TableView<Driver> standingsDriverTable;

    @FXML
    private TableColumn<Driver, Integer> positionColumn;

    @FXML
    private TableColumn<Driver, Driver> nameColumn;

    @FXML
    private TableColumn<Driver, String> teamColumn;

    @FXML
    private TableColumn<Driver, Integer> pointscolumn;

    @FXML
    private TableColumn<Team, String> nameTeamColumn;

    @FXML
    private TableColumn<Team, Integer> pointsTeamColumn;

    @FXML
    private TableColumn<Team, Integer> positionTeamColumn;

    @FXML
    private TableView<Team> standingsTeamTable;
    @FXML
    private Button manageButton;

    private User user;
    private String season;
    private DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    private TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();
    private List<Team> teamList = new ArrayList<>();
    public StandingsMenuSceneController(User user, String season) {
        this.user = user;
        this.season = season;
    }

    @FXML
    void initialize() {
        UsernameLabel.setText(user.toString());
        SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
        if (!user.isAdmin()) {
            manageButton.setVisible(false);
        }

        List<Driver> driversStandings = WebPageReader.getDriversStandings("https://www.formula1.com/en/results.html/" + season + "/drivers.html");
        positionColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1).asObject());
        nameColumn.setCellValueFactory(cellData -> {
            Driver driver = driverDao.getByName(cellData.getValue().getFirstName(), cellData.getValue().getSurname());
            return new SimpleObjectProperty(driver);
        });
        pointscolumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        List<Driver> addedDrivers = new ArrayList<>();
        teamColumn.setCellValueFactory(cellData -> {

            Driver driver = driverDao.getByName(cellData.getValue().getFirstName(), cellData.getValue().getSurname());
            Team team = teamDao.getTeamByDriver(driver.getId(), Integer.parseInt(season));
            int points = cellData.getValue().getPoints();


            if(!teamList.contains(team)){
                team.setPoints(points);
                teamList.add(team);
                addedDrivers.add(driver);
            }else{
                Team existingTeam = teamList.get(teamList.indexOf(team));
                if (!addedDrivers.contains(driver)) {
                    existingTeam.setPoints(existingTeam.getPoints() + points);
                    addedDrivers.add(driver);
                }
            }
            return new SimpleStringProperty(team.toString());
        });

        if (driversStandings != null) {
            standingsDriverTable.setItems(FXCollections.observableList(driversStandings));
        } else {
            standingsDriverTable.setPlaceholder(new Label("The new season hasn't started yet! But you can check the standings from previous seasons!"));
            standingsDriverTable.setItems(FXCollections.emptyObservableList());
        }

        standingsDriverTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    openDriverWindow(driverDao.getByName(newSelection.getFirstName(), newSelection.getSurname()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        positionTeamColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getTableView().getItems().indexOf(cellData.getValue()) + 1).asObject());
        nameTeamColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeamName()));
        pointsTeamColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        List<Team> teamsStandings = WebPageReader.getTeamsStandings("https://www.formula1.com/en/results.html/" + season + "/team.html");

        if (teamsStandings != null) {
            standingsTeamTable.setItems(FXCollections.observableList(teamList));
        } else {
            standingsTeamTable.setPlaceholder(new Label("The new season hasn't started yet! But you can check the standings from previous seasons!"));
            standingsTeamTable.setItems(FXCollections.emptyObservableList());
        }

        standingsTeamTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    openTeamWindow(newSelection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        for (String season : seasonDao.getAllSeason()) {
            MenuItem menuItem = new MenuItem(season);
            menuItem.setOnAction(this::onChooseHistory);
            chooseHistory.getItems().add(menuItem);
        }
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
            mainMenuStage.setResizable(false);
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
            RacingMenuSceneController controller = new RacingMenuSceneController(user, season);
            loader.setController(controller);
            Parent racingMenuScene = loader.load();
            Stage racingMenuStage = (Stage) logoutButton.getScene().getWindow();;
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
    void onShowStandings(ActionEvent event){

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
            racingMenuStage.setResizable(false);
            racingMenuStage.centerOnScreen();
            racingMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
            loginStage.setTitle("Login");
            loginStage.setResizable(false);
            loginStage.centerOnScreen();
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDriverWindow(Driver selectedDriver) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverDetailScene.fxml"));
        DriverDetailSceneController controller = new DriverDetailSceneController(user, selectedDriver, Integer.parseInt(season), null);
        loader.setController(controller);
        Parent raceScene = loader.load();
        Stage raceStage = (Stage) logoutButton.getScene().getWindow();
        raceStage.setScene(new Scene(raceScene));
        raceStage.setTitle("Driver Detail");
        raceStage.setResizable(false);
        raceStage.centerOnScreen();
        raceStage.show();
    }

    private void openTeamWindow(Team selectedTeam) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamDetailScene.fxml"));
        TeamDetailSceneController controller = new TeamDetailSceneController(user, selectedTeam, Integer.parseInt(season));
        loader.setController(controller);
        Parent raceScene = loader.load();
        Stage raceStage = (Stage) logoutButton.getScene().getWindow();
        raceStage.setScene(new Scene(raceScene));
        raceStage.setTitle("Team Detail");
        raceStage.setResizable(false);
        raceStage.centerOnScreen();
        raceStage.show();
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
