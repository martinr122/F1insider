package f1.f1insider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import storage.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private int year;
    private RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();
    private SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();

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
        if (dateOfRace.getValue().getYear() == year && dateOfQualifying.getValue().getYear() == year &&
        dateOfpractice3.getValue().getYear() == year && dateOfpractice2.getValue().getYear() == year &&
        dateOfpractice1.getValue().getYear() == year){
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
            race.setName(nameOfGP.getText());
            race.setPlace(placeOfGP.getText());
            SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();
            seasonDao.addSeason(year,null,null);
            raceDao.saveRace(race);
            listOfRaces.setItems(FXCollections.observableList(raceDao.getAllRaces(String.valueOf(year))));
            wrongFormat.setVisible(false);
        }else {
            wrongFormat.setVisible(true);
        }

    }

    @FXML
    void onAddRaceResultsButton(ActionEvent event) {
        if(listOfRaces.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("RaceResultsEditScene.fxml"));
                RaceResultsEditSceneController controller = new RaceResultsEditSceneController(user, listOfRaces.getSelectionModel().getSelectedItem());
                loader.setController(controller);
                Parent seasonParent = loader.load();
                Stage teamAddStage = (Stage) addTeamButton.getScene().getWindow();
                teamAddStage.setScene(new Scene(seasonParent));
                teamAddStage.setTitle("F1Insider - Add Team");
                teamAddStage.centerOnScreen();
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
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("TeamAddScene.fxml"));
            TeamAddSceneController controller = new TeamAddSceneController(user,year);
            loader.setController(controller);
            Parent seasonParent = loader.load();
            Stage teamAddStage = (Stage) addTeamButton.getScene().getWindow();
            teamAddStage.setScene(new Scene(seasonParent));
            teamAddStage.setTitle("F1Insider - Add Team");
            teamAddStage.centerOnScreen();
            teamAddStage.getIcons().add(new javafx.scene.image.Image("images/logo.png"));
            teamAddStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void onBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("ManageScene.fxml"));
            ManageSceneController controller = new ManageSceneController(user);
            loader.setController(controller);
            Parent manageScene = loader.load();
            Stage manageSceneStage = (Stage) backButton.getScene().getWindow();
            manageSceneStage.setScene(new Scene(manageScene));
            manageSceneStage.setTitle("F1Insider - Manager");
            manageSceneStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
