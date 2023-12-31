package com.fivesoft.simplehttpclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    /**
     * The main entry point for all JavaFX applications.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException if an error occurs during loading.
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 920, 700);
        stage.setTitle("Simple HTTP Client");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method for launching the application.
     * @param args The command line arguments.
     */

    public static void main(String[] args) {
        launch();
    }
}