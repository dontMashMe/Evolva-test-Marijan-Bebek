package com.example.evolvatestmarijanbebek.controllers;

import com.example.evolvatestmarijanbebek.services.CSVHandler;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Set;


public class MoneyCounterController {
    @FXML
    private Label statusText;

    private final CSVHandler csvHandler = new CSVHandler(PathConstants.UploadDir.label);

    private Set<String> foundFiles;

    @FXML
    protected void onHelloButtonClick() {
        statusText.setText("Searching for trip files in upload directory.");
        startLoadingAnimation();
    }

    /**
     * Displays a short text animation.
     * */
    private void startLoadingAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), _ -> statusText.setText("Searching for money files in upload directory.")),
                new KeyFrame(Duration.seconds(1.0), _ -> statusText.setText("Searching for money files in upload directory..")),
                new KeyFrame(Duration.seconds(1.5), _ -> statusText.setText("Searching for money files in upload directory..."))
        );

        timeline.setCycleCount(2);
        timeline.setOnFinished(event -> searchFiles());
        timeline.play();
    }

    private void searchFiles() {
        foundFiles = csvHandler.getAllFilesInUploadDir();
        statusText.setText("Found %d trip files!".formatted(foundFiles.size()));
    }
}