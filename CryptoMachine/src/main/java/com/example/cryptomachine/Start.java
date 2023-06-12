package com.example.cryptomachine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Start extends Application {
    @Override
    //public void start(Stage primaryStage) throws IOException
    public void start (Stage primaryStage)
    {

        try
        {
            primaryStage.setTitle("Crypto Machine App");
            Parent root = FXMLLoader.load(getClass().getResource("index.fxml"));
            Scene primaryScene = new Scene(root);
            InputStream iconStream = getClass().getResourceAsStream("/lock.png");
            Image image = new Image(iconStream);
            primaryStage.getIcons().add(image);
            primaryStage.setScene(primaryScene);
            primaryStage.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

