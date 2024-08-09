package com.example.evolvatestmarijanbebek.services;

import com.example.evolvatestmarijanbebek.models.DAO.CountryDao;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.models.reports.TripDataReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportCreator {

    private final Connection connection;

    public ReportCreator(Connection connection) {
        this.connection = connection;
    }

    public String generateNewTripsReport(long lastTripId, Country country) throws SQLException {
        String query = """
                SELECT curr.CurrencyName, SUM(t.SavedAmount) AS TotalSaved \
                FROM Trip t \
                INNER JOIN Currency curr ON t.CurrencyID = curr.ID \
                INNER JOIN City ci ON t.CityID = ci.ID \
                INNER JOIN Country cun ON ci.CountryID = cun.ID \
                WHERE cun.CountryName = ? AND t.ID > ? \
                GROUP BY curr.CurrencyName \
                ORDER BY TotalSaved;
                """;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, country.getCountryName());
        preparedStatement.setLong(2, lastTripId);
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<String, Integer> reportData = new HashMap<>();
        while (resultSet.next()) {
            reportData.put(
                    resultSet.getString("CurrencyName"),
                    resultSet.getInt("TotalSaved")
            );
        }

        return new TripDataReport(country.getCountryName(), reportData).getFormattedReport();
    }

    public String generateTotalTripsReport() throws SQLException {
        List<Country> allCountriesList = new CountryDao(connection).getAll();
        StringBuilder report = new StringBuilder();

        for (Country country : allCountriesList) {
            String query = """
                SELECT curr.CurrencyName, SUM(t.SavedAmount) AS TotalSaved
                FROM Trip t \
                INNER JOIN Currency curr ON t.CurrencyID = curr.ID \
                INNER JOIN City ci ON t.CityID = ci.ID \
                INNER JOIN Country cun ON ci.CountryID = cun.ID \
                WHERE cun.CountryName = ? \
                GROUP BY curr.CurrencyName \
                ORDER BY TotalSaved;
                """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getCountryName());
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<String, Integer> reportData = new HashMap<>();
            while (resultSet.next()) {
                reportData.put(
                        resultSet.getString("CurrencyName"),
                        resultSet.getInt("TotalSaved")
                );
            }

            report.append(new TripDataReport(country.getCountryName(), reportData).getFormattedReport());
        }

        return report.toString();
    }
}
