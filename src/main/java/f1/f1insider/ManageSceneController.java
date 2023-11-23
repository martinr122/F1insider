package f1.f1insider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.TimeStringConverter;
import storage.DaoFactory;
import storage.Race;
import storage.RaceDao;
import storage.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.FormatStyle;

public class ManageSceneController {
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

    private User user;
    public ManageSceneController(User user){
        this.user = user;
    }
    public void initialize() {
        RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
        listOfRaces.setItems(FXCollections.observableList(raceDao.getAllRaces()));

        timeOfRace.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfQualifying.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfPractice3.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfPractice2.setValueFactory(createNewSpinnerFactory().getValueFactory());
        timeOfPractice1.setValueFactory(createNewSpinnerFactory().getValueFactory());
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
    void OnAddRace(ActionEvent event) {
        Race race = new Race();
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
        race.setPlace(nameOfGP.getText());
        RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
        raceDao.saveRace(race);
        listOfRaces.setItems(FXCollections.observableList(raceDao.getAllRaces()));
    }

    @FXML
    void onAddDriverButton(ActionEvent event) {

    }

    @FXML
    void onAddRaceResultsButton(ActionEvent event) {

    }

    @FXML
    void onAddTeamButton(ActionEvent event) {

    }
}
