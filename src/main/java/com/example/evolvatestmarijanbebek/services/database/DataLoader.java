package com.example.evolvatestmarijanbebek.services.database;

import com.example.evolvatestmarijanbebek.models.DAO.CityDao;
import com.example.evolvatestmarijanbebek.models.DAO.CountryDao;
import com.example.evolvatestmarijanbebek.models.DAO.CurrencyDao;
import com.example.evolvatestmarijanbebek.models.DAO.TripDao;
import com.example.evolvatestmarijanbebek.models.mappings.City;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.models.mappings.Currency;
import com.example.evolvatestmarijanbebek.models.mappings.Trip;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataLoader {
    private final Connection connection;

    public DataLoader(Connection connection) {
        this.connection = connection;
    }

    public void loadBulkCurrencyData(List<Currency> currencies) throws SQLException {
        CurrencyDao currencyDao = new CurrencyDao(connection);
        currencyDao.bulkSave(currencies);
    }

    public void loadCountryData(Country country) throws SQLException {
        CountryDao countryDao = new CountryDao(connection);
        countryDao.save(country);
    }

    public void loadCityData(List<City> cities) throws SQLException {
        CityDao cityDao = new CityDao(connection);
        for (City city : cities) {
            cityDao.save(city);
        }
    }

    public void loadTripData(List<Trip> trips) throws SQLException {
        TripDao tripDao = new TripDao(connection);
        for (Trip trip : trips) {
            tripDao.save(trip);
        }
    }

    public long getLastTripId() throws SQLException {
        TripDao tripDao = new TripDao(connection);
        return tripDao.getLastKnownId();
    }
}
