package f1.f1insider;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;


public class LoginScene extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginSceneController controller = new LoginSceneController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login - F1Insider");
        primaryStage.getIcons().add(new javafx.scene.image.Image("images/logo.png"));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
