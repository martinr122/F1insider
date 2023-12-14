package f1.f1insider;

import f1.f1insider.storage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaceResultsEditSceneController {

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<Driver> eighteenthDriverChoiceBox;

    @FXML
    private CheckBox eighteenthFinishedCheckBox;

    @FXML
    private TextField eighteenthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> eighthDriverChoiceBox;

    @FXML
    private CheckBox eighthFinishedCheckBox;

    @FXML
    private TextField eighthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> eleventhDriverChoiceBox;

    @FXML
    private CheckBox eleventhFinishedCheckBox;

    @FXML
    private TextField eleventhIntervalTextField;

    @FXML
    private ChoiceBox<Driver> fifteenthDriverChoiceBox;

    @FXML
    private TextField fifteenthIntervalTextField;

    @FXML
    private CheckBox fifteenthFinishedCheckBox;

    @FXML
    private ChoiceBox<Driver> fifthDriverChoiceBox;

    @FXML
    private CheckBox fifthFinishedCheckBox;

    @FXML
    private TextField fifthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> firstDriverChoiceBox;

    @FXML
    private CheckBox firstFinishedCheckBox;

    @FXML
    private ChoiceBox<Driver> fourteenthDriverChoiceBox;

    @FXML
    private CheckBox fourteenthFinishedCheckBox;

    @FXML
    private TextField fourteenthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> fourthDriverChoiceBox;

    @FXML
    private CheckBox fourthFinishedCheckBox;

    @FXML
    private TextField fourthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> nineteenthDriverChoiceBox;

    @FXML
    private CheckBox nineteenthFinishedCheckBox;

    @FXML
    private TextField nineteenthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> ninethDriverChoiceBox;

    @FXML
    private CheckBox ninethFinishedCheckBox;

    @FXML
    private TextField ninethIntervalTextField;

    @FXML
    private Label racePlaceLabel;

    @FXML
    private Button saveButton;

    @FXML
    private ChoiceBox<Driver> secondDriverChoiceBox;

    @FXML
    private CheckBox secondFinishedCheckBox;

    @FXML
    private TextField secondIntervalTextField;

    @FXML
    private ChoiceBox<Driver> seventeenthDriverChoiceBox;

    @FXML
    private CheckBox seventeenthFinishedCheckBox;

    @FXML
    private TextField seventeenthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> seventhDriverChoiceBox;

    @FXML
    private CheckBox seventhFinishedCheckBox;

    @FXML
    private TextField seventhIntervalTextField;

    @FXML
    private ChoiceBox<Driver> sixteenthDriverChoiceBox;

    @FXML
    private CheckBox sixteenthFinishedCheckBox;

    @FXML
    private TextField sixteenthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> sixthDriverChoiceBox;

    @FXML
    private CheckBox sixthFinishedCheckBox;

    @FXML
    private TextField sixthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> tenthDriverChoiceBox;

    @FXML
    private CheckBox tenthFinishedCheckBox;

    @FXML
    private TextField tenthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> thirdDriverChoiceBox;

    @FXML
    private CheckBox thirdFinishedCheckBox;

    @FXML
    private TextField thirdIntervalTextField;

    @FXML
    private ChoiceBox<Driver> thirteenthDriverChoiceBox;

    @FXML
    private CheckBox thirteenthFinishedCheckBox;

    @FXML
    private TextField thirteenthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> twelfthDriverChoiceBox;

    @FXML
    private TextField twelfthIntervalTextField;

    @FXML
    private ChoiceBox<Driver> twentiethDriverChoiceBox;

    @FXML
    private CheckBox twentiethFinishedCheckBox;

    @FXML
    private TextField twentiethIntervalTextField;

    @FXML
    private CheckBox twelfthFinishedCheckBox;

    private User user;
    private Race race;
    private List<ChoiceBox<Driver>> choiceBoxList;
    private List<TextField> textFieldList;
    private List<CheckBox> checkBoxList;
    private DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    private RaceResultsDao raceResultsDao = DaoFactory.INSTANCE.getRaceResultsDao();
    public RaceResultsEditSceneController(User user, Race race){
        this.user = user;
        this.race = race;
    }

    public void initialize(){
        racePlaceLabel.setText(race.toString());

        choiceBoxList = Arrays.asList(firstDriverChoiceBox, secondDriverChoiceBox, thirdDriverChoiceBox,
                fourthDriverChoiceBox, fifthDriverChoiceBox, sixthDriverChoiceBox, seventhDriverChoiceBox, eighthDriverChoiceBox,
                ninethDriverChoiceBox, tenthDriverChoiceBox, eleventhDriverChoiceBox, twelfthDriverChoiceBox, thirteenthDriverChoiceBox,
                fourteenthDriverChoiceBox, fifteenthDriverChoiceBox, sixteenthDriverChoiceBox, seventeenthDriverChoiceBox,
                eighteenthDriverChoiceBox,nineteenthDriverChoiceBox, twentiethDriverChoiceBox);

        textFieldList = Arrays.asList(secondIntervalTextField, thirdIntervalTextField, fourthIntervalTextField, fifthIntervalTextField,
                sixthIntervalTextField, seventhIntervalTextField, eighthIntervalTextField, ninethIntervalTextField,
                tenthIntervalTextField, eleventhIntervalTextField, twelfthIntervalTextField, thirteenthIntervalTextField,
                fourteenthIntervalTextField, fifteenthIntervalTextField, sixteenthIntervalTextField, seventeenthIntervalTextField,
                eighteenthIntervalTextField, nineteenthIntervalTextField, twentiethIntervalTextField);

        checkBoxList = Arrays.asList(secondFinishedCheckBox, thirdFinishedCheckBox, fourthFinishedCheckBox,
                fifthFinishedCheckBox, sixthFinishedCheckBox, seventhFinishedCheckBox, eighthFinishedCheckBox, ninethFinishedCheckBox,
                tenthFinishedCheckBox, eleventhFinishedCheckBox, twelfthFinishedCheckBox, thirteenthFinishedCheckBox,
                fourteenthFinishedCheckBox, fifteenthFinishedCheckBox, sixteenthFinishedCheckBox, seventeenthFinishedCheckBox,
                eighteenthFinishedCheckBox, ninethFinishedCheckBox, twentiethFinishedCheckBox);

        List<Driver> drivers = driverDao.getAllDriversWithoutPhoto();
        for (ChoiceBox choiceBox : choiceBoxList) {
            for (Driver driver : drivers) {
                choiceBox.getItems().add(driver);
            }
        }

    }
    @FXML
    void onBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SeasonScene.fxml"));
            SeasonSceneController controller = new SeasonSceneController(user, race.getYear());
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

    @FXML
    void onSaveRaceResults(ActionEvent event) {
        List<Driver> selectedDrivers = new ArrayList<>();
//        for (TextField textField :
//                textFieldList) {
//            if (textField.getText().isEmpty()) {
//                Alert alert = new Alert(Alert.AlertType.WARNING,"You didn`t fill every Interval.");
//                alert.show();
//                return;
//            }
//        }
//        int firstChecked = Integer.MAX_VALUE;
//        for (CheckBox checkBox :
//                checkBoxList) {
//            if (checkBoxList.indexOf(checkBox) > firstChecked) {
//                Alert alert = new Alert(Alert.AlertType.WARNING,"You didn`t checked finished right.");
//                alert.show();
//                return;
//            }else{
//                if (checkBox.isSelected()) {
//                    firstChecked = checkBoxList.indexOf(checkBox);
//                }
//            }
//        }
//        for (int i = 0; i < textFieldList.size(); i++) {
//            System.out.println(!checkBoxList.get(i).isSelected()+", "+checkIfTime(textFieldList.get(i).getText()));
//            if (checkBoxList.get(i).isSelected() && !checkIfTime(textFieldList.get(i).getText())){
//                Alert alert = new Alert(Alert.AlertType.WARNING,"You didn`t checked finished right.");
//                alert.show();
//                return;
//            }
//        }
        for (ChoiceBox choiceBox:
                choiceBoxList){
            Driver selectedDriver = (Driver) choiceBox.getSelectionModel().getSelectedItem();
//            if(choiceBox.getSelectionModel().isEmpty()){
//                Alert alert = new Alert(Alert.AlertType.WARNING,"You didn`t fill every Driver.");
//                alert.show();
//                return;
//            }
//            if (selectedDrivers.contains(selectedDriver)){
//                Alert alert = new Alert(Alert.AlertType.WARNING,"You selected "+ selectedDriver.toString() +" twice.");
//                alert.show();
//                return;
//            }else{
//                selectedDrivers.add(selectedDriver);
//            }
            if (choiceBoxList.indexOf(choiceBox) == 0){
                RaceResults raceResults = new RaceResults();
                raceResults.setId(race.getId());
                raceResults.setPosition(choiceBoxList.indexOf(choiceBox) + 1);
                raceResults.setFinished(!firstFinishedCheckBox.isSelected());
                raceResults.setIntervalToWinner("WINNER");
                raceResultsDao.saveRaceResults(raceResults);
                raceResultsDao.addDriversToRaceResults(selectedDriver.getId(), race.getId());
            }
        }
    }

    private boolean checkIfTime(String string){
        return string.matches("[A-Za-z]+");
    }
}
