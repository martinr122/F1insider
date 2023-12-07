package f1.f1insider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private GridPane driverGridPane;

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

    public AddDriverSceneController(boolean firstDriver) {
        this.firstDriver = firstDriver;
    }

    public void setTeamAddSceneController(TeamAddSceneController teamAddSceneController) {
        this.teamAddSceneController = teamAddSceneController;
    }

    public void initialize() {
        birthdayDatePicker.setValue(LocalDate.now().minusYears(18));
        driverGridPane.getChildren().clear();
        List<Driver> drivers = driverDao.getAllDrivers();
        for (int i = 0; i < drivers.size(); i++) {
            Driver driver = drivers.get(i);
            Label label = new Label(driver.toString());
            label.setFont(new Font(18));
            label.setOnMouseClicked(mouseEvent -> handleDriverGridClick(mouseEvent));
            driverGridPane.addRow(i, label);
        }
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override

            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                driverGridPane.getChildren().clear();
                List<Driver> drivers = driverDao.getByNameLike(newValue);
                if (!drivers.isEmpty()) {
                    for (int i = 0; i < drivers.size(); i++) {
                        Driver driver = drivers.get(i);
                        Label label = new Label(driver.toString());
                        label.setFont(new Font(18));
                        label.setOnMouseClicked(mouseEvent -> handleDriverGridClick(mouseEvent));
                        driverGridPane.addRow(i, label);
                    }
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
    private void handleDriverGridClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Label) {
            Label clickedLabel = (Label) mouseEvent.getSource();

            for (Label label : getLabelsFromGridPane(driverGridPane)) {
                if (label != clickedLabel) {
                    label.setStyle("-fx-background-color: transparent;");
                }else {
                    clickedLabel.setStyle("-fx-background-color: #fa899a;");
                }
            }

            String[] driverName = clickedLabel.getText().trim().split(" ");
            Driver driver = driverDao.getByName(driverName[0], driverName[1]);
            firstnameTextField.setText(driver.getFirstName());
            raceNumberTextField.setText(String.valueOf(driver.getRaceNumber()));
            surnameTextField.setText(driver.getSurname());
            countryTextField.setText(driver.getCountry());
            birthdayDatePicker.setValue(driver.getBirthday());
        }
    }

}
