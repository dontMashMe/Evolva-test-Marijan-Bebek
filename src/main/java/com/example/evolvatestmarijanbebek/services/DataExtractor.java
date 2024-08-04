package com.example.evolvatestmarijanbebek.services;

import com.example.evolvatestmarijanbebek.models.mappings.City;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.models.mappings.Currency;
import com.example.evolvatestmarijanbebek.models.mappings.Trip;
import com.example.evolvatestmarijanbebek.utils.PathConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DataExtractor {
    private final CSVHandler csvHandler = new CSVHandler(PathConstants.UploadDir.label);

    public Country getCountryNameFromFile(String file) {
        String countryName = file.split("\\.")[0]; // remove .csv
        String capitalizedCountryName = "%s%s".formatted(countryName.substring(0, 1).toUpperCase(),
                countryName.substring(1)); // things like the USA require more handling
        // the ID is irrelevant, since db table has auto increment ID
        return new Country(1L, capitalizedCountryName);
    }

    public Set<Currency> getUniqueCurrenciesFromFile(String file) {
        List<Currency> currencyList = new ArrayList<>();
        List<List<String>> loadedData = csvHandler.loadCsv(file);

        for (List<String> line : loadedData) {
            String currency = line.get(1);
            currencyList.add(new Currency(1L, currency));
        }

        return new HashSet<>(currencyList); // turn list into Set to remove duplicates
    }

    public List<City> getCityDataFromFile(String file, Country country) {
        List<City> cityList = new ArrayList<>();
        List<List<String>> loadedData = csvHandler.loadCsv(file);

        for (List<String> line : loadedData) {
            String cityName = line.getFirst();
            cityList.add(new City(1L, cityName, country.getCountryName()));
        }

        return cityList;
    }

    public List<Trip> getTripDataFromFile(String file) {
        List<Trip> tripList = new ArrayList<>();
        List<List<String>> loadedData = csvHandler.loadCsv(file);

        for (List<String> line : loadedData) {
            String cityName = line.getFirst();
            String currencyName = line.get(1);
            int savedAmount = Integer.parseInt(line.getLast());
            tripList.add(new Trip(1L, currencyName, cityName, savedAmount));
        }

        return tripList;
    }

}
