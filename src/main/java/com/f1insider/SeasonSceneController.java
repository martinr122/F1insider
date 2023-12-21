package com.f1insider;

import com.f1insider.storage.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SeasonSceneController {
    @FXML
    private Button backButton;
    @FXML
    private Button addTeamButton;
    @FXML
    private DatePicker dateOfQualifying;

    @FXML
    private DatePicker dateOfRace;

    @FXML
    private DatePicker dateOfpractice1;

    @FXML
    private DatePicker dateOfpractice2;

    @FXML
    private DatePicker dateOfpractice3;

    @FXML
    private ListView<Race> listOfRaces;

    @FXML
    private TextField nameOfGP;

    @FXML
    private TextField placeOfGP;

    @FXML
    private CheckBox isSprintRace;

    @FXML
    private Spinner<LocalTime> timeOfPractice1;

    @FXML
    private Spinner<LocalTime> timeOfPractice2;

    @FXML
    private Spinner<LocalTime> timeOfPractice3;

    @FXML
    private Spinner<LocalTime> timeOfQualifying;

    @FXML
    private Spinner<LocalTime> timeOfRace;
    @FXML
    private Label seasonYearDisplay;
    @FXML
    private Label wrongFormat;
    @FXML
    private Label alertLabel;
    private int year;
    private RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
    private SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
    private RaceResultsDao raceResultsDao = DaoFactory.INSTANCE.getRaceResultsDao();
    private RaceFxModel raceFxModel;
    private User user;
    public SeasonSceneController(User user, int year){
        this.user = user;
        this.year = year;
    }
    public void initialize() {

        listOfRaces.setItems(FXCollections.observableList(raceDao.getAllRaces(String.valueOf(year))));
        seasonYearDisplay.setText(String.valueOf(year));

        timeOfRace.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfQualifying.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfPractice3.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfPractice2.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfPractice1.setValueFactory(createNewSpinnerFactory().getValueFactory());

        LocalDate defaultDate = LocalDate.now();
        dateOfRace.setValue(defaultDate.minusYears(LocalDate.now().getYear()-year));
        dateOfQualifying.setValue(defaultDate.minusYears(LocalDate.now().getYear()-year).minusDays(1));
        dateOfpractice3.setValue(defaultDate.minusYears(LocalDate.now().getYear()-year).minusDays(1));
        dateOfpractice2.setValue(defaultDate.minusYears(LocalDate.now().getYear()-year).minusDays(2));
        dateOfpractice1.setValue(defaultDate.minusYears(LocalDate.now().getYear()-year).minusDays(2));

        listOfRaces.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Race>() {
            @Override
            public void changed(ObservableValue<? extends Race> observableValue, Race race, Race selectedRace) {
                if (selectedRace != null) {
                    raceFxModel = new RaceFxModel(selectedRace);

                    placeOfGP.textProperty().bindBidirectional(raceFxModel.placeProperty());
                    nameOfGP.textProperty().bindBidirectional(raceFxModel.nameProperty());

                    timeOfRace.getValueFactory().setValue(raceFxModel.getWhenRace().toLocalTime());
                    timeOfQualifying.getValueFactory().setValue(raceFxModel.getWhenQuali().toLocalTime());
                    timeOfPractice3.getValueFactory().setValue(raceFxModel.getWhenThirdSession().toLocalTime());
                    timeOfPractice2.getValueFactory().setValue(raceFxModel.getWhenSecondSession().toLocalTime());
                    timeOfPractice1.getValueFactory().setValue(raceFxModel.getWhenFirstSession().toLocalTime());

                    dateOfRace.setValue(raceFxModel.getWhenRace().toLocalDate());
                    dateOfQualifying.setValue(raceFxModel.getWhenQuali().toLocalDate());
                    dateOfpractice3.setValue(raceFxModel.getWhenThirdSession().toLocalDate());
                    dateOfpractice2.setValue(raceFxModel.getWhenSecondSession().toLocalDate());
                    dateOfpractice1.setValue(raceFxModel.getWhenFirstSession().toLocalDate());
                }

            }
        });
    dateOfRace.valueProperty().addListener((observable, oldValue, newValue) -> {
        dateOfQualifying.setValue(newValue.minusDays(1));
        dateOfpractice3.setValue(newValue.minusDays(1));
        dateOfpractice2.setValue(newValue.minusDays(2));
        dateOfpractice1.setValue(newValue.minusDays(2));
    });
    }

    private Spinner<LocalTime> createNewSpinnerFactory() {
        Spinner<LocalTime> spinner = new Spinner<>(createSpinnerFactory());
        spinner.setEditable(true);
        return spinner;
    }

    private SpinnerValueFactory<LocalTime> createSpinnerFactory() {
        return new SpinnerValueFactory<LocalTime>() {
            {
                setValue(LocalTime.NOON);
            }
            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.NOON);
                else {
                    LocalTime time = getValue();
                    setValue(time.minusMinutes(30 * steps));
                }
            }

            @Override
            public void increment(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.NOON);
                else {
                    LocalTime time = getValue();
                    setValue(time.plusMinutes(30 * steps));
                }
            }
        };
    }


    @FXML
    void OnAddRace(ActionEvent event) throws EntityNotFoundException {
        Race race = new Race();
        if (raceFxModel == null) {
            if (dateOfRace.getValue().getYear() == year && dateOfQualifying.getValue().getYear() == year &&
            dateOfpractice3.getValue().getYear() == year && dateOfpractice2.getValue().getYear() == year &&
            dateOfpractice1.getValue().getYear() == year){
                race.setYear(dateOfRace.getValue().getYear());

                LocalDate date = dateOfRace.getValue();
                race.setWhenRace(date.atTime(timeOfRace.getValue()));

                date = dateOfQualifying.getValue();
                race.setWhenQuali(date.atTime(timeOfQualifying.getValue()));

                date = dateOfpractice1.getValue();
                race.setWhenFirstSession(date.atTime(timeOfPractice1.getValue()));

                date = dateOfpractice2.getValue();
                race.setWhenSecondSession(date.atTime(timeOfPractice2.getValue()));

                date = dateOfpractice3.getValue();
                race.setWhenThirdSession(date.atTime(timeOfPractice3.getValue()));

                race.setSprintWeekend(isSprintRace.isSelected());
                if (nameOfGP.getText().isEmpty() ){
                    alertLabel.setVisible(true);
                    alertLabel.setText("Name of GP is required!");
                    return;
                }else {
                    race.setName(nameOfGP.getText());
                }
                if (placeOfGP.getText().isEmpty()){
                    alertLabel.setVisible(true);
                    alertLabel.setText("Place of GP is required!");
                    return;
                }else {
                    race.setPlace(placeOfGP.getText());
                }
                alertLabel.setVisible(false);
                SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
                seasonDao.addSeason(year,null,null);
                listOfRaces.setItems(FXCollections.observableList(raceDao.getAllRaces(String.valueOf(year))));
                wrongFormat.setVisible(false);
            }else {
                wrongFormat.setVisible(true);
            }
        } else {
            race = raceFxModel.getRace();
        }

        raceDao.saveRace(race);
        listOfRaces.setItems(FXCollections.observableList(raceDao.getAllRaces(String.valueOf(year))));
        raceFxModel = null;
        nameOfGP.setText("");
        placeOfGP.setText("");
    }

    @FXML
    void onAddRaceResultsButton(ActionEvent event) {
        if(listOfRaces.getSelectionModel().getSelectedItem() != null) {
            if (raceResultsDao.getRaceResults(raceDao.getRaceId(raceFxModel.getRace())).size()>0){
                Alert alert = new Alert(Alert.AlertType.WARNING,"This race already has race results!");
                alert.show();
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("RaceResultsEditScene.fxml"));
                RaceResultsEditSceneController controller = new RaceResultsEditSceneController(user, listOfRaces.getSelectionModel().getSelectedItem());
                loader.setController(controller);
                Parent seasonParent = loader.load();
                Stage teamAddStage = (Stage) addTeamButton.getScene().getWindow();
                teamAddStage.setScene(new Scene(seasonParent));
                teamAddStage.setTitle("Add Team");
                teamAddStage.centerOnScreen();
                teamAddStage.setResizable(false);
                teamAddStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING,"You didn`t choose race to add race results.");
            alert.show();
        }
    }

    @FXML
    void onAddTeamButton(ActionEvent event) throws EntityNotFoundException {
        if (!seasonDao.isSeason(year)){
            seasonDao.addSeason(year,null,null);
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamAddScene.fxml"));
            TeamAddSceneController controller = new TeamAddSceneController(user,year);
            loader.setController(controller);
            Parent seasonParent = loader.load();
            Stage teamAddStage = (Stage) addTeamButton.getScene().getWindow();
            teamAddStage.setScene(new Scene(seasonParent));
            teamAddStage.setTitle("Add Team");
            teamAddStage.centerOnScreen();
            teamAddStage.setResizable(false);
            teamAddStage.getIcons().add(new javafx.scene.image.Image("images/logo.png"));
            teamAddStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void onBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageScene.fxml"));
            ManageSceneController controller = new ManageSceneController(user);
            loader.setController(controller);
            Parent manageScene = loader.load();
            Stage manageSceneStage = (Stage) backButton.getScene().getWindow();
            manageSceneStage.setScene(new Scene(manageScene));
            manageSceneStage.setTitle("Manager");
            manageSceneStage.setResizable(false);
            manageSceneStage.centerOnScreen();
            manageSceneStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
