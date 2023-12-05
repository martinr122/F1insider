package f1.f1insider;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import storage.DaoFactory;
import storage.Team;
import storage.TeamDao;

import java.util.List;

public class TeamAddSceneController {
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
    private Label alertLabel;
    private int year;
    private TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();

    public TeamAddSceneController(int year) {
        this.year = year;
    }
    public void initialize() {
        List<Team> teams = teamDao.getTeamsByYear(year-1);
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

    private void handleLastGridClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Label) {
            Label clickedLabel = (Label) mouseEvent.getSource();
            Team team = teamDao.getTeamByName(clickedLabel.getText(),year-1);
            teamNameTextField.setText(team.getTeamName());
            EngineTextField.setText(team.getNameEngine());
            teamPrincipalTextField.setText(team.getNamePrincipal());
            founderTextField.setText(team.getNameFounder());
            countryTextField.setText(team.getCountry());
            monopostTextField.setText(team.getNameMonopost());
        }
    }


    @FXML
    void onAddFirstDriver(ActionEvent event) {

    }

    @FXML
    void onAddSecondDriver(ActionEvent event) {

    }

    @FXML
    void onSaveTeam(ActionEvent event) {
        Team team = new Team();
        team.setYear(year);
        team.setTeamName(teamNameTextField.getText());
        team.setNameEngine(EngineTextField.getText());
        team.setNamePrincipal(teamPrincipalTextField.getText());
        team.setNameFounder(founderTextField.getText());
        if (countryTextField.getText().length()>3){
            alertLabel.setVisible(true);
        }else {
            alertLabel.setVisible(false);
            team.setCountry(countryTextField.getText());
        }

        team.setNameMonopost(monopostTextField.getText());
        teamDao.add(team);
        currentYearGridPane.getChildren().clear();
        List<Team> teams = teamDao.getTeamsByYear(year);
        for (int i = 0; i < teams.size(); i++) {
            Team curTeam = teams.get(i);
            Label label = new Label(curTeam.toString());
            label.setFont(new Font(16));
            label.setOnMouseEntered(mouse-> label.setCursor(Cursor.HAND));
            label.setOnMouseExited(mouse -> label.setCursor(Cursor.DEFAULT));
            label.setOnMouseClicked(this::handleCurrGridClick);
            currentYearGridPane.addRow(i, label);
        }
    }

    private void handleCurrGridClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Label) {
            Label clickedLabel = (Label) mouseEvent.getSource();
            teamDao.deleteByName(clickedLabel.getText(),year);

            currentYearGridPane.getChildren().clear();
            List<Team> teams = teamDao.getTeamsByYear(year);
            for (int i = 0; i < teams.size(); i++) {
                Team curTeam = teams.get(i);
                Label label = new Label(curTeam.toString());
                label.setFont(new Font(16));
                label.setOnMouseEntered(mouse-> label.setCursor(Cursor.HAND));
                label.setOnMouseExited(mouse -> label.setCursor(Cursor.DEFAULT));
                label.setOnMouseClicked(this::handleCurrGridClick);
                currentYearGridPane.addRow(i, label);
            }
        }
    }

}
