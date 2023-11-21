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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Button loginButton;

    @FXML
    void onCreateUser(ActionEvent event) {
        String username = LoginTextField.getText();
        if (username.length() == 0){
            RegistrationAlert.setText("Select Username");
            return;
        }
        try {
            User newUser = new User();
            String passw = PasswordTextField.getText();
            if (passw == null){
                return;
            }
            newUser.setUsername(username);

            String conpassw = ConfirmPasswordTextField.getText();
            if (passw.equals(conpassw)){
                if (isValidPassword(passw)){
                    newUser.setPassUser(passw);
                    UserDao userDao = DaoFactory.INSTANCE.getUserDao();
                    if (userDao.add(newUser)){
                        try{
                            newUser.setPassUser(null);
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource("MainScene.fxml"));
                            MainSceneController controller = new MainSceneController(newUser);
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
                        RegistrationAlert.setText("Change Username!");
                    }
                }else {
                    RegistrationAlert.setText("Password isnt strong!");
                }
            }else {
                RegistrationAlert.setText("Passwords must be same!");
            }
        }catch (EmptyResultDataAccessException e){
            RegistrationAlert.setText("Registration is incorrect!");
        }

    }

    @FXML
    void onLoginUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("LoginScene.fxml"));
            LoginSceneController controller = new LoginSceneController();
            loader.setController(controller);
            Parent loginScene = loader.load();
            Stage loginStage = (Stage) loginButton.getScene().getWindow();;
            loginStage.setScene(new Scene(loginScene));
            loginStage.setTitle("Login - F1Insider");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
