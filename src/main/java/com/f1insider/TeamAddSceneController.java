package com.f1insider;

import com.f1insider.storage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamAddSceneController {
    @FXML
    private Button backButton;
    @FXML
    private Button addDriverButton1;
    @FXML
    private Button addDriverButton2;
    @FXML
    private GridPane currentYearGridPane;
    @FXML
    private GridPane lastYearGridPane;
    @FXML
    private TextField EngineTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private Label firstNameLabel;
    @FXML
    private TextField founderTextField;
    @FXML
    private TextField monopostTextField;
    @FXML
    private Label secondNameLabel;
    @FXML
    private TextField teamNameTextField;
    @FXML
    private TextField teamPrincipalTextField;
    @FXML
    private Pane teamColor;
    @FXML
    private ColorPicker teamColorPicker;
    @FXML
    private Label alertLabel;
    private Driver firstDriver;
    private Driver secondDriver;
    private User user;
    private int year;
    private List<Driver> preDrivers;
    private TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();
    private DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    private TeamFxModel teamFxModel;
    public TeamAddSceneController(User user, int year) {
        this.user = user;
        this.year = year;
    }

    public void initialize() {
        List<Team> teams = teamDao.getTeamsByYear(year - 1);
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            Label label = new Label(team.toString());
            label.setFont(new Font(16));
            label.setOnMouseClicked(mouseEvent -> handleLastGridClick(mouseEvent));
            lastYearGridPane.addRow(i, label);
        }
        teams = teamDao.getTeamsByYear(year);
        for (int i = 0; i < teams.size(); i++) {
            Team curTeam = teams.get(i);
            Label label = new Label(curTeam.toString());
            label.setFont(new Font(16));

            label.setOnMouseEntered(mouse -> label.setCursor(Cursor.HAND));
            label.setOnMouseExited(mouse -> label.setCursor(Cursor.DEFAULT));
            label.setOnMouseClicked(this::handleCurrGridClick);

            currentYearGridPane.addRow(i, label);
        }
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

    private void handleLastGridClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Label) {
            Label clickedLabel = (Label) mouseEvent.getSource();
            Team team = teamDao.getTeamByName(clickedLabel.getText(), year - 1);
            for (Label label : getLabelsFromGridPane(lastYearGridPane)) {
                if (label != clickedLabel) {
                    label.setStyle("-fx-text-fill: black;");
                } else {
                    clickedLabel.setStyle("-fx-background-color: white;");
                }
            }
            teamColor.setVisible(true);
            teamColor.setStyle("-fx-background-color: #" + team.getTeamColor() + ";");
            teamColorPicker.setValue(Color.web(team.getTeamColor()));
            teamNameTextField.setText(team.getTeamName());
            EngineTextField.setText(team.getNameEngine());
            teamPrincipalTextField.setText(team.getNamePrincipal());
            founderTextField.setText(team.getNameFounder());
            countryTextField.setText(team.getCountry());
            monopostTextField.setText(team.getNameMonopost());
            List<Driver> drivers = driverDao.getDriversbyTeam(team.getIdTeam());
            firstDriver = drivers.get(0);
            secondDriver = drivers.get(1);
            firstNameLabel.setText(firstDriver.toString());
            secondNameLabel.setText(secondDriver.toString());
        }
    }


    @FXML
    void onAddFirstDriver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddDriverScene.fxml"));
            AddDriverSceneController controller = null;
            if (firstDriver == null) {
                controller = new AddDriverSceneController(true);
            }else{
                controller = new AddDriverSceneController(true, firstDriver);
            }
            controller.setTeamAddSceneController(this);
            loader.setController(controller);
            Parent teamAddParent = loader.load();
            Stage addDriverStage = new Stage();
            addDriverStage.initOwner(addDriverButton1.getScene().getWindow());
            addDriverStage.setScene(new Scene(teamAddParent));
            addDriverStage.setTitle("F1Insider - Add Driver");
            addDriverStage.centerOnScreen();
            addDriverStage.initModality(Modality.WINDOW_MODAL);
            addDriverStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAddSecondDriver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddDriverScene.fxml"));
            AddDriverSceneController controller = new AddDriverSceneController(false);
            controller.setTeamAddSceneController(this);
            loader.setController(controller);
            Parent teamAddParent = loader.load();
            Stage addDriverStage = new Stage();
            addDriverStage.initOwner(addDriverButton2.getScene().getWindow());
            addDriverStage.setScene(new Scene(teamAddParent));
            addDriverStage.setTitle("F1Insider - Add Driver");
            addDriverStage.centerOnScreen();
            addDriverStage.initModality(Modality.WINDOW_MODAL);
            addDriverStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void firstDriverAdded(Driver driver) {
        firstNameLabel.setText(driver.toString());
        firstDriver = driver;
    }

    public void secondDriverAdded(Driver driver) {
        secondNameLabel.setText(driver.toString());
        secondDriver = driver;
    }

    @FXML
    void onClearTeam(ActionEvent event) {
        firstDriver = new Driver();
        secondDriver = new Driver();
        teamNameTextField.setText("");
        EngineTextField.setText("");
        teamPrincipalTextField.setText("");
        founderTextField.setText("");
        countryTextField.setText("");
        monopostTextField.setText("");
        teamColorPicker.setValue(Color.WHITE);
        teamColor.setVisible(false);
        firstNameLabel.setText("Name of Driver");
        secondNameLabel.setText("Name of Driver");
    }

    @FXML
    void onSaveTeam(ActionEvent event) {
        Team team = new Team();
        if (teamFxModel != null) {
            team = teamFxModel.getTeam();
        }else {
            team.setYear(year);
            if (teamNameTextField.getText().isEmpty()) {
                alertLabel.setVisible(true);
                alertLabel.setText("Team name cannot be empty!");
                return;
            } else {
                team.setTeamName(teamNameTextField.getText());
            }
            if (EngineTextField.getText().isEmpty()) {
                alertLabel.setVisible(true);
                alertLabel.setText("Engine cannot be empty!");
                return;
            } else {
                team.setNameEngine(EngineTextField.getText());
            }
            if (teamPrincipalTextField.getText().isEmpty()) {
                alertLabel.setVisible(true);
                alertLabel.setText("Team principal cannot be empty!");
                return;
            } else {
                team.setNamePrincipal(teamPrincipalTextField.getText());
            }
            if (founderTextField.getText().isEmpty()) {
                alertLabel.setVisible(true);
                alertLabel.setText("Founder cannot be empty!");
                return;
            } else {
                team.setNameFounder(founderTextField.getText());
            }if(countryTextField.getText().length() != 3) {
                alertLabel.setVisible(true);
                alertLabel.setText("Country must be lenght 3!");
                return;
            }else {
                team.setCountry(countryTextField.getText());
            }
            if (monopostTextField.getText().isEmpty()){
                alertLabel.setVisible(true);
                alertLabel.setText("Monopost cannot be empty!");
                return;
            }else {
                team.setNameMonopost(monopostTextField.getText());
            }
            team.setTeamColor(teamColorPicker.getValue().toString().substring(2, 8));
        }
        if (firstDriver == null || secondDriver == null) {
            alertLabel.setText("Add drivers!");
            return;
        }else {
            team.setFirstDriver(firstDriver);
            team.setSecondDriver(secondDriver);
            teamDao.add(team);
        }
        team.setIdTeam(teamDao.getID(team.getTeamName(), year));
        if (driverDao.contains(firstDriver)) {
            teamDao.addDriversToTeam(team.getIdTeam(), driverDao.getID(firstDriver.getFirstName(), firstDriver.getSurname()));
        } else {
            driverDao.add(firstDriver);
            teamDao.addDriversToTeam(team.getIdTeam(), driverDao.getID(firstDriver.getFirstName(), firstDriver.getSurname()));
        }
        if (driverDao.contains(secondDriver)) {
            teamDao.addDriversToTeam(team.getIdTeam(), driverDao.getID(secondDriver.getFirstName(), secondDriver.getSurname()));
        } else {
            driverDao.add(secondDriver);
            teamDao.addDriversToTeam(team.getIdTeam(), driverDao.getID(secondDriver.getFirstName(), secondDriver.getSurname()));
        }

        List<Driver> curDrivers = Arrays.asList(firstDriver, secondDriver);
        System.out.println(curDrivers);

        for (Driver driver :
                preDrivers) {
            if (!curDrivers.contains(driver)){
                teamDao.changeContract(team.getIdTeam(), driver.getId());
            }
        }

        currentYearGridPane.getChildren().clear();
        List<Team> teams = teamDao.getTeamsByYear(year);
        for (int i = 0; i < teams.size(); i++) {
            Team curTeam = teams.get(i);
            Label label = new Label(curTeam.toString());
            label.setFont(new Font(16));
            label.setOnMouseEntered(mouse -> label.setCursor(Cursor.HAND));
            label.setOnMouseExited(mouse -> label.setCursor(Cursor.DEFAULT));
            label.setOnMouseClicked(this::handleCurrGridClick);
            currentYearGridPane.addRow(i, label);
        }
        firstDriver = new Driver();
        secondDriver = new Driver();
        teamNameTextField.setText("");
        EngineTextField.setText("");
        teamPrincipalTextField.setText("");
        founderTextField.setText("");
        countryTextField.setText("");
        monopostTextField.setText("");
        teamColorPicker.setValue(Color.WHITE);
        firstNameLabel.setText("Name of Driver");
        secondNameLabel.setText("Name of Driver");
        teamColor.setVisible(true);
        alertLabel.setVisible(false);
    }

    private void handleCurrGridClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Label) {
            Label clickedLabel = (Label) mouseEvent.getSource();

            Team team = teamDao.getTeamByName(clickedLabel.getText(), year);

            teamFxModel = new TeamFxModel(team);

            for (Label label : getLabelsFromGridPane(currentYearGridPane)) {
                if (label != clickedLabel) {
                    label.setStyle("-fx-text-fill: black;");
                } else {
                    clickedLabel.setStyle("-fx-background-color: white;");
                }
            }

            teamNameTextField.textProperty().bindBidirectional(teamFxModel.teamNameProperty());
            teamPrincipalTextField.textProperty().bindBidirectional(teamFxModel.namePrincipalProperty());
            countryTextField.textProperty().bindBidirectional(teamFxModel.countryProperty());
            EngineTextField.textProperty().bindBidirectional(teamFxModel.nameEngineProperty());
            founderTextField.textProperty().bindBidirectional(teamFxModel.nameFounderProperty());
            monopostTextField.textProperty().bindBidirectional(teamFxModel.nameMonopostProperty());
            teamColor.setVisible(true);
            teamColor.setStyle("-fx-background-color: #" + team.getTeamColor() + ";");
            teamColorPicker.setValue(Color.web(teamFxModel.getTeamColor()));

            preDrivers = driverDao.getDriversbyTeam(team.getIdTeam());

            if (!preDrivers.isEmpty()) {
                firstDriver = preDrivers.get(0);
                secondDriver = preDrivers.get(1);
                firstNameLabel.setText(firstDriver.toString());
                secondNameLabel.setText(secondDriver.toString());
            }
            System.out.println(firstDriver);
        }
    }

    @FXML
    void onBack(ActionEvent event) {
        try {
            FXMLLoader seasonLoader = new FXMLLoader(getClass().getResource("SeasonScene.fxml"));
            SeasonSceneController seasonController = new SeasonSceneController(user, year);
            seasonLoader.setController(seasonController);
            Parent seasonScene = seasonLoader.load();
            Stage manageSceneStage = (Stage) backButton.getScene().getWindow();
            manageSceneStage.setScene(new Scene(seasonScene));
            manageSceneStage.setTitle("F1Insider - Season");
            manageSceneStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}