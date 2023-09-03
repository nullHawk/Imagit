package com.nullhawk.imagit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Filter extends  Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Filter");
        FXMLLoader fxmlLoader = new FXMLLoader(Imagit.class.getResource("Filters.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        ImageView imageView = new ImageView();
        imageView.setImage(ImagitControler.fxImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);


        // Set the ImageView as the background content of the ScrollPane\ // Clear default background
        if (this.photoViewer != null) {
            this.photoViewer.getChildren().clear();
            this.photoViewer.getChildren().add(imageView);



        } else {
            System.out.println("ScrollPane is null, cannot set style.");
        }

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}


