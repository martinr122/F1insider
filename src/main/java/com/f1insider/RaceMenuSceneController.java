package com.f1insider;

import com.f1insider.storage.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RaceMenuSceneController {

    private Race race;
    private User user;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private GridPane commentGridPane;
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
    private ImageView RaceTrackImage;

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

    @FXML
    private TableColumn<RaceResults, Driver> driverColumn;

    @FXML
    private TableColumn<RaceResults, String> intervalColumn;

    @FXML
    private TableColumn<RaceResults, Integer> positionColumn;

    @FXML
    private TableView<RaceResults> raceResultsTable;

    @FXML
    private TableColumn<RaceResults, String> teamColumn;

    private CommentDao commentDao = DaoFactory.INSTANCE.getCommentDao();
    private RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
    private RaceResultsDao raceResultsDao = DaoFactory.INSTANCE.getRaceResultsDao();
    private TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();


    public RaceMenuSceneController(User user, Race race) {
        this.user = user;
        this.race = race;
    }

    @FXML
    void initialize() throws EntityNotFoundException {
        List<Comment> comments = commentDao.allCommentByRace(raceDao.getRaceId(race));
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);

            Text newCommentText = new Text(comment.getComment());
            newCommentText.setWrappingWidth(280.0);
            Text userName = new Text(user.getUsername() + ":");
            userName.setWrappingWidth(70);
            commentGridPane.addRow(commentGridPane.getRowCount(), userName, newCommentText);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        UsernameLabel.setText(user.toString());
        PlaceOfGrandPrixLabel.setText(race.getPlace());
        NameOfGrandPrixLabel.setText(race.getName());
        WhenFirstSessionLabel.setText(formatter.format(race.getWhenFirstSession()));
        FirstSessionLabel.setText("Practice 1:");
        WhenFifthSessionLabel.setText(formatter.format(race.getWhenRace()));
        FifthSessionLabel.setText("Race:");

        if (race.isSprintWeekend()) {
            WhenSecondSessionLabel.setText(formatter.format(race.getWhenQuali()));
            SecondSessionLabel.setText("Qualifying:");
            WhenThirdSessionLabel.setText(formatter.format(race.getWhenSecondSession()));
            ThirdSessionLabel.setText("Sprint Shootout:");
            WhenFourthSessionLabel.setText(formatter.format(race.getWhenThirdSession()));
            FourthSessionLabel.setText("Sprint Race:");
        } else {
            WhenSecondSessionLabel.setText(formatter.format(race.getWhenSecondSession()));
            SecondSessionLabel.setText("Practice 2:");
            WhenThirdSessionLabel.setText(formatter.format(race.getWhenThirdSession()));
            ThirdSessionLabel.setText("Practice 3:");
            WhenFourthSessionLabel.setText(formatter.format(race.getWhenQuali()));
            FourthSessionLabel.setText("Qualifying:");
        }
//        File file = new File("images/Brazil.png");
//        Image image = new Image(file.toURI().toString());
//        RaceTrackImage.setImage(image);
        List<RaceResults> raceResults = raceResultsDao.getRaceResults(race.getId());
        if (raceResults.isEmpty()){
            raceResultsTable.setVisible(false);
        }else{
            positionColumn.setCellValueFactory(new PropertyValueFactory<RaceResults, Integer>("position"));
            driverColumn.setCellValueFactory(new PropertyValueFactory<RaceResults, Driver>("driver"));
            teamColumn.setCellValueFactory(param ->
                    new SimpleStringProperty(teamDao.getTeamByDriver(param.getValue().getDriver().getId(), race.getYear()).toString()));
            intervalColumn.setCellValueFactory(param -> {
                RaceResults raceResult = param.getValue();
                if (raceResult.isFinished()) {
                    return new SimpleStringProperty(String.valueOf(raceResult.getIntervalToWinner()));
                } else {
                    return new SimpleStringProperty(raceResult.getReason());
                }
            });
        }

        raceResultsTable.setItems(FXCollections.observableList(raceResultsDao.getRaceResults(race.getId())));

        raceResultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    openDriverWindow(newSelection.getDriver());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
            RacingMenuSceneController controller = new RacingMenuSceneController(user, String.valueOf(race.getYear()));
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
            StandingsMenuSceneController controller = new StandingsMenuSceneController(user, String.valueOf(race.getYear()));
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
    void onAddComment(ActionEvent event) {
        UserDao userDao = DaoFactory.INSTANCE.getUserDao();
        Comment comment = new Comment(commentTextArea.getText(), userDao.getIdbyUsername(user.getUsername()), race.getId());

        Text newCommentText = new Text(commentTextArea.getText());
        newCommentText.setWrappingWidth(280.0);
        Text userName = new Text(user.getUsername() + ":");
        userName.setWrappingWidth(70);
        commentDao.add(comment);
        commentGridPane.addRow(commentGridPane.getRowCount(), userName, newCommentText);
        commentTextArea.clear();
    }

    private void openDriverWindow(Driver selectedDriver) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverDetailScene.fxml"));
        DriverDetailSceneController controller = new DriverDetailSceneController(user, selectedDriver, race.getYear(), race);
        loader.setController(controller);
        Parent raceScene = loader.load();
        Stage raceStage = (Stage) logoutButton.getScene().getWindow();
        raceStage.setScene(new Scene(raceScene));
        raceStage.setTitle("Race Results");
        raceStage.centerOnScreen();
        raceStage.show();
    }
}
