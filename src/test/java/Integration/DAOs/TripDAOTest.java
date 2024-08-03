package Integration.DAOs;

import com.example.evolvatestmarijanbebek.models.DAO.TripDao;
import com.example.evolvatestmarijanbebek.models.mappings.Trip;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripDAOTest extends BaseDAOTest {
    private final TripDao tripDao = new TripDao(connection);

    @Test
    @Order(1)
    public void testGetAll() throws SQLException {
        List<Trip> tripList = tripDao.getAll();

        List<Trip> expected = Arrays.asList(
                new Trip(1L, "USD", "New York", 1000),
                new Trip(2L, "EUR", "Berlin", 2000),
                new Trip(3L, "GBP", "London", 3000)
        );

        assertEquals(expected, tripList, "The queried Trip data should match the expected array");
    }

    @Test
    @Order(2)
    public void testGet() throws SQLException {
        Trip expectedTrip = new Trip(1L, "USD", "New York", 1000);
        Optional<Trip> queriedTrip = tripDao.get(expectedTrip.getId());

        assertEquals(expectedTrip, queriedTrip.orElse(null));
    }

    @Test
    @Order(3)
    public void testInsert() throws SQLException {
        Trip newTrip = new Trip(4L, "USD", "New York", 2000);
        tripDao.save(newTrip);

        assertEquals(newTrip, tripDao.get(newTrip.getId()).orElse(null));
    }
}
