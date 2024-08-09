package com.example.evolvatestmarijanbebek.controllers;

import com.example.evolvatestmarijanbebek.models.mappings.City;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.models.mappings.Currency;
import com.example.evolvatestmarijanbebek.models.mappings.Trip;
import com.example.evolvatestmarijanbebek.services.ReportCreator;
import com.example.evolvatestmarijanbebek.services.fileHandling.CSVHandler;
import com.example.evolvatestmarijanbebek.services.fileHandling.DataExtractor;
import com.example.evolvatestmarijanbebek.services.database.DataLoader;
import com.example.evolvatestmarijanbebek.services.database.DatabaseConnectionProvider;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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
    @FXML
    private TextArea recentTripsArea;
    @FXML
    private Label statusText;

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
    private final DataExtractor dataExtractor = new DataExtractor(PathConstants.UploadDir.label);
    private final ReportCreator reportCreator = new ReportCreator(connection);


    @FXML
    protected void onHelloButtonClick() throws SQLException {
        statusText.setText("Searching for trip files in upload directory.");
        CSVHandler csvHandler = new CSVHandler(PathConstants.UploadDir.label);

        Set<String> foundFiles = csvHandler.getAllFilesInUploadDir();
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
            recentTripsReport.append(reportCreator.generateNewTripsReport(lastTripId, country));
        }
        recentTripsArea.setText(recentTripsReport.toString());

        // Generate reports for all trip data available.
        totalTripsArea.setText(reportCreator.generateTotalTripsReport());

        if (foundCountries.isEmpty()) {
            statusText.setText("Upload directory is empty!");
        } else {
            statusText.setText("Report generated successfully!");
        }

    }

}