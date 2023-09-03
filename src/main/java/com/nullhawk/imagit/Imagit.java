package com.nullhawk.imagit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;

public class Imagit extends Application {

    public static Stage publicStage;

    @Override
    public void start(Stage stage) throws IOException {
        publicStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Imagit.class.getResource("Imagit.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 720);
        stage.setTitle("Imagit");
        stage.getIcons().add(new Image(Imagit.class.getResourceAsStream("icons/logo.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
