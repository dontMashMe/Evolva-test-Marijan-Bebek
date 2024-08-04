package com.example.evolvatestmarijanbebek.models.DAO;

import com.example.evolvatestmarijanbebek.models.mappings.City;

import java.sql.*;
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

                WHERE ci.id = ?""");

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

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
     * <br>
     * The method simply calls the "insert_city" procedure, which does the following:
     * <ul>
     *  <li> Take city name, country name as parameters.</li>
     *  <li> Find the country id by country name.</li>
     *  <li> Insert new row into City table.</li>
     *</ul>
     * */
    @Override
    public void save(City city) throws SQLException {
        String query = "CALL insert_city(?, ?)";
        CallableStatement callableStatement = conn.prepareCall(query);
        callableStatement.setString(1, city.getCityName());
        callableStatement.setString(2, city.getCountryName());

        callableStatement.executeUpdate();
    }

}
