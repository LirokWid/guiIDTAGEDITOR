package com.example.guiidtageditor;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Style
{
    static void applyDataStyle(Text text)
    {
        text.setStyle(
                "-fx-font-size: 20px;"+
                        "-fx-font-family:\"OCR A Extended\";"+
                        "-fx-text-fill: #FFFFFF;"+
                        "-fx-border-width: 1px;"+
                        "-fx-border-color: #000000;"+
                        "-fx-padding: 0px;");
    }

    static void applyNameStyle(Text text) {
        text.setStyle(
                "-fx-font-size: 20px;"+
                        "-fx-font-family:\"OCR A Extended\";"+
                        "-fx-text-fill: #FFFFFF;"+
                        "-fx-border-width: 1px;"+
                        "-fx-border-color: #000000;"+
                        "-fx-padding: 0px;");
    }
    static void applyBoxStyle(HBox text) {
        text.setStyle(
                        "-fx-border-width: 1px;"+
                        "-fx-border-style: solid;"+
                        "-fx-border-color: #000000;"+
                        "-fx-background-radius: 0px;"+
                        "-fx-padding: 0px;");
    }
}
