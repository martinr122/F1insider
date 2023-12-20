package com.f1insider;

import com.f1insider.storage.DaoFactory;
import com.f1insider.storage.Driver;
import com.f1insider.storage.DriverDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddDriverSceneController {
    @FXML
    private Button closeButton;
    @FXML
    private ImageView imageViewer;
    @FXML
    private Button imagechooseButton;
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
    private Image driverImage;
    private boolean firstDriver;
    private Driver curDriver;
    private Driver selectedDriver;
    DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    private TeamAddSceneController teamAddSceneController;
    private ObservableList<Driver> driversModel;

    public AddDriverSceneController(boolean firstDriver) {
        this.firstDriver = firstDriver;
    }

    public AddDriverSceneController(boolean firstDriver, Driver driver){
        this.firstDriver = firstDriver;
        this.curDriver = driver;
    }

    public void setTeamAddSceneController(TeamAddSceneController teamAddSceneController) {
        this.teamAddSceneController = teamAddSceneController;
    }

    public void initialize() {
        if (curDriver == null) {
            birthdayDatePicker.setValue(LocalDate.now().minusYears(18));
        } else {
            firstnameTextField.textProperty().setValue(curDriver.getFirstName());
            surnameTextField.textProperty().setValue(curDriver.getSurname());
            countryTextField.textProperty().setValue(curDriver.getCountry());
            birthdayDatePicker.setValue(curDriver.getBirthday());
            raceNumberTextField.textProperty().setValue(String.valueOf(curDriver.getRaceNumber()));
            if (curDriver.getPhoto() != null) {
                imageViewer.setVisible(true);
                imageViewer.setImage(curDriver.getPhoto());
                closeButton.setVisible(true);
                imagechooseButton.setVisible(false);
            } else {
                imageViewer.setVisible(false);
                imagechooseButton.setVisible(true);
                closeButton.setVisible(false);
            }
        }
        List<Driver> drivers = driverDao.getAllDriversWithoutPhoto();
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
        if (driverImage == null) {
            alertLabel.setVisible(true);
            alertLabel.setText("Please select image");
            return;
        } else {
            driver.setPhoto(driverImage);

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

    private List<Label> getLabelsFromGridPane(GridPane gridPane) {
        List<Label> labels = new ArrayList<>();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Label) {
                labels.add((Label) node);
            }
        }
        return labels;
    }

    private void handleDriverListClick(MouseEvent mouseEvent) {
        Driver clickedDriver = driversListView.getSelectionModel().getSelectedItem();

        if (clickedDriver != null) {
            Driver driver = driverDao.getByName(clickedDriver.getFirstName(),
                    clickedDriver.getSurname());
            selectedDriver = driver;
            if (driver.getPhoto() != null) {
                imageViewer.setVisible(true);
                imageViewer.setImage(driver.getPhoto());
                driverImage = driver.getPhoto();
                closeButton.setVisible(true);
                imagechooseButton.setVisible(false);
            } else {
                imageViewer.setVisible(false);
                imagechooseButton.setVisible(true);
                closeButton.setVisible(false);
            }
            firstnameTextField.setText(driver.getFirstName());
            raceNumberTextField.setText(String.valueOf(driver.getRaceNumber()));
            surnameTextField.setText(driver.getSurname());
            countryTextField.setText(driver.getCountry());
            birthdayDatePicker.setValue(driver.getBirthday());
            closeButton.setVisible(true);
        }
    }

    @FXML
    void onCloseImage(ActionEvent event) {
        driverImage = null;
        imagechooseButton.setVisible(true);
        imageViewer.setVisible(false);
        closeButton.setVisible(false);
    }

    @FXML
    void onChoose(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select race image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            driverImage = new Image(file.toURI().toString());
            imagechooseButton.setVisible(false);
            imageViewer.setVisible(true);
            closeButton.setVisible(true);
            imageViewer.setImage(driverImage);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Image error");
            alert.setHeaderText("Error in image! Please choose again.");
            alert.showAndWait();
        }
    }
}
