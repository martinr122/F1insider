package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import storage.DaoFactory;
import storage.EntityNotFoundException;
import storage.User;
import storage.UserDao;

import java.io.IOException;


public class LoginSceneController {


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
        UserDao userDao = DaoFactory.INSTANCE.getUserDao();
        String password = userDao.givePassword(LoginTextField.getText());
        if(password.equals(null)){
            System.out.println("Username is incorrect");
        }else {
            if (password.equals(PasswordTextField.getText())){
                System.out.println("Logged in");
            }else {
                System.out.println("Incorect password");
            }
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

