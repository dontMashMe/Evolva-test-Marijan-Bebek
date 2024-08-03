package Integration.DAOs;

import com.example.evolvatestmarijanbebek.services.DatabaseConnectionProvider;

import com.example.evolvatestmarijanbebek.utils.PathConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * Abstract class which implements the common setup & cleanup operations.
 */
public abstract class BaseDAOTest {
    protected static Connection connection;

    private static void getConnection() {
        try {
            connection = DatabaseConnectionProvider.getTestConnection();
        } catch (SQLException e) {
            fail("Filed to establish a connection " + e.getMessage());
        }
    }

    private static void runInitScripts() throws SQLException {
        StringBuilder sql = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(PathConstants.InitTestDatabaseScript.label))) {
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Statement statement = connection.createStatement();
        statement.execute(sql.toString());
    }

    @BeforeAll
    public static void setup() throws SQLException {
        getConnection();
        runInitScripts();
    }

    private static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void cleanup() throws SQLException {
        StringBuilder sql = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(PathConstants.CleanupTestDatabaseScript.label))) {
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Statement statement = connection.createStatement();
        statement.execute(sql.toString());
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        cleanup();
        closeConnection();
    }

    protected abstract void testGet() throws SQLException;

    protected abstract void testGetAll() throws SQLException;

    protected abstract void testInsert() throws SQLException;
}
