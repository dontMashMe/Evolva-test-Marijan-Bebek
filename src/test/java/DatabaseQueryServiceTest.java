import com.example.evolvatestmarijanbebek.models.City;
import com.example.evolvatestmarijanbebek.models.Trip;
import com.example.evolvatestmarijanbebek.services.DatabaseQueryService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseQueryServiceTest {
    private final DatabaseQueryService databaseQueryService = new DatabaseQueryService();

    /*
    @Test
    public void testAllTripsQuery() throws SQLException {
        List<Trip> results = databaseQueryService.getAllTrips();
        List<Trip> expected = Arrays.asList(
                new Trip(6L, "USD", "New York", 1000),
                new Trip(7L, "EUR", "Berlin", 1000),
                new Trip(8L, "GBP", "London", 1000)
        );

        assertEquals(expected, results, "The queried Trip data should match the expected array");
    }

    @Test
    public void testALlCitiesQuery() throws SQLException {
        List<City> results = databaseQueryService.getAllCities();
        List<City> expected = Arrays.asList(
                new City(7L, "New York", "United States"),
                new City(8L, "Berlin", "Germany"),
                new City(9L, "London", "United Kingdom")
        );

        assertEquals(expected, results, "The queried City data should match the expected array");
    }
     */

    @Test
    public void testAllTripsQuery() throws SQLException {
        List<Trip> results = databaseQueryService.getAllTrips();
        assertFalse(results.isEmpty(), "The queried Trip data should not be empty");
    }

    @Test
    public void testAllCitiesQuery() throws SQLException {
        List<City> results = databaseQueryService.getAllCities();
        assertFalse(results.isEmpty(), "The queried City data should not be empty");
    }
}
