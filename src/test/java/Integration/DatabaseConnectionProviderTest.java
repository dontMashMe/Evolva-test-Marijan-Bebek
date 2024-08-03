package Integration;

import com.example.evolvatestmarijanbebek.services.DatabaseConnectionProvider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionProviderTest {
    private static Connection connection;

    @BeforeAll
    public static void setup() {
        try {
            connection = DatabaseConnectionProvider.getConnection();
        } catch (SQLException e) {
            fail("Filed to establish a connection " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testConnectionNotNull() {
        assertNotNull(connection, "Connection should not be null");
    }

    @Test
    public void testConnectionValid() {
        try {
            assertTrue(connection.isValid(2), "Connection should be valid");
        } catch (SQLException e) {
            fail("Failed to check if connection is valid: " + e.getMessage());
        }
    }
}
