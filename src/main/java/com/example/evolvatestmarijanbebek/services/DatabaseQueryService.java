package com.example.evolvatestmarijanbebek.services;

import com.example.evolvatestmarijanbebek.models.City;
import com.example.evolvatestmarijanbebek.models.Country;
import com.example.evolvatestmarijanbebek.models.Currency;
import com.example.evolvatestmarijanbebek.models.Trip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private static final Connection conn;

    static {
        try {
            conn = DatabaseConnectionProvider.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Trip> getAllTrips() throws SQLException {
        List<Trip> allTrips = new ArrayList<>();
        String query = """
                SELECT t.id, curr.CurrencyName, ci.CityName, t.SavedAmount\

                FROM Trip t\

                INNER JOIN Currency curr on t.currencyId = curr.id\

                INNER JOIN City ci on t.CityId = ci.id;""";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Trip trip = new Trip(
                    resultSet.getLong("id"),
                    resultSet.getString("CurrencyName"),
                    resultSet.getString("CityName"),
                    resultSet.getInt("SavedAmount")
            );
            allTrips.add(trip);
        }
        return allTrips;
    }

    public List<Currency> getAllCurrencies() throws SQLException {
        List<Currency> allCurrencies = new ArrayList<>();
        String query = "SELECT * FROM Currency;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Currency currency = new Currency(
                    resultSet.getLong("id"),
                    resultSet.getString("CurrencyName")
            );
            allCurrencies.add(currency);
        }
        return allCurrencies;
    }

    public List<Country> getAllCountries() throws SQLException {
        List<Country> allCountries = new ArrayList<>();
        String query = "SELECT * FROM Country;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Country country = new Country(
                    resultSet.getLong("id"),
                    resultSet.getString("CountryName")
            );
            allCountries.add(country);
        }
        return allCountries;
    }

    public List<City> getAllCities() throws SQLException {
        List<City> allCities = new ArrayList<>();
        String query = """
                SELECT ci.id, ci.CityName, coun.CountryName\

                FROM City ci\

                INNER JOIN Country coun on ci.countryid = coun.id;""";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            City city = new City(
                    resultSet.getLong("id"),
                    resultSet.getString("CityName"),
                    resultSet.getString("CountryName")

            );
            allCities.add(city);
        }
        return allCities;
    }

    /**
     *
     * TODO:
     *
     * SELECT curr.CurrencyName, SUM(t.SavedAmount) AS TotalSaved
     * FROM Trip t
     * INNER JOIN Currency curr ON t.CurrencyID = curr.ID
     * INNER JOIN City ci ON t.CityID = ci.ID
     * INNER JOIN Country cun ON ci.CountryID = cun.ID
     * WHERE cun.CountryName = 'United Kingdom'
     * GROUP BY curr.CurrencyName
     * ORDER BY TotalSaved;
     *
     *
     * */
}
