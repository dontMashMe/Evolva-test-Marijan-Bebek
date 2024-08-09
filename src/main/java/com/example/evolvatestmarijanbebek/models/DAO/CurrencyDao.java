package com.example.evolvatestmarijanbebek.models.DAO;

import com.example.evolvatestmarijanbebek.models.mappings.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<Currency> {

    private final Connection conn;

    public CurrencyDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Currency> get(long id) throws SQLException {
        String query = "SELECT * FROM Currency WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return Optional.of(
                new Currency(resultSet.getLong("ID"), resultSet.getString("CurrencyName"))
        );
    }

    @Override
    public List<Currency> getAll() throws SQLException {
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

    @Override
    public void save(Currency currency) throws SQLException {
        String query = """
                        INSERT INTO Currency(CurrencyName) VALUES (?) \
                        ON CONFLICT (CurrencyName) DO NOTHING
                        """;
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, currency.getCurrencyName());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void bulkSave(List<Currency> currencies) throws SQLException {
        String query = "INSERT INTO Currency(CurrencyName) VALUES (?) ON CONFLICT (CurrencyName) DO NOTHING";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        for (Currency currency : currencies) {
            preparedStatement.setString(1, currency.getCurrencyName());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        preparedStatement.close();
    }

}
