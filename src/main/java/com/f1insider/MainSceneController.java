package com.f1insider;


import com.f1insider.storage.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class MainSceneController {

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;

    @FXML
    private MenuButton chooseHistory;

    @FXML
    private Button logoutButton;

    @FXML
    private Button manageButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label nextGpLabel;

    @FXML
    private Label nextGpDateLabel;

    @FXML
    private Label leaderOfDriversLabel;

    @FXML
    private ImageView leaderDriverImage;

    @FXML
    private Label lastGpLabel;

    @FXML
    private TableColumn<RaceResults, String> intervalColumn;

    @FXML
    private TableView<RaceResults> lastRacePodiumTable;

    @FXML
    private TableColumn<RaceResults, Driver> driverColumn;

    @FXML
    private TableColumn<RaceResults, Integer> positionColumn;
    @FXML
    private Button showRaceButton;

    @FXML
    private Label leaderOfTeamsLabel;

    @FXML
    private GridPane commentGridPane;

    private User user;
    private Race lastRace;
    private String season;
    private ObservableList<String> podiumModel;
    private RaceResultsDao raceResultsDao = DaoFactory.INSTANCE.getRaceResultsDao();
    private CommentDao commentDao = DaoFactory.INSTANCE.getCommentDao();

    public MainSceneController(User user) {
        this.user = user;
        this.season = String.valueOf(LocalDate.now().getYear());
    }

    @FXML
    void initialize() throws EntityNotFoundException {
        RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
        DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
        SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
        UsernameLabel.setText(user.toString());
        String leaderName = WebPageReader.getLeader("https://www.formula1.com/en/results.html/" + season + "/drivers.html");
        leaderOfDriversLabel.setText(leaderName);
        String[] driverName = leaderName.split(" ");

        String surname = "";

        for (int i = 1; i <= driverName.length - 1; i++) {
            surname += driverName[i];
            surname += " ";
        }
        Driver driver = driverDao.getByName(driverName[0], surname);
        if (driver != null) {
            leaderDriverImage.setImage(driver.getPhoto());
            leaderOfTeamsLabel.setText(WebPageReader.getLeader("https://www.formula1.com/en/results.html/" + season + "/team.html"));
        }

        if (!user.isAdmin()) {
            manageButton.setVisible(false);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        if (raceDao.getNextRace() == null) {
            nextGpLabel.setText("");
            nextGpDateLabel.setText("");
        } else {
            nextGpLabel.setText(raceDao.getNextRace().getPlace());
            nextGpDateLabel.setText(formatter.format(raceDao.getNextRace().getWhenRace()));
        }

        if (raceDao.getLastRace() == null) {
            lastGpLabel.setText("");
            showRaceButton.setVisible(false);
            lastRacePodiumTable.setVisible(false);
        } else {
            lastRace = raceDao.getLastRace();
            lastGpLabel.setText(raceDao.getLastRace().getPlace());
            positionColumn.setCellValueFactory(new PropertyValueFactory<RaceResults, Integer>("position"));
            driverColumn.setCellValueFactory(new PropertyValueFactory<RaceResults, Driver>("driver"));
            intervalColumn.setCellValueFactory(param -> {
                RaceResults raceResult = param.getValue();
                if (raceResult.isFinished()) {
                    return new SimpleStringProperty(String.valueOf(raceResult.getIntervalToWinner()));
                } else {
                    return new SimpleStringProperty(raceResult.getReason());
                }
            });

            lastRacePodiumTable.setItems(FXCollections.observableList(raceResultsDao.getPodiumOfRace(lastRace.getId())));

        }

        for (String season : seasonDao.getAllSeason()) {
            MenuItem menuItem = new MenuItem(season);
            menuItem.setOnAction(this::onChooseHistory);
            chooseHistory.getItems().add(menuItem);
        }
        if (lastRace != null) {
            List<Comment> comments = commentDao.allCommentByRace(raceDao.getRaceId(lastRace));
            for (int i = 0; i < comments.size(); i++) {

                Comment comment = comments.get(i);

                Text newCommentText = new Text(comment.getComment());
                newCommentText.setWrappingWidth(280.0);
                Text userName = new Text(user.getUsername() + ":");
                userName.setWrappingWidth(70);
                commentGridPane.addRow(commentGridPane.getRowCount(), userName, newCommentText);
            }
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
            Stage loginStage = (Stage) logoutButton.getScene().getWindow();
            loginStage.setScene(new Scene(loginScene));
            loginStage.setTitle("Login - F1Insider");
            loginStage.setResizable(false);
            loginStage.centerOnScreen();
            loginStage.show();
        } catch (IOException e) {
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
            StandingsMenuSceneController controller = new StandingsMenuSceneController(user, season);
            loader.setController(controller);
            Parent standingsMenuScene = loader.load();
            Stage standingsMenuStage = (Stage) showStandingsButton.getScene().getWindow();
            standingsMenuStage.setScene(new Scene(standingsMenuScene));
            standingsMenuStage.setTitle("Standings");
            standingsMenuStage.setResizable(false);
            standingsMenuStage.centerOnScreen();
            standingsMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowHome(ActionEvent event) {

    }

    @FXML
    void onShowRace(ActionEvent event) {
        if (lastRace != null) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("RaceMenuScene.fxml"));
                RaceMenuSceneController controller = new RaceMenuSceneController(user, lastRace);
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

    @FXML
    void onChooseHistory(ActionEvent event) {

        MenuItem clickedMenuItem = (MenuItem) event.getSource();
        String selectedOption = clickedMenuItem.getText();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("RacingMenuScene.fxml"));
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
}