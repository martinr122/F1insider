package com.f1insider;

import com.f1insider.storage.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML
    private Button saveChampionsButton;
    @FXML
    private ComboBox<Team> wccComboBox;

    @FXML
    private ComboBox<Driver> wdcComboBox;
    SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
    DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();

    public ManageSceneController(User user) {
        this.user = user;
    }

    @FXML
    void initialize() throws EntityNotFoundException {
        seasons = seasonDao.getAllSeason();
        seasonComboBox.setItems(FXCollections.observableList(seasons));
        seasonComboBox.getSelectionModel().selectFirst();
        LocalDate localDate = LocalDate.now();
        seasonYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1950, localDate.getYear() + 1, localDate.getYear()));
        if (!seasonComboBox.getSelectionModel().isEmpty()) {


            int year = Integer.parseInt(seasonComboBox.getSelectionModel().getSelectedItem());

            wccComboBox.setItems(FXCollections.observableList(teamDao.getTeamsByYear(year)));
            wdcComboBox.setItems(FXCollections.observableList(driverDao.getAllFromSeason(year)));
            if (seasonDao.getWcc(year) != null) {
                wccComboBox.getSelectionModel().select(teamDao.getTeamByName(seasonDao.getWcc(year), year));
            }
            if (seasonDao.getWdc(year) != null) {
                String[] name = seasonDao.getWdc(year).split("");
                wdcComboBox.getSelectionModel().select(driverDao.getByName(name[0], name[1]));
            }

            seasonComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    wccComboBox.setItems(FXCollections.observableList(teamDao.getTeamsByYear(Integer.parseInt(newValue))));
                    wdcComboBox.setItems(FXCollections.observableList(driverDao.getAllFromSeason(Integer.parseInt(newValue))));
                    if (seasonDao.getWcc(Integer.parseInt(newValue)) != null) {
                        wccComboBox.getSelectionModel().select(teamDao.getTeamByName(seasonDao.getWcc(Integer.parseInt(newValue)), Integer.parseInt(newValue)));
                    } else {
                        wccComboBox.getSelectionModel().clearSelection();
                    }
                    if (seasonDao.getWdc(Integer.parseInt(newValue)) != null) {
                        String[] name = seasonDao.getWdc(Integer.parseInt(newValue)).split(" ");
                        wdcComboBox.getSelectionModel().select(driverDao.getByName(name[0], name[1]));
                    } else {
                        wdcComboBox.getSelectionModel().clearSelection();
                    }
                }
            });
        }

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
            SeasonStage.setTitle("Season");
            SeasonStage.setResizable(false);
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
            SeasonStage.setResizable(false);
            SeasonStage.centerOnScreen();
            SeasonStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSaveChampions(ActionEvent event) {
        String wdc = wdcComboBox.getSelectionModel().getSelectedItem().getFirstName() + " " + wdcComboBox.getSelectionModel().getSelectedItem().getSurname();
        seasonDao.setWdc(Integer.parseInt(seasonComboBox.getSelectionModel().getSelectedItem()), wdc);
        seasonDao.setWcc(Integer.parseInt(seasonComboBox.getSelectionModel().getSelectedItem()), wccComboBox.getSelectionModel().getSelectedItem().getTeamName());
    }

}
