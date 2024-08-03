package Integration.DAOs;

import com.example.evolvatestmarijanbebek.models.DAO.CityDao;
import com.example.evolvatestmarijanbebek.models.mappings.City;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityDAOTest extends BaseDAOTest {
    private final CityDao cityDao = new CityDao(connection);

    @Test
    @Order(1)
    public void testGetAll() throws SQLException {
        List<City> cityList = cityDao.getAll();

        List<City> expected = Arrays.asList(
                new City(1L, "New York", "United States"),
                new City(2L, "Berlin", "Germany"),
                new City(3L, "London", "United Kingdom")
        );

        assertEquals(expected, cityList, "The queried City data should match the expected array");
    }

    @Test
    @Order(2)
    public void testGet() throws SQLException {
        City expectedCity = new City(1L, "New York", "United States");
        Optional<City> queriedCity = cityDao.get(expectedCity.getId());

        assertEquals(expectedCity, queriedCity.orElse(null));
    }

    @Test
    @Order(3)
    public void testInsert() throws SQLException {
        City newCity = new City(4L, "Washington D.C", "United States");
        cityDao.save(newCity);

        assertEquals(newCity, cityDao.get(newCity.getId()).orElse(null));
    }

}
