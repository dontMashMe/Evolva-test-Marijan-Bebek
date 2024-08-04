package com.example.evolvatestmarijanbebek.controllers;

import com.example.evolvatestmarijanbebek.services.CSVHandler;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Currency;
import java.util.List;
import java.util.Set;


public class MoneyCounterController {
    @FXML
    private Label statusText;

    @FXML
    protected void onHelloButtonClick() {
        statusText.setText("Searching for trip files in upload directory.");
        CSVHandler csvHandler = new CSVHandler(PathConstants.UploadDir.label);

        Set<String>foundFiles = csvHandler.getAllFilesInUploadDir();
        statusText.setText("Found %d trip files!".formatted(foundFiles.size()));


    }

}