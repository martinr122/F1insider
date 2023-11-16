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
import storage.DaoFactory;
import storage.EntityNotFoundException;
import storage.User;
import storage.UserDao;

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
            String password = userDao.givePassword(username);
            if(password == null || password == null){
                LoginAlert.setText("Username is incorrect");
            }else {
                if (password.equals(PasswordTextField.getText())){
                    LoginAlert.setText("Logged in");
                }else {
                    LoginAlert.setText("Incorect password");
                }
            }
        }catch (EmptyResultDataAccessException e){
            LoginAlert.setText("Login is incorrect");
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

