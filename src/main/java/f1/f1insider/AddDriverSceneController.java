package f1.f1insider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import storage.DaoFactory;
import storage.Driver;
import storage.DriverDao;
import storage.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddDriverSceneController {
    @FXML
    private Label alertLabel;
    @FXML
    private Button addDriverButton;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private TextField countryTextField;

    @FXML
    private ListView<Driver> driversListView;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField raceNumberTextField;

    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField searchTextField;
    private boolean firstDriver;
    DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    private TeamAddSceneController teamAddSceneController;
    private ObservableList<Driver> driversModel;

    public AddDriverSceneController(boolean firstDriver) {
        this.firstDriver = firstDriver;
    }

    public void setTeamAddSceneController(TeamAddSceneController teamAddSceneController) {
        this.teamAddSceneController = teamAddSceneController;
    }

    public void initialize() {
        birthdayDatePicker.setValue(LocalDate.now().minusYears(18));
        List<Driver> drivers = driverDao.getAllDrivers();
        driversModel = FXCollections.observableList(drivers);
        driversListView.setItems(driversModel);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Driver> driversFiltered = driverDao.getByNameLike(newValue);
            ObservableList<Driver> observableDriversFiltered = FXCollections.observableArrayList(driversFiltered);
            driversListView.setItems(observableDriversFiltered);
        });

        driversListView.setCellFactory(param -> new ListCell<Driver>() {
            @Override
            protected void updateItem(Driver driver, boolean empty) {
                super.updateItem(driver, empty);

                if (empty || driver == null) {
                    setText(null);
                } else {
                    setText(driver.toString());
                    setFont(new Font(18));
                    setOnMouseClicked(mouseEvent -> handleDriverListClick(mouseEvent));
                }
            }
        });


    }


    @FXML
    void onAddDriver(ActionEvent event) {
        Driver driver = new Driver();
        if (firstnameTextField.getText().isEmpty()) {
            alertLabel.setVisible(true);
            alertLabel.setText("First name cannot be empty");
            return;
        } else {
            driver.setFirstName(firstnameTextField.getText());
        }

        if (surnameTextField.getText().isEmpty()) {
            alertLabel.setVisible(true);
            alertLabel.setText("Surname cannot be empty");
            return;
        } else {
            driver.setSurname(surnameTextField.getText());
        }

        if (countryTextField.getText().trim().isEmpty() || countryTextField.getText().trim().length() > 3) {
            alertLabel.setVisible(true);
            alertLabel.setText("Country cannot be empty and must be more than 3 characters");
            return;
        } else {
            driver.setCountry(countryTextField.getText().trim());
        }

        if (raceNumberTextField.getText().matches("\\d{1,3}")) {
            driver.setRaceNumber(Integer.parseInt(raceNumberTextField.getText()));
        } else {
            alertLabel.setVisible(true);
            alertLabel.setText("Wrong race number");
            return;
        }

        if (birthdayDatePicker.getValue() == null) {
            alertLabel.setVisible(true);
            alertLabel.setText("Please select a birthday");
            return;
        } else {
            driver.setBirthday(birthdayDatePicker.getValue());
        }

        if (firstDriver && teamAddSceneController != null) {
            teamAddSceneController.firstDriverAdded(driver);
        }
        if (!firstDriver && teamAddSceneController != null) {
            teamAddSceneController.secondDriverAdded(driver);
        }
        addDriverButton.getScene().getWindow().hide();
    }

    private List<Label> getLabelsFromGridPane(GridPane gridPane){
        List<Label> labels = new ArrayList<>();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Label) {
                labels.add((Label) node);
            }
        }
        return labels;
    }
    private void handleDriverListClick(MouseEvent mouseEvent) {
        Driver driverSelected = driversListView.getSelectionModel().getSelectedItem();

        if (driverSelected != null) {
            Driver driver = driverDao.getByName(driverSelected.getFirstName(),
                    driverSelected.getSurname());
            System.out.println(driver.getBirthday().toString());
            firstnameTextField.setText(driver.getFirstName());
            raceNumberTextField.setText(String.valueOf(driver.getRaceNumber()));
            surnameTextField.setText(driver.getSurname());
            countryTextField.setText(driver.getCountry());
            birthdayDatePicker.setValue(driver.getBirthday());
        }
    }
}
