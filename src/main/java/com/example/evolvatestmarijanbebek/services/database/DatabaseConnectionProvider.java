package com.example.evolvatestmarijanbebek.services.database;

import com.example.evolvatestmarijanbebek.utils.PathConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionProvider {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Files.newInputStream(
                Paths.get(PathConstants.DBProperties.label)
        )) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load properties file: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize properties", e);
        }
    }

    public static Connection getProductionConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/postgres";
        return DriverManager.getConnection(url, properties);
    }

    public static Connection getTestConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/testdb";
        return DriverManager.getConnection(url, properties);
    }
}
