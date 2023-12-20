package com.f1insider;

import com.f1insider.storage.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ManageSceneController {
    private User user;
    private List<String> seasons;
    private RaceDao raceDao;
    @FXML
    private ComboBox<String> seasonComboBox;
    @FXML
    private Spinner<Integer> seasonYear;

    @FXML
    private Button editButton;

    @FXML
    private Button newButton;

    public ManageSceneController(User user) {
        this.user = user;
    }

    @FXML
    void initialize() throws EntityNotFoundException {
        SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
        seasons = seasonDao.getAllSeason();
        seasonComboBox.setItems(FXCollections.observableList(seasons));
        seasonComboBox.getSelectionModel().selectFirst();
        LocalDate localDate = LocalDate.now();
        seasonYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1950, localDate.getYear() + 1, localDate.getYear()));
    }

    @FXML
    void onEditSeason(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeasonScene.fxml"));
            SeasonSceneController controller = new SeasonSceneController(user, Integer.parseInt(seasonComboBox.getSelectionModel().getSelectedItem()));
            loader.setController(controller);
            Parent ManageParent = loader.load();
            Stage SeasonStage = (Stage) editButton.getScene().getWindow();
            SeasonStage.setScene(new Scene(ManageParent));
            SeasonStage.setTitle("F1insider - Season");
            SeasonStage.centerOnScreen();
            SeasonStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onNewSeason(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeasonScene.fxml"));
            SeasonSceneController controller = new SeasonSceneController(user, seasonYear.getValue());
            loader.setController(controller);
            Parent ManageParent = loader.load();
            Stage SeasonStage = (Stage) newButton.getScene().getWindow();
            SeasonStage.setScene(new Scene(ManageParent));
            SeasonStage.setTitle("Season");
            SeasonStage.centerOnScreen();
            SeasonStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
