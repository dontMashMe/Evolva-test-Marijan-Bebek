package Unit;

import com.example.evolvatestmarijanbebek.models.mappings.City;
import com.example.evolvatestmarijanbebek.models.mappings.Country;
import com.example.evolvatestmarijanbebek.models.mappings.Currency;
import com.example.evolvatestmarijanbebek.models.mappings.Trip;
import com.example.evolvatestmarijanbebek.services.DataExtractor;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataExtractorTest {
    private final DataExtractor dataExtractor = new DataExtractor(PathConstants.TestUploadDir.label);

    @Test
    public void testGetCountryNameFromFile() {              // also tests correct capitalization
        Country expectedCountry = new Country(1L, "Lobotomized");

        String[] sampleCSVParts = PathConstants.SampleCSVFile.label.split("/");
        String sampleCSVFileName = sampleCSVParts[sampleCSVParts.length - 1];

        Country importedCountry = dataExtractor.getCountryNameFromFile(sampleCSVFileName);

        assertEquals(expectedCountry, importedCountry);
    }

    @Test
    public void testGetUniqueCurrenciesFromFile() {
        Set<Currency> expectedCurrencies = Set.of(
                new Currency(1L, "EUR"),
                new Currency(1L, "USD"),
                new Currency(1L, "HRK")
        );

        Set<Currency> importedCurrencies = dataExtractor.getUniqueCurrenciesFromFile(PathConstants.SampleCSVFile.label);

        assertEquals(expectedCurrencies, importedCurrencies);
    }

    @Test
    public void testGetCityDataFromFile() {
        List<City> expectedCities = Arrays.asList(
                new City(1L, "Zagreb", "Lobotomized"),
                new City(1L, "Osijek", "Lobotomized"),
                new City(1L, "Rijeka", "Lobotomized"),
                new City(1L, "Split", "Lobotomized"),
                new City(1L, "Kutjevo", "Lobotomized")
        );


        List<City> importedCities = dataExtractor.getCityDataFromFile(
                PathConstants.SampleCSVFile.label, new Country(1L, "Lobotomized")
        );

        assertEquals(expectedCities, importedCities);
    }

    @Test
    public void testGetTripDataFromFile() {
        List<Trip> expectedTrips = Arrays.asList(
                new Trip(1L, "EUR", "Zagreb", 1),
                new Trip(1L, "EUR", "Osijek", 2),
                new Trip(1L, "EUR", "Rijeka", 4),
                new Trip(1L, "USD", "Split", 5),
                new Trip(1L, "HRK", "Kutjevo", 15)
        );

        List<Trip> importedTrips = dataExtractor.getTripDataFromFile(PathConstants.SampleCSVFile.label);

        assertEquals(expectedTrips, importedTrips);
    }


}
