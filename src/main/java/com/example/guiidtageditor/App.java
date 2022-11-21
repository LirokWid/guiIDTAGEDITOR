package com.example.guiidtageditor;

import javafx.application.Application;
import javafx.css.CssParser;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class App extends Application {
    public static Mp3 track;
    public static void main(String[] args) {
        launch();
    }

    public static void generateTrack(String path)
    {
        try {
            track = new Mp3(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populateFields(VBox infoVbox)
    {//Generate text fields based on ID3 tags in the fxml file
        //Get the fxml file
        //Get the pane
        //Get the text fields
        //Get the text field names
        //Get the ID3 tags
        //Set the text fields to the ID3 tags
        for (Frame frame:track.frameArrayList) {
            //AppController.addTagInfo(frame);
            //AppController.infoVbox.getChildren().add(new Label(frame.toString()));
            String name = frame.getName();
            Text frameName = new Text(identToText(name)+" (" + name+") : ");
            frameName.setStyle("-fx-font-weight: bold");
            Text frameData = new Text(frame.getTextData());
            //Add trackInfoText property to values

            Style.applyNameStyle(frameName);
            Style.applyDataStyle(frameData);

            //TODO
            HBox hBox = new HBox(frameName,frameData);
            Style.applyBoxStyle(hBox);
            hBox.alignmentProperty().setValue(Pos.CENTER_LEFT);
            infoVbox.getChildren().add(hBox);
        }
    }



    private static void setCssStyle(Text frameName, String trackInfoText)
    {
        //TODO : osekour
    }

    private static String identToText(String name)
    {//Get readable name from ID3tag ident
        return switch (name) {
            case "TIT2" -> "Title";
            case "TPE1" -> "Artist";
            case "TALB" -> "Album";
            case "TYER" -> "Year";
            case "TCON" -> "Genre";
            case "TRCK" -> "Track";
            case "APIC" -> "Cover Art";
            default -> name;
        };
    }
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mainApp.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        stage.setResizable(false);
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