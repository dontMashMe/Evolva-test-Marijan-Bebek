package com.example.evolvatestmarijanbebek.controllers;

import com.example.evolvatestmarijanbebek.models.mappings.City;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.models.mappings.Currency;
import com.example.evolvatestmarijanbebek.models.mappings.Trip;
import com.example.evolvatestmarijanbebek.services.ReportCreator;
import com.example.evolvatestmarijanbebek.services.fileHandling.FileHandler;
import com.example.evolvatestmarijanbebek.services.fileHandling.DataExtractor;
import com.example.evolvatestmarijanbebek.services.database.DataLoader;
import com.example.evolvatestmarijanbebek.services.database.DatabaseConnectionProvider;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SantaTripsController {

    /**
     * Reference FXML fields
     */
    @FXML
    public TextArea totalTripsArea;
    public TextField uploadDir;
    public TextArea recentTripsArea;
    public Label statusText;

    /**
     * Create connection to the database.
     */
    private static final Connection connection;

    static {
        try {
            connection = DatabaseConnectionProvider.getProductionConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Instances of Services.
     */
    private final DataLoader dataLoader = new DataLoader(connection);
    private final ReportCreator reportCreator = new ReportCreator(connection);


    private void setupDragAndDrop() {
        recentTripsArea.setOnDragOver(this::handleDragOver);
        recentTripsArea.setOnDragDropped(this::handleDragDropped);
    }

    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != recentTripsArea && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            for (File file : db.getFiles()) {
                FileHandler.copyFileToUploadDir(file);
            }
            success = true;
            statusText.setText("Files successfully uploaded to '" + PathConstants.UploadDir.label + "'.");
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    protected void initialize() {
        setupDragAndDrop();
        statusText.setText("Add files to the upload directory.");
        uploadDir.setText(PathConstants.UploadDir.label);
    }

    @FXML
    protected void onHelloButtonClick() throws SQLException, IOException {
        DataExtractor dataExtractor = new DataExtractor(PathConstants.UploadDir.label);


        statusText.setText("Searching for trip files in upload directory.");
        FileHandler fileHandler = new FileHandler(PathConstants.UploadDir.label);

        Set<String> foundFiles = fileHandler.getAllFilesInUploadDir();
        statusText.setText("Found %d trip files!".formatted(foundFiles.size()));


        long lastTripId = dataLoader.getLastTripId();
        List<Country> foundCountries = new ArrayList<>();

        // Extract the data from CSV files
        // and load into Database
        for (String file : foundFiles) {
            // extract
            Set<Currency> foundCurrencies = dataExtractor.getUniqueCurrenciesFromFile(file);
            Country country = dataExtractor.getCountryNameFromFile(file);
            foundCountries.add(country);

            List<City> foundCities = dataExtractor.getCityDataFromFile(file, country);
            List<Trip> foundTrips = dataExtractor.getTripDataFromFile(file);

            // load
            dataLoader.loadCountryData(country);
            dataLoader.loadBulkCurrencyData(foundCurrencies.stream().toList());
            dataLoader.loadCityData(foundCities);
            dataLoader.loadTripData(foundTrips);
        }

        // Generate reports for Trips from recent data.
        StringBuilder recentTripsReport = new StringBuilder();
        for (Country country : foundCountries) {
            recentTripsReport.append(reportCreator.generateNewTripsReportString(lastTripId, country));
        }

        if (foundCountries.isEmpty()) {
            statusText.setText("Upload directory does not contain trip files!");
            recentTripsArea.setText("");
        } else {
            statusText.setText("Report generated successfully!");

            // Generate reports for Trips from recent data.
            recentTripsArea.setText(recentTripsReport.toString());

            // Generate reports for all trip data available.
            totalTripsArea.setText(reportCreator.generateTotalTripsReport());

            // Generate HTML report
            String generatedHTMLReport = reportCreator.generateNewTripsReportHTML(lastTripId, foundCountries);
            FileHandler.createReportFile(generatedHTMLReport);
        }

    }

    public void onReloadDirClick() {

        File f = new File(uploadDir.getText());
        if (f.exists() && f.isDirectory()) {
            PathConstants.UploadDir.label = uploadDir.getText();
            PathConstants.HTMLReportsPath.label = "%s/reports/".formatted(uploadDir.getText());
            statusText.setText("Changed upload directory to '%s'.".formatted(uploadDir.getText()));
        } else {
            statusText.setText("Provided directory does not exist.");
        }

    }
}