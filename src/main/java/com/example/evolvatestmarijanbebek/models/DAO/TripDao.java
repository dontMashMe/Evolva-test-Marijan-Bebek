package com.example.evolvatestmarijanbebek.models.DAO;

import com.example.evolvatestmarijanbebek.models.mappings.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TripDao implements Dao<Trip> {

    private final Connection conn;

    public TripDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Trip> get(long id) throws SQLException {
        String query = """
                SELECT t.id, curr.CurrencyName, ci.CityName, t.SavedAmount\

                FROM Trip t\

                INNER JOIN Currency curr on t.currencyId = curr.id\

                INNER JOIN City ci on t.CityId = ci.id \
                
                WHERE t.id = ?""";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return Optional.of(new Trip(
                        resultSet.getLong("id"),
                        resultSet.getString("CurrencyName"),
                        resultSet.getString("CityName"),
                        resultSet.getInt("SavedAmount")
                )
        );
    }

    @Override
    public List<Trip> getAll() throws SQLException {
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

    /**
     * Trip model and Trip table aren't mapped 1:1 for simplicity's sake.
     * The job of mapping the Currency name to currency id, and city name to city id is relegated to the database, through
     * the call of a stored procedure.
     * <br>
     * insert_trip procedure does the following:
     * <ul>
     *  <li> Take currency name, city name and saved amount as params.</li>
     *  <li> Retrieve currency id & city id through the names.</li>
     *  <li> Insert new row into Trip table.</li>
     *</ul>
     * */
    @Override
    public void save(Trip trip) throws SQLException {
        String query = "CALL insert_trip(?, ?, ?)";

        CallableStatement callableStatement = conn.prepareCall(query);
        callableStatement.setString(1, trip.getCurrencyName());
        callableStatement.setString(2, trip.getCityName());
        callableStatement.setInt(3, trip.getSavedAmount());

        callableStatement.executeUpdate();
    }

    public long getLastKnownId() throws SQLException {
        String query = """
                SELECT id \

                FROM Trip \

                ORDER BY id DESC \

                LIMIT 1""";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();


        if (resultSet.next()) {
            return resultSet.getLong("id");
        } else {
            return -1;
        }
    }
}
