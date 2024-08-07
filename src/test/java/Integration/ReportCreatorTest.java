package Integration;

import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.services.ReportCreator;
import com.example.evolvatestmarijanbebek.services.database.DatabaseConnectionProvider;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReportCreatorTest {
    private static Connection connection;

    private static ReportCreator reportCreator;

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

        reportCreator = new ReportCreator(connection);
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

    @Test
    public void testGenerateNewTripsReport() throws SQLException {
        long lastTripId = 2L;
        Country country = new Country(1L, "Germany");


        System.out.println(reportCreator.generateNewTripsReport(lastTripId, country));
    }

    @Test
    public void testGenerateAllTripsReport() throws SQLException {
        String expected = """
                "germany.csv" found
                     Totals by currencies:
                         EUR: 7000
                """;

        Country country = new Country(1L, "Germany");

        assertEquals(reportCreator.generateTotalTripsReport(country), expected);
    }

}
