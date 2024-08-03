package com.example.evolvatestmarijanbebek.models.DAO;

import com.example.evolvatestmarijanbebek.models.mappings.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryDao implements Dao<Country> {

    private final Connection conn;

    public CountryDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Country> get(long id) throws SQLException {
        String query = "SELECT * FROM Country WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return Optional.of(
                new Country(resultSet.getLong("ID"), resultSet.getString("CountryName"))
        );
    }

    @Override
    public List<Country> getAll() throws SQLException {
        List<Country> allCountries = new ArrayList<>();
        String query = "SELECT * FROM Currency;";
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

    @Override
    public void save(Country country) throws SQLException {
        String query = "INSERT INTO Country(CountryName) VALUES (?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, country.getCountryName());

        preparedStatement.executeQuery();
    }

    /* Not needed right now.
    @Override
    public void delete(Country country) throws SQLException {
        String query = "DELETE FROM Country WHERE id = %d".formatted(country.getId());
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.executeQuery();
    }
     */
}
