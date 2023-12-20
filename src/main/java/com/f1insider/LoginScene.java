package com.f1insider;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LoginScene extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginSceneController controller = new LoginSceneController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("style/login.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("F1Insider");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new javafx.scene.image.Image("images/logo.png"));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
