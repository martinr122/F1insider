package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.springframework.dao.EmptyResultDataAccessException;
import storage.*;

import java.awt.event.KeyEvent;
import java.io.IOException;


public class LoginSceneController {



    @FXML
    private Label LoginAlert;

    @FXML
    private TextField LoginTextField;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button loginButton;
    @FXML
    private CheckBox VisibilityCheckBox;
    @FXML
    private TextField VisiblePasswordField;


    @FXML
    void onVisibleCheck(ActionEvent event) {
        boolean isChecked = VisibilityCheckBox.isSelected();
        VisiblePasswordField.setVisible(isChecked);
        VisiblePasswordField.setManaged(isChecked);
        PasswordTextField.setVisible(!isChecked);
        PasswordTextField.setManaged(!isChecked);

        if (isChecked) {
            VisiblePasswordField.setText(PasswordTextField.getText());
        } else {
            PasswordTextField.setText(VisiblePasswordField.getText());
        }

    }

    @FXML
    void onLoginUser(ActionEvent event) throws EntityNotFoundException {
        try{
            UserDao userDao = DaoFactory.INSTANCE.getUserDao();
            String username = LoginTextField.getText();
            String userPassword = userDao.givePassword(username);
            String loginPassword = PasswordTextField.getText();
            loginPassword = PasswordHashing.doHashing(loginPassword);
            if(userPassword == null || userPassword == null){
                LoginAlert.setText("Username is incorrect!");
            }else {
                if (userPassword.equals(loginPassword)){
                    try{
                        User user = new User();
                        user.setUsername(username);
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("MainScene.fxml"));
                        MainSceneController controller = new MainSceneController(user);
                        loader.setController(controller);
                        Parent mainMenuScene = loader.load();
                        Stage mainMenuStage = (Stage) loginButton.getScene().getWindow();
                        mainMenuStage.setScene(new Scene(mainMenuScene));
                        mainMenuStage.setTitle("Standings");
                        mainMenuStage.centerOnScreen();
                        mainMenuStage.show();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }else {
                    LoginAlert.setText("Incorect password!");
                }
            }
        }catch (EmptyResultDataAccessException e){
            LoginAlert.setText("Login is incorrect!");
        }

    }

    @FXML
    void onRegisterUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("RegisterScene.fxml"));
            RegisterSceneController controller = new RegisterSceneController();
            loader.setController(controller);
            Parent registerScene = loader.load();
            Stage registrationStage = (Stage) RegisterButton.getScene().getWindow();;
            registrationStage.setScene(new Scene(registerScene));
            registrationStage.setTitle("Register - F1Insider");
            registrationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

