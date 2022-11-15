package com.example.guiidtageditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.io.File;

public class AppController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane coverArt;
    @FXML
    private Pane imagePane;
    @FXML
    public VBox infoVbox;
    @FXML
    private ToggleButton btnBackGrnd;

    @FXML
    private Text txtFldPath;

    @FXML
    private ImageView imgViewCoverArt;



    @FXML
    protected void onOpenFileClick() throws Exception
    {
        System.out.println("Open File Clicked");
        String path = getPath((Stage) mainPane.getScene().getWindow(), "Open File");
        if (path != null)
        {
            txtFldPath.setText(path);
            //Change field path to new path
            System.out.println(path);
            App.generateTrack(path);
            App.populateFields(infoVbox);
            Image img = App.track.findFrameByName("APIC").getImage();

            imgViewCoverArt.setImage(img);
        }
    }

    @FXML
    protected void eventBtnBackGrnd(ActionEvent event){
        System.out.println("ButtonPushed");
        if(btnBackGrnd.isSelected()){
            System.out.println("Button is Selected");
        } else {
            System.out.println("Button is not Selected");
        }
    }

    private static void setStyle(Node node, String style) {
        if (isBlank(style)) { return; }
        if (style.startsWith("&")) {
            // append style
            String nodeStyle = node.getStyle();
            node.setStyle(nodeStyle + (nodeStyle.endsWith(";") ? "" : ";") + style.substring(1));
        } else {
            node.setStyle("-fx-background-color: "+ style+";");
        }
    }
    static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }
    public static @Nullable String getPath(Stage stage, String title) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return null;
            //No Directory selected
        } else {
            return selectedFile.getAbsolutePath();
        }
    }
}