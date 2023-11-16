package f1.f1insider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginSceneController {


    @FXML
    private Button RegisterButton;

    @FXML
    private Button loginButton;

    @FXML
    void onLoginUser(ActionEvent event) {
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

