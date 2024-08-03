package com.example.evolvatestmarijanbebek.models.DAO;

import com.example.evolvatestmarijanbebek.models.mappings.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityDao implements Dao<City> {

    private final Connection conn;

    public CityDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<City> get(long id) throws SQLException {
        String query = ("""
                SELECT ci.id, ci.CityName, coun.CountryName \

                FROM City ci \

                INNER JOIN Country coun on ci.CountryId = coun.id\

                WHERE id = ?""");

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return Optional.of(new City(
                        resultSet.getLong("id"),
                        resultSet.getString("CityName"),
                        resultSet.getString("CountryName")
                )
        );
    }

    @Override
    public List<City> getAll() throws SQLException {
        List<City> cityList = new ArrayList<>();
        String query = ("""
                 SELECT ci.id, ci.CityName, coun.CountryName \

                 FROM City ci \

                 INNER JOIN Country coun on ci.CountryId = coun.id\
                """);

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            City city = new City(
                    resultSet.getLong("id"),
                    resultSet.getString("CityName"),
                    resultSet.getString("CountryName")
            );
            cityList.add(city);
        }
        return cityList;
    }

    /**
     * Since the models and database tables aren't mapped 1:1 for simplicity’s sake,
     * we can let the database do the heavy work by relegating the CountryName = CountryId mapping to it.
     *
     * The method simply calls the "insert_city" procedure, which does the following:
     *  * take city name, country name as parameters
     *  * find the country id by country name
     *  * insert new row into City table
     *
     * */
    @Override
    public void save(City city) throws SQLException {
        String query = "CALL insert_city(?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, city.getCityName());
        preparedStatement.setString(2, city.getCountryName());

        preparedStatement.executeQuery();
    }

}
