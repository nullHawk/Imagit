package com.nullhawk.imagit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Filter extends  Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Filter");
        FXMLLoader fxmlLoader = new FXMLLoader(Imagit.class.getResource("Filters.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 750);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Imagit.class.getResourceAsStream("icons/logo.png")));
        primaryStage.show();
        FilterController obj = new FilterController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Filters.fxml"));
        Parent root = loader.load();
        FilterController controller = loader.getController();
        ImageView imageView = new ImageView();
        imageView.setImage(ImagitControler.fxImage);
        imageView.setPreserveRatio(true);
        imageView.maxHeight(650);
        imageView.maxWidth(700);
        imageView.setFitWidth(700);
        imageView.setFitHeight(650);
        if (controller.photo != null) {
            controller.photo.getChildren().clear();
            controller.photo.getChildren().add(imageView);
            controller.photo.setMaxHeight(650);
            controller.photo.setMaxWidth(700);



        } else {
            System.out.println("ScrollPane is null, cannot set style.");
        }

    }
}


