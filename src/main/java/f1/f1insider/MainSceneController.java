package f1.f1insider;


import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;

import javafx.stage.Modality;
import javafx.stage.Stage;
import storage.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainSceneController {

    @FXML
    private ListView<?> lastGrandPrixTextField;

    @FXML
    private Pagination lastRaceCommentsField;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<?> nextGrandPrixTextField;

    @FXML
    private Button showHistoryButton;

    @FXML
    private Button showRacingButton;

    @FXML
    private Button showStandingsButton;
    @FXML
    private Button manageButton;

    private User user;

    private Race race;

    @FXML
    private Button showRaceButton;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label nextGpLabel;

    @FXML
    private Label nextGpDateLabel;

    @FXML
    private Label lastGpLabel;

    public MainSceneController(User user) {
        this.user = user;
    }
    @FXML
    void initialize() throws EntityNotFoundException {

        RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
        UsernameLabel.setText(user.toString());
        if (!user.isAdmin()){
            manageButton.setVisible(false);
        }

        if (raceDao.getNextRace() == null){
            nextGpLabel.setText("");
        }else {
            nextGpLabel.setText(raceDao.getNextRace().getPlace());
            nextGpDateLabel.setText(raceDao.getNextRace().getWhenRace().toString());
        }

        if (raceDao.getLastRace() == null){
            lastGpLabel.setText("");
        }else {
            lastGpLabel.setText(raceDao.getLastRace().getPlace());
        }

    }

    @FXML
    void loadLastGrandPrixTextField(ActionEvent event) {

    }

    @FXML
    void loadNextGrandPrixTextField(ActionEvent event) {

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

    }

    @FXML
    void onShowRacing(ActionEvent event) {
    }

    @FXML
    void onShowStandings(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("StandingsMenuScene.fxml"));
            StandingsMenuSceneController controller = new StandingsMenuSceneController(user);
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
    void onShowHome(ActionEvent event){

    }
    @FXML
    void onShowRace(ActionEvent event) {

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
        stage.setTitle("F1Insider - Manager");
        stage.show();
    }
}
