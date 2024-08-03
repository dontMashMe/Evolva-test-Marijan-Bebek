package Integration.DAOs;

import com.example.evolvatestmarijanbebek.models.DAO.CurrencyDao;
import com.example.evolvatestmarijanbebek.models.mappings.Currency;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyDAOTest extends BaseDAOTest {
    private final CurrencyDao currencyDao = new CurrencyDao(connection);

    @Test
    @Order(1)
    public void testGetAllCurrencies() throws SQLException {
        List<Currency> currencyList = currencyDao.getAll();

        List<Currency> expected = Arrays.asList(
                new Currency(1L, "USD"),
                new Currency(2L, "EUR"),
                new Currency(3L, "GBP")
        );

        assertEquals(expected, currencyList, "The queried Currency data should match the expected array");
    }

    @Test
    @Order(2)
    public void testGetSpecificCurrency() throws SQLException {
        Currency expectedCurrency = new Currency(1L, "USD");
        Optional<Currency> queriedCurrency = currencyDao.get(expectedCurrency.getId());

        assertEquals(expectedCurrency, queriedCurrency.orElse(null));
    }

    @Test
    @Order(3)
    public void testInsert() throws SQLException {
        Currency newCurrency = new Currency(4L, "YEN");
        currencyDao.save(newCurrency);

        assertEquals(newCurrency, currencyDao.get(newCurrency.getId()).orElse(null));
    }
}
