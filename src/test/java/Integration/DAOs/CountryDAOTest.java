package Integration.DAOs;

import com.example.evolvatestmarijanbebek.models.DAO.CountryDao;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryDAOTest extends BaseDAOTest {

    private final CountryDao countryDao = new CountryDao(connection);

    @Test
    @Order(1)
    public void testGetAll() throws SQLException {
        List<Country> countryList = countryDao.getAll();
        List<Country> expected = Arrays.asList(
                new Country(1L, "United States"),
                new Country(2L, "Germany"),
                new Country(3L, "United Kingdom")
        );

        assertEquals(expected, countryList, "The queried Country data should match the expected array");
    }

    @Test
    @Order(2)
    public void testGet() throws SQLException {
        Country expectedCountry = new Country(1L, "United States");
        Optional<Country> queriedCountry = countryDao.get(1L);

        assertEquals(expectedCountry, queriedCountry.orElse(null));
    }

    @Test
    @Order(3)
    public void testInsert() throws SQLException {
        Country newCountry = new Country(4L, "Panama");
        countryDao.save(newCountry);

        assertEquals(newCountry, countryDao.get(newCountry.getId()).orElse(null));
    }

    @Test
    @Order(4)
    public void testBulkInsert() throws SQLException {
        List<Country> countryList = Arrays.asList(
                new Country(5L, "Slovenia"),
                new Country(6L, "Hungary"),
                new Country(7L, "Montenegro")
        );
        countryDao.bulkSave(countryList);

        assertEquals(new Country(6L, "Hungary"), countryDao.get(6L).orElse(null));
    }


}
