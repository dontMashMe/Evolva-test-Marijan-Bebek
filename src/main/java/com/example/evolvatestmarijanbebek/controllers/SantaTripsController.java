package com.example.evolvatestmarijanbebek.controllers;

import com.example.evolvatestmarijanbebek.models.mappings.City;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.models.mappings.Currency;
import com.example.evolvatestmarijanbebek.models.mappings.Trip;
import com.example.evolvatestmarijanbebek.services.fileHandling.CSVHandler;
import com.example.evolvatestmarijanbebek.services.fileHandling.DataExtractor;
import com.example.evolvatestmarijanbebek.services.database.DataLoader;
import com.example.evolvatestmarijanbebek.services.database.DatabaseConnectionProvider;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;


public class SantaTripsController {
    @FXML
    private Label statusText;

    private final DataLoader dataLoader = new DataLoader(DatabaseConnectionProvider.getProductionConnection());
    private final DataExtractor dataExtractor = new DataExtractor(PathConstants.UploadDir.label);

    public SantaTripsController() throws SQLException {
    }


    @FXML
    protected void onHelloButtonClick() throws SQLException {
        statusText.setText("Searching for trip files in upload directory.");
        CSVHandler csvHandler = new CSVHandler(PathConstants.UploadDir.label);

        Set<String>foundFiles = csvHandler.getAllFilesInUploadDir();
        statusText.setText("Found %d trip files!".formatted(foundFiles.size()));

        long lastTripId = dataLoader.getLastTripId();

        for (String file : foundFiles) {
            // extract
            Set<Currency> foundCurrencies = dataExtractor.getUniqueCurrenciesFromFile(file);
            Country country = dataExtractor.getCountryNameFromFile(file);
            List<City> foundCities = dataExtractor.getCityDataFromFile(file, country);
            List<Trip> foundTrips = dataExtractor.getTripDataFromFile(file);

            // load
            dataLoader.loadCountryData(country);
            dataLoader.loadBulkCurrencyData(foundCurrencies.stream().toList());
            dataLoader.loadCityData(foundCities);
            dataLoader.loadTripData(foundTrips);
        }

        statusText.setText("Data successfully loaded!");
    }

}