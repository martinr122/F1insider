package com.f1insider;

import com.f1insider.storage.DaoFactory;
import com.f1insider.storage.UserDao;
import com.f1insider.storage.EntityNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.dao.EmptyResultDataAccessException;
import com.f1insider.storage.User;

import java.io.IOException;

public class RegisterSceneController {

    @FXML
    private Label RegistrationAlert;

    @FXML
    private PasswordField ConfirmPasswordTextField;

    @FXML
    private TextField LoginTextField;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private Button RegisterButton;

    @FXML
    private CheckBox VisibilityCheckBox;

    @FXML
    private TextField VisibleConfirmPasswordTextField;

    @FXML
    private TextField VisiblePasswordTextField;


    @FXML
    private Button loginButton;
    @FXML
    private void initialize() {
        LoginTextField.setOnKeyPressed(e -> {
            try {
                onKeyPressed(e);
            } catch (EntityNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        PasswordTextField.setOnKeyPressed(e -> {
            try {
                onKeyPressed(e);
            } catch (EntityNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        ConfirmPasswordTextField.setOnKeyPressed(e -> {
            try {
                onKeyPressed(e);
            } catch (EntityNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        VisiblePasswordTextField.setOnKeyPressed(e -> {
            try {
                onKeyPressed(e);
            } catch (EntityNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        VisibleConfirmPasswordTextField.setOnKeyPressed(e -> {
            try {
                onKeyPressed(e);
            } catch (EntityNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    protected void onKeyPressed(KeyEvent e) throws EntityNotFoundException {
        if (e.getCode() == KeyCode.ENTER) {
            onCreateUser(new ActionEvent());
        }
    }
    @FXML
    void onCreateUser(ActionEvent event) {
        String username = LoginTextField.getText();
        if (username.equals("")){
            RegistrationAlert.setText("Select Username!");
            return;
        }
        try {
            User newUser = new User();
            String passw = PasswordTextField.getText();
            if (passw.equals("")) {
                return;
            }
            newUser.setUsername(username);

            String conpassw = ConfirmPasswordTextField.getText();
            if (passw.equals(conpassw)){
                if (passw.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")){
                    newUser.setPassUser(passw);
                    UserDao userDao = DaoFactory.INSTANCE.getUserDao();
                    if (userDao.add(newUser)){
                        RegistrationAlert.setStyle("-fx-text-fill: green");
                        RegistrationAlert.setText("Account created!");
                    }else {
                        RegistrationAlert.setStyle("-fx-text-fill: red");
                        RegistrationAlert.setText("Change Username!");
                    }
                }else {
                    RegistrationAlert.setStyle("-fx-text-fill: red");
                    RegistrationAlert.setText("Password isnt strong!");
                }
            }else {
                RegistrationAlert.setStyle("-fx-text-fill: red");
                RegistrationAlert.setText("Passwords must be same!");
            }
        }catch (EmptyResultDataAccessException e){
            RegistrationAlert.setStyle("-fx-text-fill: red");
            RegistrationAlert.setText("Registration is incorrect!");
        }

    }

    @FXML
    void onLoginUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
            LoginSceneController controller = new LoginSceneController();
            loader.setController(controller);
            Parent loginScene = loader.load();
            Scene scene = new Scene(loginScene);
            scene.getStylesheets().add("style/login.css");
            Stage loginStage = (Stage) loginButton.getScene().getWindow();;
            loginStage.setScene(scene);
            loginStage.setTitle("Login - F1Insider");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onVisibleCheck(ActionEvent event) {
        boolean isChecked = VisibilityCheckBox.isSelected();
        VisiblePasswordTextField.setVisible(isChecked);
        VisiblePasswordTextField.setManaged(isChecked);
        VisibleConfirmPasswordTextField.setVisible(isChecked);
        VisibleConfirmPasswordTextField.setManaged(isChecked);

        PasswordTextField.setVisible(!isChecked);
        PasswordTextField.setManaged(!isChecked);
        ConfirmPasswordTextField.setVisible(!isChecked);
        ConfirmPasswordTextField.setManaged(!isChecked);

        if (isChecked) {
            VisiblePasswordTextField.setText(PasswordTextField.getText());
            VisibleConfirmPasswordTextField.setText(ConfirmPasswordTextField.getText());
        } else {
            PasswordTextField.setText(VisiblePasswordTextField.getText());
            ConfirmPasswordTextField.setText(VisibleConfirmPasswordTextField.getText());
        }

    }

}
