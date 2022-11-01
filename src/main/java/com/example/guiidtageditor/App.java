package com.example.guiidtageditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        System.out.println(getProjectName());

        stage.setTitle(getProjectName());
        stage.setScene(scene);
        stage.show();
    }

    public String getProjectName() {
        String userDir = System.getProperty("user.dir");
        Path path = Paths.get(userDir);
        String project = String.valueOf(path.getFileName());
        return project;
    }
    static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }




}