package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.dao.EmptyResultDataAccessException;
import storage.*;

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
                    //TO DO login to next
                    LoginAlert.setText("you are in");
                    try{
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("MainScene.fxml"));
                        MainSceneController controller = new MainSceneController();
                        loader.setController(controller);
                        Parent mainMenuScene = loader.load();
                        Stage mainMenuStage = (Stage) loginButton.getScene().getWindow();
                        mainMenuStage.setScene(new Scene(mainMenuScene));
                        mainMenuStage.setTitle("Standings");
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

